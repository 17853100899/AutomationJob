package com.oddjob;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Entranceclass {

	public static void main(String[] args) {

		Constant.setWorkpath(CompletionOfInspectionData.getRealPath());
		System.out.println("工作路径：" + Constant.getWorkpath());
		String userID[] = Constant.readTxtFile(Constant.getWorkpath() + "userID.txt").split(",");
		for (int i = 0; i < userID.length; i++) {
			System.out.println(i + "：" + userID[i]);
		}
		System.out.println("选择工作账号：");
		Scanner input = new Scanner(System.in);
		int x = input.nextInt();
		Constant.setUserID(userID[x]);
		Constant.setFilePath(Constant.getWorkpath() + Constant.getUserID() + "\\");
		Constant.setUnfinishedTask(Constant.getFilePath() + "未完成任务\\");
		Constant.setFinishedTask(Constant.getFilePath() + "已完成任务\\");
		Constant.setFormatErrorTask(Constant.getFilePath() + "格式错误任务\\");
		Constant.setOverallTask("http://211.137.189.2:8088/cruise/cruise/list?acount=" + Constant.getUserID()
				+ "&status=0%2C1&startTime=");
		Constant.setSingleTask("http://211.137.189.2:8088/cruise/cruise/getInfo?taskId=");
		StartUp startUp = new StartUp();
		Thread threadStartUp = new Thread(startUp);
		threadStartUp.start();
		Tasking tasking = new Tasking();
		Thread threadTasking = new Thread(tasking);
		threadTasking.start();

	}

}

class StartUp implements Runnable {

	@Override
	public void run() {

		if (Constant.createDirectory(Constant.getFilePath()) && Constant.createDirectory(Constant.getUnfinishedTask())
				&& Constant.createDirectory(Constant.getFinishedTask())
				&& Constant.createDirectory(Constant.getFormatErrorTask())) {// 判断所需路径是否存在，若不存在就创建

			Constant.writeTxtFile(Constant.getURL(Constant.getOverallTask()), Constant.getFilePath() + "待巡检任务.txt");// 从服务器读取所有待巡检任务并储存

			Constant.writeTxtFile(
					Constant.decomposingTaskNamesAndID(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务.txt")),
					Constant.getFilePath() + "待巡检任务名和对应ID.txt");// 分解待巡检任务名和对应ID并储存

			Constant.decomposingOneTask(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务名和对应ID.txt"));// 从服务器读取单个待巡检任务并储存

			Constant.structuralTaskJsonAndSend(Constant.getUnfinishedTask());// 发送任务
		}

	}

}

class Tasking implements Runnable {

	@Override
	public void run() {

		do {

			Date dateTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTime);
			if (calendar.get(Calendar.HOUR_OF_DAY) == 7 && CompletionOfInspectionData.tasking
					&& CompletionOfInspectionData.taskNum != 0) {
				Constant.Logg("任务清零，今日份工作重新开始");
				CompletionOfInspectionData.taskNum = 0;
				CompletionOfInspectionData.tasking = false;
				Constant.writeTxtFile(Constant.getURL(Constant.getOverallTask()), Constant.getFilePath() + "待巡检任务.txt");// 从服务器读取所有待巡检任务并储存
				Constant.writeTxtFile(
						Constant.decomposingTaskNamesAndID(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务.txt")),
						Constant.getFilePath() + "待巡检任务名和对应ID.txt");// 分解待巡检任务名和对应ID并储存
				Constant.decomposingOneTask(Constant.readTxtFile(Constant.getFilePath() + "待巡检任务名和对应ID.txt"));// 从服务器读取单个待巡检任务并储存
			}
			if (calendar.get(Calendar.HOUR_OF_DAY) == 8) {
				CompletionOfInspectionData.tasking = true;
			}
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (true);

	}

}
