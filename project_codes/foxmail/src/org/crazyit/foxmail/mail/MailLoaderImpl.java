package org.crazyit.foxmail.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;

import org.crazyit.foxmail.object.FileObject;
import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.object.MailComparator;
import org.crazyit.foxmail.ui.MailContext;
import org.crazyit.foxmail.util.FileUtil;

/**
 * 读取邮件实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailLoaderImpl implements MailLoader {
	
	
	@Override
	public List<Mail> getMessages(MailContext ctx) {
		//得到INBOX对应的Folder
		Folder inbox = getINBOXFolder(ctx);
		try {
			inbox.open(Folder.READ_WRITE);
			//得到INBOX里的所有信息
			Message[] messages = inbox.getMessages();
			//将Message数组封装成Mail集合
			List<Mail> result = getMailList(ctx, messages);
			//按照时间降序排序
			sort(result);
			//删除邮箱中全部的邮件, 那么每次使用邮件系统, 只会拿新收到的邮件
			deleteFromServer(messages);
			//删除邮件并提交删除状态
			inbox.close(true);
			return result;
		} catch (Exception e) {
			throw new LoadMailException(e.getMessage());
		}
	}

	//将javamail中的Message对象转换成本项目中的Mail对象
	private List<Mail> getMailList(MailContext ctx, Message[] messages) {
		List<Mail> result = new ArrayList<Mail>();
		try {
			//将得到的Message对象封装成Mail对象
			for (Message m : messages) {
				//生成UUID的文件名
				String xmlName = UUID.randomUUID().toString() + ".xml"; 
				//获得内容
				String content = getContent(m, new StringBuffer()).toString();
				//得到邮件的各个值
				Mail mail = new Mail(xmlName, getAllRecipients(m), getSender(m), 
						m.getSubject(), getReceivedDate(m), Mail.getSize(m.getSize()), hasRead(m), 
						content, FileUtil.INBOX);
				//为mail对象设置抄送
				mail.setCcs(getCC(m));
				//设置附件集合
				mail.setFiles(getFiles(ctx, m));
				result.add(mail);
			}
			return result;
		} catch (Exception e) {
			throw new LoadMailException("得到邮件的信息出错: " + e.getMessage());
		}
	}
	
	//得到接收的日期, 优先返回发送日期, 其次返回收信日期
	private Date getReceivedDate(Message m) throws Exception {
		if (m.getSentDate() != null) return m.getSentDate();
		if (m.getReceivedDate() != null) return m.getReceivedDate();
		return new Date();
	}
	

	
	//得到抄送的地址
	private List<String> getCC(Message m) throws Exception {
		Address[] addresses = m.getRecipients(Message.RecipientType.CC);
		return getAddresses(addresses);
	}
	
	//获得邮件的附件
	private List<FileObject> getFiles(MailContext ctx, Message m) throws Exception {
		List<FileObject> files = new ArrayList<FileObject>();
		//是混合类型, 就进行处理
		if (m.isMimeType("multipart/mixed")) {
			Multipart mp = (Multipart)m.getContent();
			//得到邮件内容的Multipart对象并得到内容中Part的数量
			int count = mp.getCount();
			for (int i = 1; i < count; i++) {
				Part part = mp.getBodyPart(i);
				//在本地创建文件并添加到结果中
				files.add(FileUtil.createFileFromPart(ctx, part));
			}
		}
		return files;
	}
	
	
	//返回邮件正文
	private StringBuffer getContent(Part part, StringBuffer result) throws Exception {
		if (part.isMimeType("multipart/*")) {
			Multipart p = (Multipart)part.getContent();
			int count = p.getCount();
			//Multipart的第一部分是text/plain, 第二部分是text/html的格式, 只需要解析第一部分即可
			if (count > 1) count = 1; 
			for(int i = 0; i < count; i++) {
				BodyPart bp = p.getBodyPart(i);
				//递归调用
				getContent(bp, result);
			}
		} else if (part.isMimeType("text/*")) {
			//遇到文本格式或者html格式, 直接得到内容
			result.append(part.getContent());
		}
		return result;
	}	
	
	//判断一封邮件是否已读, true表示已读取, false表示没有读取
	private boolean hasRead(Message m) throws Exception {
		Flags flags = m.getFlags();
		if (flags.contains(Flags.Flag.SEEN)) return true;
		return false;
	}
	
	//得到一封邮件的所有收件人
	private List<String> getAllRecipients(Message m) throws Exception {
		Address[] addresses = m.getAllRecipients();
		return getAddresses(addresses);
	}
	
	//工具方法, 将参数的地址字符串封装成集合
	private List<String> getAddresses(Address[] addresses) {
		List<String> result = new ArrayList<String>();
		if (addresses == null) return result;
		for (Address a : addresses) {
			result.add(a.toString());
		}
		return result;
	}
	
	//得到发送人的地址
	private String getSender(Message m) throws Exception  {
		Address[] addresses = m.getFrom();
		return MimeUtility.decodeText(addresses[0].toString());
	}
	
	
	/*
	 * 得到邮箱INBOX
	 */
	private Folder getINBOXFolder(MailContext ctx) {
		Store store = ctx.getStore();
		try {
			return store.getFolder("INBOX");
		} catch (Exception e) {
			throw new LoadMailException("加载邮箱错误，请检查配置");
		}
	}
	
	//将邮件数组设置为删除状态
	private void deleteFromServer(Message[] messages) throws Exception {
		for (Message m : messages) {
			m.setFlag(Flags.Flag.DELETED, true);
		}
	}
	
	//进行时间排序
	private void sort(List<Mail> mails) {
		Collections.sort(mails, new MailComparator());
	}

}
