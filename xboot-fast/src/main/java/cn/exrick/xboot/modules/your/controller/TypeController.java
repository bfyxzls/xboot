package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Type;
import cn.exrick.xboot.modules.your.service.TypeService;
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
@Api(description = "测试管理接口")
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
}
