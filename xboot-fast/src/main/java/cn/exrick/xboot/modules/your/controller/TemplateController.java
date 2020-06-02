package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.utils.CommonUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.your.entity.Template;
import cn.exrick.xboot.modules.your.service.TemplateService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

/**
 * @author lind
 */
@Slf4j
@RestController
@Api(description = "评价模板管理接口")
@RequestMapping("/xboot/template")
@CacheConfig(cacheNames = "template")
@Transactional
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<List<Template>> getByParentId(@PathVariable String parentId){

        List<Template> list = templateService.findByParentIdOrderBySortOrder(parentId);
        setInfo(list);
        return new ResultUtil<List<Template>>().setData(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(Template template){

        templateService.save(template);
        // 如果不是添加的一级 判断设置上级为父节点标识
        if(!CommonConstant.PARENT_ID.equals(template.getParentId())){
            Template parent = templateService.get(template.getParentId());
            if(parent.getIsParent()==null||!parent.getIsParent()){
                parent.setIsParent(true);
                templateService.update(parent);
            }
        }
        // 更新缓存
        Set<String> keys = redisTemplate.keys("template::*");
        redisTemplate.delete(keys);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(Template template){

        templateService.update(template);
        // 手动删除所有分类缓存
        Set<String> keys = redisTemplate.keys("template:" + "*");
        redisTemplate.delete(keys);
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable String[] ids){

        for(String id : ids){
            deleteRecursion(id, ids);
        }
        // 手动删除所有缓存
        Set<String> keys = redisTemplate.keys("template:" + "*");
        redisTemplate.delete(keys);
        return ResultUtil.success("批量通过id删除数据成功");
    }

    public void deleteRecursion(String id, String[] ids){

        // 获得其父节点
        Template o = templateService.get(id);
        Template parent = null;
        if(o!=null&& StrUtil.isNotBlank(o.getParentId())){
            parent = templateService.get(o.getParentId());
        }
        templateService.delete(id);
        // 判断父节点是否还有子节点
        if(parent!=null){
            List<Template> childrenDeps = templateService.findByParentIdOrderBySortOrder(parent.getId());
            if(childrenDeps==null||childrenDeps.size()==0){
                parent.setIsParent(false);
                templateService.update(parent);
            }
        }
        // 递归删除
        List<Template> objs = templateService.findByParentIdOrderBySortOrder(id);
        for(Template obj : objs){
            if(!CommonUtil.judgeIds(obj.getId(), ids)){
                deleteRecursion(obj.getId(), ids);
            }
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "名称模糊搜索")
    public Result<List<Template>> searchByTitle(@RequestParam String title) {

        List<Template> list = templateService.findByTitleLikeOrderBySortOrder("%"+title+"%");
        setInfo(list);
        return new ResultUtil<List<Template>>().setData(list);
    }

    public List<Template> setInfo(List<Template> list){

        // lambda表达式
        list.forEach(item -> {
            if(!CommonConstant.PARENT_ID.equals(item.getParentId())){
                Template parent = templateService.get(item.getParentId());
                item.setParentTitle(parent.getTitle());
            }else{
                item.setParentTitle("一级节点");
            }
        });
        return list;
    }
}
