package com.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取今天0点的时间
	 * 
	 * @return
	 */
	public static long getTimesMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取指定日期0点的时间
	 * @param date 指定日期
	 * @return
	 */
	public static long getTimesMorning(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获得跟今天比偏移了day的0点的时间
	 * @param day 跟今天比偏移的天数，可以是负数，默认加
	 * @return
	 */
	public static long getTimesMorning(int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTimeInMillis();
	}

	/**
	 * 获得当天24点时间
	 * 
	 * @return
	 */
	public static long getTimesNight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获得跟今天比偏移了day的24点时间
	 * @param day 跟今天比偏移的天数 ,可以是负数,默认加
	 * @return
	 */
	public static long getTimesNight(int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 指定日期的24点的时间
	 * @param date 指定的日期
	 * @return 从1970年开始计算的时间
	 */
	public static long getTimesNight(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 *  获得本周一0点时间
	 * @return
	 */
	public static long getTimesWeekMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTimeInMillis();
	}

	/**
	 * 获得本周日24点时间
	 * @return
	 */
	public static long getTimesWeekNight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime().getTime() + (7 * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获得本月第一天0点时间
	 * @return
	 */
	public static long getTimesMonthMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTimeInMillis();
	}

	/**
	 * 获得本月最后一天24点时间
	 * @return
	 */
	public static long getTimesMonthNight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 偏移后的天数
	 * @param date 原始日期
	 * @param day 偏移天数
	 * @return 偏移后的日期
	 */
	public static Date getShiftDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}
	
	/**
	 * 根据时间获取小时和分值
	 * @param date
	 * @return
	 */
	public static String getHourMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return hour + ":" + minute;
	}
	
	/**
	 * 根据时间获取小时和分值
	 * @param milliseconds 毫秒
	 * @return
	 */
	public static String getHourMinute(long milliseconds) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(milliseconds);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return hour + ":" + minute;
	}
}
