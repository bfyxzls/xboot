package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.service.DepartmentService;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.dao.RecordDetailDao;
import cn.exrick.xboot.modules.your.dto.RecordDetailDTO;
import cn.exrick.xboot.modules.your.dto.RecordFormDTO;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import cn.exrick.xboot.modules.your.entity.Template;
import cn.exrick.xboot.modules.your.service.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 小区接口实现
 *
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class RecordDetailServiceImpl implements RecordDetailService {

    @Autowired
    RecordService recordService;
    @Autowired
    TemplateService templateService;
    @Autowired
    TypeService typeService;
    @Autowired
    EntityUtil entityUtil;
    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    CourtService courtService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    private RecordDetailDao recordDetailDao;

    @Override
    public RecordDetailDao getRepository() {
        return recordDetailDao;
    }

    @Override
    public Page<RecordDetail> findByCondition(RecordDetail court, SearchVo searchVo, Pageable pageable) {

        return recordDetailDao.findAll(new Specification<RecordDetail>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<RecordDetail> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");
                Path<String> titleField = root.get("title");
                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StringUtils.isNotBlank(court.getTemplateTitle())) {
                    list.add(cb.like(titleField, court.getTemplateTitle().trim() + "%"));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<RecordDetail> findByRecordId(String recordId) {

        return recordDetailDao.findAll(new Specification<RecordDetail>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<RecordDetail> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<String> titleField = root.get("recordId");
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(titleField, recordId.trim()));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
    }

    @Override
    public void addRecordDetails(RecordFormDTO recordFormDTO, Boolean isAudit) {
        log.info("addRecordDetails:{}", recordFormDTO);
        String taskId = recordFormDTO.getTaskId();
        String recordId = null;
        List<RecordDetail> recordDetailList = new ArrayList<>();
        for (RecordDetailDTO detail : recordFormDTO.getJsonRecordDetails()) {
            Template template = templateService.get(detail.getTemplateId());
            if (template == null) {
                throw new XbootException("template不存在");
            }
            RecordDetail recordDetail = new RecordDetail();
            if (detail.getRecordDetailId() != null) {
                recordDetail = recordDetailDao.getOne(detail.getRecordDetailId());//更新现有的
            }
            recordId = recordDetail.getRecordId();
            recordDetail.setTemplateId(detail.getTemplateId());
            recordDetail.setTaskId(taskId);
            recordDetail.setTypeId(template.getTypeId());
            if (detail.getScore() != null) {
                recordDetail.setScore(detail.getScore());
            } else {
                recordDetail.setScore(0d);
            }
            if (detail.getContent() != null) {
                recordDetail.setContent(detail.getContent());
            }
            if (detail.getTextValue() != null) {
                recordDetail.setTextValue(detail.getTextValue());
            }
            if (detail.getPictureUrl() != null) {
                recordDetail.setPictureUrl(detail.getPictureUrl());
            }
            if (detail.getDateValue() != null) {
                recordDetail.setDateValue(detail.getDateValue());
            }
            recordDetailList.add(recordDetail);
        }

        //写入统计
        Double sum = recordDetailList.stream().mapToDouble(RecordDetail::getScore).sum();

        //新的记录
        Record record = new Record();
        entityUtil.initEntity(record);
        record.setCourtId(recordFormDTO.getCourtId());
        record.setTaskId(recordFormDTO.getTaskId());
        record.setTypeId(recordFormDTO.getTypeId());

        if (recordId != null) {
            record = recordService.get(recordId);
        }
        if (isAudit) {
            record.setStatus(1);
        }
        if (StringUtils.isNotBlank(record.getCourtId())) {
            Court court = courtService.get(record.getCourtId());
            if (court != null) {
                record.setDepartmentId(court.getDepartmentId());
            }
        }
        String deptId = securityUtil.getCurrUser().getDepartmentId();
        record.setDepartmentId(deptId);
        record.setDepartmentIds(departmentService.generateParentIdsString(deptId));
        record.setScore(sum);
        recordService.save(record);
        //写入表单明细
        for (RecordDetail o : recordDetailList) {
            entityUtil.initEntity(o);
            Template template = templateService.get(o.getTemplateId());
            o.setTaskId(taskId);
            o.setRecordId(record.getId());
            o.setTemplateId(template.getId());
            o.setTemplateTitle(template.getTitle());
            o.setTypeId(template.getTypeId());
            o.setTypeTitle(typeService.get(template.getTypeId()).getTitle());
            o.setCreateDepartmentId(securityUtil.getCurrUser().getDepartmentId());
            save(o);
        }
        ;

    }

    @Override
    public void updateRecordDetail(List<RecordDetail> list, Boolean isAudit) {
        String recordId = null;
        for (RecordDetail o : list) {
            RecordDetail recordDetail = recordDetailDao.getOne(o.getId());
            recordId = recordDetail.getRecordId();
            Template template = templateService.get(recordDetail.getTemplateId());
            if (template != null) {
                if (template.getScoreType() != null & template.getScoreType().equals(1)) {
                    recordDetail.setScore(o.getScore());
                } else {
                    recordDetail.setContent(o.getContent());
                }
            }
            save(recordDetail);
        }
        List<RecordDetail> recordDetails = findByRecordId(recordId);
        for (RecordDetail recordDetail : recordDetails) {
            if (recordDetail.getScore() == null) {
                recordDetail.setScore(0d);
            }
        }
        Double sum = recordDetails.stream().mapToDouble(RecordDetail::getScore).sum();
        Record record = recordService.get(recordId);
        //如果当前角色是审核员，就将记录状态改为已审核
        if (isAudit) {
            record.setStatus(1);
        }
        record.setScore(sum);
        recordService.update(record);
    }

}