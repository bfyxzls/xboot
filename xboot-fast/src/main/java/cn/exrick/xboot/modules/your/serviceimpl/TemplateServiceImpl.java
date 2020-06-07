package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.modules.base.entity.Permission;
import cn.exrick.xboot.modules.your.dao.TemplateDao;
import cn.exrick.xboot.modules.your.entity.Template;
import cn.exrick.xboot.modules.your.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评价模板接口实现
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
}