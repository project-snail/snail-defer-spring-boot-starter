package com.snail.defer.annotation;

import java.lang.annotation.*;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.snail.defer.annotation
 * @Description:
 * @date: 2021/04/11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Deferrable {
}
