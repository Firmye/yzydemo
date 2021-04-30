package com.yzy.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author firmye
 * @Date 2017年12月27日 下午1:55:55
 *
 * @Description
 */
public class DateUtil {

	/**
	 * @Author firmye
	 * @Date 2017年12月27日 下午1:59:40
	 *
	 * @Description pattern为null，默认格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String parseDate(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		if (null == pattern) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * @Author Firmye
	 * @Date 2017年12月29日 上午1:12:46
	 *
	 * @Description pattern为null，默认格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static Date parseStr(String str, String pattern) throws ParseException {
		if (null == str) {
			return null;
		}
		if (null == pattern) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(str);
	}
	
	private static String zeroize(int i) {
		if(i < 10)
			return "0" + i;
		return "" + i;
	}

	/**
	 * @throws InterruptedException
	 * @Author Firmye
	 * @Date 2017年12月29日 上午1:12:46
	 *
	 * @Description 时区转换
	 */
	public static Date changeTimeZone(Date date,String oldTimeZone,String newTimeZone) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		
		sdf.setTimeZone(TimeZone.getTimeZone(oldTimeZone));
		Date tmp = sdf.parse(str);
		sdf.setTimeZone(TimeZone.getTimeZone(newTimeZone));
        str = sdf.format(tmp);
        
        return parseStr(str,null);
    }

	/**
	 * @Author Firmye
	 * @Date 2017年12月29日 上午1:12:46
	 *
	 * @Description 获取日期零点零分零秒
	 */
	public static Date getDateStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * @Author Firmye
	 * @Date 2017年12月29日 上午1:12:46
	 *
	 * @Description 获取日期几天前零点零分零秒
	 */
	public static Date getDateAgoStart(Date date, int dateAgo) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - dateAgo);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static void main(String[] args) throws ParseException, InterruptedException {
		Date date = parseStr("2017-10-29 11:18:02", null);

		System.out.println(changeTimeZone(date, "Asia/Shanghai", "Europe/London"));
		System.out.println(changeTimeZone(date, "UTC", "Europe/London"));
	}

}
