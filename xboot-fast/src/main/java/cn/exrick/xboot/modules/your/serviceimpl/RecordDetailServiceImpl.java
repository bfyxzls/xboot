package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.dao.CourtDao;
import cn.exrick.xboot.modules.your.dao.RecordDetailDao;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.RecordDetail;
import cn.exrick.xboot.modules.your.service.CourtService;
import cn.exrick.xboot.modules.your.service.RecordDetailService;
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
public class RecordDetailServiceImpl implements RecordDetailService {

    @Autowired
    private RecordDetailDao recordDetailDao;

    @Override
    public RecordDetailDao getRepository() {
        return recordDetailDao;
    }

    @Override
    public Page<RecordDetail> findByCondition(RecordDetail court, SearchVo searchVo, Pageable pageable) {

        return recordDetailDao.findAll(new Specification<RecordDetail>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<RecordDetail> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

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
                if (StringUtils.isNotBlank(court.getTemplateTitle())) {
                    list.add(cb.like(titleField, court.getTemplateTitle().trim()+"%"));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

}