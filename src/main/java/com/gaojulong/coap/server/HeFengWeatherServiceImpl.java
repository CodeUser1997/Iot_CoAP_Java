package com.gaojulong.coap.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaojulong.coap.common.UrlValue;
import com.gaojulong.coap.model.HttpResultModel;
import com.gaojulong.coap.model.QweatherVo;
import com.gaojulong.coap.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 和风天气服务
 *
 * @author xiongsulong
 * @create 2021-10-16 00:14
 */
@Service("heFengWeatherService")
public class HeFengWeatherServiceImpl implements WeatherService {

    @Value("${qweather.key}")
    private String key;

    @Override
    public HttpResultModel getWatherNow(String location) {

        Map<String, String> params = new HashMap<>();
        params.put("location", location);
        params.put("key", key);

        try {
            String result = HttpClientUtil.doGet(UrlValue.DEV_NOW, null, params);
            JSONObject jsonObject = JSON.parseObject(result);
            if("200".equals(jsonObject.get("code"))){
                QweatherVo qweatherVo = JSON.parseObject(jsonObject.getString("now"), QweatherVo.class);
                return new HttpResultModel(200, qweatherVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpResultModel(405, "接口异常");
    }
}
