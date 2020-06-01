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
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "测试管理接口")
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
}
