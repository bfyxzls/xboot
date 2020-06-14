package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dao.TaskDao;
import cn.exrick.xboot.modules.your.dao.TaskTypeDao;
import cn.exrick.xboot.modules.your.dao.TypeDao;
import cn.exrick.xboot.modules.your.entity.Task;
import cn.exrick.xboot.modules.your.entity.TaskType;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.TaskTypeService;
import cn.hutool.core.collection.CollectionUtil;
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
import java.util.stream.Collectors;

/**
 * 任务与类型关系接口实现
 *
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class TaskTypeServiceImpl implements TaskTypeService {

    @Autowired
    private TaskTypeDao taskTypeDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TypeDao typeDao;

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

                Path<Date> createTimeField = root.get("createTime");
                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                List<String> taskIdArr = new ArrayList<>();
                List<String> typeIdArr = new ArrayList<>();
                if (StringUtils.isNotBlank(taskType.getTaskTitle())) {
                    taskIdArr = taskDao.findAll(new Specification<Task>() {
                        @Override
                        public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                            criteriaQuery.where(criteriaBuilder.like(root.get("title"), taskType.getTaskTitle().trim()+"%"));
                            return null;
                        }
                    }).stream().map(o -> o.getId()).collect(Collectors.toList());
                    CriteriaBuilder.In<String> in = cb.in(root.get("taskId"));
                    for (String id : taskIdArr) {
                        in.value(id);
                    }
                    list.add(in);
                }
                if (StringUtils.isNotBlank(taskType.getTypeTitle())) {
                    typeIdArr = typeDao.findAll(new Specification<Type>() {
                        @Override
                        public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                            Path<String> titleField = root.get("title");
                            List<Predicate> list = new ArrayList<>();
                            list.add(criteriaBuilder.like(titleField, taskType.getTypeTitle().trim()+"%"));
                            Predicate[] arr = new Predicate[list.size()];
                            criteriaQuery.where(list.toArray(arr));
                            return null;
                        }
                    }).stream().map(o -> o.getId()).collect(Collectors.toList());
                    CriteriaBuilder.In<String> in = cb.in(root.get("typeId"));
                    for (String id : typeIdArr) {
                        in.value(id);
                    }
                    list.add(in);
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

}