package com.gaojulong.coap.model;

/**
 * 描述:
 *
 * @author xiongsulong
 * @create 2021-10-17 03:27
 */
public class QweatherVo {

    private String temp;

    private String humidity;

    private String icon;

    private String text;

    private String time;


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
