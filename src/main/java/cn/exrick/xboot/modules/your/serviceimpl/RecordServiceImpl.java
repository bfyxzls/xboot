package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dao.RecordDao;
import cn.exrick.xboot.modules.your.dao.TaskTypeDao;
import cn.exrick.xboot.modules.your.dao.TypeDao;
import cn.exrick.xboot.modules.your.dto.CourtTotal;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.entity.TaskType;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.RecordService;
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
 * 评价记录接口实现
 *
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    TaskTypeDao taskTypeDao;
    @Autowired
    TypeDao typeDao;
    @Autowired
    private RecordDao recordDao;

    @Override
    public RecordDao getRepository() {
        return recordDao;
    }

    @Override
    public Page<Record> findByCondition(boolean isSelf, Record record, SearchVo searchVo, Pageable pageable) {

        return recordDao.findAll(new Specification<Record>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");
                Path<String> courtIdField = root.get("courtId");
                Path<Integer> statusField = root.get("status");
                Path<String> departmentIdsField = root.get("departmentIds");

                List<Predicate> list = new ArrayList<Predicate>();

                // 数据权限
                String currentDeptId = securityUtil.getCurrUser().getDepartmentId();
                list.add(cb.like(departmentIdsField, "%" + currentDeptId + "%"));
                list.add(cb.notEqual(courtIdField, ""));

                if (isSelf) {
                    list.add(cb.equal(root.get("createBy"), securityUtil.getCurrUser().getId()));
                }
                //创建时间F
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StringUtils.isNotBlank(record.getCourtId())) {
                    list.add(cb.equal(courtIdField, record.getCourtId()));
                }
                if (record.getStatus() != null) {
                    list.add(cb.equal(statusField, record.getStatus()));
                }
                if (StringUtils.isNotBlank(record.getDepartmentId())) {
                    list.add(cb.like(departmentIdsField, "%" + record.getDepartmentId() + "%"));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public CourtTotal getRecordCourtTotal(String courtId) {

        CourtTotal courtTotal = new CourtTotal();
        Long courtCount = recordDao.count(new Specification<Record>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("courtId"), courtId));
                CriteriaBuilder.In<Integer> in = cb.in(root.get("typeId"));
                Integer[] typeIds = new Integer[]{1, 2, 3};
                for (Integer id : typeIds) {
                    in.value(id);
                }
                list.add(in);
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });


        Type type = typeDao.findOne(new Specification<Type>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("title"), "业主评价表"));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }).orElse(new Type());
        TaskType taskType = taskTypeDao.findOne(new Specification<TaskType>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<TaskType> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("typeId"), type.getId()));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }).orElse(new TaskType());
        Integer limitCount = taskType.getLimitCount();
        Long createByCount = recordDao.count(new Specification<Record>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("courtId"), courtId));
                list.add(cb.equal(root.get("createBy"), securityUtil.getCurrUser().getId()));
                list.add(cb.equal(root.get("typeId"), 3));
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        });
        courtTotal.setCourtId(courtId);
        courtTotal.setTotalCount(courtCount);
        courtTotal.setSelfCount(createByCount);
        courtTotal.setPlanCount(limitCount);
        return courtTotal;
    }
}