package com.gdpu.homework.Config;

import java.lang.annotation.*;
/*
  不需要token验证的注解,拦截器将不拦截

 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface DisableToken {
}
