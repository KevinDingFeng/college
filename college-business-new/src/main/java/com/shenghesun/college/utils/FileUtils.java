package com.shenghesun.college.utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class FileUtils {

	/**
	 * 返回 父级 路径
	 * 	根据当前时间戳 截取 年月日时，格式： yyyyMMdd/hh/
	 * 		如：20181017/18/
	 * @return
	 */
	public static String generateSubPathStr() {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date(System.currentTimeMillis());
	
		builder.append(sdf.format(now));
		builder.append(File.separator);
		sdf = new SimpleDateFormat("hh");
		builder.append(sdf.format(now));
		builder.append(File.separator);
		
		return builder.toString();
	}
	/**
	 * 返回文件名
	 * 	根据当前时间戳 截取 分秒，格式 mmss ，再加两位 随机字符串
	 * 		如：1525ab
	 * @return
	 */
	public static String generateFilename() {
		SimpleDateFormat sdf = new SimpleDateFormat("mmss");
		Date now = new Date(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		builder.append(sdf.format(now));
		builder.append(RandomUtil.randomString(2));
		return builder.toString();
	}
}
