package com.yjxxt.crm.interceptors;

import com.yjxxt.crm.pojo.User;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptors
 * @Desc 用户拦截器
 * @Author xiaoding
 * @Date 2021-12-29 9:28
 * @Version 1.0
 */
public class LoginInterceptors implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登录
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //判断用户是否为空
        if (userId == null || userService.selectByPrimaryKey(userId) == null) {
            throw new LoginException("请登录");
        }
        return true;
    }
}
