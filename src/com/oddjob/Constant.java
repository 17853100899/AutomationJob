package com.oddjob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Constant {

	static boolean friststart = true;

	/**
	 * 工作账号
	 */
	public static String userID;

	/**
	 * 工作路径
	 */
	public static String workpath;

	/**
	 * 任务个数
	 */
	public static int numberOfTasks;

	/**
	 * 任务天数
	 */
	public static int dayOfTasks;

	/**
	 * 每天完成任务数
	 */
	public static int dayOfTasksNumber;

	/**
	 * 休眠时间
	 */
	public static int sleep;

	/**
	 * 文件储存路径
	 */
	public static String filePath;

	/**
	 * 未完成任务文件储存路径
	 */
	public static String unfinishedTask;

	/**
	 * 已完成任务文件储存路径
	 */
	public static String finishedTask;

	/**
	 * 格式错误任务文件储存路径
	 */
	public static String formatErrorTask;

	/**
	 * 整体任务 acount后接用户名
	 */
	public static String overallTask;

	/**
	 * 单个任务 taskId后接任务ID
	 */
	public static String singleTask;

	/**
	 * 任务点间隔经纬度
	 */
	public static double lastLo = 0.00, lastLa = 0.00, thisLo = 0.00, thisLa = 0.00;

	public static String getUserID() {
		return userID;
	}

	public static void setUserID(String userID) {
		Constant.userID = userID;
	}

	public static String getWorkpath() {
		return workpath;
	}

	public static void setWorkpath(String workpath) {
		Constant.workpath = workpath;
	}

	public static int getNumberOfTasks() {
		return numberOfTasks;
	}

	public static void setNumberOfTasks(int numberOfTasks) {
		Constant.numberOfTasks = numberOfTasks;
	}

	public static int getDayOfTasks() {
		return dayOfTasks;
	}

	public static void setDayOfTasks(int dayOfTasks) {
		Constant.dayOfTasks = dayOfTasks;
	}

	public static int getDayOfTasksNumber() {
		return dayOfTasksNumber;
	}

	public static void setDayOfTasksNumber(int dayOfTasksNumber) {
		Constant.dayOfTasksNumber = dayOfTasksNumber;
	}

	public static int getSleep() {
		return sleep;
	}

	public static void setSleep(int sleep) {
		Constant.sleep = sleep;
	}

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		Constant.filePath = filePath;
	}

	public static String getUnfinishedTask() {
		return unfinishedTask;
	}

	public static void setUnfinishedTask(String unfinishedTask) {
		Constant.unfinishedTask = unfinishedTask;
	}

	public static String getFinishedTask() {
		return finishedTask;
	}

	public static void setFinishedTask(String finishedTask) {
		Constant.finishedTask = finishedTask;
	}

	public static String getFormatErrorTask() {
		return formatErrorTask;
	}

	public static void setFormatErrorTask(String formatErrorTask) {
		Constant.formatErrorTask = formatErrorTask;
	}

	public static String getOverallTask() {
		return overallTask;
	}

	public static void setOverallTask(String overallTask) {
		Constant.overallTask = overallTask;
	}

	public static String getSingleTask() {
		return singleTask;
	}

	public static void setSingleTask(String singleTask) {
		Constant.singleTask = singleTask;
	}

	/**
	 * log打印
	 * 
	 * @param message
	 */
	public static void Logg(String message) {
		System.out.println(message);
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param destDirName
	 * @return
	 */
	public static boolean createDirectory(String targetDirectory) {
		File directory = new File(targetDirectory);
		if (directory.exists()) {// 判断目录是否存在
			Constant.Logg("目标目录已存在！");
			return true;
		}
		if (!targetDirectory.endsWith(File.separator)) {// 判断路径结尾是否以"/"结束
			targetDirectory = targetDirectory + File.separator;
		}
		if (directory.mkdirs()) {// 创建目标目录
			Constant.Logg("创建目录成功！" + targetDirectory);
			return true;
		} else {
			Constant.Logg("创建目录失败！");
			return false;
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param file
	 */
	public static boolean judeFileExists(String targetFile) {
		File file = new File(targetFile);
		if (file.exists()) {// 判断文件是否存在
			Constant.Logg("目标文件已存在！");
			return true;
		} else {
			Constant.Logg("目标文件不存在！");
			return false;
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param 文件内容content
	 * @param 文件路径filepath
	 * @return
	 */
	public static boolean writeTxtFile(String filecontent, String filepath) {
		File f = new File(filepath);
		RandomAccessFile mm = null;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(f);
			o.write(filecontent.getBytes("UTF-8"));
			o.close();
			Constant.Logg(filepath + "写入成功");
			return true;
		} catch (Exception e) {
			Constant.Logg(filepath + "写入失败");
			e.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 读文件内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readTxtFile(String filePath) {
		File f = new File(filePath);
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(f);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Constant.Logg("路径" + filePath + "读取出来的文件内容是：" + result);
		return result;
	}

	/**
	 * 移动文件
	 * 
	 * @param startPath
	 * @param endPath
	 * @return
	 */
	public static boolean moveFile(String startPath, String endPath) {
		File file = new File(startPath);
		if (file.renameTo(new File(endPath))) {
			Constant.Logg("文件移动成功,目标路径:" + endPath);
		} else {
			Constant.Logg("文件移动失败,目标路径:" + endPath);
		}

		return false;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				Constant.Logg("删除文件" + filePath + "成功！");
				return true;
			} else {
				Constant.Logg("删除文件" + filePath + "失败！");
				return false;
			}
		} else {
			Constant.Logg("删除文件失败：" + filePath + "不存在！");
			return false;
		}
	}

	/**
	 * 分解任务名和ID
	 * 
	 * @param content
	 * @return
	 */
	public static String decomposingTaskNamesAndID(String content) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray json = null;
		try {
			JSONObject jo1 = JSONObject.fromObject(content);
			JSONArray ja1 = jo1.getJSONArray("obj");
			for (int i = 0; i < ja1.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jo2 = ja1.getJSONObject(i);
				map.put("TASKNAME", jo2.getString("TASKNAME"));
				map.put("ID", jo2.getString("ID"));
				list.add(map);
			}
			json = JSONArray.fromObject(list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Constant.Logg("分解任务名和ID个数" + list.size());
		Constant.Logg("分解任务名和ID内容" + json.toString());
		return json.toString();
	}

	/**
	 * 分解单个任务并储存
	 * 
	 * @param url
	 * @return
	 */
	public static boolean decomposingOneTask(String content) {
		try {
			JSONArray ja1 = JSONArray.fromObject(content);
			int i = 0;
			for (; i < ja1.size(); i++) {
				JSONObject jo1 = ja1.getJSONObject(i);
				jo1.getString("TASKNAME");
				jo1.getString("ID");
				Constant.writeTxtFile(Constant.getURL(Constant.getSingleTask() + jo1.getString("ID")),
						Constant.getUnfinishedTask() + jo1.getString("ID")
								+ jo1.getString("TASKNAME").replace('/', ' ').replace('\\', ' ') + ".txt");
			}
			Constant.Logg("用户名：" + Constant.getUserID() + "\n数据个数：" + i);
			Constant.setNumberOfTasks(i);
			if (friststart) {
				System.out.println("输入任务天数：");
				Scanner input = new Scanner(System.in);
				Constant.setDayOfTasks(input.nextInt());
			}
			Constant.setSleep((16 - CompletionOfInspectionData.addNewTime()) / Constant.getNumberOfTasks());
			System.out.println("每条任务休眠时间：" + Constant.getSleep());
			friststart = false;
			// Constant.setDayOfTasksNumber((int) Math.ceil(Constant.getNumberOfTasks() /
			// Constant.getDayOfTasks()));
			Constant.setDayOfTasksNumber(500);
			System.out.println("每天完成任务数：[" + Constant.getDayOfTasksNumber() + "]");
			System.out.println("开始工作");
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 向指定 URL 发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @return 所代表远程资源的响应结果
	 */
	public static String getURL(String url) {
		StringBuilder jo = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				jo.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String postURL(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			Constant.Logg("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 遍历单个任务并储存和发送
	 * 
	 * @param path任务路径
	 * @param max发送任务间隔最大值ss
	 * @param min发送任务间隔最小值ss
	 */
	public static void structuralTaskJsonAndSend(String path) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				Constant.Logg("路径" + path + "下文件夹是空的!休眠一分钟");
				try {
					Thread.sleep(Constant.getSleep());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Constant.writeTxtFile(Constant.getURL(Constant.getOverallTask()), Constant.getFilePath() + "待巡检任务.txt");// 从服务器读取所有待巡检任务并储存

				Constant.writeTxtFile(
						Constant.decomposingTaskNamesAndID(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务.txt")),
						Constant.getFilePath() + "待巡检任务名和对应ID.txt");// 分解待巡检任务名和对应ID并储存

				Constant.decomposingOneTask(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务名和对应ID.txt"));// 从服务器读取单个待巡检任务并储存

				Constant.structuralTaskJsonAndSend(Constant.getUnfinishedTask());// 发送任务
				return;
			} else {
				String data = Constant.getURL(Constant.getOverallTask());
				for (File file2 : files) {
					if (file2.isDirectory()) {
						Constant.Logg("路径" + path + "下有文件夹" + file2.getAbsolutePath());
						// structuralTaskJsonAndSend(file2.getAbsolutePath());
					} else if (data.indexOf(file2.getName().substring(0, 7)) != -1) {
						Constant.Logg("路径" + path + "下有文件" + file2.getName());
						try {
							Thread.sleep(80000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String oneTask = Constant.structureTaskJson(Constant.readTxtFile(file2.getAbsolutePath()),
								file2.getName());
						if (!oneTask.equals("任务格式错误") && !oneTask.equals("未找到任务时间")) {
							Constant.Logg(postURL("http://211.137.189.2:8088/cruise/cruise/getStaticById",
									"taskId=" + file2.getName().substring(0, 6)));// "提交任务完成？"
							Constant.Logg(
									postURL("http://211.137.189.2:8088/cruise/cruise/finish", "param=" + oneTask));// "提交任务完成详细信息？"
							writeTxtFile(oneTask, Constant.getFinishedTask()
									+ file2.getName().substring(0, file2.getName().length() - 4) + "-已完成.txt");// 记录以完成任务
							Constant.deleteFile(file2.getPath());
							Constant.Logg("任务完成：" + CompletionOfInspectionData.addTime());
							CompletionOfInspectionData.workTime();// 是否可以工作
						} else {
							Constant.moveFile(file2.getPath(), Constant.getFormatErrorTask() + file2.getName());
						}
					} else {
						Constant.Logg("文件:" + file2.getAbsolutePath() + "..." + file2.getName().substring(0, 7)
								+ "任务已上传，本次跳过");
					}
				}
				Constant.Logg("路径" + path + "下所有任务全部完成!休眠一分钟");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Constant.writeTxtFile(Constant.getURL(Constant.getOverallTask()), Constant.getFilePath() + "待巡检任务.txt");// 从服务器读取所有待巡检任务并储存

				Constant.writeTxtFile(
						Constant.decomposingTaskNamesAndID(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务.txt")),
						Constant.getFilePath() + "待巡检任务名和对应ID.txt");// 分解待巡检任务名和对应ID并储存

				Constant.decomposingOneTask(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务名和对应ID.txt"));// 从服务器读取单个待巡检任务并储存

				Constant.structuralTaskJsonAndSend(Constant.getUnfinishedTask());// 发送任务
			}
		} else {
			Constant.Logg("路径" + path + "下无文件!");
		}
	}

	/**
	 * 构造任务json
	 * 
	 * @param data
	 * @param TASKNAME
	 */
	public static String structureTaskJson(String data, String TASKNAME) {
		do {
			int TASKID/* ID */, SUBID/* SUBID */, ISREQUIRED/* ISMUST */, ID/*
																			 * ID. .
																			 */;
			double REALLAT, REALLNG;// 纬度经度偏移值
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			try {
				JSONObject jo1 = JSONObject.fromObject(data);
				JSONObject jo2 = jo1.getJSONObject("obj");
				if (jo2.getString("SUBLIST").length() < 5) {
					break;
				}
				TASKID = jo2.getInt("ID");
				JSONArray ja1 = jo2.getJSONArray("SUBLIST");
				JSONObject jo3 = null;
				if (ja1.size() != 1) {
					Map<Integer, Integer> map = new HashMap<Integer, Integer>();
					for (int i = 0; i < ja1.size(); i++) {
						map.put(i, CompletionOfInspectionData.appearNumber(ja1.getString(i), "SEQ"));
					}
					jo3 = ja1.getJSONObject(CompletionOfInspectionData.hashMapSort(map));
				} else {
					jo3 = ja1.getJSONObject(0);
				}
				SUBID = jo3.getInt("SUBID");
				JSONArray ja2 = jo3.getJSONArray("TERMLIST");
				for (int i = 0; i < ja2.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject jo4 = ja2.getJSONObject(i);
					map.put("ID", jo4.getInt("ID"));
					map.put("TYPE", jo4.getString("TYPE"));
					map.put("NAME", jo4.getString("NAME"));
					map.put("GEOX", jo4.getDouble("GEOX"));
					map.put("GEOY", jo4.getDouble("GEOY"));
					if (jo4.getInt("ISMUST") == 0) {
						map.put("ISREQUIRED", "否");
					} else {
						map.put("ISREQUIRED", "是");
					}
					map.put("SEQ", jo4.getInt("SEQ"));
					list.add(map);
				}
				JSONArray ja3 = JSONArray.fromObject(list);

				JSONObject jo5 = new JSONObject();
				jo5.put("CHECKER", "DW3_LIQINGLIANG");
				jo5.put("COMMITTYPE", "0:******TASKCOMMIT");
				jo5.put("LENGTH", 0);
				jo5.put("RATE", 0);
				jo5.put("STATUS", 1);
				jo5.put("SUBID", 0);
				jo5.put("TASKID", TASKID);
				jo5.put("TASKNAME", StringUtils.substringBeforeLast(TASKNAME, ".").substring(7));
				Constant.Logg("本次任务共有" + ja3.size() + "个任务点");
				for (int i = 0; i < ja3.size(); i++) {
					Constant.Logg("开始构造第" + (i + 1) + "个任务点信息");
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject jo6 = ja3.getJSONObject(i);
					REALLAT = CompletionOfInspectionData.longitudeMigration(jo6.getDouble("GEOY"));// 经度偏移
					REALLNG = CompletionOfInspectionData.latitudinalMigration(jo6.getDouble("GEOX"));// 纬度偏移
					Constant.thisLo = REALLAT;
					Constant.thisLa = REALLNG;// 记录本次经纬度
					Constant.Logg("原经度：" + jo6.getDouble("GEOX") + "原纬度：" + jo6.getDouble("GEOY") + "偏移后经度：" + REALLNG
							+ "偏移后纬度：" + REALLAT);
					// map.put("CHECKTIME", CompletionOfInspectionData.addTimeThroughDistance());//
					// 通过经纬度差值获取时间
					map.put("CHECKTIME", CompletionOfInspectionData.addTime());// 直接获取时间
					map.put("DISTANCE", CompletionOfInspectionData.getDistance(jo6.getDouble("GEOY"),
							jo6.getDouble("GEOX"), REALLAT, REALLNG));
					map.put("ID", jo6.getInt("ID"));
					map.put("ISCHECKED", "是");
					map.put("ISREQUIRED", jo6.getString("ISREQUIRED"));
					map.put("ISOK", "是");
					map.put("LAT", jo6.getDouble("GEOY"));
					map.put("LENGTH", 0);
					map.put("LNG", jo6.getDouble("GEOX"));
					map.put("RATE", 0);
					map.put("REALLAT", REALLAT);
					map.put("REALLNG", REALLNG);
					map.put("SEQ", jo6.getInt("SEQ"));
					map.put("STATIONNAME", jo6.getString("NAME"));
					map.put("STATUS", 0);
					map.put("SUBID", SUBID);
					map.put("TASKID", TASKID);
					map.put("TASKTYPE", 0);
					map.put("TYPE", jo6.getString("TYPE"));
					list1.add(map);
				}
				JSONArray ja4 = JSONArray.fromObject(list1);
				jo5.put("TASKSTATIONLIST", ja4);
				jo5.put("TASKTYPE", 1);
				String time = Constant.readTxtFile(Constant.getFilePath() + "待巡检任务名和对应ID.txt");
				JSONArray ja5 = JSONArray.fromObject(time);
				for (int i = 0; i < ja5.size(); i++) {
					JSONObject jo7 = ja5.getJSONObject(i);
					if (jo7.getString("ID").equals(TASKID)) {
						Constant.Logg("TASKID：" + TASKID + "，" + jo7.getString("ID"));
						jo5.put("WINDOWEND", jo7.getString("WINDOWEND"));
						jo5.put("WINDOWSTART", jo7.getString("WINDOWSTART"));
					}
					if (i == ja5.size()) {
						return "未找到任务时间";
					}
				}
				jo5.put("FINISHDATE", CompletionOfInspectionData.addTime());// 上传时间
				Constant.lastLo = 0.00;
				Constant.lastLa = 0.00;
				Constant.thisLo = 0.00;
				Constant.thisLa = 0.00;
				return jo5.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} while (false);
		return "任务格式错误";
	}

}
