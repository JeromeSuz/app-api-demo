package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关操作工具类
 *
 * @author jerome
 * @date 2017/2/25 17:02
 */
public class DateUtils {

    public static void main(String[] args) {

        System.out.println("shortDate = " + shortDate(new Date()));
        System.out.println("longDate = " + longDate(new Date()));
        System.out.println("getTodaySurplusSeconds = " + getTodaySurplusSeconds());

    }

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD = "yyyy-MM-dd";

    /**
     * 短日期格式（yyyy-MM-dd）
     *
     * @param date 当前时间
     * @return 格式化后的字符串
     */
    public static String shortDate(Date date) {
        return new SimpleDateFormat(YYYYMMDD).format(date);
    }

    /**
     * 长日期格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param date 当前时间
     * @return 格式化后的字符串
     */
    public static String longDate(Date date) {
        return new SimpleDateFormat(YYYYMMDDHHMMSS).format(date);
    }

    /**
     * 格式化日期
     *
     * @param format 要格式的格式比如"yyyy-MM-dd hh:mm:ss"
     * @return 字符串
     */
    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format.trim()).format(date);
    }

    /**
     * 字符串转日期
     *
     * @param strDate   字符串的日期
     * @param formatStr 格式 比如"yyyy-MM-dd hh:mm:ss"
     * @return Date
     */
    public static Date strToDate(String strDate, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间比较
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return true:开始时间小于结束时间
     * false:开始时间大于/等于结束时间
     */
    public static boolean compareDate(Date startDate, Date endDate) {
        if (startDate.before(endDate)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 日期加几个月
     *
     * @param date       原来的日期
     * @param plusMonths 要加几个月
     * @return
     */
    public static Date plusMonths(Date date, int plusMonths) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusMonths(plusMonths).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期减几个月
     *
     * @param date        原来的日期
     * @param minusMonths 要加几个月
     * @return
     */
    public static Date minusMonths(Date date, int minusMonths) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.minusMonths(minusMonths).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期加几天
     *
     * @param date     原来的日期
     * @param plusDays 要加几天
     * @return
     */
    public static Date plusDays(Date date, int plusDays) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusDays(plusDays).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期减几天
     *
     * @param date      原来的日期
     * @param minusDays 要加几天
     * @return
     */
    public static Date minusDays(Date date, int minusDays) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.minusDays(minusDays).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期加几个小时
     *
     * @param date      原来的日期
     * @param plusHours 要加几个小时
     * @return
     */
    public static Date plusHours(Date date, int plusHours) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusHours(plusHours).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期减去几个小时
     *
     * @param date       原来的日期
     * @param minusHours 要减几个小时
     * @return
     */
    public static Date minusHours(Date date, int minusHours) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.minusHours(minusHours).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期加几分钟
     *
     * @param date        原来的日期
     * @param plusMinutes 要加几分钟
     * @return
     */
    public static Date plusMinutes(Date date, int plusMinutes) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusMinutes(plusMinutes).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期减去几分钟
     *
     * @param date         原来的日期
     * @param minusMinutes 要减几分钟
     * @return
     */
    public static Date minusMinutes(Date date, int minusMinutes) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.minusMinutes(minusMinutes).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期加几秒
     *
     * @param date        原来的日期
     * @param plusSeconds 要加几秒
     * @return
     */
    public static Date plusSeconds(Date date, int plusSeconds) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusSeconds(plusSeconds).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期减去几秒
     *
     * @param date         原来的日期
     * @param minusSeconds 要减几秒
     * @return
     */
    public static Date minusSeconds(Date date, int minusSeconds) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.minusSeconds(minusSeconds).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取某时间的中文星期
     *
     * @param date 日期
     * @return 星期一 星期二 ...
     */
    public static String getWeekByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取两个日期之间所差的天数
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 所差的天数(非负整数)
     */
    public static int dateNum(Date from, Date to) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();

        calendar.setTime(to);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date toDate = calendar.getTime();
        int diff = Math.abs((int) ((fromDate.getTime() - toDate.getTime()) / (24 * 3600 * 1000)));
        return diff;
    }

    /**
     * 获取今日剩余秒数
     *
     * @author jerome
     * @date 2017/2/25 17:12
     */
    public static int getTodaySurplusSeconds() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date now = new Date();
        long interval = (cal.getTimeInMillis() - now.getTime()) / 1000;
        return (int) interval;
    }
}
