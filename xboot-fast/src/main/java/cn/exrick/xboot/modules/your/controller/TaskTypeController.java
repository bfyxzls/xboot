package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.TaskType;
import cn.exrick.xboot.modules.your.service.TaskTypeService;
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
@Api(description = "任务与类型关系管理接口")
@RequestMapping("/xboot/taskType")
@Transactional
public class TaskTypeController extends XbootBaseController<TaskType, String> {

    @Autowired
    private TaskTypeService taskTypeService;

    @Override
    public TaskTypeService getService() {
        return taskTypeService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<TaskType>> getByCondition(TaskType taskType,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<TaskType> page = taskTypeService.findByCondition(taskType, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<TaskType>>().setData(page);
    }
}
