package org.crazyit.foxmail.system.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.object.MailComparator;
import org.crazyit.foxmail.system.SystemLoader;
import org.crazyit.foxmail.ui.MailContext;
import org.crazyit.foxmail.util.FileUtil;

/**
 * 本地系统邮件加载实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SystemLoaderImpl implements SystemLoader {

	//实现接口方法, 得到垃圾箱的邮件
	public List<Mail> getDeletedBoxMails(MailContext ctx) {
		//先从与用户对应的deleted目录中得到全部的xml文件
		return getMails(ctx, FileUtil.DELETED);
	}

	//实现接口方法, 得到草稿箱的邮件
	public List<Mail> getDraftBoxMails(MailContext ctx) {
		//先从与用户对应的draft中得到全部的xml文件
		return getMails(ctx, FileUtil.DRAFT);
	}

	@Override
	public List<Mail> getInBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.INBOX);
	}
	
	//将xml文件转换成Mail对象, 并排序
	private List<Mail> convert(List<File> xmlFiles, MailContext ctx) {
		List<Mail> result = new ArrayList<Mail>();
		for (File file : xmlFiles) {
			//将xml转换成Mail对象
			Mail mail = FileUtil.fromXML(ctx, file);
			result.add(mail);
		}
		sort(result);
		return result;
	}
	
	//按照时间降序排序
	private void sort(List<Mail> mails) {
		Collections.sort(mails, new MailComparator());
	}

	//实现接口方法, 得到发件箱中的邮件
	public List<Mail> getOutBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.OUTBOX);
	}

	//实现接口方法, 得到已发送的邮件
	public List<Mail> getSentBoxMails(MailContext ctx) {
		return getMails(ctx, FileUtil.SENT);
	}

	private List<Mail> getMails(MailContext ctx, String box) {
		List<File> xmlFiles = FileUtil.getXMLFiles(ctx, box);
		List<Mail> result = convert(xmlFiles, ctx);
		return result;
	}
}
