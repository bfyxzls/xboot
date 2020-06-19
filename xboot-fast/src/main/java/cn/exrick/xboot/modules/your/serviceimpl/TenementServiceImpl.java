package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.dao.DepartmentDao;
import cn.exrick.xboot.modules.your.dao.TenementDao;
import cn.exrick.xboot.modules.your.entity.Tenement;
import cn.exrick.xboot.modules.your.service.TenementService;
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
 * 物业接口实现
 *
 * @author lind
 */
@Slf4j
@Service
@Transactional
public class TenementServiceImpl implements TenementService {

    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    private TenementDao tenementDao;

    @Override
    public TenementDao getRepository() {
        return tenementDao;
    }

    @Override
    public Page<Tenement> findByCondition(Tenement tenement, SearchVo searchVo, Pageable pageable) {


        Page<Tenement> page = tenementDao.findAll(new Specification<Tenement>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Tenement> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");
                Path<String> titleField = root.get("title");
                List<Predicate> list = new ArrayList<Predicate>();

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StringUtils.isNotBlank(tenement.getTitle())) {
                    list.add(cb.like(titleField, tenement.getTitle().trim() + "%"));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        return page;
    }


}