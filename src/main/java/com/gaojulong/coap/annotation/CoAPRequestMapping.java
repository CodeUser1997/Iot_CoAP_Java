package com.gaojulong.coap.annotation;

import com.gaojulong.coap.common.CoAPRequestType;

import java.lang.annotation.*;

/**
 * @desc 映射URL
 * @author sulongx
 * @date 2021-10-15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CoAPRequestMapping {
    String value() default "";

    CoAPRequestType[] method() default {};
}
