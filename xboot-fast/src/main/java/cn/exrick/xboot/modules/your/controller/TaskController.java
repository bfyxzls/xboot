package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Task;
import cn.exrick.xboot.modules.your.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-任务管理")
@RequestMapping("/xboot/task")
@Transactional
public class TaskController extends XbootBaseController<Task, String> {

    @Autowired
    private TaskService taskService;

    @Override
    public TaskService getService() {
        return taskService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "后台管理-列表")
    public Result<Page<Task>> getByCondition(Task task,
                                             SearchVo searchVo,
                                             PageVo pageVo) {

        Page<Task> page = taskService.findByCondition(task, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Task>>().setData(page);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "前台显示已启用的列表")
    public Result<List<Task>> list() {

        Specification<Task> specification = (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<Integer> statusField = root.get("status");
            List<Predicate> list = new ArrayList<Predicate>();
            //状态
            list.add(criteriaBuilder.equal(statusField, 1));
            Predicate[] arr = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(arr));
            return null;
        };
        List<Task> page = taskService.findAll(specification);
        return new ResultUtil<List<Task>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加任务")
    public Result<Object> add(Task entity) {
        taskService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理任务")
    public Result<Object> edit(Task entity, @RequestParam String id) {
        Task old = taskService.get(id);
        old.setTitle(entity.getTitle());
        old.setDescription(entity.getDescription());
        old.setStatus(entity.getStatus());
        taskService.save(old);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除任务")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            taskService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
