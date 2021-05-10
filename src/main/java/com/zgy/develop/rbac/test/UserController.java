package com.zgy.develop.rbac.test;

import com.zgy.develop.annotation.rbac.LoginRequired;
import com.zgy.develop.annotation.rbac.PermissionRequired;
import com.zgy.develop.common.utils.Result;
import com.zgy.develop.rbac.dao.UserDao;
import com.zgy.develop.rbac.enums.ExceptionCodeEnum;
import com.zgy.develop.rbac.enums.Logical;
import com.zgy.develop.rbac.enums.UserType;
import com.zgy.develop.rbac.enums.WebConstant;
import com.zgy.develop.rbac.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    private HttpSession session;

    @GetMapping("/login")
    public Result<User> login(@RequestParam String name, @RequestParam String password) {

        User user = userDao.selectOne(name, password);
        if (user == null) {
            return Result.error(ExceptionCodeEnum.FAIL,"用户名或密码错误");
        }
        session.setAttribute(WebConstant.CURRENT_USER_IN_SESSION, user);
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

    @PermissionRequired(userType = {UserType.ADMIN, UserType.TEACHER}, logical = Logical.OR)
    @GetMapping("/needPermission")
    public Result<String> needPermission() {
        return Result.success("if you see this, you has the permission.");
    }
}
