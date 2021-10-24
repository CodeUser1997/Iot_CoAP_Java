package com.gaojulong.coap.model;

import lombok.Data;

/**
 * 描述:
 *
 * @author xiongsulong
 * @create 2021-10-17 03:27
 */
@Data
public class QweatherVo {

    private String temp;

    private String humidity;

    private String icon;

    private String text;

    private String time;


    private String fxDate;

    private String tempMin;

    private String tempMax;

    private String iconDay;

    private String textDay;

}
