package org.crazyit.foxmail.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.Part;
import javax.mail.internet.MimeUtility;

import org.crazyit.foxmail.exception.FileException;
import org.crazyit.foxmail.object.FileObject;
import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.ui.MailContext;

import com.thoughtworks.xstream.XStream;

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

	//存放所有用户数据的目录
	public static final String DATE_FOLDER = "datas" + File.separator;
	//存放具体某个用户配置的properties文件
	public static final String CONFIG_FILE = File.separator + "mail.properties";
	
	//收件箱的目录名
	public static final String INBOX = "inbox";
	//发件箱的目录名
	public static final String OUTBOX = "outbox";
	//已发送的目录名
	public static final String SENT = "sent";
	//草稿箱的目录名
	public static final String DRAFT = "draft";
	//垃圾箱的目录名
	public static final String DELETED = "deleted";
	//附件的存放目录名
	public static final String FILE = "file";
	
	/**
	 * 创建用户的帐号目录和相关的子目录
	 * @param ctx
	 */
	public static void createFolder(MailContext ctx) {
		String accountRoot = getAccountRoot(ctx);
		//使用用户当前设置的帐号来生成目录, 例如一个用户叫user1,那么将会在datas/user1/下生成一个帐号目录
		mkdir(new File(accountRoot));
		//创建INBOX目录
		mkdir(new File(accountRoot + INBOX));
		//发件箱
		mkdir(new File(accountRoot + OUTBOX));
		//已发送
		mkdir(new File(accountRoot + SENT));
		//草稿箱
		mkdir(new File(accountRoot + DRAFT));
		//垃圾箱
		mkdir(new File(accountRoot + DELETED));
		//附件存放目录
		mkdir(new File(accountRoot + FILE));
	}
	
	//得到邮件帐号的根目录
	private static String getAccountRoot(MailContext ctx) {
		String accountRoot = DATE_FOLDER + ctx.getUser() + 
		File.separator + ctx.getAccount() + File.separator;
		return accountRoot;
	}
	
	//得到某个目录名字, 例如得到file的目录, inbox的目录
	public static String getBoxPath(MailContext ctx, String folderName) {
		return getAccountRoot(ctx) + folderName + File.separator;
	}
	
	//为附件创建本地文件, 目录是登录用户的邮箱名的file下
	public static FileObject createFileFromPart(MailContext ctx, Part part) {
		try {
			//得到文件存放的目录
			String fileRepository = getBoxPath(ctx, FILE);
			String serverFileName = MimeUtility.decodeText(part.getFileName());
			//生成UUID作为在本地系统中唯一的文件标识
			String fileName = UUID.randomUUID().toString();
			File file = new File(fileRepository + fileName + 
					getFileSufix(serverFileName));
			//读写文件
			FileOutputStream fos = new FileOutputStream(file);
			InputStream is = part.getInputStream();
			BufferedOutputStream outs = new BufferedOutputStream(fos);
			//如果附件内容为空part.getSize为-1, 如果直接new byte, 将抛出异常
			int size = (part.getSize() > 0) ? part.getSize() : 0;
			byte[] b = new byte[size];
			is.read(b);
			outs.write(b);
			outs.close();
			is.close();
			fos.close();
			//封装对象
			FileObject fileObject = new FileObject(serverFileName, file);
			return fileObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileException(e.getMessage());
		}
	}
	
	//从相应的box中得到全部的xml文件
	public static List<File> getXMLFiles(MailContext ctx, String box) {
		String rootPath = getAccountRoot(ctx);
		String boxPath = rootPath + box;
		//得到某个box的目录
		File boxFolder = new File(boxPath);
		//对文件进行后缀过滤
		List<File> files = filterFiles(boxFolder, ".xml");
		return files;
	}
	
	//从一个文件目录中, 以参数文件后缀subfix为条件, 过滤文件
	private static List<File> filterFiles(File folder, String sufix) {
		List<File> result = new ArrayList<File>();
		File[] files = folder.listFiles();
		if (files == null) return new ArrayList<File>();
		for (File f : files) {
			if (f.getName().endsWith(sufix)) result.add(f);
		}
		return result;
	}
		
	//得到文件名的后缀
	public static String getFileSufix(String fileName) {
		if (fileName == null || fileName.trim().equals("")) return "";
		if (fileName.indexOf(".") != -1) {
			return fileName.substring(fileName.indexOf("."));
		}
		return "";
	}
	
	//创建XStream对象
	private static XStream xstream = new XStream();
	
	//将一个邮件对象使用XStream写到xml文件中
	public static void writeToXML(MailContext ctx, Mail mail, String boxFolder) {
		//得到mail对应的xml文件的文件名
		String xmlName = mail.getXmlName();
		//得到对应的目录路径
		String boxPath = getAccountRoot(ctx) + boxFolder + File.separator;
		File xmlFile = new File(boxPath + xmlName);
		writeToXML(xmlFile, mail);
	}
	
	//将一个mail对象写到xmlFile中
	public static void writeToXML(File xmlFile, Mail mail) {
		try {
			if (!xmlFile.exists()) xmlFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(xmlFile);
			OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF8");
			xstream.toXML(mail, writer);
			writer.close();
			fos.close();
		} catch (Exception e) {
			throw new FileException("写入文件异常: " + xmlFile.getAbsolutePath());
		}
	}
	
	//将一份xml文档转换成Mail对象
	public static Mail fromXML(MailContext ctx, File xmlFile) {
		try {
			FileInputStream fis = new FileInputStream(xmlFile);
			//调用XStream的转换方法将文件转换成对象
			Mail mail = (Mail)xstream.fromXML(fis);
			fis.close();
			return mail;
		} catch (Exception e) {
			throw new FileException("转换数据异常: " + xmlFile.getAbsolutePath());
		}
	}
	//复制文件的方法
	public static void copy(File sourceFile, File targetFile) {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c copy \"" + 
					sourceFile.getAbsolutePath() + "\" \"" + 
					targetFile.getAbsolutePath() + "\"");
			process.waitFor();
		} catch (Exception e) {
			throw new FileException("另存文件错误: " + targetFile.getAbsolutePath());
		}
	}

	/*
	 * 创建目录的工具方法, 判断目录是否存在
	 */
	private static void mkdir(File file) {
		if (!file.exists()) file.mkdir();
	}
}
