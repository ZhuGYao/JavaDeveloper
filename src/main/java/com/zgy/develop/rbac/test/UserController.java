package com.zgy.develop.rbac.test;

import com.zgy.develop.annotation.rbac.LoginRequired;
import com.zgy.develop.annotation.rbac.PermissionRequired;
import com.zgy.develop.common.utils.Result;
import com.zgy.develop.rbac.dao.PermissionDao;
import com.zgy.develop.rbac.dao.RolePermissionMergeDao;
import com.zgy.develop.rbac.dao.UserDao;
import com.zgy.develop.rbac.dao.UserRoleMergeDao;
import com.zgy.develop.rbac.enums.ExceptionCodeEnum;
import com.zgy.develop.rbac.enums.Logical;
import com.zgy.develop.rbac.enums.UserType;
import com.zgy.develop.rbac.enums.WebConstant;
import com.zgy.develop.rbac.pojo.Permission;
import com.zgy.develop.rbac.pojo.RolePermissionMerge;
import com.zgy.develop.rbac.pojo.User;
import com.zgy.develop.rbac.pojo.UserRoleMerge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zgy
 * @data 2021/5/10 15:37
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleMergeDao userRoleMergeDao;

    @Autowired
    private RolePermissionMergeDao rolePermissionMergeDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public Result<User> login(@RequestParam String name, @RequestParam String password) {

        User user = userDao.selectOne(name, password);
        if (user == null) {
            return Result.error(ExceptionCodeEnum.FAIL,"用户名或密码错误");
        }

        session.setAttribute(WebConstant.CURRENT_USER_IN_SESSION, user);
        session.setAttribute(WebConstant.USER_PERMISSIONS, getUserPermissions(user.getId()));

        return Result.success(user);
    }

    @LoginRequired
    @GetMapping("/needLogin")
    public Result<String> needLogin() {
        return Result.success("if you see this, you are logged in.");
    }

    @GetMapping("/needNotLogin")
    public Result<String> needNotLogin() {
        return Result.success("if you see this, you are logged in.");
    }

    @PermissionRequired
    @GetMapping("/needPermission")
    public Result<String> needPermission() {
        return Result.success("if you see this, you has the permission.");
    }

    /**
     * 获取用户的所有权限
     * @param uid
     * @return
     */
    private Set<String> getUserPermissions(Long uid) {

        List<UserRoleMerge> userRoleMerges = userRoleMergeDao.selectListByUserId(uid);
        if (userRoleMerges == null || userRoleMerges.size() <= 0) {
            // 没有角色没有权限
            return new HashSet<>();
        }
        // 取出角色Id
        List<Long> roleIds = userRoleMerges.stream().map(UserRoleMerge::getRoleId).collect(Collectors.toList());
        // 获取对应的权限与角色对应表
        List<RolePermissionMerge> rolePermissionMerges = rolePermissionMergeDao.selectListByRoleIds(roleIds);
        if (rolePermissionMerges.size() <= 0) {
            // 角色没有权限
            return new HashSet<>();
        }

        // 获取所有的权限Id
        List<Long> permissionIds = rolePermissionMerges.stream().map(RolePermissionMerge::getPermissionId).collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByIds(permissionIds);
        if (rolePermissionMerges.size() <= 0) {
            // 角色不存在
            return new HashSet<>();
        }
        Set<String> methods = permissions.stream().map(Permission::getMethod).collect(Collectors.toSet());

        return methods;
    }
}
