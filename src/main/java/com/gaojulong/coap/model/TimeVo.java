package com.gaojulong.coap.model;

import lombok.Data;

/**
 * 描述:
 *
 * @author xiongsulong
 * @create 2021-10-24 17:16
 */
@Data
public class TimeVo {
    /**
     * 时间戳
     */
    private long t;

    /**
     * 格式化后的时间字符串
     */
    private String timeFormat;

}
