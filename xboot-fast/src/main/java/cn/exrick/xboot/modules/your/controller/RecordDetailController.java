package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dao.CourtDao;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Record;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-表单管理")
@RequestMapping("/xboot/recordDetail")
@Transactional
public class RecordDetailController extends XbootBaseController<RecordDetail, String> {

    @Autowired
    private RecordDetailService recordDetailService;

    @Override
    public RecordDetailService getService() {
        return recordDetailService;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "按recordid获取表单列表")
    public Result<List<RecordDetail>> getByCondition(@RequestParam String recordId) {
        List<RecordDetail> page = recordDetailService.findByRecordId(recordId);
        return new ResultUtil<List<RecordDetail>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "前台批量添加表单")
    public Result<Object> add(@RequestBody List<RecordDetail> list) {

        recordDetailService.addRecordDetails(list);
        return ResultUtil.success("添加成功");
    }

    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "单条编辑表单的分类")
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
