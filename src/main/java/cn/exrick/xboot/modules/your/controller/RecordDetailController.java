package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.your.dto.RecordFormDTO;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import cn.exrick.xboot.modules.your.entity.Template;
import cn.exrick.xboot.modules.your.service.RecordDetailService;
import cn.exrick.xboot.modules.your.service.RecordService;
import cn.exrick.xboot.modules.your.service.TemplateService;
import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    TemplateService templateService;
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
        for (RecordDetail detail : page) {
            Template template = templateService.get(detail.getTemplateId());
            if (template != null) {
                detail.setQuestionType(template.getQuestionType());
            }
          List<Template> valueList=  templateService.findByParentIdOrderBySortOrder(detail.getTemplateId());
            if(CollectionUtil.isNotEmpty(valueList)){
                detail.setValueTemplateList(valueList);
            }
        }
        return new ResultUtil<List<RecordDetail>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "前台批量添加表单")
    public Result<Object> add(@RequestBody RecordFormDTO recordFormDTO) {
        log.info("RecordDetailController.add.param:{}", recordFormDTO);
        recordDetailService.addRecordDetails(recordFormDTO, false);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/editOne", method = RequestMethod.POST)
    @ApiOperation(value = "单条编辑表单的分类")
    public Result<Object> editOne(@RequestBody RecordFormDTO recordFormDTO) {
        if (recordFormDTO.getLatitude() == null || recordFormDTO.getLatitude().toString() == "undefined") {
            recordFormDTO.setLatitude(0d);
        }
        if (recordFormDTO.getLongitude() == null || recordFormDTO.getLongitude().toString() == "undefined") {
            recordFormDTO.setLongitude(0d);
        }
        recordDetailService.addRecordDetails(recordFormDTO, false);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/auditOne", method = RequestMethod.POST)
    @ApiOperation(value = "单条编辑表单的分类")
    public Result<Object> auditOne(@RequestBody RecordFormDTO recordFormDTO) {
        recordDetailService.addRecordDetails(recordFormDTO, true);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "单条编辑表单的分类-目前已经不用了")
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
        recordDetailService.updateRecordDetail(recordDetailList, false);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ApiOperation(value = "审核单条编辑表单")
    public Result<Object> audit(@RequestParam String recordDetails) {
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
        recordDetailService.updateRecordDetail(recordDetailList, true);
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
