package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Report;
import cn.exrick.xboot.modules.your.service.ReportService;
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
@Api(description = "评价报告管理接口")
@RequestMapping("/xboot/report")
@Transactional
public class ReportController extends XbootBaseController<Report, String> {

    @Autowired
    private ReportService reportService;

    @Override
    public ReportService getService() {
        return reportService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Report>> getByCondition(Report report,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Report> page = reportService.findByCondition(report, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Report>>().setData(page);
    }
}
