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
}
