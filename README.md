# Iot_CoAP_Java

Java实现物联网CoAP服务

### **API**

时间：coap://api.gaojulong.com/weather/ntp

当前天气：coap://api.gaojulong.com/weather/now?[请求参数]

未来三天：coap://api.gaojulong.com/weather/3d?[请求参数]

##### key 

用户认证key，请参考[如何获取你的KEY](https://dev.qweather.com/docs/resource/get-key/)。支持[数字签名](https://dev.qweather.com/docs/resource/signature-auth/)方式进行认证。例如 `key=123456789ABC`

##### location 

需要查询地区的[LocationID](https://dev.qweather.com/docs/resource/glossary#locationid)或以英文逗号分隔的[经度,纬度坐标](https://dev.qweather.com/docs/resource/glossary#coordinate)（十进制），LocationID可通过[城市搜索](https://dev.qweather.com/docs/api/geo/)服务获取。例如 `location=101010100` 或 `location=116.41,39.92`

##### lang 

多语言设置，**默认中文**，当数据不匹配你设置的语言时，将返回英文或其本地语言结果。

- `zh` 中文, **默认**
- `en` 英语
- `fr` 法语
- `es` 西班牙语
- `ja` 日语
- `ko` 韩语
- [查看更多语言代码](https://dev.qweather.com/docs/resource/language/)

##### unit 

[度量衡单位参数](https://dev.qweather.com/docs/resource/unit)选择，例如温度选摄氏度或华氏度、公里或英里。**默认公制单位**

- `m` 公制单位，**默认**
- `i` 英制单位

#### 1. NTP对时：

**请求示例：**

~~~
coap://api.gaojulong.com/ntp
~~~

**返回参数**：

~~~json
{
    "code": 200,
    "data": {
        "t":"1634894633478",
        "time_format":"2021-10-22 17:23:53"
    }
}
~~~

#### 2. 当前天气：

**请求示例：**

~~~
coap://api.gaojulong.com/weather/now?location=117.282488,31.775297&key=9e54b1e3d00e4f36b813065e30b0eec7&lang=en
~~~

**返回参数**：

~~~json
{
    "code":"200",
    "data":{
        "temp":"16",
        "icon":"104",
        "text":"Overcast",
        "humidity":"63"
    }
}
~~~



#### 3. 未来三天天气：

**请求示例：**

~~~
coap://api.gaojulong.com/weather/3d?location=117.282488,31.775297&key=9e54b1e3d00e4f36b813065e30b0eec7&lang=en
~~~

**返回参数**：

~~~json
{
    "code":"200",
    "daily":[
        {
            "fxDate":"2021-10-23",
            "tempMin":"8",
            "tempMax":"18",
            "iconDay":"100",
            "textDay":"Sunny"
        },
        {
            "fxDate":"2021-10-23",
            "tempMin":"8",
            "tempMax":"18",
            "iconDay":"100",
            "textDay":"Sunny"
        },
        {
            "fxDate":"2021-10-23",
            "tempMin":"8",
            "tempMax":"18",
            "iconDay":"100",
            "textDay":"Sunny"
        }
    ]
}
~~~

