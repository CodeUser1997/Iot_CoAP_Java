package com.gaojulong.coap.common;

/**
 * 描述:
 * 和风天气开发平台URL
 *
 * @author xiongsulong
 * @create 2021-10-16 00:11
 */
public interface UrlValue {

    /**
     * 开发版-实时天气接口
     */
    String DEV_NOW = "https://devapi.qweather.com/v7/weather/now";
    /**
     * 开发板-3天天气接口
     */
    String DEV_3D = "https://devapi.qweather.com/v7/weather/3d";

    /**
     * 开发版-获取实时时间(这个接口是获取北京时区)
     */
    String DEV_NTP = "http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp";

}
