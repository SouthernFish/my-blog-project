package com.tong.common.core.util;

import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;

/**
 * @Author TR
 * @Create 2021/8/11 14:08
 * @Title: DateUtil
 * @Description: 基于java8新时间包封装的新的时间工具类，一并删除了之前DateUtil工具类中重复创建的方法。
 */
public class DateUtil {
	public static final long DATE_BASE = 946828800000L;// 基础时间 取 2000-01-03
	public static final long WEEK_NUMBER = 1000 * 3600 * 24 * 7;
	public static final long DAY_NUMBER = 1000 * 60 * 60 * 24;//一天的毫秒数

	public static final String DATE_FORMAR_STRING = "yyyy-MM-dd";// 时间格式化字符串
	public static final String DATETIME_FORMAR_STRING = "yyyy-MM-dd HH:mm:ss";// 时间格式化字符串
	public static final String TIME_FORMAR_STRING = "HH:mm:ss";// 时间格式化字符串
	public static final String DATE_NOLINE_STRING = "yyyyMMdd";// 时间格式化字符串
	public static final String DATE_YEAL_MM_DD_STRING = "yyyy年MM月dd日HH:mm";// 时间格式化字符串
	public static final String DATE_FORMAT_TO_MINUTE = "yyyy-MM-dd HH:mm";// 时间格式化至分钟

	public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAR_STRING);
	public static final DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern(DATETIME_FORMAR_STRING);
	public static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(TIME_FORMAR_STRING);
	public static final DateTimeFormatter dateNoLineFormat = DateTimeFormatter.ofPattern(DATE_NOLINE_STRING);
	public static final DateTimeFormatter dateFormatYMD = DateTimeFormatter.ofPattern(DATE_YEAL_MM_DD_STRING);
	public static final DateTimeFormatter dateFormatToMinute = DateTimeFormatter.ofPattern(DATE_FORMAT_TO_MINUTE);
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT_DATE = new SimpleDateFormat(DATE_FORMAR_STRING);
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT_DATETIME = new SimpleDateFormat(DATETIME_FORMAR_STRING);

	public static final DateTimeFormatter DF_YYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	public static final DateTimeFormatter DF_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	/**
	 * 转换成想要的格式
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午7:55:04
	 * @param ldt
	 * @param formatter
	 * @return
	 */
	public static String formatDate(LocalDateTime ldt, DateTimeFormatter formatter) {
		if (ldt == null || formatter == null) {
			return "";
		} else {
			return ldt.format(formatter);
		}
	}

	public static String formatDateTime(Date date) {
		if (null == date) {
			return null;
		}
		return getSimpleDateFormat(DATETIME_FORMAR_STRING).format(date);
	}

	public static String formatDate(Date date) {
		if (null == date) {
			return null;
		}
		return getSimpleDateFormat(DATE_FORMAR_STRING).format(date);
	}

	public static String formatDateTime(LocalDateTime date) {
		if (null == date) {
			return null;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern(DATETIME_FORMAR_STRING);
		return df.format(date);
	}

	private static SimpleDateFormat getSimpleDateFormat(String defaultFormat) {
		if (StringUtils.isBlank(defaultFormat)) {
			defaultFormat = DATETIME_FORMAR_STRING;
		}
		return new SimpleDateFormat(defaultFormat);
	}

	/**
	 * 转换成想要格式
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午7:55:30
	 * @param ldt
	 * @return
	 */
	public static String formatDate(LocalDateTime ldt, String pattern) {
		if (ldt == null || StringUtil.isEmpty(pattern)) {
			return "";
		} else {
			return ldt.format(DateTimeFormatter.ofPattern(pattern));
		}
	}

	/**
	 * 按照指定格式将字符串转换成时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:03:50
	 * @param text      注意:java8 中对时间（LocalTime）、日期（LocalDate）、日期时间（LocalDateTime）
	 *                  做了严格区分，由于字符串逆向转化为LocaDate 至少要带全年月日 ，所以本方法入参字符串必须要带全年月日。
	 * @param formatter
	 * @return
	 */
	public static LocalDateTime parseDate(String text, DateTimeFormatter formatter) {
		if (StringUtil.isEmpty(text) || formatter == null)
			return null;
		try {
			return LocalDateTime.parse(text, formatter);
		} catch (Exception e) {// 兼容没用带时或分或秒的日期字符串
			LocalDate date = LocalDate.parse(text, formatter);
			return date.atStartOfDay();
		}
	}

	/**
	 * 按照指定格式将字符串转换成时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:03:50
	 * @param text    注意:java8 中对时间（LocalTime）、日期（LocalDate）、日期时间（LocalDateTime）
	 *                做了严格区分，由于字符串逆向转化为LocaDate 至少要带全年月日 ，所以本方法入参字符串必须要带全年月日。
	 * @param pattern
	 * @return
	 */
	public static LocalDateTime parseDate(String text, String pattern) {
		if (StringUtil.isEmpty(text) || StringUtil.isEmpty(pattern))
			return null;
		try {
			return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
		} catch (Exception e) {// 兼容没用带时或分或秒的日期字符串
			LocalDate date = LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
			return date.atStartOfDay();
		}
	}

	/**
	 * 按照指定格式将字符串转换成时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:03:50
	 * @param text      注意:java8 中对时间（LocalTime）、日期（LocalDate）、日期时间（LocalDateTime）
	 *                  做了严格区分，由于字符串逆向转化为LocaDate 至少要带全年月日 ，所以本方法入参字符串必须要带全年月日。
	 * @param formatter
	 * @return
	 */
	public static long parseDateToLong(String text, DateTimeFormatter formatter) {
		if (StringUtil.isEmpty(text) || formatter == null)
			return 0L;
		try {
			return LocalDateTime.parse(text, formatter).toInstant(ZoneOffset.of("+8")).toEpochMilli();
		} catch (Exception e) {// 兼容没用带时或分或秒的日期字符串
			LocalDate date = LocalDate.parse(text, formatter);
			return date.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		}
	}

	/**
	 * 按照指定格式将字符串转换成时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:03:50
	 * @param text    注意:java8 中对时间（LocalTime）、日期（LocalDate）、日期时间（LocalDateTime）
	 *                做了严格区分，由于字符串逆向转化为LocaDate 至少要带全年月日 ，所以本方法入参字符串必须要带全年月日。
	 * @param pattern
	 * @return
	 */
	public static long parseDateToLong(String text, String pattern) {
		if (StringUtil.isEmpty(text) || StringUtil.isEmpty(pattern))
			return 0L;
		try {
			return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern)).toInstant(ZoneOffset.of("+8")).toEpochMilli();
		} catch (Exception e) {// 兼容没用带时或分或秒的日期字符串
			LocalDate date = LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
			return date.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		}
	}

	/**
	 * 按照指定格式将字符串转换成时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:03:50
	 * @param text
	 * @param formatter
	 * @return
	 */
	public static String parseDate(long timeStamp, DateTimeFormatter formatter) {
		if (formatter == null)
			return null;
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneOffset.of("+8")).format(formatter);
	}

	/**
	 * 按照指定格式将字符串转换成时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:03:50
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static String parseDate(long timeStamp, String pattern) {
		if (StringUtil.isEmpty(pattern))
			return null;
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneOffset.of("+8")).format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 时间字符串转日期字符串
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:02:23
	 * @param timeStr yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd
	 */
	public static String timeToDate(String timeStr) {
		if (StringUtil.isEmpty(timeStr))
			return null;
		return LocalDateTime.parse(timeStr, datetimeFormat).format(dateFormat);
	}

	/**
	 * 得到当前处在当年的第几周
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:00:41
	 * @return
	 */
	public static int getWeekOfYear() {
		return LocalDateTime.now().get(WeekFields.ISO.weekOfWeekBasedYear());
	}

	/**
	 * 得到当前是第几月
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:01:45
	 * @return
	 */
	public static int getMonth() {
		return LocalDateTime.now().getMonthValue();
	}

	/**
	 * 通过传入时间与当前时间比较，获得时间差值形成文字,非当月，直接返回日期
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:10:26
	 * @param date
	 * @return
	 */
	public static String getStringForDate(LocalDateTime date) {

		String result = "";
		if (date == null)
			return result;

		LocalDateTime now = LocalDateTime.now();

		if (now.getYear() == date.getYear() && now.getMonth() == date.getMonth()) {
			int day = now.getDayOfMonth() - date.getDayOfMonth();
			switch (day) {
			case 0:
				break;
			case 1:
				result = "昨天";
				break;
			default:
				result = date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
				break;
			}
			result = result + date.format(DateTimeFormatter.ofPattern("HH:mm"));
		} else {
			result = date.format(DateTimeFormatter.ofPattern(DateUtil.DATE_YEAL_MM_DD_STRING));

		}

		return result;
	}

	/**
	 * 通过传入时间与当前时间比较，获得时间差值形成文字(简易)
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:16:18
	 * @param date
	 * @return
	 */
	public static String getSimpleStringForDate(LocalDateTime date) {

		String result = "";
		if (date == null)
			return result;

		LocalDateTime now = LocalDateTime.now();

		int day = now.getDayOfMonth() - date.getDayOfMonth();
		switch (day) {
		case 0:
			result = date.format(DateTimeFormatter.ofPattern("HH:mm"));
			break;
		default:
			result = date.format(DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAR_STRING));
			break;
		}

		return result;
	}

	/**
	 * 获得与当前系统时间的相差天数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:25:11
	 * @param date 精确到毫秒的时间戳
	 * @return
	 */
	public static long compareDate(long date) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.of("+8"));
		return Duration.between(dateTime, LocalDateTime.now()).toDays();
	}

	/**
	 * 获得与当前系统时间的相差天数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:25:11
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long compareDate(String date) {
		LocalDateTime dateTime = LocalDateTime.parse(date, datetimeFormat);
		return Duration.between(dateTime, LocalDateTime.now()).toDays();
	}

	/**
	 * 获得传入两个时间的相差天数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:28:57
	 * @param startdate 精确到毫秒的时间戳
	 * @param enddate   精确到毫秒的时间戳
	 * @return
	 */
	public static long compareDate(long startdate, long enddate) {
		LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startdate), ZoneOffset.of("+8"));
		LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(enddate), ZoneOffset.of("+8"));
		return Duration.between(start, end).toDays();
	}

	/**
	 * 获得传入两个时间的相差天数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:28:57
	 * @param startdate yyyy-MM-dd HH:mm:ss
	 * @param enddate   yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long compareDate(String startdate, String enddate) {
		LocalDateTime start = LocalDateTime.parse(startdate, datetimeFormat);
		LocalDateTime end = LocalDateTime.parse(enddate, datetimeFormat);
		return Duration.between(start, end).toDays();
	}

	/**
	 * 获得传入时间和当前时间的相差小时
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:38
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static float compareHours(Long date) {
		LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.of("+8"));
		return (float) Duration.between(start, LocalDateTime.now()).getSeconds() / 3600;
	}

	/**
	 * 获得传入时间和当前时间的相差小时
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:38
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static float compareHours(String date) {
		LocalDateTime start = LocalDateTime.parse(date, datetimeFormat);
		return (float) Duration.between(start, LocalDateTime.now()).getSeconds() / 3600;
	}

	/**
	 * 获得传入两个时间的相差小时
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:38
	 * @param startdate 精确到毫秒的时间戳
	 * @param enddate   精确到毫秒的时间戳
	 * @return
	 */
	public static float compareHours(long startdate, long enddate) {
		LocalDateTime start = LocalDateTime.ofEpochSecond(startdate, 0, ZoneOffset.of("+8"));
		LocalDateTime end = LocalDateTime.ofEpochSecond(enddate, 0, ZoneOffset.of("+8"));
		return (float) Duration.between(start, end).getSeconds() / 3600;
	}

	/**
	 * 获得传入两个时间的相差小时
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:38
	 * @param startdate yyyy-MM-dd HH:mm:ss
	 * @param enddate   yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static float compareHours(String startdate, String enddate) {
		LocalDateTime start = LocalDateTime.parse(startdate, datetimeFormat);
		LocalDateTime end = LocalDateTime.parse(enddate, datetimeFormat);
		return (float) Duration.between(start, end).getSeconds() / 3600;
	}

    /**
     * 获得传入两个时间的相差分钟
     *
     * @param startdate yyyy-MM-dd HH:mm:ss
     * @param enddate   yyyy-MM-dd HH:mm:ss
     * @author CYT
     * @date 2021/6/8 14:54
     */
    public static float compareMinute(String startdate, String enddate) {
        LocalDateTime start = LocalDateTime.parse(startdate, datetimeFormat);
        LocalDateTime end = LocalDateTime.parse(enddate, datetimeFormat);
        return (float) Duration.between(start, end).getSeconds() / 60;
    }

	/**
	 * 获得与当前系统时间的相差周数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:02:34
	 * @param date
	 * @return
	 */
	public static long compareWeek(long date) {
		LocalDateTime start = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.of("+8"));
		Duration duration = Duration.between(start, LocalDateTime.now());
		return duration.toMillis() / WEEK_NUMBER;
	}

	/**
	 * 获得与当前系统时间的相差周数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:02:34
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long compareWeek(String date) {
		LocalDateTime start = LocalDateTime.parse(date, datetimeFormat);
		Duration duration = Duration.between(start, LocalDateTime.now());
		return duration.toMillis() / WEEK_NUMBER;
	}

	/**
	 * 获得传入两个时间的相差周数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:38
	 * @param startdate 精确到毫秒的时间戳
	 * @param enddate   精确到毫秒的时间戳
	 * @return
	 */
	public static long compareWeek(long startdate, long enddate) {
		LocalDateTime start = LocalDateTime.ofEpochSecond(startdate, 0, ZoneOffset.of("+8"));
		LocalDateTime end = LocalDateTime.ofEpochSecond(enddate, 0, ZoneOffset.of("+8"));
		return Duration.between(start, end).toMillis() / WEEK_NUMBER;
	}

	/**
	 * 获得传入两个时间的相差周数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:38
	 * @param startdate yyyy-MM-dd HH:mm:ss
	 * @param enddate   yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long compareWeek(String startdate, String enddate) {
		LocalDateTime start = LocalDateTime.parse(startdate, datetimeFormat);
		LocalDateTime end = LocalDateTime.parse(enddate, datetimeFormat);
		return Duration.between(start, end).toMillis() / WEEK_NUMBER;
	}

	/**
	 * 获得与当前系统时间的相差月数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:04:04
	 * @param date
	 * @return
	 */
	public static long compareMonth(long date) {
		LocalDateTime start = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.of("+8"));
		return compareMonth(start, LocalDateTime.now());
	}

	/**
	 * 获得与当前系统时间的相差月数
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:04:04
	 * @param date
	 * @return
	 */
	public static long compareMonth(String date) {
		LocalDateTime start = LocalDateTime.parse(date, datetimeFormat);
		return compareMonth(start, LocalDateTime.now());
	}

	/**
	 * 计算两个时间的月份差
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:12:18
	 * @param startDate yyyy-MM-dd HH:mm:ss
	 * @param endDate   yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static long compareMonth(String startDate, String endDate) throws ParseException {
		if (StringUtil.isEmpty(startDate) || StringUtil.isEmpty(endDate)) {
			return 0L;
		}
		LocalDateTime start = LocalDateTime.parse(startDate, datetimeFormat);
		LocalDateTime end = LocalDateTime.parse(endDate, datetimeFormat);
		return compareMonth(start, end);
	}

	/**
	 * 计算两个时间的月份差
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:14:15
	 * @param startDate 精确到毫秒的时间戳
	 * @param endDate   精确到毫秒的时间戳
	 * @return
	 */
	public static long compareMonth(long startDate, long endDate) {
		LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate), ZoneOffset.of("+8"));
		LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate), ZoneOffset.of("+8"));
		return compareMonth(start, end);
	}

	/**
	 * 计算两个时间的月份差
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:11:19
	 * @return
	 */
	public static long compareMonth(LocalDateTime start, LocalDateTime end) {
		return end.getYear() * 12 + end.getMonthValue() - start.getYear() * 12 - start.getMonthValue();
	}

	/**
	 * 获得传入两个时间的相差毫秒
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月10日 下午8:28:57
	 * @param startdate yyyy-MM-dd HH:mm:ss
	 * @param enddate   yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long compareMillis(String startdate, String enddate) {
		LocalDateTime start = LocalDateTime.parse(startdate, datetimeFormat);
		LocalDateTime end = LocalDateTime.parse(enddate, datetimeFormat);
		return Duration.between(start, end).toMillis();
	}

	/**
	 * 
	 * 获得传入时间与当前时间的差额，如果大于一天返回"**天前"，如大于一小时返回"**小时"，如果大于一分钟返回"**分钟"
	 *
	 * @param date yyyy-MM-dd HH:mm
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午8:53:14
	 * @param date
	 * @return
	 */
	public static String compareDateStr(String date) {
		String followDate = "";
		if (StringUtil.isEmpty(date))
			return followDate;

		LocalDateTime ldtdate = LocalDateTime.parse(date, dateFormatToMinute);
		Duration diff = Duration.between(ldtdate, LocalDateTime.now());// 获取差额对象
		if (diff.getSeconds() > 86400) {
			followDate = diff.toDays() + "天前";
		} else if (diff.getSeconds() > 3600) {
			followDate = diff.toHours() + "小时前";
		} else if (diff.getSeconds() > 60) {
			followDate = diff.toMinutes() + "分钟前";
		} else {
			followDate = "刚刚";
		}

		return followDate;
	}

	/**
	 * 获取当前时间距离目标时间的天、小时数<br/>
	 * 如果当前时间距离目标时间大于1天，则返回：xx天，如果小于1天，则返回：xx小时
	 * @param targetDate
	 * @return java.lang.String
	 * @Author Ajian
	 * @Date 2021-04-02 12:06
	 **/
	public static String getTimeDiffWithNow(Date targetDate) {
		String followDate = "";

		LocalDateTime ldtdate = LocalDateTime.ofInstant(targetDate.toInstant(), ZoneId.systemDefault());
		Duration diff = Duration.between(LocalDateTime.now(),ldtdate);
		if (diff.getSeconds() > 86400) {
			followDate = diff.toDays() + "天";
		} else if (diff.getSeconds() > 3600) {
			followDate = diff.toHours() + "小时";
		} else {
			followDate = "几分钟";
		}

		return followDate;
	}

	/**
	 * 
	 * 计算年龄
	 * 
	 * @param birthday 出生日期 yyyy-MM-dd
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:23:16
	 * @param birthday
	 * @return
	 */
	public static int calcAge(String birthday) {
		if (StringUtil.isEmpty(birthday)) {
			return 0;
		}
		LocalDate ldtdate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return LocalDate.now().getYear() - ldtdate.getYear();
	}

	/**
	 * 根据生日计算某一个日期的年龄
	 * 
	 * @param date 出生日期 yyyy-MM-dd
	 * @param time 某一个日期 yyyy-MM-dd
	 * @return
	 * @return int
	 * @author chentianjin
	 * @throws Exception
	 * @date 2017年8月29日
	 */
	public static int calcAge(String birthday, String time) {
		if (StringUtil.isEmpty(birthday) || StringUtil.isEmpty(time)) {
			return 0;
		}
		LocalDate birthdayDay = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate timeDay = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return timeDay.getYear() - birthdayDay.getYear();
	}

	/**
	 * 
	 * 根据传入时间 获得该周第一天（周一)的时间
	 * 
	 * @param day yyyy-MM-dd
	 * @return 精确到毫秒的时间戳
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:45:43
	 */
	public static long getFristDayForWeek(String day) {
		if (StringUtil.isEmpty(day))
			return 0L;
		return LocalDate.parse(day).with(DayOfWeek.MONDAY).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 根据传入时间 获得该周最后一天（周日)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:47:53
	 * @param day yyyy-MM-dd
	 * @return 精确到毫秒的时间戳
	 */
	public static long getLastDayForWeek(String day) {
		if (StringUtil.isEmpty(day))
			return 0L;
		return LocalDate.parse(day).with(DayOfWeek.SUNDAY).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 获得本周第一天（周一)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:46:43
	 * @return 精确到毫秒的时间戳
	 */
	public static long getFristDayForWeek() {
		return LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 获得本周最后一天（周日)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:47:44
	 * @return 精确到毫秒的时间戳
	 */
	public static long getLastDayForWeek() {
		return LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 
	 * 根据传入时间 获得该周第一天（周一)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:45:43
	 * @param day yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getFristDayForWeekStr(String day) {
		if (StringUtil.isEmpty(day))
			return "";
		return LocalDate.parse(day).with(DayOfWeek.MONDAY).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得该周最后一天（周日)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:47:53
	 * @param day yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getLastDayForWeekStr(String day) {
		if (StringUtil.isEmpty(day))
			return "";
		return LocalDate.parse(day).with(DayOfWeek.SUNDAY).format(dateFormat);
	}

	/**
	 * 获得本周第一天（周一)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:46:43
	 * @return 日期yyyy-MM-dd
	 */
	public static String getFristDayForWeekStr() {
		return LocalDate.now().with(DayOfWeek.MONDAY).format(dateFormat);
	}

	/**
	 * 获得本周最后一天（周日)的时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午9:47:44
	 * @return 日期yyyy-MM-dd
	 */
	public static String getLastDayForWeekStr() {
		return LocalDate.now().with(DayOfWeek.SUNDAY).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在月的 第一天的日期
	 * 
	 * @param date 精确到毫秒的时间戳
	 * @return 日期yyyy-MM-dd
	 */
	public static String getMonthFristDay(long date) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.of("+8")).with(TemporalAdjusters.firstDayOfMonth()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在月的 最后一天的日期
	 * 
	 * @param date 精确到毫秒的时间戳
	 * @return 日期yyyy-MM-dd
	 */
	public static String getMonthLastDay(long date) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.of("+8")).with(TemporalAdjusters.lastDayOfMonth()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在月的 第一天的日期
	 * 
	 * @param date yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getMonthFristDay(String date) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDate.parse(date).with(TemporalAdjusters.firstDayOfMonth()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在月的 最后一天的日期
	 * 
	 * @param date yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getMonthLastDay(String date) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDate.parse(date).with(TemporalAdjusters.lastDayOfMonth()).format(dateFormat);
	}

	/**
	 * 本月第一天的日期
	 * 
	 * @return 日期yyyy-MM-dd
	 */
	public static String getMonthFristDay() {
		return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).format(dateFormat);
	}

	/**
	 * 本月最后一天的日期
	 * 
	 * @return 日期yyyy-MM-dd
	 */
	public static String getMonthLastDay() {
		return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在年的 第一天的日期
	 * 
	 * @param date 精确到毫秒的时间戳
	 * @return 日期yyyy-MM-dd
	 */
	public static String getYearFristDay(long date) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.of("+8")).with(TemporalAdjusters.firstDayOfYear()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在年的 最后一天的日期
	 * 
	 * @param date 精确到毫秒的时间戳
	 * @return 日期yyyy-MM-dd
	 */
	public static String getYearLastDay(long date) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.of("+8")).with(TemporalAdjusters.lastDayOfYear()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在年的 第一天的日期
	 * 
	 * @param date yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getYearFristDay(String date) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDate.parse(date).with(TemporalAdjusters.firstDayOfYear()).format(dateFormat);
	}

	/**
	 * 根据传入时间 获得此时间所在年的 最后一天的日期
	 * 
	 * @param date yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getYearLastDay(String date) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDate.parse(date).with(TemporalAdjusters.lastDayOfYear()).format(dateFormat);
	}

	/**
	 * 本年第一天的日期
	 * 
	 * @param date yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getYearFristDay() {
		return LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).format(dateFormat);
	}

	/**
	 * 本年最后一天的日期
	 * 
	 * @param date yyyy-MM-dd
	 * @return 日期yyyy-MM-dd
	 */
	public static String getYearLastDay() {
		return LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).format(dateFormat);
	}

	/**
	 * 返回查询的开始时间
	 * 
	 * @param date 查询时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getBeginTime(String date) {
		if (StringUtil.isEmpty(date))
			return "";
		return StringUtil.isEmpty(date) ? "2011-12-01 00:00:00" : LocalDateTime.parse(date, datetimeFormat).format(datetimeFormat);
	}

	/**
	 * 返回查询的结束时间
	 * 
	 * @param date 查询时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getEndTime(String date) {
		if (StringUtil.isEmpty(date))
			return "";
		return StringUtil.isEmpty(date) ? LocalDateTime.now().format(datetimeFormat) : LocalDateTime.parse(date, datetimeFormat).format(datetimeFormat);
	}

	/**
	 * 获取当前时间戳（秒）
	 * 
	 * @author: chentianjin
	 * @date: 2021年3月31日 上午11:45:02
	 * @return
	 */
	public static Long currentStamp() {
		return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
	}

	/**
	 * 获取当前时间戳（毫秒）
	 * 
	 * @author: chentianjin
	 * @date: 2021年3月31日 上午11:45:02
	 * @return
	 */
	public static Long currentStampMill() {
		return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:28:26
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String currentDateTime() {
		return LocalDateTime.now().format(datetimeFormat);
	}

	/**
	 * 获取当前日期
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:33:08
	 * @return yyyy-MM-dd
	 */
	public static String currentDate() {
		return LocalDateTime.now().format(dateFormat);
	}

	/**
	 * 获取当前时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:33:08
	 * @return yyyy-MM-dd
	 */
	public static String currentTime() {
		return LocalDateTime.now().format(timeFormat);
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:28:26
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static LocalDateTime currentLocalDateTime() {
		return LocalDateTime.now();
	}

	/**
	 * 获取当前日期
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:33:08
	 * @return yyyy-MM-dd
	 */
	public static LocalDate currentLocalDate() {
		return LocalDate.now();
	}

	/**
	 * 获取当前时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:33:08
	 * @return yyyy-MM-dd
	 */
	public static LocalTime currentLocalTime() {
		return LocalTime.now();
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:28:52
	 * @param pattern
	 * @return
	 */
	public static String currentTime(String pattern) {
		if (StringUtil.isEmpty(pattern))
			return "";
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:28:52
	 * @param formatter
	 * @return
	 */
	public static String currentTime(DateTimeFormatter formatter) {
		if (formatter == null)
			return "";
		return LocalDateTime.now().format(formatter);
	}

	/**
	 * 
	 * 比较两个时间的大小
	 * 
	 * DATE1>DATE2 返回1 DATE1<DATE2 返回-1 DATE2=DATE1 返回0
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param DATE1 yyyy-MM-dd HH:mm:ss
	 * @param DATE2 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {
		if (StringUtil.isEmpty(DATE1) || StringUtil.isEmpty(DATE2))
			return 0;
		Duration duration = Duration.between(LocalDateTime.parse(DATE2, datetimeFormat), LocalDateTime.parse(DATE1, datetimeFormat));
		return duration.getSeconds() > 0 ? 1 : (duration.getSeconds() < 0 ? -1 : 0);
	}

	/**
	 * 给定日期加减（单位年）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddYear(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusYears(i).format(datetimeFormat);
	}

	/**
	 * 给定日期加减（单位月）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddMonth(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusMonths(i).format(datetimeFormat);
	}

	/**
	 * 给定日期加减（单位周）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddWeek(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusWeeks(i).format(datetimeFormat);
	}

	/**
	 * 给定日期加减（单位天）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddDay(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusDays(i).format(datetimeFormat);
	}

	/**
	 * 给定日期加减（单位小时）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddHour(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusHours(i).format(datetimeFormat);
	}

	/**
	 * 给定日期加减（单位分钟）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddMinute(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusMinutes(i).format(datetimeFormat);
	}

	/**
	 * 给定日期加减（单位秒）
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午10:30:53
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateAddSecond(String date, int i) {
		if (StringUtil.isEmpty(date))
			return "";
		return LocalDateTime.parse(date, datetimeFormat).plusSeconds(i).format(datetimeFormat);
	}

	/**
	 * 获得上周第一天日期
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:37:37
	 * @return
	 */
	public static String getLastWeekMonday() {
		return LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY).format(dateFormat);
	}

	/**
	 * 获得上周最后一天日期
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:37:42
	 * @return
	 */
	public static String getLastWeekSunday() {
		return LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY).format(dateFormat);
	}

	/**
	 * 获得上个月第一天日期
	 * 
	 * @return String
	 * @author chentianjin
	 * @date 2017年5月31日
	 */
	public static String getLastMonthfirstDay() {
		return LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).format(dateFormat);
	}

	/**
	 * 获得上个月最后一天日期
	 * 
	 * @return String
	 * @author chentianjin
	 * @date 2017年5月31日
	 */
	public static String getLastMonthLastDay() {
		return LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(dateFormat);
	}

	/**
	 * 获得昨天日期
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:41:48
	 * @return
	 */
	public static String getYesterday() {
		return LocalDate.now().minusDays(1).format(dateFormat);
	}

	/**
	 * 获取今天之前（含今天）的day天内的日期数组
	 * 
	 * @param day    ( 大于等于 0 )
	 * @param format
	 * @return
	 */
	public static String[] getBeforeDateFromNow(Integer day, String format) {
		if (day <= 0)
			day = 0;
		String[] dates = new String[day];

		if (StringUtil.isEmpty(format))
			return dates;

		try {
			for (int i = 0; i < day; i++)
				dates[day - i - 1] = LocalDateTime.now().minusDays(i).format(DateTimeFormatter.ofPattern(format));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dates;
	}

	/**
	 * 1、本月返回（本月） 2、今年的其他月返回（1月、2月） 3、今年以外的返回（1999年4月）
	 * 
	 * @param key "2015-02"
	 * @return
	 * @return String
	 * @author chentianjin
	 * @date 2017年7月26日
	 */
	public static String changeMonth(String key) {
		String nowDay = currentDate();
		if (key.substring(0, 4).equals(nowDay.substring(0, 4))) {
			if (key.substring(5, 7).equals(nowDay.substring(5, 7))) {
				return "本月";
			} else {
				return Integer.parseInt(key.substring(5, 7)) + "月";
			}
		} else {
			return key.substring(0, 4) + "年" + Integer.parseInt(key.substring(5, 7)) + "月";
		}
	}

	/**
	 * 验证某个时间字符串是否满足某个格式
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月11日 上午11:52:47
	 * @param formatStr
	 * @param dateString
	 * @return
	 */
	public static boolean checkDateFormart(String formatStr, String dateString) {
		if (StringUtils.isEmpty(formatStr)) {
			formatStr = DateUtil.DATETIME_FORMAR_STRING;
		}
		try {
			LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(formatStr));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 时间转化未距离当前多少分，时，秒
	 * 
	 * @author: chentianjin
	 * @date: 2021年3月18日 下午4:58:58
	 * @param time
	 * @return
	 */
	public static String timeTurnMsgForNow(String time) {
		try {
			String msg = null;

			long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数

			long reset = LocalDateTime.parse(time, datetimeFormat).toInstant(ZoneOffset.of("+8")).toEpochMilli(); // 获取指定时间的毫秒数
			long dateDiff = nowTime - reset;

			if (dateDiff < 0) {
				msg = "输入的时间不对";
			} else {
				long dateTemp1 = dateDiff / 1000; // 秒
				long dateTemp2 = dateTemp1 / 60; // 分钟
				long dateTemp3 = dateTemp2 / 60; // 小时
				long dateTemp4 = dateTemp3 / 24; // 天数
				long dateTemp5 = dateTemp4 / 30; // 月数
				long dateTemp6 = dateTemp5 / 12; // 年数

				if (dateTemp6 > 0) {
					msg = dateTemp6 + "年前";
				} else if (dateTemp5 > 0) {
					msg = dateTemp5 + "个月前";
				} else if (dateTemp4 > 0) {
					msg = dateTemp4 + "天前";
				} else if (dateTemp3 > 0) {
					msg = dateTemp3 + "小时前";
				} else if (dateTemp2 > 0) {
					msg = dateTemp2 + "分钟前";
				} else if (dateTemp1 > 0) {
					msg = dateTemp1 + "秒前";
				}
			}
			return msg;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 毫秒改为时分秒格式
	public static String millisToStringShort(long millis) {
		StrBuilder strBuilder = new StrBuilder();
		long temp = millis;
		long hper = 60 * 60 * 1000;
		long mper = 60 * 1000;
		long sper = 1000;
		if (temp / hper > 0) {
			strBuilder.append(temp / hper).append("小时");
		}
		temp = temp % hper;
		if (temp / mper > 0) {
			strBuilder.append(temp / mper).append("分");
		}
		temp = temp % mper;
		if (temp / sper > 0) {
			strBuilder.append(temp / sper).append("秒");
		} else {
			strBuilder.append(1).append("秒");
		}
		return strBuilder.toString();
	}

	/**
	 * @Author: TR
	 * @Create: 2021/03/23
	 * @Title: timeTurnMsgForFuture
	 * @Description: 时间转化 当前距离未来某时间多少天，时，分，秒
	 * @param: time 未来某时间
	 */
	public static String timeTurnMsgForFuture(String time) {
		try {
			String msg = null;
			long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
			long futureTime = LocalDateTime.parse(time, datetimeFormat).toInstant(ZoneOffset.of("+8")).toEpochMilli(); // 获取指定时间的毫秒数
			long dateDiff = futureTime - nowTime;

			if (dateDiff < 0) {
				msg = "输入的时间不对";
			} else {
				long dateTemp1 = dateDiff / 1000; // 秒
				long dateTemp2 = dateTemp1 / 60; // 分钟
				long dateTemp3 = dateTemp2 / 60; // 小时
				long dateTemp4 = dateTemp3 / 24; // 天数
				long dateTemp5 = dateTemp4 / 30; // 月数
				long dateTemp6 = dateTemp5 / 12; // 年数

				if (dateTemp6 > 0) {
					msg = dateTemp6 + "年";
				} else if (dateTemp5 > 0) {
					msg = dateTemp5 + "月";
				} else if (dateTemp4 > 0) {
					msg = dateTemp4 + "天";
				} else if (dateTemp3 > 0) {
					msg = dateTemp3 + "小时";
				} else if (dateTemp2 > 0) {
					msg = dateTemp2 + "分钟";
				} else if (dateTemp1 > 0) {
					msg = dateTemp1 + "秒";
				}
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 毫秒转x天x小时格式
	 */
	public static String millisToDayAndHour(long millis) {
		StrBuilder strBuilder = new StrBuilder();
		long temp = millis;
		long dper = 24 * 60 * 60 * 1000;
		long hper = 60 * 60 * 1000;

		if (temp / dper > 0) {
			strBuilder.append(temp / dper).append("天");
		}
		temp = temp % dper;
		if (new BigDecimal(temp).divide(new BigDecimal(hper)).compareTo(new BigDecimal(0)) > 0) {
			strBuilder.append(new BigDecimal(temp).divide(new BigDecimal(hper), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()).append("小时");
		}
		return strBuilder.toString();
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.currentStamp());
		System.out.println(DateUtil.currentStampMill());
		System.out.println(DateUtil.millisToDayAndHour(6*60*1000));
		System.out.println(timeTurnMsgForFuture("2021-03-25 00:00:00"));
	}
}
