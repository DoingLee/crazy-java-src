package org.crazyit.mysql.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.crazyit.mysql.exception.FileException;

/**
 * 文件工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FileUtil {

	
	//全局配置文件的目录
	public final static String MYSQL_PROPERTIES_FILE = "config" + File.separator 
		+ "mysql.properties";
	
	//存放数据库连接的目录
	public final static String CONNECTIONS_FOLDER = "connections" + File.separator;
	
	public final static String MYSQL_HOME = "mysql.home";
	
	public final static String HOST = "host";
	
	public final static String PORT = "port";
	
	public final static String USERNAME = "username";
	
	public final static String PASSWORD = "password";
	
	//返回配置文件的MYSQL_HOME配置
	public static String getMySQLHome() {
		try {
			File configFile = new File(MYSQL_PROPERTIES_FILE);
			Properties props = getProperties(configFile);
			return props.getProperty(MYSQL_HOME);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 保存MySQL Home的配置进配置文件
	 * @param mysqlHome
	 */
	public static void saveMysqlHome(String mysqlHome) {
		try {
			File configFile = new File(MYSQL_PROPERTIES_FILE);
			Properties props = getProperties(configFile);
			props.setProperty(MYSQL_HOME, mysqlHome);
			FileOutputStream fos = new FileOutputStream(configFile);
			props.store(fos, "MySQL Home config");
			fos.close();
		} catch (Exception e) {
			throw new FileException("系统文件错误：" + MYSQL_PROPERTIES_FILE);
		}
	}
	
	/**
	 * 将属性保存到参数的文件中, 添加入对应的注释
	 * @param propertiesFile
	 * @param props
	 * @param comment
	 */
	public static void saveProperties(File propertiesFile, Properties props, 
			String comment) {
		try {
			//写入properties文件中
			FileOutputStream fos = new FileOutputStream(propertiesFile);
			props.store(fos, comment);
			fos.close();
		} catch (Exception e) {
			throw new FileException("数据库配置文件写入错误：" + 
					propertiesFile.getAbsolutePath());
		}
	}
	
	/**
	 * 创建一分新文件的工具方法
	 * @param file
	 */
	public static void createNewFile(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			throw new FileException("写入文件错误：" + file.getAbsolutePath());
		}
	}
	
	/**
	 * 创建文件并将content写到File中
	 * @param file
	 * @param content
	 */
	public static void writeToFile(File file, String content) {
		try {
			//创建新文件
			createNewFile(file);
			//写入文件
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			throw new FileException("写入文件错误：" + file.getAbsolutePath());
		}
	}
	
	
	/*
	 * 根据properties文件得到对应的Properties对象
	 */
	public static Properties getProperties(File propertyFile) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		prop.load(fis);
		fis.close();
		return prop;
	}
	
	/**
	 * 得到文件的文件名, 不带后缀
	 * @param file
	 * @return
	 */
	public static String getFileName(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1)
		{
			return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}
}
