package com.yinhu.tools;

import java.text.*;
import java.util.*;

/**
 * 日期帮助类
 * @author 张卓
 */
public class TimeHelp {
    private static String CurrentTime;

    private static String CurrentDate;

    /**
     * 得到当前的年份 返回格式:yyyy
     *
     * @return String
     */
    public static String getCurrentYear() {
        java.util.Date NowDate = new java.util.Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的月份 返回格式:MM
     *
     * @return String
     */
    public static String getCurrentMonth() {
        java.util.Date NowDate = new java.util.Date();

        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的日期 返回格式:dd
     *
     * @return String
     */
    public static String getCurrentDay() {
        java.util.Date NowDate = new java.util.Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的时间，精确到毫秒,共19位 返回格式:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getCurrentTime() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CurrentTime = formatter.format(NowDate);
        return CurrentTime;
    }

    /**
     * 得到当前的时间，精确到毫秒,共16位 返回格式:yyyy-MM-dd HH:mm
     *
     * @return String
     */
    public static String getCurrentTimeZhi() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CurrentTime = formatter.format(NowDate);
        return CurrentTime;
    }

    /**
     * 得到当前的时间，精确到毫秒,共14位 返回格式:yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getCurrentCompactTime() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        CurrentTime = formatter.format(NowDate);
        return CurrentTime;
    }

    /**
     * 得到前一天的时间，精确到毫秒,共14位 返回格式:yyyyMMddHHmmss
     *
     * @return String
     */
    @SuppressWarnings("static-access")
    public static String getYesterdayCompactTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DATE, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        CurrentTime = formatter.format(cal.getTime());
        return CurrentTime;
    }



    /**
     * 得到当前的年月日,共10位 返回格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String getCurrentYearDateDay() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        CurrentDate = formatter.format(NowDate);
        return CurrentDate;
    }

    /**
     * 得到当前的年月日,共10位 返回格式：yyyyMMdd
     *
     * @return String
     */
    public static String getCurrentYearDateDay1() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        CurrentDate = formatter.format(NowDate);
        return CurrentDate;
    }

    /**
     * 得到当前的年月,共10位 返回格式：yyyy-MM
     * @return
     */
    public static String getCurrentYearMonth() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        CurrentDate = formatter.format(NowDate);
        return CurrentDate;
    }

    /**
     * 得到当前的日期,共10位 返回格式：yyyy年MM月dd日
     *
     * @return String
     */
    public static String getCurrentDate() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        CurrentDate = formatter.format(NowDate);
        return CurrentDate;
    }

    /**
     * 得到当月的第一天,共10位 返回格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String getCurrentFirstDate() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01");
        CurrentDate = formatter.format(NowDate);
        return CurrentDate;
    }

    /**
     * 得到当月的最后一天,共10位 返回格式：yyyy-MM-dd
     *
     * @return String
     * @throws ParseException
     */
    public static String getCurrentLastDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = null;
        try {
            java.util.Date date = formatter.parse(getCurrentFirstDate());
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate(calendar.getTime());

    }

    /**
     * 常用的格式化日期
     *
     * @param date
     *            Date
     * @return String
     */
    public static String formatDate(java.util.Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 以指定的格式来格式化日期
     *
     * @param date
     *            Date
     * @param format
     *            String
     * @return String
     */
    public static String formatDateByFormat(java.util.Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 得到当前日期加上某一个整数的日期，整数代表天数 输入参数：currentdate : String 格式 yyyy-MM-dd add_day :
     * int 返回格式：yyyy-MM-dd
     */
    public static String addDay(String currentdate, int add_day) {
        GregorianCalendar gc = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int year, month, day;

        try {
            year = Integer.parseInt(currentdate.substring(0, 4));
            month = Integer.parseInt(currentdate.substring(5, 7)) - 1;
            day = Integer.parseInt(currentdate.substring(8, 10));

            gc = new GregorianCalendar(year, month, day);
            gc.add(GregorianCalendar.DATE, add_day);

            return formatter.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 得到当前日期减上某一个整数的日期，整数代表天数 输入参数：currentdate : String 格式 yyyy-MM-dd subtraction_day :
     * int 返回格式：yyyy-MM-dd
     */
    public static String subtractionDay(String currentdate, int subtraction_day) {
        GregorianCalendar gc = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int year, month, day;

        try {
            year = Integer.parseInt(currentdate.substring(0, 4));
            month = Integer.parseInt(currentdate.substring(5, 7)) - 1;
            day = Integer.parseInt(currentdate.substring(8, 10));

            gc = new GregorianCalendar(year, month, day);
            gc.set(year, month, day-subtraction_day);
            return formatter.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到当前日期加上某一个整数的日期，整数代表月数 输入参数：currentdate : String 格式 yyyy-MM-dd add_month
     * : int 返回格式：yyyy-MM-dd
     */
    public static String addMonth(String currentdate, int add_month) {
        GregorianCalendar gc = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int year, month, day;

        try {
            year = Integer.parseInt(currentdate.substring(0, 4));
            month = Integer.parseInt(currentdate.substring(5, 7)) - 1;
            day = Integer.parseInt(currentdate.substring(8, 10));

            gc = new GregorianCalendar(year, month, day);
            gc.add(GregorianCalendar.MONTH, add_month);

            return formatter.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到endTime比beforeTime晚几个月，整数代表月数 输入参数：endTime、beforeTime : String
     * 格式前7位的格式为 yyyy-MM
     */
    public static int monthDiff(String beforeTime, String endTime) {
        if (beforeTime == null || endTime == null) {
            return 0;
        }
        int beforeYear, endYear, beforeMonth, endMonth;
        try {
            beforeYear = Integer.parseInt(beforeTime.substring(0, 4));
            endYear = Integer.parseInt(endTime.substring(0, 4));
            beforeMonth = Integer.parseInt(beforeTime.substring(5, 7)) - 1;
            endMonth = Integer.parseInt(endTime.substring(5, 7)) - 1;
            return (endYear - beforeYear) * 12 + (endMonth - beforeMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到当前日期加上某一个整数的分钟 输入参数：currentdatetime : String 格式 yyyy-MM-dd HH:mm:ss
     * add_minute : int 返回格式：yyyy-MM-dd HH:mm:ss
     */
    public static String addMinute(String currentdatetime, int add_minute) {
        GregorianCalendar gc = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int year, month, day, hour, minute, second;

        try {
            year = Integer.parseInt(currentdatetime.substring(0, 4));
            month = Integer.parseInt(currentdatetime.substring(5, 7)) - 1;
            day = Integer.parseInt(currentdatetime.substring(8, 10));

            hour = Integer.parseInt(currentdatetime.substring(11, 13));
            minute = Integer.parseInt(currentdatetime.substring(14, 16));
            second = Integer.parseInt(currentdatetime.substring(17, 19));

            gc = new GregorianCalendar(year, month, day, hour, minute, second);
            gc.add(GregorianCalendar.MINUTE, add_minute);

            return formatter.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 得到当前日期加上某一个整数的分钟 输入参数：currentdatetime : String 格式 yyyy-MM-dd HH:mm
     * add_minute : int 返回格式：yyyy-MM-dd HH:mm:ss
     */
    public static String addMinuteNoS(String currentdatetime, int add_minute) {
        GregorianCalendar gc = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int year, month, day, hour, minute;

        try {
            year = Integer.parseInt(currentdatetime.substring(0, 4));
            month = Integer.parseInt(currentdatetime.substring(5, 7)) - 1;
            day = Integer.parseInt(currentdatetime.substring(8, 10));

            hour = Integer.parseInt(currentdatetime.substring(11, 13));
            minute = Integer.parseInt(currentdatetime.substring(14, 16));

            gc = new GregorianCalendar(year, month, day, hour, minute);
            gc.add(GregorianCalendar.MINUTE, add_minute);

            return formatter.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 得到当前日期加上某一个整数的秒 输入参数：currentdatetime : String 格式 yyyy-MM-dd HH:mm:ss
     * add_second : int 返回格式：yyyy-MM-dd HH:mm:ss
     */
    public static String addSecond(String currentdatetime, int add_second) {
        GregorianCalendar gc = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int year, month, day, hour, minute, second;

        try {
            year = Integer.parseInt(currentdatetime.substring(0, 4));
            month = Integer.parseInt(currentdatetime.substring(5, 7)) - 1;
            day = Integer.parseInt(currentdatetime.substring(8, 10));

            hour = Integer.parseInt(currentdatetime.substring(11, 13));
            minute = Integer.parseInt(currentdatetime.substring(14, 16));
            second = Integer.parseInt(currentdatetime.substring(17, 19));

            gc = new GregorianCalendar(year, month, day, hour, minute, second);
            gc.add(GregorianCalendar.SECOND, add_second);

            return formatter.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 转换成date类型日期  格式yyyy-MM-dd
     * @param sDate
     * @return
     */
    public static Date parseDate(String sDate) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = bartDateFormat.parse(sDate);
            return date;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * 解析日期及时间
     *
     * @param sDateTime
     *            日期及时间字符串
     * @return 日期
     */
    public static Date parseDateTime(String sDateTime) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        try {
            Date date = bartDateFormat.parse(sDateTime);
            return date;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * 取得一个月的天数 date:yyyy-MM-dd
     *
     * @throws ParseException
     */
    public static int getTotalDaysOfMonth(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();

        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        calendar.setTime(date);
//		int month = calendar.get(Calendar.MONTH) + 1; // 月份
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 天数
        return day;
    }
    /**
     * 取得开始时间和结束时间相差多少天
     * @param startDate
     * @param endDate
     * @return 相差多少天
     */
    public static long getDateSubDay(String startDate, String endDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long theday = 0;
        try {
            calendar.setTime(sdf.parse(startDate));
            long timethis = calendar.getTimeInMillis();
            calendar.setTime(sdf.parse(endDate));
            long timeend = calendar.getTimeInMillis();
            theday = (timethis - timeend) / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theday;
    }

    /**
     * 取得开始时间和结束时间相差多少小时
     * @param startDate
     * @param endDate
     * @return 相差多少小时
     */
    public static long getDateSubHour(String startDate, String endDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        long theHour = 0;
        try {
            calendar.setTime(sdf.parse(startDate));
            long timethis = calendar.getTimeInMillis();
            calendar.setTime(sdf.parse(endDate));
            long timeend = calendar.getTimeInMillis();
            theHour = (timethis - timeend) / (1000 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theHour;
    }


    /**
     * 取得开始时间和结束时间相差多少分钟
     * @param startDate
     * @param endDate
     * @return 相差多少分钟
     */
    public static long getDateSubMinute(String startDate, String endDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long theMinute = 0;
        try {
            calendar.setTime(sdf.parse(startDate));
            long timethis = calendar.getTimeInMillis();
            calendar.setTime(sdf.parse(endDate));
            long timeend = calendar.getTimeInMillis();
            theMinute = (timethis - timeend) / (1000 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theMinute;
    }

    public static void main(String[] args) {
        System.out.println(TimeHelp.subtractionDay(TimeHelp.getCurrentTimeZhi(), 10));
    }
}
