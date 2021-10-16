package com.gaojulong.coap.model;

import java.io.Serializable;

/**
 * 描述:
 * Http请求相应结果
 *
 * @author xiongsulong
 * @create 2021-10-15 23:34
 */
public class HttpResultModel implements Serializable {
    private int code;

    private Object data;

    public HttpResultModel(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
