package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.RunTask;
import cn.exrick.xboot.modules.your.service.RunTaskService;
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
@Api(description = "评价-运行中的任务")
@RequestMapping("/xboot/runTask")
@Transactional
public class RunTaskController extends XbootBaseController<RunTask, String> {

    @Autowired
    private RunTaskService runTaskService;

    @Override
    public RunTaskService getService() {
        return runTaskService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<RunTask>> getByCondition(RunTask runTask,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){
        pageVo.setSort("createTime");
        pageVo.setOrder("desc");
        Page<RunTask> page = runTaskService.findByCondition(runTask, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<RunTask>>().setData(page);
    }
}
