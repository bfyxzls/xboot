package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.Role;
import cn.exrick.xboot.modules.base.service.RoleService;
import cn.exrick.xboot.modules.your.entity.TaskType;
import cn.exrick.xboot.modules.your.service.TaskService;
import cn.exrick.xboot.modules.your.service.TaskTypeService;
import cn.exrick.xboot.modules.your.service.TemplateService;
import cn.exrick.xboot.modules.your.service.TypeService;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-任务分类配置")
@RequestMapping("/xboot/taskType")
public class TaskTypeController extends XbootBaseController<TaskType, String> {

    @Autowired
    TaskService taskService;
    @Autowired
    TypeService typeService;
    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    RoleService roleService;
    @Autowired
    private TaskTypeService taskTypeService;

    @Override
    public TaskTypeService getService() {
        return taskTypeService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取配置")
    public Result<Page<TaskType>> getByCondition(TaskType taskType,
                                                 SearchVo searchVo,
                                                 PageVo pageVo) {
        pageVo.setSort("sortOrder");
        pageVo.setOrder("asc");
        Page<TaskType> page = taskTypeService.findByCondition(taskType, searchVo, PageUtil.initPage(pageVo));
        for (TaskType taskType1 : page) {
            taskType1.setTaskTitle(taskService.get(taskType1.getTaskId()).getTitle());
            taskType1.setTypeTitle(typeService.get(taskType1.getTypeId()).getTitle());
            if (StringUtils.isNotBlank(taskType1.getRoleIds())) {
                List<String> roleNames = new ArrayList<>();
                List<Role> roleList = new ArrayList<>();
                for (String roleId : taskType1.getRoleIds().split(",")) {
                    Role role = roleService.get(roleId);
                    roleNames.add(role.getName());
                    roleList.add(role);
                }
                taskType1.setRoles(roleList);
                taskType1.setRoleNames(String.join(",", roleNames));
            }
        }
        return new ResultUtil<Page<TaskType>>().setData(page);
    }

    @Autowired
    TemplateService templateService;

    @RequestMapping(value = "/getByRoles", method = RequestMethod.GET)
    @ApiOperation(value = "按着当前登陆人的角色返回列表,包括了评价模版")
    public Result<List<TaskType>> getListByRoles() {

        Specification<TaskType> s1 = new Specification<TaskType>() {
            @Override
            public Predicate toPredicate(Root<TaskType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("roleIds"), securityUtil.getCurrUser().getRoles().stream().map(o -> o.getId()));
                return criteriaBuilder.and(p1);
            }
        };
        List<TaskType> page = taskTypeService.findAll(s1);
        if (CollectionUtils.isEmpty(page)) {
            return new ResultUtil<List<TaskType>>().setData(null);
        }
        for (TaskType taskType1 : page) {
            taskType1.setTaskTitle(taskService.get(taskType1.getTaskId()).getTitle());
            taskType1.setTypeTitle(typeService.get(taskType1.getTypeId()).getTitle());
            taskType1.setTemplates(templateService.findByTypeIdOrderBySortOrder(taskType1.getTypeId()));
        }
        return new ResultUtil<List<TaskType>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加任务配置")
    public Result<Object> add(TaskType entity) {
        taskTypeService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理任务配置")
    public Result<Object> edit(TaskType entity, @RequestParam String id) {
        TaskType old = taskTypeService.get(id);
        old.setTaskId(entity.getTaskId());
        old.setTypeId(entity.getTypeId());
        old.setLimitCount(entity.getLimitCount());
        old.setPercent(entity.getPercent());
        old.setDescription(entity.getDescription());
        taskTypeService.save(entity);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除任务配置")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            taskTypeService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
