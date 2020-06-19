package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.entity.Tenement;
import cn.exrick.xboot.modules.your.service.TenementService;
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
@Api(description = "评价-物业管理")
@RequestMapping("/xboot/tenement")
@Transactional
public class TenementController extends XbootBaseController<Tenement, String> {

    @Autowired
    EntityUtil entityUtil;
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
                                                 PageVo pageVo) {

        Page<Tenement> page = tenementService.findByCondition(tenement, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Tenement>>().setData(page);
    }

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "物业列表")
    public Result<List<Tenement>> getAllList() {
        List<Tenement> list = tenementService.getAll();
        return new ResultUtil<List<Tenement>>().setData(list);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加物业")
    public Result<Object> add(Tenement tenement) {
        entityUtil.initEntity(tenement);
        tenementService.save(tenement);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "添加物业")
    public Result<Object> edit(Tenement tenement, @RequestParam String id) {
        Tenement old = tenementService.get(id);
        old.setTitle(tenement.getTitle());
        old.setDescription(tenement.getDescription());
        tenementService.save(old);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除物业")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            tenementService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
