package cn.exrick.xboot.modules.base.serviceimpl;

import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.modules.base.dao.DepartmentDao;
import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.base.service.DepartmentService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门接口实现
 * @author Exrick
 */
@Slf4j
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public DepartmentDao getRepository() {
        return departmentDao;
    }

    @Override
    public List<Department> findByParentIdOrderBySortOrder(String parentId, Boolean openDataFilter) {

        // 数据权限
        List<String> depIds = securityUtil.getDeparmentIds();
        if(depIds!=null&&depIds.size()>0&&openDataFilter){
            return departmentDao.findByParentIdAndIdInOrderBySortOrder(parentId, depIds);
        }
        return departmentDao.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<Department> findByParentIdAndStatusOrderBySortOrder(String parentId, Integer status) {

        return departmentDao.findByParentIdAndStatusOrderBySortOrder(parentId, status);
    }

    @Override
    public List<Department> findByTitleLikeOrderBySortOrder(String title, Boolean openDataFilter) {

        // 数据权限
        List<String> depIds = securityUtil.getDeparmentIds();
        if(depIds!=null&&depIds.size()>0&&openDataFilter){
            return departmentDao.findByTitleLikeAndIdInOrderBySortOrder(title, depIds);
        }
        return departmentDao.findByTitleLikeOrderBySortOrder(title);
    }

    /**
     * 找老子.
     *
     * @param son
     */
    @Override
    public void generateParents(Department son) {
        if (StringUtils.isNotBlank(son.getParentId()) && son.getParentId() != "0") { //没有到顶级
            Department father = get(son.getParentId());
            if (father != null) {
                son.setParent(father);
                generateParents(father);
            }
        }
    }

    /**
     * 生成上级的名称，返回一个集合.
     *
     * @param department
     */
    @Override
   public void generateParentTitle(Department department, List<String> result) {
        result.add(0,department.getTitle());
        if (department.getParent() != null) {
            generateParentTitle(department.getParent(), result);
        }
    }

    /**
     * 生成上级的ID，返回一个集合.
     *
     * @param department
     */
    @Override
    public void generateParentId(Department department, List<String> result) {
        result.add(0,department.getId());
        if (department.getParent() != null) {
            generateParentId(department.getParent(), result);
        }
    }

}