package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dao.CourtDao;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import cn.exrick.xboot.modules.your.service.CourtService;
import cn.exrick.xboot.modules.your.service.RecordDetailService;
import cn.exrick.xboot.modules.your.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-表单管理")
@RequestMapping("/xboot/court")
@Transactional
public class RecordDetailController extends XbootBaseController<RecordDetail, String> {

    @Autowired
    private RecordDetailService recordDetailService;

    @Override
    public RecordDetailService getService() {
        return recordDetailService;
    }



    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<RecordDetail>> getByCondition(RecordDetail recordDetail,
                                              SearchVo searchVo,
                                               PageVo pageVo) {

        Page<RecordDetail> page = recordDetailService.findByCondition(recordDetail, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<RecordDetail>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加表单")
    public Result<Object> add(RecordDetail entity) {
        recordDetailService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑表单")
    public Result<Object> edit(RecordDetail entity, @RequestParam String id) {
        RecordDetail old = recordDetailService.get(id);
        old.setScore(entity.getScore());
        recordDetailService.save(old);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            recordDetailService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
