package com.gaojulong.coap.controller;

import com.gaojulong.coap.annotation.CoAPGetMapping;
import com.gaojulong.coap.annotation.CoAPRequestMapping;
import com.gaojulong.coap.model.HttpResultModel;
import com.gaojulong.coap.server.WeatherService;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 描述:
 * 天气数据接口
 *
 * @author xiongsulong
 * @create 2021-10-15 22:38
 */
@Component
@CoAPRequestMapping("weather")
public class WeatherController {

    @Resource(name = "heFengWeatherService")
    private WeatherService weatherService;

    @CoAPGetMapping("/getWeather")
    public HttpResultModel getWeather(CoapExchange exchange){

        String location = exchange.getQueryParameter("location");
        if(StringUtils.isEmpty(location)){
            return new HttpResultModel(405, "参数缺失");
        }
        return weatherService.getWatherNow(Long.valueOf(location));

    }

}
