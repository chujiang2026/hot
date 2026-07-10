package com.hot.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期工具类, 继承 org.apache.commons.lang3.time.DateUtils 类。
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串，格式 yyyy-MM-dd。
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串，使用指定格式，如 "yyyy-MM-dd HH:mm:ss"。
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 按指定格式格式化日期，pattern 缺省为 yyyy-MM-dd。
     */
    public static String formatDate(Date date, String... pattern) {
        if (date == null) {
            return "";
        }
        if (pattern != null && pattern.length > 0) {
            return DateFormatUtils.format(date, pattern[0]);
        }
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期时间，格式 yyyy-MM-dd HH:mm:ss。
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串，格式 HH:mm:ss。
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串，格式 yyyy-MM-dd HH:mm:ss。
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将字符串按内置的多种常见格式解析为日期，解析失败返回 null。
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), PARSE_PATTERNS);
        } catch (ParseException e) {
            return null;
        }
    }

}
