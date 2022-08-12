package com.chk.wx.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtil
 *
 * @author chk
 * @since 2022/8/10 10:14
 */
public class TimeUtil {

    /**
     * 获取当前时间是周几
     * @author chk
     * @date 2022/8/10 10:15
     * @return java.lang.String
     **/
    public static String getWeek() {
        String week = "";
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "星期日";
        } else if (weekday == 2) {
            week = "星期一";
        } else if (weekday == 3) {
            week = "星期二";
        } else if (weekday == 4) {
            week = "星期三";
        } else if (weekday == 5) {
            week = "星期四";
        } else if (weekday == 6) {
            week = "星期五";
        } else if (weekday == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 计算两个时间相差的天数
     * @author chk
     * @param date1 开始时间
     * @param date2 结束时间
     * @return int
     **/
    public static int differentDays(Date date1,Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {//同一年
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        } else {// 不同年
            return day2-day1;
        }
    }
}
