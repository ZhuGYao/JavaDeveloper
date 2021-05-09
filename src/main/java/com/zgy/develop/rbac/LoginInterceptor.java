package com.zgy.develop.rbac;

import com.zgy.develop.annotation.rbac.LoginRequired;
import com.zgy.develop.common.utils.ThreadLocalUtil;
import com.zgy.develop.rbac.enums.ExceptionCodeEnum;
import com.zgy.develop.rbac.enums.WebConstant;
import com.zgy.develop.rbac.exception.BusinessException;
import com.zgy.develop.rbac.pojo.User;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author zgy
 * @data 2021/5/9 18:46
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果方法上没有加@LoginRequired,无需登录,直接放行
        if (isLoginRequired(handler)) {
            return true;
        }

        // 验证是否登录
        User user = isLogin(request);
        // 登录成功,把用户信息存入ThreadLocal
        ThreadLocalUtil.put(WebConstant.USER_INFO, user);

        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 避免ThreadLocal内存泄漏
        ThreadLocalUtil.remove(WebConstant.USER_INFO);
    }

    /**
     * 接口是否需要登录
     * @param handler
     * @return
     */
    private boolean isLoginRequired(Object handler) {
        // 判断是否支持免登录
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // AnnotationUtils是Spring提供的注解工具类
            LoginRequired loginRequiredAnnotation = AnnotationUtils.getAnnotation(method, LoginRequired.class);
            // 没有加@LoginRequired，不需要登录
            return loginRequiredAnnotation == null;
        }

        return true;
    }

    /**
     * 登录校验
     * @param request
     * @return
     */
    private User isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(WebConstant.CURRENT_USER_IN_SESSION);
        if (currentUser == null) {
            // 抛异常,请先登录
            throw new BusinessException(ExceptionCodeEnum.NEED_LOGIN);
        }
        return currentUser;
    }
}
