package org.crazyit.foxmail.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.crazyit.foxmail.exception.PropertiesException;
import org.crazyit.foxmail.ui.MailContext;

/**
 * 属性工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class PropertiesUtil {

	
	/*
	 * 根据文件得到对应的properties文件
	 */
	private static Properties getProperties(File propertyFile) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		prop.load(fis);
		return prop;
	}

	/*
	 * 根据配置文件的对象来构造MailContext对象
	 */
	public static MailContext createContext(File propertyFile) throws IOException {
		Properties props = getProperties(propertyFile);
		//如果没有配置smtp的端口，则使用默认的25端口
		Integer smtpPort = getInteger(props.getProperty("smtpPort"), 25);
		//如果没有配置pop3的端口，则使用默认的110端口
		Integer pop3Port = getInteger(props.getProperty("pop3Port"), 110);
		return new MailContext(null, 
				props.getProperty("account"), props.getProperty("password"), 
				props.getProperty("smtpHost"), smtpPort, 
				props.getProperty("pop3Host"), pop3Port);
	}
	
	//将参数s转换成一个Integer对象，该字符串为空则返回参数中的默认值
	private static Integer getInteger(String s, int defaultValue) {
		if (s == null || s.trim().equals("")) {
			return defaultValue;
		}
		return Integer.parseInt(s);
	}
	
	/*
	 * 保存一个MailContext对象， 将它的属性写入文件中
	 */
	public static void store(MailContext ctx) {
		try {
			File propFile = new File(FileUtil.DATE_FOLDER + ctx.getUser() + 
					FileUtil.CONFIG_FILE);
			Properties prop = getProperties(propFile);
			prop.setProperty("account", ctx.getAccount());
			prop.setProperty("password", ctx.getPassword());
			prop.setProperty("smtpHost", ctx.getSmtpHost());
			prop.setProperty("smtpPort", String.valueOf(ctx.getSmtpPort()));
			prop.setProperty("pop3Host", ctx.getPop3Host());
			prop.setProperty("pop3Port", String.valueOf(ctx.getPop3Port()));
			FileOutputStream fos = new FileOutputStream(propFile);
			prop.store(fos, "These are mail configs.");
			fos.close();
		} catch (IOException e) {
			throw new PropertiesException("请检查系统的配置文件, " + FileUtil.DATE_FOLDER + ctx.getUser() + 
					FileUtil.CONFIG_FILE);
		}
	}
}
