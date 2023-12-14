package com.cx.wms.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    /**
     * 获取昨天日期
     *
     * @return 昨天日期
     * @author xrc
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取昨天日期
     *
     * @return 昨天日期
     * @author xrc
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取本周开始和结束日期
     *
     * @return 本周开始和结束日期数组
     * @author xrc
     */
    public static String[] getWeekRange() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DATE, -week);
        String startOfWeek = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) dayOfWeek = 7;
        calendar.add(Calendar.DATE, -dayOfWeek + 7);
        String endOfWeek = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        return new String[]{startOfWeek, endOfWeek};
    }

    /**
     * 获取上周开始和结束日期
     *
     * @return 上周开始和结束日期数组
     * @author xrc
     */
    public static String[] getLastWeekRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        String startOfLastWeek = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        calendar.add(Calendar.DATE, 6);
        String endOfLastWeek = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        return new String[]{startOfLastWeek, endOfLastWeek};
    }

    /**
     * 获取本月开始和结束日期
     *
     * @return 本月开始和结束日期数组
     * @author xrc
     */
    public static String[] getMonthRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startOfMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        String endOfMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        return new String[]{startOfMonth, endOfMonth};
    }

    /**
     * 获取上月开始和结束日期
     *
     * @return 上月开始和结束日期数组
     * @author xrc
     */
    public static String[] getLastMonthRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startOfLastMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        String endOfLastMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        return new String[]{startOfLastMonth, endOfLastMonth};
    }

    public static void main(String[] args) {
        System.out.println("今天:" + getToday());
        System.out.println("昨天: " + getYesterday());

        String[] weekRange = getWeekRange();
        System.out.println("本周开始日期: " + weekRange[0]);
        System.out.println("本周结束日期: " + weekRange[1]);

        String[] lastWeekRange = getLastWeekRange();
        System.out.println("上周开始日期: " + lastWeekRange[0]);
        System.out.println("上周结束日期: " + lastWeekRange[1]);

        String[] monthRange = getMonthRange();
        System.out.println("本月开始日期: " + monthRange[0]);
        System.out.println("本月结束日期: " + monthRange[1]);

        String[] lastMonthRange = getLastMonthRange();
        System.out.println("上月开始日期: " + lastMonthRange[0]);
        System.out.println("上月结束日期: " + lastMonthRange[1]);
    }
}
