package com.gaojulong.coap.server;

import com.gaojulong.coap.model.HttpResultModel;

/**
 * 描述:
 * 天气服务接口
 *
 * @author xiongsulong
 * @create 2021-10-16 00:13
 */
public interface WeatherService {

    /**
     * 获取实时天气
     * @param location 城市编码
     * @return
     */
    HttpResultModel getWatherNow(String location);

    /**
     * 获取当前时间
     * @return
     */
    HttpResultModel ntp();

    /**
     * 3天天气
     * @param location
     * @return
     */
    HttpResultModel threeDays(String location);
}
