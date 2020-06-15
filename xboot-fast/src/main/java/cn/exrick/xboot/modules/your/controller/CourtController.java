package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.utils.EntityUtil;
import cn.exrick.xboot.modules.your.dao.CourtDao;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.service.CourtService;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
    EntityUtil entityUtil;
    @Autowired
    private CourtService courtService;
    @Autowired
    private CourtDao courtDao;

    @Override
    public CourtService getService() {
        return courtService;
    }

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "所有小区")
    public Result<List<Court>> getAllList() {

        List<Court> list = courtDao.findAll();
        return new ResultUtil<List<Court>>().setData(list);
    }

    @RequestMapping(value = "/getListByName", method = RequestMethod.GET)
    @ApiOperation(value = "按名称获取小区")
    public Result<List<Court>> getListByName(@RequestParam String name) {
        List<Court> list = courtDao.findAll(new Specification<Court>() {
            @Override
            public Predicate toPredicate(Root<Court> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(name)) {
                    list.add(criteriaBuilder.equal(root.get("title"), name));
                }
                Predicate[] arr = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(arr));
                return null;
            }
        });
        return new ResultUtil<List<Court>>().setData(list);
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
        entityUtil.initEntity(entity);
        courtService.save(entity);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑小区")
    public Result<Object> edit(Court entity, @RequestParam String id) {
        Court old = courtService.get(id);
        old.setTitle(entity.getTitle());
        old.setDescription(entity.getDescription());
        courtService.save(old);
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
