package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dao.ExpertDao;
import cn.exrick.xboot.modules.your.entity.Expert;
import cn.exrick.xboot.modules.your.service.ExpertService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 小区接口实现
 *
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    @Autowired
    private ExpertDao expertDao;

    @Override
    public ExpertDao getRepository() {
        return expertDao;
    }

    @Override
    public Page<Expert> findByCondition(Expert expert, SearchVo searchVo, Pageable pageable) {

        Page<Expert> page = expertDao.findAll(new Specification<Expert>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Expert> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<Date> createTimeField = root.get("createTime");
                Path<String> titleField = root.get("name");
                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StringUtils.isNotBlank(expert.getName())) {
                    list.add(cb.like(titleField, expert.getName().trim() + "%"));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        return page;
    }

}