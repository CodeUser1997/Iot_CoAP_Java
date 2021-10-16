package com.gaojulong.coap.annotation;


import com.gaojulong.coap.common.CoAPRequestType;

import java.lang.annotation.*;

/**
 * @desc CoAP协议 Get请求
 * @author sulongx
 * @date 2021-10-15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CoAPRequestMapping(method = {
        CoAPRequestType.GET
})
public @interface CoAPGetMapping {
    String value();
}
