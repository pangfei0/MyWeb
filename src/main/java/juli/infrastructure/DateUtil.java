package juli.infrastructure;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    //按指定格式将字符串转成日期
    public static Date stringToDate2(String s) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.parse(s);
    }

    //将字符串转换成完整的日期
    public static Date stringToFullDate(String s) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(s);
    }

    //将字符串转成日期格式
    public static Date stringToDate(String s) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.parse(s);
    }
    //将字符串转成完整日期格式
    public static Date stringToFullDateformat(String s) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(s);
    }
    //将日期格式转成字符串
    public static String dateToString(Date date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
    //将完整日期格式转成字符串
    public static String dateToFullString(Date date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return sdf.format(date);
    }
    //将完整日期格式转成字符串
    public static String dateToFullString2(Date date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //将完整日期格式转成字符串
    public static String dateToFullString3(Date date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 日期格式转成字符串
     * @param date
     * @param pattern
     * @return
     * @throws Exception
     */
    public static String dateToString(Date date,String pattern) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 时间相减
     * @param d1
     * @param d2
     * @return
     */
    public static Date dateDiff(Date d1,Date d2){
        Date d = new Date(0);
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        c.add(Calendar.YEAR, d1.getYear());
        c.add(Calendar.MONTH, d1.getMonth());
        c.add(Calendar.DAY_OF_YEAR, d1.getDate());
        c.add(Calendar.HOUR_OF_DAY, d1.getHours());
        c.add(Calendar.MINUTE,d1.getMinutes());
        c.add(Calendar.SECOND, d1.getSeconds());

        c.add(Calendar.YEAR, 0 - d2.getYear());
        c.add(Calendar.MONTH, 0 - d2.getMonth());
        c.add(Calendar.DAY_OF_YEAR, 0-d2.getDate());
        c.add(Calendar.HOUR_OF_DAY, 0 - d2.getHours());
        c.add(Calendar.MINUTE,0-d2.getMinutes());
        c.add(Calendar.SECOND,0-d2.getSeconds());
        return c.getTime();
    }

    /**
     * 时间转换成时间段
     * @param d
     * @return
     */
    public static String dateToTimeSpan(Date d){
        return (int)Math.floor(d.getTime()/(60*60*1000)) + ":" + d.getMinutes()+":"+ d.getSeconds();
    }

    /**
     * 时间相差的分钟数
     * @param t1
     * @param t2
     * @return
     */
    public static long dateSub(String t1, String t2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        long days;
        long hours = 0;
        long minutes = 0;
        try {
            Date d1 =  sdf.parse(t1);
            Date d2 =  sdf.parse(t2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);

            hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hours * 60 + minutes;
    }

    /**
     * 根据日期判断是星期几
     * @param d
     * @return
     */
    public static String getWeekday(Date d){
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        }
    public static String dateAddMinutes(String datestr,int minutes) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(datestr);
        Date afterDate = new Date(date.getTime()+minutes*60*1000);
        return sdf.format(afterDate);
    }


    /**
     *
     * @param d
     * @param day
     * @return
     */
    public static Date toSomeDay(String d,int day){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        date = calendar.getTime();
        return date;
    }

    //统计用到的
    /**
     * 获得当前月第一天 (日期格式自定义)
     */
    @SuppressWarnings("static-access")
    public static String getFirtDayOfMonth(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar startCal = Calendar.getInstance();
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return df.format(startCal.getTime());
    }

    /**
     * 获得某月的第一天 (自定义)
     */
    @SuppressWarnings("static-access")
    public static String getFirtDayOfMonth(String format,String date) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar startCal = Calendar.getInstance();
        try {
            startCal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return df.format(startCal.getTime());
    }


    /**
     * 获取当前月最后一天23时59分59秒
     *
     * @return
     * @throws
     */
    @SuppressWarnings("static-access")
    public static String getLastDayDateTimeOfMonth(String format,String date) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar endCal = Calendar.getInstance();
        try {
            endCal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endCal.add(endCal.MONTH, 1);
        endCal.set(endCal.DATE, 1);
        endCal.add(endCal.DATE, -1);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 59);
        return df.format(endCal.getTime());

    }

    //获取上n个月的这天
    public static String getMonth(int n){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -n);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取某个月n的第一天 格式是 yyyy-MM-dd HH:mm:ss
     * 0表示当月，1表示上个月，2表示上两个月，以此类推
     * @param n
     * @return
     */
    public static String getFirstDayOfSomeMonth(int n){
        return getFirtDayOfMonth("yyyy-MM-dd HH:mm:ss", getMonth(n));
    }

    /**
     * 获取某个月n的最后一天 格式是 yyyy-MM-dd HH:mm:ss
     * 0表示当月，1表示上个月，2表示上两个月，以此类推
     * @param n
     * @return
     */
    public static String getEndDayOfSomeMonth(int n){
        return getLastDayDateTimeOfMonth("yyyy-MM-dd HH:mm:ss", getMonth(n));
    }

    /**
     * String 转成固定格式的Date类型
     * @param date
     * @return
     */
    public static Date stringToFormatDate(String date){
        Date time1=null;
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isNotEmpty(date)){
            Date b = null;//不是yyyy-MM-dd格式的date型
            try {
                b = sf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.util.Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
            time1 =be;
        }
        return time1;
    }

    /**
     * 获取当前时间前n天
     * @param n
     * @return
     */
    public static String getSomeDayFromToday(int n){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -n);  //设置为前一天
        return df.format(calendar.getTime());
    }

    /**
     * 获取周一
     * @param n
     * @return
     */
    public  static String getMonday(int n) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1 * 7*n);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return monday+" 00:00:00";
    }

    /**
     * 获取周日
     * @param n
     * @return
     */
    public static String getSunday(int n) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1 * 7 * n);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return sunday+" 00:00:00";
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static String getYearFirst(int year){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return df.format(currYearFirst);
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(int year){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return df.format(currYearLast);
    }

//    public static void main(String args[]) {
//        //天
//        System.out.println("今天：" + stringToFormatDate(getSomeDayFromToday(0)));
//        System.out.println("昨天：" + stringToFormatDate(getSomeDayFromToday(1)));
//        //周
//        System.out.println("本周一：" + stringToFormatDate(getMonday(0)));
//        System.out.println("本周日：" + stringToFormatDate(getSunday(0)));
//        System.out.println("上周一：" + stringToFormatDate(getMonday(1)));
//        System.out.println("上周日：" + stringToFormatDate(getSunday(1)));
//        //月
//        System.out.println("本月第一天：" + stringToFormatDate(getFirstDayOfSomeMonth(0)));
//        System.out.println("本月最后一天：" + stringToFormatDate(getEndDayOfSomeMonth(0)));
//        System.out.println("上个月第一天：" + stringToFormatDate(getFirstDayOfSomeMonth(1)));
//        System.out.println("上个月最后一天：" + stringToFormatDate(getEndDayOfSomeMonth(1)));
        //年
//        System.out.println("今年第一天：" + stringToFormatDate(getYearFirst(2016)));
//        System.out.println("今年最后一天：" + stringToFormatDate(getYearLast(2016)));
//        System.out.println("去年第一天：" + stringToFormatDate(getYearFirst(2015)));
//        System.out.println("去年最后一天：" + stringToFormatDate(getYearLast(2015)));

//    }


}
