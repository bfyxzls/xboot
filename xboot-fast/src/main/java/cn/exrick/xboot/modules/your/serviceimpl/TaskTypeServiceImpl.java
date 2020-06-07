package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.modules.your.dao.TaskTypeDao;
import cn.exrick.xboot.modules.your.entity.TaskType;
import cn.exrick.xboot.modules.your.service.TaskTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * 任务与类型关系接口实现
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class TaskTypeServiceImpl implements TaskTypeService {

    @Autowired
    private TaskTypeDao taskTypeDao;

    @Override
    public TaskTypeDao getRepository() {
        return taskTypeDao;
    }

    @Override
    public Page<TaskType> findByCondition(TaskType taskType, SearchVo searchVo, Pageable pageable) {
        return taskTypeDao.findAll(new Specification<TaskType>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<TaskType> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

}