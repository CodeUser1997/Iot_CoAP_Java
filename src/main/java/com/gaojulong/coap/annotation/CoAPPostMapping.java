package com.gaojulong.coap.annotation;

import com.gaojulong.coap.common.CoAPRequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc: CoAP协议 Post请求
 *
 * @author xiongsulong
 * @create 2021-10-15 22:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CoAPRequestMapping(method = {
        CoAPRequestType.POST
})
public @interface CoAPPostMapping {
    String value();
}
