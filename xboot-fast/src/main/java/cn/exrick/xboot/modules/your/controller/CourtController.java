package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Tenement;
import cn.exrick.xboot.modules.your.service.CourtService;
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
@Api(description = "评价-小区管理")
@RequestMapping("/xboot/court")
@Transactional
public class CourtController extends XbootBaseController<Court, String> {

    @Autowired
    private CourtService courtService;

    @Override
    public CourtService getService() {
        return courtService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Court>> getByCondition(Court court,
                                              SearchVo searchVo,
                                              PageVo pageVo) {

        Page<Court> page = courtService.findByCondition(court, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Court>>().setData(page);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加小区")
    public Result<Object> add(Court entity) {
        courtService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑小区")
    public Result<Object> edit(Court entity, @RequestParam String id) {
        Court old = courtService.get(id);
        old.setTitle(entity.getTitle());
        old.setDescription(entity.getDescription());
        courtService.save(entity);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除小区")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            courtService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
