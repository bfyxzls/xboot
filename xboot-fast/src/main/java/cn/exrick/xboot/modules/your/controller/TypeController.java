package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价-分类管理")
@RequestMapping("/xboot/type")
@Transactional
public class TypeController extends XbootBaseController<Type, String> {

    @Autowired
    private TypeService typeService;

    @Override
    public TypeService getService() {
        return typeService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Type>> getByCondition(Type type,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Type> page = typeService.findByCondition(type, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Type>>().setData(page);
    }

    @RequestMapping(value = "/getAllTypeList", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<List<Type>> getAllTypeList(){
        return new ResultUtil<List<Type>>().setData(typeService.getRepository().findAll());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加类型")
    public Result<Object> add(Type entity) {
        typeService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编号类型")
    public Result<Object> edit(Type entity, @RequestParam String id) {
        Type old = typeService.get(id);
        old.setTitle(entity.getTitle());
        old.setDescription(entity.getDescription());
        typeService.save(entity);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除类型")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            typeService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
