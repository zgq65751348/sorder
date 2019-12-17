package com.tlongx.sorder.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DATE_NUMBER_PATTERN = "yyyyMMdd";
    public static final String DATE_TIME_PATTERN   = "yyyyMMddHHmmss";

    /**
     * 获取时间字符串，格式：yyyyMMddHHmmss
     */
    public static String getDateAndTime() {
        return getFormatString(DATE_TIME_PATTERN);
    }

    /**
     * 获取时间字符串，格式：yyyyMMdd
     */
    public static String getDate() {
        return getFormatString(DATE_NUMBER_PATTERN);
    }

    /**
     * 返回pattern格式的字符串
     *
     * @param pattern
     * @return
     */
    private static String getFormatString(String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
