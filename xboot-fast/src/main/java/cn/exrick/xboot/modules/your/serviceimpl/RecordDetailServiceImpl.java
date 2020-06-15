package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.dao.RecordDetailDao;
import cn.exrick.xboot.modules.your.dto.RecordFormDTO;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import cn.exrick.xboot.modules.your.entity.Template;
import cn.exrick.xboot.modules.your.service.RecordDetailService;
import cn.exrick.xboot.modules.your.service.RecordService;
import cn.exrick.xboot.modules.your.service.TemplateService;
import cn.exrick.xboot.modules.your.service.TypeService;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public void addRecordDetails(RecordFormDTO recordFormDTO) {
        String recordDetails = recordFormDTO.getRecordDetails();
        String taskId = recordFormDTO.getTaskId();
        recordDetails = recordDetails.substring(0, recordDetails.length() - 1);
        List<RecordDetail> recordDetailList = new ArrayList<>();
        String[] one = recordDetails.split("\\|");
        for (String detail : one) {
            String[] id = detail.split("_");
            if (id.length > 1) {
                RecordDetail recordDetail = new RecordDetail();
                recordDetail.setTemplateId(id[0]);
                recordDetail.setTaskId(taskId);
                recordDetail.setScore(Double.parseDouble(id[1]));
                recordDetailList.add(recordDetail);
            }
        }
        //写入表单明细
        recordDetailList.forEach(o -> {
            entityUtil.initEntity(o);
            Template template = templateService.get(o.getTemplateId());
            o.setTaskId(taskId);
            o.setTemplateId(template.getId());
            o.setTemplateTitle(template.getTitle());
            o.setTypeId(template.getTypeId());
            o.setTypeTitle(typeService.get(template.getTypeId()).getTitle());
            o.setCreateDepartmentId(securityUtil.getCurrUser().getDepartmentId());
            save(o);
        });
        //写入统计
        Double sum = recordDetailList.stream().mapToDouble(RecordDetail::getScore).sum();
        Record record =new Record();
        BeanUtils.copyProperties(recordFormDTO,record);
        entityUtil.initEntity(record);
        record.setCreateDepartmentId(securityUtil.getCurrUser().getDepartmentId());
        record.setScore(sum);
        record.setJoinTime(DateTime.now().toString());
        recordService.save(record);
    }

    @Override
    public void updateRecordDetail(List<RecordDetail> list) {
        String recordId = null;
        for (RecordDetail o : list) {
            RecordDetail recordDetail = recordDetailDao.getOne(o.getId());
            recordId = recordDetail.getRecordId();
            recordDetail.setScore(o.getScore());
            save(recordDetail);
        }
        List<RecordDetail> recordDetails = findByRecordId(recordId);
        Double sum = recordDetails.stream().mapToDouble(RecordDetail::getScore).sum();
        Record record = recordService.get(recordId);
        record.setScore(sum);
        recordService.update(record);
    }

}