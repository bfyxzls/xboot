package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Tenement;
import cn.exrick.xboot.modules.your.service.TenementService;
import cn.exrick.xboot.modules.your.serviceimpl.TenementServiceImpl;
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
@Api(description = "物业管理接口")
@RequestMapping("/xboot/tenement")
@Transactional
public class TenementController extends XbootBaseController<Tenement, String> {

    @Autowired
    private TenementService tenementService;

    @Override
    public TenementService getService() {
        return tenementService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Tenement>> getByCondition(Tenement tenement,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Tenement> page = tenementService.findByCondition(tenement, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Tenement>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加物业")
    public Result<Object> add(Tenement tenement)
    {
        tenementService.save(tenement);
        return ResultUtil.success("添加成功");
    }
}
