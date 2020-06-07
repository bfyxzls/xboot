package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.base.entity.Permission;
import cn.exrick.xboot.modules.your.entity.Template;

import java.util.List;

/**
 * 评价模板接口
 * @author lind
 */
public interface TemplateService extends XbootBaseService<Template, String> {


    /**
     * 通过父id获取
     * @param parentId
     * @return
     */
    List<Template> findByParentIdOrderBySortOrder(String parentId);

    /**
     * 通过名称模糊搜索
     * @param title
     * @return
     */
    List<Template> findByTitleLikeOrderBySortOrder(String title);

    /**
     * 通过层级查找
     * 默认升序
     * @param level
     * @return
     */
    List<Template> findByLevelOrderBySortOrder(Integer level);
}