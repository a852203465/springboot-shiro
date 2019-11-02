package com.unionman.shiro.utils;

import com.unionman.shiro.constants.NumberConstant;
import com.unionman.shiro.enums.ResponseEnum;
import com.unionman.shiro.exception.SpringbootShiroException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: 时间操作工具类
 * @date 2019/01/07 16:54:22
 * @author Rong.Jia
 */
@Slf4j
public class DateUtils {

    private static  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private static  SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * @description: 获取当前时间 后推或前移的时间
     * @param offset 偏移量
     * @param shiftUnit 时间单位
     * @return Long 偏移后的时间
     */
    public static Long getPassageTime(Integer shiftUnit, Integer offset){

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(shiftUnit, offset);

        return c.getTime().getTime();

    }

    /**
     * @description: 将时间戳转换为时间
     * @param s 时间戳
     * @date 2018/11/23 14:22
     */
    public static String stampToDateString(Long s){
        String res;
        Date date = new Date(s);
        synchronized (simple_date_format){
            res = simple_date_format.format(date);
        }

        return res;
    }

    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * @description: 根据指定时间 后推或前移的时间
     * @param date 时间
     * @param offset 偏移量
     * @param shiftUnit 时间单位
     * @return Long 偏移后的时间
     */
    public static Date getPassageTime(Date date, Integer shiftUnit, Integer offset){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(shiftUnit, offset);

        return c.getTime();

    }

    /**
     * @description: 判断指定时间是否在当月
     * @param timeStamp 时间戳
     * @return boolean true/false
     */
    public static boolean whetherThisMonth(Long timeStamp){

        boolean flag = false;

        // 当前时间
        String date;
        synchronized (formatter){
            date =   formatter.format(new Date());
        }


        //截取系统月份
        String newDateMouth = date.substring(4, 6);

        //需要判断的时间
        String date2;
        synchronized (formatter){
            date2  = formatter.format(stampToDate(timeStamp));
        }

        String specifiedMouth = date2.substring(4, 6);

        if(newDateMouth.equals(specifiedMouth)){
            flag = true;
        }
        return flag;
    }

    /**
     * 将时间戳转换为时间
     */
    public static Date stampToDate(Long timeStamp){
        Date date = new Date(timeStamp);
        return date;
    }

    /**
     * /获取当天的开始时间
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     * @return
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    /**
     * @description: 获取本周的开始时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * @description: 获取本周的结束时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getEndDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * @description: 获取本周的开始，结束时间
     * @return List<Date> index:0 开始时间, index:1 结束时间
     */
    public static List<Date> getStartAndEndDayOfWeek(){

        List<Date> dates = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        dates.add(cal.getTime());
        cal.add(Calendar.DATE, 6);
        dates.add(cal.getTime());
        return dates;
    }

    /**
     * @description: 获取本月的开始时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * @description: 获取指定时间的月初的开始时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getBeginDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDayStartTime(calendar.getTime());
    }

    /**
     * @description: 获取指定时间的月末时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDayEndTime(calendar.getTime());
    }

    /**
     * @description: 获取本月的结束时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * @description: 获取本年的开始时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    /**
     * @description: 获取本年的结束时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * @description: 获取某个日期的开始时间
     * @param d 时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * @description: 获取某个日期的结束时间
     * @param d 时间
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * @description: 获取某年某月的第一天
     * @param year 年
     * @param month 月
     * @return
     */
    public static Date getStartMonthDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getTime();
    }

    /**
     * @description: 获取某年某月的最后一天
     * @param year 年
     * @param month 月
     * @return
     */
    public static Date getEndMonthDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * @description: 获取今年是哪一年
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * @description: 获取本月是哪一月
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * @description: 两个日期相减得到的天数
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return int 天数
     */
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new SpringbootShiroException(ResponseEnum.PARAMETER_ERROR);
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();

        return days;
    }

    /**
     * @description: 两个日期相减得到的毫秒数
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return long 毫秒数
     */
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    /**
     * @description: 获取两个日期中的最大日期
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return Date 最大日期
     */
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    /**
     * @description: 获取两个日期中的最小日期
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return  Date 最小日期
     */
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    /**
     * @description: 返回某月该季度的第一个月
     * @param date
     * @return
     */
    public static Date getFirstSeasonDate(Date date) {
        final int[] seasons = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = seasons[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    /**
     * @description: 返回某个日期下几天的日期
     * @param date
     * @param i
     * @return
     */
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    /**
     * @description: 返回某个日期前几天的日期
     * @param date
     * @param i
     * @return
     */
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    /**
     * @description: 获取某年某月到某年某月按天的切片日期集合（间隔天数的日期集合）
     * @param beginYear
     * @param beginMonth
     * @param endYear
     * @param endMonth
     * @param k
     * @return
     */
    public static List<List<Date>> getTimeList(int beginYear, int beginMonth, int endYear,
                                               int endMonth, int k) {
        List<List<Date>> list = new ArrayList<List<Date>>();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < NumberConstant.TWELVE; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < NumberConstant.TWELVE; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    /**
     * @description: 获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
     * @param beginYear
     * @param beginMonth
     * @param k
     * @return
     */
    public static List<Date> getTimeList(int beginYear, int beginMonth, int k) {
        List<Date> list = new ArrayList<Date>();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

}
