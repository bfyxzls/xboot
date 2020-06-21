package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.modules.your.dao.TemplateDao;
import cn.exrick.xboot.modules.your.entity.Template;
import cn.exrick.xboot.modules.your.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评价模板接口实现
 *
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Override
    public TemplateDao getRepository() {
        return templateDao;
    }


    @Override
    public List<Template> findByParentIdOrderBySortOrder(String parentId) {

        return templateDao.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<Template> findByTitleLikeOrderBySortOrder(String title) {

        return templateDao.findByTitleLikeOrderBySortOrder(title);
    }

    @Override
    public List<Template> findByLevelOrderBySortOrder(Integer level) {
        return templateDao.findByLevelOrderBySortOrder(level);

    }

    @Override
    public List<Template> findByTypeIdOrderBySortOrder(String typeId) {
        return templateDao.findByTypeIdOrderBySortOrder(typeId);
    }

    @Override
    public List<Template> findAllTreeByTypeId(String typeId) {
        // 0级
        List<Template> list0 = findByLevelOrderBySortOrder(CommonConstant.LEVEL_ZERO);
        list0 = list0.stream().filter(o -> o.getTypeId().equals(typeId)).collect(Collectors.toList());
        generateSons(list0);
        return list0;
    }

    /**
     * 生儿子.
     *
     * @param list0
     */
    void generateSons(List<Template> list0) {
        for (Template p0 : list0) {
            List<Template> list1 = findByParentIdOrderBySortOrder(p0.getId());
            if (list1 != null) {
                p0.setChildren(list1);
                generateSons(list1);
            }
        }
    }

}