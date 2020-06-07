package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Task;
import cn.exrick.xboot.modules.your.entity.Tenement;
import cn.exrick.xboot.modules.your.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

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
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Task>> getByCondition(Task task,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Task> page = taskService.findByCondition(task, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Task>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加任务")
    public Result<Object> add(Task entity) {
        taskService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理任务")
    public Result<Object> edit(Task entity , @RequestParam String id) {
        Task old = taskService.get(id);
        old.setTitle(entity.getTitle());
        old.setDescription(entity.getDescription());
        old.setStatus(entity.getStatus());
        taskService.save(entity);
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
