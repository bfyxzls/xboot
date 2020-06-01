package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Record;
import cn.exrick.xboot.modules.your.service.RecordService;
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
@Api(description = "评价记录管理接口")
@RequestMapping("/xboot/record")
@Transactional
public class RecordController extends XbootBaseController<Record, String> {

    @Autowired
    private RecordService recordService;

    @Override
    public RecordService getService() {
        return recordService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Record>> getByCondition(Record record,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Record> page = recordService.findByCondition(record, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Record>>().setData(page);
    }
}
