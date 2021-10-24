package com.gaojulong.coap.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaojulong.coap.common.UrlValue;
import com.gaojulong.coap.model.HttpResultModel;
import com.gaojulong.coap.model.QweatherVo;
import com.gaojulong.coap.model.TimeVo;
import com.gaojulong.coap.util.DateUtil;
import com.gaojulong.coap.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Override
    public HttpResultModel ntp() {
        try {
            String result = HttpClientUtil.doGet(UrlValue.DEV_NTP, null, null);
            JSONObject jsonObject = JSON.parseObject(result);
            System.out.println(jsonObject);
            TimeVo timeVo = new TimeVo();
            timeVo.setT(Long.valueOf(jsonObject.getJSONObject("data").getString("t")));
            timeVo.setTimeFormat(DateUtil.format(new Date(timeVo.getT()), DateUtil.FORMAT_1));
            return new HttpResultModel(200, timeVo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HttpResultModel(405, "接口异常");
    }

    @Override
    public HttpResultModel threeDays(String location) {
        Map<String, String> params = new HashMap<>();
        params.put("location", location);
        params.put("key", key);

        try {
            String result = HttpClientUtil.doGet(UrlValue.DEV_3D, null, params);
            JSONObject jsonObject = JSON.parseObject(result);
            if("200".equals(jsonObject.get("code"))){
                List<QweatherVo> dataList = new ArrayList<>();
                JSONArray daily = jsonObject.getJSONArray("daily");
                for(int i = 0; i < daily.size(); i ++){
                    JSONObject object = daily.getJSONObject(i);
                    QweatherVo qweatherVo = JSON.parseObject(object.toJSONString(), QweatherVo.class);
                    dataList.add(qweatherVo);
                }
                return new HttpResultModel(200, dataList);
            }

            return new HttpResultModel(200, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HttpResultModel(405, "接口异常");
    }
}
