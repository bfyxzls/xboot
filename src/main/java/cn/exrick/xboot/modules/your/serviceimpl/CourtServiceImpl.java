package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.dao.DepartmentDao;
import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.your.dao.CourtDao;
import cn.exrick.xboot.modules.your.dao.TenementDao;
import cn.exrick.xboot.modules.your.entity.Court;
import cn.exrick.xboot.modules.your.entity.Tenement;
import cn.exrick.xboot.modules.your.service.CourtService;
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
public class CourtServiceImpl implements CourtService {

    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    TenementDao tenementDao;
    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    private CourtDao courtDao;

    @Override
    public CourtDao getRepository() {
        return courtDao;
    }

    @Override
    public Page<Court> findByCondition(Court court, SearchVo searchVo, Pageable pageable) {

        Page<Court> page = courtDao.findAll(new Specification<Court>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Court> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<Date> createTimeField = root.get("createTime");
                Path<String> titleField = root.get("title");
                Path<String> projectTypeField = root.get("projectType");

                List<Predicate> list = new ArrayList<Predicate>();
                // 数据权限
                String currentDeptId = securityUtil.getCurrUser().getDepartmentId();
                list.add(cb.like(root.get("departmentIds"), "%" + currentDeptId + "%"));

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }
                if (StringUtils.isNotBlank(court.getTitle())) {
                    list.add(cb.like(titleField, court.getTitle().trim() + "%"));
                }
                if (StringUtils.isNotBlank(court.getProjectType())) {
                    list.add(cb.like(projectTypeField, court.getProjectType().trim() + "%"));
                }
                if (StringUtils.isNotBlank(court.getDepartmentId())) {
                    list.add(cb.like(root.get("departmentIds"), "%"+court.getDepartmentId()+"%"));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
        for (Court court1 : page) {
            if (court1.getDepartmentId() != null) {
                Department department = departmentDao.findById(court1.getDepartmentId()).orElse(null);
                if (department != null) {
                    court1.setDepartmentTitle(department.getTitle());
                }
            }
            if (court1.getTenementId() != null) {
                Tenement tenement = tenementDao.findById(court1.getTenementId()).orElse(null);
                if (tenement != null) {
                    court1.setTenementTitle(tenement.getTitle());
                }
            }
        }
        return page;
    }

}