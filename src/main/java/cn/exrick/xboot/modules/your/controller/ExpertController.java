package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.service.DictDataService;
import cn.exrick.xboot.modules.base.service.DictService;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.dao.ExpertDao;
import cn.exrick.xboot.modules.your.entity.Expert;
import cn.exrick.xboot.modules.your.service.ExpertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Api(description = "评价-专业管理")
@RequestMapping("/xboot/expert")
@Transactional
public class ExpertController extends XbootBaseController<Expert, String> {

    @Autowired
    EntityUtil entityUtil;
    @Autowired
    DictService dictService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private ExpertDao expertDao;
    @Autowired
    private DictDataService dictDataService;

    @Override
    public ExpertService getService() {
        return expertService;
    }

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "所有专家")
    public Result<List<Expert>> getAllList() {

        List<Expert> list = expertDao.findAll();
        for (Expert one : list) {
            initDict(one);
        }
        return new ResultUtil<List<Expert>>().setData(list);
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Expert>> getByCondition(Expert expert,
                                               SearchVo searchVo,
                                               PageVo pageVo) {

        Page<Expert> page = expertService.findByCondition(expert, searchVo, PageUtil.initPage(pageVo));
        for (Expert one : page) {
            initDict(one);
        }
        return new ResultUtil<Page<Expert>>().setData(page);
    }

    private void initDict(Expert one) {

        one.setTypeTitle(dictDataService.findByTypeAndValue("education", one.getEducation()).getTitle());
        one.setEducationTitle(dictDataService.findByTypeAndValue("expertType", one.getType()).getTitle());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(Expert entity) {
        entityUtil.initEntity(entity);
        expertService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(Expert entity, @RequestParam String id) {
        Expert old = expertService.get(id);
        BeanUtils.copyProperties(entity, old);
        expertService.save(old);
        return ResultUtil.success("保存成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            expertService.delete(id);
        }
        return ResultUtil.success("删除成功");
    }
}
