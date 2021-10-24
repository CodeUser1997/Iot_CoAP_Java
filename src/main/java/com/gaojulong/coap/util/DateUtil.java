package com.gaojulong.coap.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:
 *
 * @author xiongsulong
 * @create 2021-10-24 17:57
 */
public class DateUtil {

    public static String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";


    public static String format(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }
}
