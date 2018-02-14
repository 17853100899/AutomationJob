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
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Main {

	public static void main(String[] args) {

		String a = "\\";
		System.out.println(a);
		// System.out.println(CompletionOfInspectionData.getDistance(36.68229763455801,
		// 116.97321839868977,
		// CompletionOfInspectionData.latitudinalMigration(36.68229763455801),
		// CompletionOfInspectionData.latitudinalMigration(116.97321839868977)));

		// System.out.println(loadJson(Constant.overallTask));

		// writeTxtFile(loadJson(Constant.overallTask), "1.7整体任务.txt");

		// readTxtFile("2350.txt");

		// jsonAnalysis(readTxtFile("1.7整体任务.txt"));

		// writeTxtFile(jsonAnalysis1(readTxtFile("1.7整体任务.txt")),
		// "1.7任务名和对应ID.txt");

		// loadJson1(readTxtFile("1.7任务名和对应ID.txt"));

		// structureTaskJson(readTxtFile1("I:\\oddjob\\1.7单个任务\\4390633济南骨矫形医院-师范路GJ01管道分支.txt"),
		// "4390633济南骨矫形医院-师范路 GJ01管道分支.txt");

		// traverseFolder2("I:\\oddjob\\1.7单个任务");
		// traverseFolder2("I:\\oddjob\\1.7单个任务json待完成");
	}

	/**
	 * 任务下载
	 */
	public void downloadTask() {

		writeTxtFile(loadJson(Constant.overallTask), "1.7整体任务.txt");
		writeTxtFile(jsonAnalysis1(readTxtFile("1.7整体任务.txt")), "1.7任务名和对应ID.txt");
		loadJson1(readTxtFile("1.7任务名和对应ID.txt"));

	}

	/**
	 * 任务整理
	 */
	public void consolidationTask() {

	}

	/**
	 * 读取整体任务json
	 * 
	 * @param url
	 * @return
	 */
	public static String loadJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 读取单个任务json
	 * 
	 * @param url
	 * @return
	 */
	public static String loadJson1(String data) {

		try {
			JSONArray ja1 = JSONArray.fromObject(data);
			int i = 0;
			for (; i < ja1.size(); i++) {
				JSONObject jo2 = ja1.getJSONObject(i);
				jo2.getString("TASKNAME");
				jo2.getString("ID");
				writeTxtFile1(loadJson(Constant.singleTask + jo2.getString("ID")),
						jo2.getString("ID") + jo2.getString("TASKNAME").replace('/', ' ') + ".txt");
			}
			Constant.Logg("数据个数：" + i);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
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
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
	 * 获取文件夹下的所有文件
	 * 
	 * @param path
	 */
	public static void traverseFolder2(String path) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return;
			} else {
				String data = loadJson(Constant.overallTask);
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						traverseFolder2(file2.getAbsolutePath());
					} else if (data.indexOf(file2.getName().substring(0, 7)) != -1) {
						System.out.println("文件:" + file2.getAbsolutePath() + "..." + file2.getName().substring(0, 7));
						// structureTaskJson(readTxtFile1(file2.getAbsolutePath()),
						// file2.getName());
						Constant.Logg(sendPost("http://211.137.189.2:8088/cruise/cruise/getStaticById",
								"taskId=" + file2.getName().substring(0, 6)));
						Constant.Logg(sendPost("http://211.137.189.2:8088/cruise/cruise/finish",
								"param=" + readTxtFile1(file2.getAbsolutePath())));
						if (file2.renameTo(new File("I:\\oddjob\\1.7单个任务json已完成\\" + file2.getName()))) {
							System.out.println("File is moved successful!");
						} else {
							System.out.println("File is failed to move!");
						}

						int s = CompletionOfInspectionData.addRandomNumber(1800000, 600000);
						System.out.println("休眠" + s);
						try {
							Thread.sleep(s);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println(
								"文件:" + file2.getAbsolutePath() + "..." + file2.getName().substring(0, 7) + "任务已上传");
					}
				}
				System.out.println("任务执行完毕！");
			}
		} else {
			System.out.println("文件不存在!");
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param 文件内容content
	 * @param 文件名fileName
	 * @return
	 */
	public static boolean writeTxtFile(String content, String fileName) {
		File f = new File(Constant.filePath + fileName);
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(f);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
			Constant.Logg(fileName + "写入成功");
		} catch (Exception e) {
			Constant.Logg(fileName + "写入失败");
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
		return flag;
	}

	/**
	 * 写入文件1
	 * 
	 * @param 文件内容content
	 * @param 文件名fileName
	 * @return
	 */
	public static boolean writeTxtFile1(String content, String fileName) {
		File f = new File(Constant.filePath + "1.7单个任务\\" + fileName);
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(f);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
			Constant.Logg(fileName + "写入成功");
		} catch (Exception e) {
			Constant.Logg(fileName + "写入失败");
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
		return flag;
	}

	/**
	 * 写入文件2
	 * 
	 * @param 文件内容content
	 * @param 文件名fileName
	 * @return
	 */
	public static boolean writeTxtFile2(String content, String fileName) {
		File f = new File(Constant.filePath + "1.7单个任务json待完成\\" + fileName);
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(f);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
			Constant.Logg(fileName + "写入成功");
		} catch (Exception e) {
			Constant.Logg(fileName + "写入失败");
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
		return flag;
	}

	/**
	 * 读文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(String fileName) {
		File f = new File(Constant.filePath + fileName);
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
		// Constant.Logg("读取出来的文件内容是：" + "\r\n" + result);
		return result;
	}

	/**
	 * 读文件内容1
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile1(String fileName) {
		File f = new File(fileName);
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
		// Constant.Logg("读取出来的文件内容是：" + "\r\n" + result);
		return result;
	}

	public static String jsonAnalysis(String data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray json = null;
		try {
			JSONObject jo1 = JSONObject.fromObject(data);
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
		// Constant.Logg(list.size() + "");
		Constant.Logg(json.toString());
		return json.toString();
	}

	/**
	 * 解析任务名和对应ID
	 * 
	 * @param data
	 * @return
	 */
	public static String jsonAnalysis1(String data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray json = null;
		try {
			JSONObject jo1 = JSONObject.fromObject(data);
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
		// Constant.Logg(list.size() + "");
		Constant.Logg(json.toString());
		return json.toString();
	}

	/**
	 * 构造任务json
	 * 
	 * @param data
	 * @param TASKNAME
	 */
	public static void structureTaskJson(String data, String TASKNAME) {
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
				if (!jo2.getString("SYSTEM_TYPE").equals("1")) {
					break;
				}
				TASKID = jo2.getInt("ID");
				JSONArray ja1 = jo2.getJSONArray("SUBLIST");
				JSONObject jo3 = ja1.getJSONObject(0);
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
				jo5.put("FINISHDATE", CompletionOfInspectionData.addAndSubtractDaysByGetTime());// 上传时间
				jo5.put("LENGTH", 0);
				jo5.put("RATE", 0);
				jo5.put("STATUS", 1);
				jo5.put("SUBID", 0);
				jo5.put("TASKID", TASKID);
				jo5.put("TASKNAME", StringUtils.substringBeforeLast(TASKNAME, ".").substring(7));
				for (int i = 0; i < ja3.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject jo6 = ja3.getJSONObject(i);
					REALLAT = CompletionOfInspectionData.longitudeMigration(jo6.getDouble("GEOY"));
					REALLNG = CompletionOfInspectionData.latitudinalMigration(jo6.getDouble("GEOX"));
					map.put("CHECKTIME", CompletionOfInspectionData.addAndSubtractDaysByGetTime());
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
				jo5.put("WINDOWEND", "2018-01-10 20:00:00");
				jo5.put("WINDOWSTART", "2018-01-01 08:00:00");
				writeTxtFile2(jo5.toString(), TASKNAME + "副本.txt");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} while (false);

	}

}
