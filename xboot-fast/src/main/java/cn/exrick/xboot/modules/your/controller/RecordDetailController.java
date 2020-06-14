package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import cn.exrick.xboot.modules.your.service.RecordDetailService;
import cn.exrick.xboot.modules.your.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    RecordService recordService;
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

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "单条编辑表单的分类")
    public Result<Object> edit(@RequestParam String recordDetails) {
        recordDetails = recordDetails.substring(0, recordDetails.length() - 1);
        List<RecordDetail> recordDetailList = new ArrayList<>();
        String[] one = recordDetails.split("\\|");
        for (String detail : one) {
            String[] id = detail.split("_");
            if (id.length > 1) {
                RecordDetail recordDetail = new RecordDetail();
                recordDetail.setId(id[0]);
                recordDetail.setScore(Double.parseDouble(id[1]));
                recordDetailList.add(recordDetail);
            }
        }
        recordDetailService.updateRecordDetail(recordDetailList);
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
