package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.*;
import cn.exrick.xboot.modules.base.service.*;
import cn.exrick.xboot.modules.base.service.mybatis.IUserRoleService;
import cn.exrick.xboot.modules.base.vo.ModifyPasswordDto;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/xboot/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {

    @Autowired
    DictDataService dictDataService;
    @Autowired
    DictService dictService;
    @Resource(name = "emailServiceImpl")
    EmailService emailHelper;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private DepartmentHeaderService departmentHeaderService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SecurityUtil securityUtil;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public Result<Object> regist(@RequestBody User u) {

        if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
            return ResultUtil.error("缺少必需表单字段");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return ResultUtil.error("该用户名已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        u.setType(CommonConstant.USER_TYPE_NORMAL);
        User user = userService.save(u);
        if (user == null) {
            return ResultUtil.error("注册失败");
        }
        // 默认角色
        List<Role> roleList = roleService.findByDefaultRole(true);
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }

        return ResultUtil.data(user);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<User> getUserInfo() {

        User u = securityUtil.getCurrUser();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        u.setPassword(null);
        return new ResultUtil<User>().setData(u);
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password) {

        User u = securityUtil.getCurrUser();
        if (!new BCryptPasswordEncoder().matches(password, u.getPassword())) {
            return ResultUtil.error("密码不正确");
        }
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    public Result<Object> resetPass(@RequestParam String[] ids) {

        for (String id : ids) {
            User u = userService.get(id);
            u.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userService.update(u);
            redisTemplate.delete("user::" + u.getUsername());
        }
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料", notes = "用户名密码不会修改 需要username更新缓存")
    public Result<Object> editOwn(@RequestBody User u) {
        User old = securityUtil.getCurrUser();
        if (StringUtils.isNotBlank(u.getNickName())) {
            old.setNickName(u.getNickName());
        }
        if (StringUtils.isNotBlank(u.getAvatar())) {
            old.setAvatar(u.getAvatar());
        }
        if (StringUtils.isNotBlank(u.getStreet())) {
            old.setStreet(u.getStreet());
        }
        if (StringUtils.isNotBlank(u.getSex())) {
            old.setSex(u.getSex());
        }
        if (StringUtils.isNotBlank(u.getEduCertification())) {
            old.setEduCertification(u.getEduCertification());
        }
        if (u.getWorkYear() != null) {
            old.setWorkYear(u.getWorkYear());
        }
        if (StringUtils.isNotBlank(u.getIntelligence())) {
            old.setIntelligence(u.getIntelligence());
        }
        if (StringUtils.isNotBlank(u.getTrade())) {
            old.setTrade(u.getTrade());
        }
        User user = userService.update(old);
        redisTemplate.delete("user::" + old.getUsername());
        if (user == null) {
            return ResultUtil.error("修改失败");
        }
        return ResultUtil.success("修改成功");
    }

    /**
     * @param u
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理员修改资料", notes = "需要通过id获取原用户信息 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public Result<Object> edit(User u,
                               @RequestParam(required = false) String[] roleIds) {

        User old = userService.get(u.getId());
        //若修改了用户名
        if (!old.getUsername().equals(u.getUsername())) {
            //若修改用户名删除原用户名缓存
            redisTemplate.delete("user::" + old.getUsername());
            //判断新用户名是否存在
            if (userService.findByUsername(u.getUsername()) != null) {
                return ResultUtil.error("该用户名已被存在");
            }
        }

        // 若修改了手机和邮箱判断是否唯一
        if (old.getMobile() != null &&
                !old.getMobile().equals(u.getMobile()) &&
                userService.findByMobile(u.getMobile()) != null) {
            return ResultUtil.error("该手机号已绑定其他账户");
        }
        if (!old.getEmail().equals(u.getEmail()) && userService.findByMobile(u.getEmail()) != null) {
            return ResultUtil.error("该邮箱已绑定其他账户");
        }

        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return ResultUtil.error("修改失败");
        }
        //删除该用户角色
        userRoleService.deleteByUserId(u.getId());
        if (roleIds != null && roleIds.length > 0) {
            //新角色
            for (String roleId : roleIds) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(u.getId());
                userRoleService.save(ur);
            }
        }
        //手动删除缓存
        redisTemplate.delete("userRole::" + u.getId());
        redisTemplate.delete("userRole::depIds:" + u.getId());
        redisTemplate.delete("userPermission::" + u.getId());
        redisTemplate.delete("permission::userMenuList:" + u.getId());
        redisTemplate.delete("user::" + u.getUsername());

        return ResultUtil.success("修改成功");
    }

    /**
     * 线上demo不允许测试账号改密码
     *
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public Result<Object> modifyPass(ModifyPasswordDto modifyPasswordDto) {
        String password = modifyPasswordDto.getPassword();
        String newPass = modifyPasswordDto.getNewPass();
        User user = securityUtil.getCurrUser();

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return ResultUtil.error("旧密码不正确");
        }

        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        userService.update(user);

        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());

        return ResultUtil.success("修改密码成功");
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<Page<User>> getByCondition(User user,
                                             SearchVo searchVo,
                                             PageVo pageVo) {

        Page<User> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
        for (User u : page.getContent()) {
            // 关联部门
            if (StrUtil.isNotBlank(u.getDepartmentId())) {
                Department department = departmentService.get(u.getDepartmentId());
                if (department != null) {
                    u.setDepartmentTitle(department.getTitle());
                }
            }
            // 关联角色
            List<Role> list = iUserRoleService.findByUserId(u.getId());
            u.setRoles(list);
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
            Dict dict = dictService.findByType("userType");
            if (dict != null) {
                List<DictData> dicList = dictDataService.findByDictId(dict.getId());
                DictData dictData = dicList.stream().filter(o -> o.getValue().equals(u.getType().toString())).findFirst().orElse(null);
                if (dictData != null) {
                    u.setTypeTitle(dictData.getTitle());
                }
            }

        }
        return new ResultUtil<Page<User>>().setData(page);
    }

    @RequestMapping(value = "/getByDepartmentId/{departmentId}", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<List<User>> getByCondition(@PathVariable String departmentId) {

        List<User> list = userService.findByDepartmentId(departmentId);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<User>>().setData(list);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部用户数据")
    public Result<List<User>> getByCondition() {

        List<User> list = userService.getAll();
        for (User u : list) {
            // 关联部门
            if (StrUtil.isNotBlank(u.getDepartmentId())) {
                Department department = departmentService.get(u.getDepartmentId());
                if (department != null) {
                    u.setDepartmentTitle(department.getTitle());
                }
            }
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }
        return new ResultUtil<List<User>>().setData(list);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public Result<Object> regist(User u,
                                 @RequestParam(required = false) String[] roles) {

        if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
            return ResultUtil.error("缺少必需表单字段");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return ResultUtil.error("该用户名已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User user = userService.save(u);
        if (user == null) {
            return ResultUtil.error("添加失败");
        }
        if (roles != null && roles.length > 0) {
            //添加角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                userRoleService.save(ur);
            }
        }

        return ResultUtil.data(user);
    }

    @RequestMapping(value = "/admin/disable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public Result<Object> disable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.get(userId);
        if (user == null) {
            return ResultUtil.error("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_LOCK);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/admin/enable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public Result<Object> enable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.get(userId);
        if (user == null) {
            return ResultUtil.error("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            User u = userService.get(id);
            //删除缓存
            redisTemplate.delete("user::" + u.getUsername());
            redisTemplate.delete("userRole::" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            Set<String> keys = redisTemplate.keys("department::*");
            redisTemplate.delete(keys);
            userService.delete(id);
            //删除关联角色
            userRoleService.deleteByUserId(id);
            //删除关联部门负责人
            departmentHeaderService.deleteByUserId(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/sendmail", method = RequestMethod.POST)
    @ApiOperation(value = "找回密码-发送邮件")
    public Result<Object> sendmail(@RequestParam String username) {
        return ResultUtil.success("验证码已发到您的" +
                emailHelper.sendFindPassworldMail(username) +
                "邮箱，请及时查收 ！");
    }

    /**
     * 重置自己的密码
     *
     * @param username 用户名
     * @param code     验证码
     * @return
     */
    @RequestMapping(value = "/resetMePass", method = RequestMethod.POST)
    @ApiOperation(value = "找回密码-验证-并重置密码123456")
    public Result<Object> verifyCode(@ApiParam("账号") @RequestParam String username,
                                     @ApiParam("验证码") @RequestParam String code) {
        if (emailHelper.verifyCode(username, code)) {
            User u = userService.findByUsername(username);
            u.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userService.update(u);
            redisTemplate.delete("user::" + u.getUsername());
            redisTemplate.delete("vode:" + u.getUsername());
            return ResultUtil.success("操作成功，请使用密码123456进行登陆");
        }
        return ResultUtil.error("操作失败，验证码不正确");

    }


    /**
     * 得到组织机构.
     *
     * @return
     */
    @GetMapping("getDepartment")
    @ApiOperation(value = "得到当前用户的组织机构")
    public Result<Department> getDepartmens() {
        Department department = departmentService.get(securityUtil.getCurrUser().getDepartmentId());
        if (department != null) {
            departmentService.generateParents(department);
            List<Department> sons = departmentService.findByParentIdAndStatusOrderBySortOrder(department.getId(), CommonConstant.STATUS_NORMAL);
            departmentService.generateSons(sons);
            department.setChildren(sons);
        }

        return ResultUtil.data(department);
    }

    /**
     * 得到组织机构.
     *
     * @return
     */
    @GetMapping("getDepartmentTree")
    @ApiOperation(value = "得到当前用户的组织机构")
    public Result<Department> getDepartmentTree() {
        Department department = departmentService.get(securityUtil.getCurrUser().getDepartmentId());
        if (department != null) {
            departmentService.generateParents(department);
            List<Department> sons = departmentService.findByParentIdAndStatusOrderBySortOrder(department.getId(), CommonConstant.STATUS_NORMAL);
            departmentService.generateSons(sons);
            department.setChildren(sons);
        }
        List<Department> departmentTrees = new ArrayList<>();
        findFather(departmentTrees, department);

        departmentTrees = departmentTrees.stream()
                .filter(dept -> dept.getParentId() != null && dept.getParentId().length() >= 4)
                .collect(Collectors.toList());
        CollectionUtil.reverse(departmentTrees);
        department.setParent(null);

        if (CollectionUtil.isNotEmpty(departmentTrees)) {
            Department departmentTree = departmentTrees.get(0);
            departmentTree.setParent(null);
            if (departmentTrees.size() == 2) {//第一级区，下一级，第三级社区
                Department son = departmentTrees.get(1);
                son.setParent(null);
                son.setChildren(Arrays.asList(department));
                departmentTree.setChildren(Arrays.asList(son));
            } else if (departmentTrees.size() == 1) {
                departmentTree.setChildren(Arrays.asList(department));
            }

            return ResultUtil.data(departmentTree);
        }

        return ResultUtil.data(department);
    }


    void findFather(List<Department> departmentTrees, Department department) {
        Department departmentFather = department.getParent();
        if (departmentFather != null) {
            departmentTrees.add(departmentFather);
            findFather(departmentTrees, departmentFather);
        }
    }
}
