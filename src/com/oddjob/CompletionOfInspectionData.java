package com.oddjob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class CompletionOfInspectionData {

	private static double EARTH_RADIUS = 6378.137;

	public static int taskNum = 0;

	public static boolean tasking = true;

	/**
	 * 获取项目所在路径
	 * 
	 * @return
	 */
	public static String getRealPath() {
		String realPath = Entranceclass.class.getClassLoader().getResource("").getFile();
		java.io.File file = new java.io.File(realPath);
		realPath = file.getAbsolutePath();
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return realPath + "\\";
	}

	/**
	 * 获取两个时间的时间差，精确到毫秒
	 * 
	 * @param str
	 * @return
	 */
	public static String TimeDifference(long start, long end) {

		long between = end - start;
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		// String timeDifference = day + "天" + hour + "小时" + min + "分" + s + "秒"
		// + ms + "毫秒";
		String timeDifference = min + "分" + s + "秒" + ms + "毫秒";
		return timeDifference;
	}

	/**
	 * 获取随机时间
	 * 
	 * @param dateTime
	 * @param n
	 * @return
	 */
	public static String addAndSubtractDaysByGetTime() {

		int d = 0, h = 0, m = 0, s = 0;
		Date dateTime = new Date();
		Random random = new Random();
		int mm = random.nextInt(10);
		int ss = random.nextInt(30);
		// 日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date(dateTime.getTime() + (d * 24 * 60 * 60 * 1000L) + (h * 60 * 60 * 1000L)// 注意这里一定要转换成Long类型，要不n超过25时会出现范围溢出，从而得不到想要的日期值
				+ (mm * 60 * 1000L) + (ss * 1000L)));
		Constant.Logg("构造时间，休眠" + CompletionOfInspectionData.TimeDifference(0, ((mm * 60 * 1000L) + (ss * 1000L))));
		try {// 时间做假
			Thread.sleep((mm * 60 * 1000L) + (ss * 1000L));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return time;

	}

	/**
	 * 通过距离获取时间
	 * 
	 * @return
	 */
	public static String addTimeThroughDistance() {
		if (Constant.lastLo == 0.00) {
			Constant.Logg("本次任务开始");
			Constant.lastLo = Constant.thisLo;
			Constant.lastLa = Constant.thisLa;
			return CompletionOfInspectionData.addTime();
		} else {
			long time = (long) (CompletionOfInspectionData.getDistance(Constant.lastLo, Constant.lastLa,
					Constant.thisLo, Constant.thisLa) / 5);
			long days = time / 86400; // 转换天数
			time = time % 86400; // 剩余秒数
			long hours = time / 3600; // 转换小时
			time = time % 3600; // 剩余秒数
			long minutes = time / 60; // 转换分钟
			time = time % 60; // 剩余秒数
			Date dateTime = new Date();
			// 日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timee = df
					.format(new Date(dateTime.getTime() + (days * 24 * 60 * 60 * 1000L) + (hours * 60 * 60 * 1000L)// 注意这里一定要转换成Long类型，要不n超过25时会出现范围溢出，从而得不到想要的日期值
							+ (minutes * 60 * 1000L) + (time * 1000L)));
			Constant.Logg("构造时间，休眠"
					+ CompletionOfInspectionData.TimeDifference(0, ((minutes * 60 * 1000L) + (time * 1000L))));
			try {// 时间做假
				Thread.sleep((minutes * 60 * 1000L) + (time * 1000L));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Constant.lastLo = Constant.thisLo;
			Constant.lastLa = Constant.thisLa;
			return timee;
		}
	}

	/**
	 * 获取时间
	 * 
	 * @param dateTime
	 * @param n
	 * @return
	 */
	public static String addTime() {

		Date dateTime = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date(dateTime.getTime()));

	}

	/**
	 * 判断是否在工作时间时间
	 */
	public static void workTime() {

		taskNum++;

		if (taskNum < Constant.getDayOfTasksNumber()) {
			Constant.Logg("今日需完成任务个数：[" + Constant.getDayOfTasksNumber() + "]\n今日已完成任务个数：[" + taskNum
					+ "]\n今日还需完成任务个数：[" + (Constant.getDayOfTasksNumber() - taskNum) + "]");
			return;
		}
		Constant.Logg("今日份任务已完成，正在休眠。[" + CompletionOfInspectionData.addTime() + "]");
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CompletionOfInspectionData.workTime();
	}

	/**
	 * 获取随机数
	 * 
	 * @param max
	 * @param min
	 * @return
	 */
	public static int addRandomNumber(int max, int min) {

		Random random = new Random();

		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 通过经纬度获取距离(单位：米)
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000d) / 10000d;//单位精度
		s = s * 1000;
		Constant.Logg("距离：" + s);
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 经度偏移
	 * 
	 * @param longitude
	 * @return
	 */
	public static double longitudeMigration(double longitude) {
		double min = longitude - 0.000331564631321;
		double max = longitude + 0.000336587566416;
		return min + new Random().nextDouble() * (max - min);
	}

	/**
	 * 纬度偏移
	 * 
	 * @param latitudinal
	 * @return
	 */
	public static double latitudinalMigration(double latitudinal) {
		double min = latitudinal - 0.000331564631321;
		double max = latitudinal + 0.000331564631321;
		return min + new Random().nextDouble() * (max - min);
	}

	/**
	 * 输出HashMap最大值序号
	 * 
	 * @param map
	 * @return
	 */
	public static int hashMapSort(Map<Integer, Integer> map) {
		List<Map.Entry<Integer, Integer>> infoIds = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
		// 排序
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o1.getValue() - o2.getValue());
			}
		});
		Entry<Integer, Integer> ent = infoIds.get(infoIds.size() - 1);
		Constant.Logg("最大值：" + ent.getKey() + "=" + ent.getValue());
		return ent.getKey();
	}

	/**
	 * 获取指定字符串出现的次数
	 * 
	 * @param srcText
	 *            源字符串
	 * @param findText
	 *            要查找的字符串
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
		int count = 0;
		Pattern p = Pattern.compile(findText);
		Matcher m = p.matcher(srcText);
		while (m.find()) {
			count++;
		}
		return count;
	}

}
