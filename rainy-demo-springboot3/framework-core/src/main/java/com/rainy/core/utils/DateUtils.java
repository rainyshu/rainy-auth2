package com.rainy.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 */
public class DateUtils {

    // 常用日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期加减天数
     * @param date 原始日期
     * @param days 要增加的天数（负数表示减）
     * @return 计算后的日期
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 日期加减月数
     * @param date 原始日期
     * @param months 要增加的月数（负数表示减）
     * @return 计算后的日期
     */
    public static Date addMonths(Date date, int months) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 日期加减年数
     * @param date 原始日期
     * @param years 要增加的年数（负数表示减）
     * @return 计算后的日期
     */
    public static Date addYears(Date date, int years) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间的天数差
     * @param start 开始日期
     * @param end 结束日期
     * @return 天数差（end - start）
     */
    public static long daysBetween(Date start, Date end) {
        long diff = resetTime(end).getTime() - resetTime(start).getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    /**
     * 判断日期是否是周末
     * @param date 要判断的日期
     * @return 如果是周六或周日返回true
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = getCalendar(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * 获取当月的第一天
     * @param date 任意日期
     * @return 当月第一天的日期
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return resetTime(calendar.getTime());
    }

    /**
     * 获取当月的最后一天
     * @param date 任意日期
     * @return 当月最后一天的日期
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return resetTime(calendar.getTime());
    }

    /**
     * 日期转字符串
     * @param date 日期
     * @param pattern 格式模式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串转日期
     * @param dateStr 日期字符串
     * @param pattern 格式模式
     * @return 解析后的日期
     */
    public static Date parse(String dateStr, String pattern) {
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 计算年龄（周岁）
     * @param birthDate 出生日期
     * @param currentDate 当前日期
     * @return 年龄
     */
    public static int calculateAge(Date birthDate, Date currentDate) {
        Calendar birth = getCalendar(birthDate);
        Calendar current = getCalendar(currentDate);

        int yearDiff = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (yearDiff < 0) return 0;

        int monthDiff = current.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
        int dayDiff = current.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH);

        if (monthDiff < 0 || (monthDiff == 0 && dayDiff < 0)) {
            yearDiff--;
        }
        return yearDiff;
    }

    /**
     * 判断是否是闰年
     * @param date 日期
     * @return 是否是闰年
     */
    public static boolean isLeapYear(Date date) {
        Calendar calendar = getCalendar(date);
        int year = calendar.get(Calendar.YEAR);
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // 辅助方法：获取Calendar实例
    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // 辅助方法：重置时间为00:00:00.000
    private static Date resetTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // 示例用法
    public static void main(String[] args) throws ParseException {
        Date now = new Date();
        System.out.println("当前时间: " + format(now, DATETIME_FORMAT));

        Date tomorrow = addDays(now, 1);
        System.out.println("明天: " + format(tomorrow, DATETIME_FORMAT));

        Date lastMonth = addMonths(now, -1);
        System.out.println("上月今日: " + format(lastMonth, DATETIME_FORMAT));

        System.out.println("是否周末: " + isWeekend(now));
        System.out.println("本月第一天: " + format(getFirstDayOfMonth(now), DATE_FORMAT));

        Date birth = parse("1990-06-15", DATE_FORMAT);
        System.out.println("年龄: " + calculateAge(birth, now));

        System.out.println("日期差: " + daysBetween(parse("2023-01-01", DATE_FORMAT),
                parse("2023-01-02", DATE_FORMAT)) + "天");
    }

}
