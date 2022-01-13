package com.yjxxt.crm.annotation;

import java.lang.annotation.*;

/**
 * @ClassName RequirePermission
 * @Desc 自定义资源注解
 * @Author xiaoding
 * @Date 2022-01-03 10:35
 * @Version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    String code() default "";
}
