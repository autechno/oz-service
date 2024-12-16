package com.aucloud.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Integer MILLISECOND_OF_DAY = 86400000;

    public static String format(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }
    /**
     * 本月周数
     */
    public static long getThisMonthWeekNum(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int actualMaximum = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        return actualMaximum;
    }
    public static long getThisMonthWeekNum(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int actualMaximum = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        return actualMaximum;
    }
    /**
     * 本月最后时间
     * @return
     */
    public static long getMonthLastTime(){//获取本月最后一天23:59:59
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long tt = calendar.getTime().getTime();
        return tt;
    }
    public static long getMonthLastTime(Date date){//获取本月最后一天23:59:59
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long tt = calendar.getTime().getTime();
        return tt;
    }
    /**
     * 上月最后时间
     * @return
     */
    public static long getLastMonthLastTime(){//获取本月最后一天23:59:59
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -1);
        long tt = calendar.getTime().getTime();
        return tt;
    }
    /**
     * 上月最后时间
     * @return
     */
    public static long getLastMonthLastTime(Date date){//获取本月最后一天23:59:59
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -1);
        long tt = calendar.getTime().getTime();
        return tt;
    }

    /**
     * 本月第一个时间
     * @return
     */
    public static long getMonthFirstTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        long monthZero = calendar.getTime().getTime();
        return monthZero;
    }
    public static long getMonthFirstTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        long monthZero = calendar.getTime().getTime();
        return monthZero;
    }
    /**
     * 上月第一个时间
     * @return
     */
    public static long getLastMonthFirstTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -1);
        long monthZero = calendar.getTime().getTime();
        return monthZero;
    }
    public static long getLastMonthFirstTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -1);
        long monthZero = calendar.getTime().getTime();
        return monthZero;
    }

    /**
     * 本周最后一秒
     * @return
     */
    public static long getWeekLastTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return calendar.getTime().getTime()+60*60*24*1000;
    }
    /**
     * 本周最后一秒
     * @return
     */
    public static long getWeekLastTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return calendar.getTime().getTime()+60*60*24*1000;
    }
    /**
     * 本周第一秒
     * @return
     */
    public static long getWeekFirstTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime().getTime();
    }
    /**
     * 本周第一秒
     * @return
     */
    public static long getWeekFirstTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime().getTime();
    }
    /**
     * 本季度第一秒
     * @return
     */
    public static long getQuarterFirstTime(){
        int month = new Date().getMonth()+1;
        int quarterTh = month/3;
        int a = month%3;
        if(a!=0){
            quarterTh+=1;
        }
        System.out.println(month);
        System.out.println(quarterTh);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(calendar3.get(Calendar.YEAR),calendar3.get(Calendar.MONDAY), calendar3.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar3.set(Calendar.DAY_OF_MONTH,calendar3.getActualMinimum(Calendar.DAY_OF_MONTH));
        int firstMonth = (quarterTh-1)*3+1;
        System.out.println(firstMonth);
        calendar3.add(Calendar.MONTH, firstMonth-month);
        return calendar3.getTime().getTime();
    }
    /**
     * 本季度最后一秒
     * @return
     */
    public static long getQuarterLastTime(){
        int month = new Date().getMonth()+1;
        int quarterTh = month/3;
        int a = month%3;
        if(a!=0){
            quarterTh+=1;
        }
        System.out.println(month);
        System.out.println(quarterTh);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(calendar3.get(Calendar.YEAR),calendar3.get(Calendar.MONDAY), calendar3.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar3.set(Calendar.DAY_OF_MONTH,calendar3.getActualMaximum(Calendar.DAY_OF_MONTH));
        int firstMonth = (quarterTh-1)*3+3;
        System.out.println(firstMonth);
        calendar3.add(Calendar.MONTH, firstMonth-month);
        return calendar3.getTime().getTime();
    }
    /**
     * 所在半年第一秒
     * @return
     */
    public static long getHalfYearFirstTime(){
        int month2 = new Date().getMonth()+1;
        int quarterTh2 = month2/6;
        int a2 = month2%6;
        if(a2!=0){
            quarterTh2+=1;
        }
        System.out.println(month2);
        System.out.println(quarterTh2);
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(calendar4.get(Calendar.YEAR),calendar4.get(Calendar.MONDAY), calendar4.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar4.set(Calendar.DAY_OF_MONTH,calendar4.getActualMinimum(Calendar.DAY_OF_MONTH));
        int firstMonth2 = (quarterTh2-1)*6+1;
        System.out.println(firstMonth2);
        calendar4.add(Calendar.MONTH, firstMonth2-month2);
        return calendar4.getTime().getTime();
    }
    /**
     * 所在半年最后一秒
     * @return
     */
    public static long getHalfYearLastTime(){
        int month2 = new Date().getMonth()+1;
        int quarterTh2 = month2/6;
        int a2 = month2%6;
        if(a2!=0){
            quarterTh2+=1;
        }
        System.out.println(month2);
        System.out.println(quarterTh2);
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(calendar4.get(Calendar.YEAR),calendar4.get(Calendar.MONDAY), calendar4.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar4.set(Calendar.DAY_OF_MONTH,calendar4.getActualMaximum(Calendar.DAY_OF_MONTH));
        int firstMonth2 = (quarterTh2-1)*6+6;
        System.out.println(firstMonth2);
        calendar4.add(Calendar.MONTH, firstMonth2-month2);
        return calendar4.getTime().getTime();
    }
    /**
     * 所在年第一秒
     * @return
     */
    public static long getYearFirstTime(){
        int month2 = new Date().getMonth()+1;
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(calendar4.get(Calendar.YEAR),calendar4.get(Calendar.MONDAY), calendar4.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        calendar4.set(Calendar.DAY_OF_MONTH,calendar4.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar4.add(Calendar.MONTH, 1-month2);
        return calendar4.getTime().getTime();
    }
    /**
     * 所在年最后一秒
     * @return
     */
    public static long getYearLastTime(){
        int month2 = new Date().getMonth()+1;
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(calendar4.get(Calendar.YEAR),calendar4.get(Calendar.MONDAY), calendar4.get(Calendar.DAY_OF_MONTH), 23, 59,59);
        calendar4.set(Calendar.DAY_OF_MONTH,calendar4.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar4.add(Calendar.MONTH, 12-month2);
        return calendar4.getTime().getTime();
    }

    /**
     * 今天第一秒
     * @return
     */
    public static long getThisDayFirstTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero;
    }
    public static long getThisDayFirstTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero;
    }
    /**
     * 今天最后一秒
     * @return
     */
    public static long getThisDayLastTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero+1000*60*60*24-1000;
    }
    public static long getThisDayLastTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero+1000*60*60*24-1000;
    }
    /* public static void main(String[] args) {
     *//*ArrayList arrayList = new ArrayList();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        String join = StringUtils.join(arrayList.toArray(), ",");
        System.out.println(join);*//*
        System.out.println(simpleDateFormat.format(new Date(getThisDayFirstTime())));
        System.out.println(simpleDateFormat.format(new Date(getThisDayLastTime())));
    }*/
    /**
     * 昨天第一秒
     * @return
     */
    public static long getLastDayFirstTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero;
    }

    public static long getLastDayFirstTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero;
    }

    /**
     * 昨天最后一秒
     * @return
     */
    public static long getLastDayLastTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long zero = calendar.getTime().getTime();
        return zero+1000*60*60*24-1000;
    }
    /**
     * 获取当前年份
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return year.format(date);
    }
    /**
     * 获取上个月年份
     *
     */
    public static String getLastMonthYear(){
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        Date date = calendar.getTime();
        return year.format(date);
    }
    /**
     * 获取当前月份
     * @return
     */
    public static String getCurrentMonth(){
        SimpleDateFormat month = new SimpleDateFormat("MM");
        Date date = new Date();
        return month.format(date);
    }

    public static String getCurrentDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取上个月月份
     */
    public static String getLastMonth(){
        SimpleDateFormat month = new SimpleDateFormat("MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        Date date = calendar.getTime();
        return month.format(date);
    }

    public static Long getNowTime() {
        return new Date().getTime();
    }

    public static long getLongTime(String date)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long longTime = 0;
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(date);
            longTime =parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            longTime = Long.parseLong(date);
        }

        return longTime;
    }

    public static Date getHourTime(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime().getTime());
        return calendar.getTime();
    }

    public static Integer getDayNum(Long beginTime,Long currentTime) {
        Long difference = currentTime - beginTime;
        Long dayNum = difference/86400000;
        if (difference % 86400000 > 0) {
            dayNum += 1;
        }
        return dayNum.intValue();
    }

}