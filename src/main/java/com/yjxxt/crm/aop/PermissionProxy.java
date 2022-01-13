package com.yjxxt.crm.aop;

import com.yjxxt.crm.annotation.RequirePermission;
import com.yjxxt.crm.exception.NoAccessException;
import com.yjxxt.crm.pojo.Permission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName PermissionProxy
 * @Desc 资源切面类
 * @Author xiaoding
 * @Date 2022-01-03 10:32
 * @Version 1.0
 */
@Component
@Aspect
public class PermissionProxy {
    @Autowired
    private HttpSession session;


    @Around(value = "@annotation(com.yjxxt.crm.annotation.RequirePermission)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取用户的资源权限
        List<Permission> list = (List<Permission>) session.getAttribute("permission");
        if (list == null || list.size() == 0) {
            throw new LoginException("未登录异常");
        }
        //获取将要执行指定的方法
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        //获取注解
        RequirePermission requirePermission = methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        //判断是否包含
        if (!list.contains(requirePermission.code())) {
            throw new NoAccessException();
        }
        //返回对象
        Object obj = point.proceed();
        return obj;
    }
}
