package org.crazyit.foxmail.system.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.crazyit.foxmail.object.FileObject;
import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.system.SystemHandler;
import org.crazyit.foxmail.ui.MailContext;
import org.crazyit.foxmail.util.FileUtil;

/**
 * 本地系统邮件处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SystemHandlerImpl implements SystemHandler {

	@Override
	public void delete(Mail mail, MailContext ctx) {
		//找到对应的xml文件
		File file = getMailXmlFile(mail.getXmlName(), ctx);
		//删除文件, 并在deleted目录中创建新的文件
		file.delete();
		//创建新的xml文件
		FileUtil.writeToXML(ctx, mail, FileUtil.DELETED);
	}

	@Override
	public void realDelete(Mail mail, MailContext ctx) {
		//找到对应的xml文件
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		//得到所有的附件并删除
		List<FileObject> files = mail.getFiles();
		//删除附件
		for (FileObject f : files) f.getFile().delete();
		//删除xml文件
		if (xmlFile.exists()) xmlFile.delete();
	}

	/*
	 * 将邮件对象保存到草稿箱的目录中
	 * @see org.crazyit.foxmail.system.SystemHandler#saveDraftBox(org.crazyit.foxmail.object.Mail, org.crazyit.foxmail.ui.MailContext)
	 */
	public void saveDraftBox(Mail mail, MailContext ctx) {
		//保存Mail的附件
		saveFiles(mail, ctx);
		FileUtil.writeToXML(ctx, mail, FileUtil.DRAFT);
	}

	@Override
	public void saveInBox(Mail mail, MailContext ctx) {
		FileUtil.writeToXML(ctx, mail, FileUtil.INBOX);
	}

	/*
	 * 保存Mail对象到发件箱
	 * @see org.crazyit.foxmail.system.SystemHandler#saveOutBox(org.crazyit.foxmail.object.Mail, org.crazyit.foxmail.ui.MailContext)
	 */
	public void saveOutBox(Mail mail, MailContext ctx) {
		//保存Mail的附件
		saveFiles(mail, ctx);
		FileUtil.writeToXML(ctx, mail, FileUtil.OUTBOX);
	}

	/*
	 * 保存到发送成功的邮件, 只可能在写邮件的时候出现, 因此该Mail对象中的所有附件, 
	 * 都在本地系统的另外目录下, 需要将这些附件保存到数据目录下
	 * @see org.crazyit.foxmail.system.SystemHandler#saveSent(org.crazyit.foxmail.object.Mail, org.crazyit.foxmail.ui.MailContext)
	 */
	public void saveSent(Mail mail, MailContext ctx) {
		saveFiles(mail, ctx);
		//为Mail对象生成xml文件
		FileUtil.writeToXML(ctx, mail, FileUtil.SENT);
	}
	
	//保存Mail对象中的附件
	private void saveFiles(Mail mail, MailContext ctx) {
		List<FileObject> files = mail.getFiles();
		List<FileObject> newFiles = new ArrayList<FileObject>();
		int byteSize = mail.getContent().getBytes().length;
		for (FileObject f : files) {
			String sentBoxPath = FileUtil.getBoxPath(ctx, FileUtil.FILE);
			//使用UUID生成新的文件名(该文件保存在file目录中)
			String fileName = UUID.randomUUID().toString();
			//得到文件的后缀
			String sufix = FileUtil.getFileSufix(f.getFile().getName());
			File targetFile = new File(sentBoxPath + fileName + sufix);
			//复制到file目录中
			FileUtil.copy(f.getFile(), targetFile);
			//设置Mail对象中附件集合的文件对象为新的文件对象(在file目录中)
			newFiles.add(new FileObject(f.getSourceName(), targetFile));
			byteSize += targetFile.length();
		}
		mail.setSize(Mail.getSize(byteSize));
		mail.setFiles(newFiles);
	}

	@Override
	public void saveMail(Mail mail, MailContext ctx) {
		//需要寻找该Mail对象所对应的xml文件，根据id去找文件
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		FileUtil.writeToXML(xmlFile, mail);
	}
	
	@Override
	public void revert(Mail mail, MailContext ctx) {
		//找到对应的xml文件
		File xmlFile = getMailXmlFile(mail.getXmlName(), ctx);
		//删除该文件, 再还原到原来的目录中
		xmlFile.delete();
		FileUtil.writeToXML(ctx, mail, mail.getFrom());
	}

	//从所有的邮件中查找名字为xmlName的xml文件
	private File getMailXmlFile(String xmlName, MailContext ctx) {
		List<File> allXMLFiles = getAllFiles(ctx);
		for (File f : allXMLFiles) {
			if (f.getName().equals(xmlName)) return f;
		}
		return null;
	}

	//得到全部的邮件（收件箱、发件箱、草稿箱、垃圾箱、已发送）的xml文件集合
	private List<File> getAllFiles(MailContext ctx) {
		List<File> inboxXmls = FileUtil.getXMLFiles(ctx, FileUtil.INBOX);
		List<File> outboxXmls = FileUtil.getXMLFiles(ctx, FileUtil.OUTBOX);
		List<File> draftXmls = FileUtil.getXMLFiles(ctx, FileUtil.DRAFT);
		List<File> sentXmls = FileUtil.getXMLFiles(ctx, FileUtil.SENT);
		List<File> deletedXmls = FileUtil.getXMLFiles(ctx, FileUtil.DELETED);
		List<File> result = new ArrayList<File>();
		result.addAll(inboxXmls);
		result.addAll(outboxXmls);
		result.addAll(draftXmls);
		result.addAll(sentXmls);
		result.addAll(deletedXmls);
		return result;
	}
}
