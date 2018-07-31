package com.example.a83776.demo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/28 9:40
*/

public class DateTimeFormatUtil {
    public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String HH_MM = "HH:mm";
    public static final String YY_MM_DD = "yy-MM-dd";
    public static final String YYMMDD = "yy.MM.dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";

    /**
     * @param date
     * @描述 —— 格式化日期对象
     */
    public static Date date2date(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * @param date
     * @return
     * @描述 —— 时间对象转换成字符串
     */
    public static String date2string(Date date, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * @param timestamp
     * @param formatStr
     * @return
     * @描述 —— sql时间对象转换成字符串
     */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    /**
     * @param dateString
     * @param formatStr
     * @return
     * @描述 —— 字符串转换成时间对象
     */
    public static Date string2date(String dateString, String formatStr) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            formateDate = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formateDate;
    }

    /**
     * @param date
     * @return
     * @描述 —— Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * @return
     */
    public static String getNowHour(String dataStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(HH_MM);
        return sdf.format(new Date(getMillions(dataStr, YYYY_MM_DD_HH_MM_SS)));
    }

    /**
     * @return
     * @描述 —— 获得当前年份
     */
    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
        return sdf.format(new Date());
    }

    /**
     * @return
     * @描述 —— 获得当前月份
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM);
        return sdf.format(new Date());
    }

    /**
     * @return
     * @描述 —— 获得当前月日份
     */
    public static String getNowMonthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_HH_MM);
        return sdf.format(new Date());
    }


    /**
     * @return
     * @描述 —— 获得当前月日份
     */
    public static String getNowYearMonthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(new Date());
    }


    /**
     * @return
     * @描述 —— 获得当前日期中的日
     */
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD);
        return sdf.format(new Date());
    }

    /**
     * @param time
     * @return
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = cal.getTimeInMillis() - time;
        if (timel / 1000 < 60) {
            return "1分钟以内";
        } else if (timel / 1000 / 60 < 60) {
            return timel / 1000 / 60 + "分钟前";
        } else if (timel / 1000 / 60 / 60 < 24) {
            return timel / 1000 / 60 / 60 + "小时前";
        } else {
            return timel / 1000 / 60 / 60 / 24 + "天前";
        }
    }

    /**
     * @param time
     * @return
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    public static String getTimeOffset(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = time - cal.getTimeInMillis();

        if (timel <= 0) {//超过了系统当前时间显示0不显示负数
            return "0";
        } else {
            int s = (int) (timel / 1000);
            int m = (int) (s / 60);//分总数107
            int h = (int) (m / 60);
            m = m % 60;//应该求余107%60 = 47
            String timeStr;
            if (h > 0) {
                timeStr = h + "小时" + m + "分"/*+ s+ ""*/;
            } else {
                if (m == 0) {
                    timeStr = 1 + "分"/*+ s+ ""*/;
                } else {
                    timeStr = m + "分"/*+ s+ ""*/;
                }
            }

            return timeStr;
        }
    }

    /**
     * time--->HH:MM:SS
     */
    public static String getTimeOffsetHHMMSS(long time) {
        if (time <= 0) {//超过了系统当前时间显示0不显示负数
            return "0";
        } else {
            int s = (int) (time / 1000);//秒数
            System.out.println(s);
            int m = (int) (s / 60);//分数
            System.out.println(m);
            int h = (int) (m / 60);//小时数
            System.out.println(h);
            m = m % 60;//应该求余107%60 = 47
            s = s % 60;
            String timeStr;
            String h_ = (h < 10) ? ("0" + h) : String.valueOf(h);
            String m_ = (m < 10) ? ("0" + m) : String.valueOf(m);
            String s_ = (s < 10) ? ("0" + s) : String.valueOf(s);
            timeStr = h_ + ":" + m_ + ":" + s_;
            return timeStr;
        }
    }

    public static String getDefault(long time) {
        return date2string(new Date(time), "yy-MM-dd HH:mm");
    }

    public static String getTimeStr(long time, String format) {
        return date2string(new Date(time), format);
    }

    public static String getTimeStr(String date, String newPattern) {
        if (date == null || newPattern == null)
            return "";
        Date d = string2date(date, "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);        // 实例化模板对象
        return sdf2.format(d);
    }

    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * String strDate = "2011-11-11 10:11:30.345" ;
     * // 准备第一个模板，从字符串中提取出日期数字
     * String pat1 = "yyyy-MM-dd HH:mm:ss.SSS" ;
     * // 准备第二个模板，将提取后的日期数字变为指定的格式
     * String pat2 = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒" ;
     * System.out.println(StringPattern(strDate,pat1,pat2));
     *
     * @param date       String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static String stringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);        // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(date);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }
        return sdf2.format(d);
    }

    public static String getDefault(String timeStr) {
        if (timeStr == null) {
            return "";
        }
        //return date2string(string2date(timeStr,YYYY_MM_DD_HH_MM),YYYY_MM_DD_HH_MM);
        return date2string(string2date(timeStr, "yy-MM-dd HH:mm"), "yy-MM-dd HH:mm");
    }

    public static String getDefault2(String timeStr) {
        if (timeStr == null) {
            return "";
        }
        return date2string(string2date(timeStr, "yy-MM-dd HH:mm"), "yy-MM-dd HH:mm");
    }

    public static long getMillions(String dataString) {
        return string2date(dataString, "yyyy-MM-dd").getTime();
    }

    public static long getMillions(String dataString, String format) {
        return string2date(dataString, format).getTime();
    }

    /**
     * 整数(秒数)转换为时分秒格式(xx:xx:xx)
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static String MSToStringTime(int ms) {
        String str;
        int minutes = (ms / 1000) / 60, seconds = (ms / 1000) % 60;
        if (minutes >= 10 && seconds >= 10) {
            str = "" + minutes + ":" + seconds;
        } else if (minutes >= 10 && seconds < 10) {
            str = "" + minutes + ":0" + seconds;
        } else if (minutes < 10 && seconds >= 10) {
            str = "0" + minutes + ":" + seconds;
        } else {
            str = "0" + minutes + ":0" + seconds;
        }
        return str;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 返回两个日期之间的时长（分钟）
     * @param time1（小）
     * @param time2（大）
     * @param formatStr（格式）
     * @return
     */
    public static long timeBetween(String time1, String time2, String formatStr){
        Date date1 = string2date(time1, formatStr);
        Date date2 = string2date(time2, formatStr);
        return (date2.getTime() - date1.getTime())/(1000*60);
    }

    /**
     * 字符串的日期格式的计算
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    private static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    //-------------------------------------------------------

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    //测试
    private void test(String[] args) {
        String timeStamp = timeStamp();
        System.out.println("timeStamp=" + timeStamp); //运行输出:timeStamp=1470278082
        System.out.println(System.currentTimeMillis());//运行输出:1470278082980
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);//运行输出:date=2016-08-04 10:34:42

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(timeStamp2);  //运行输出:1470278082
    }
    //-------------------------------------------------------

    /**
     * 比较时间大小
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(Date date1,Date date2){
        return date1.before(date2);
    }

    /**
     * 和当前时间比较
     * @param date
     * @return
     */
    public static boolean isBefore(Date date){
        if(date == null){
            return false;
        }
        Date date1 = string2date(getNowYearMonthDay(), DateTimeFormatUtil.YYYY_MM_DD_HH_MM_SS);
        if(date1.before(date)){
            return true;
        }
        return false;
    }
}
