package com.select.integrated.annotation;

import springfox.documentation.annotations.ApiIgnore;

import java.lang.annotation.*;

/**
 * 登录用户获取注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiIgnore
public @interface LoginUser {
}
