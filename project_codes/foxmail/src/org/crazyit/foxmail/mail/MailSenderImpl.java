package org.crazyit.foxmail.mail;

import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.crazyit.foxmail.exception.SendMailException;
import org.crazyit.foxmail.object.FileObject;
import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.ui.MailContext;

/**
 * 邮件发送实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailSenderImpl implements MailSender {

	@Override
	public Mail send(Mail mail, MailContext ctx) {
		try {
			Session session = ctx.getSession();
			Message mailMessage = new MimeMessage(session);
			//设置发件人地址
			Address from = new InternetAddress(ctx.getUser() + " <" + ctx.getAccount() + ">");
			mailMessage.setFrom(from);
			//设置所有收件人的地址
			Address[] to = getAddress(mail.getReceivers());
			mailMessage.setRecipients(Message.RecipientType.TO, to);
			//设置抄送人地址
			Address[] cc = getAddress(mail.getCcs());
			mailMessage.setRecipients(Message.RecipientType.CC, cc);
			//设置主题
			mailMessage.setSubject(mail.getSubject());
			//发送日期
			mailMessage.setSentDate(new Date());
			//构建整封邮件的容器
			Multipart main = new MimeMultipart();
			//正文的body
			BodyPart body = new MimeBodyPart();
			body.setContent(mail.getContent(), "text/html; charset=utf-8");
			main.addBodyPart(body);
			//处理附件
			for (FileObject f : mail.getFiles()) {
				//每个附件的body
				MimeBodyPart fileBody = new MimeBodyPart();
				fileBody.attachFile(f.getFile());
				//为文件名进行转码
				fileBody.setFileName(MimeUtility.encodeText(f.getSourceName()));
				main.addBodyPart(fileBody);
			}
			//将正文的Multipart对象设入Message中
			mailMessage.setContent(main);
			Transport.send(mailMessage);
			return mail;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SendMailException("发送邮件错误, 请检查邮箱配置及邮件的相关信息");
		}
	}
	//获得所有的收件人地址或者抄送的地址
	private Address[] getAddress(List<String> addList) throws Exception {
		Address[] result = new Address[addList.size()];
		for (int i = 0; i < addList.size(); i++) {
			if (addList.get(i) == null || "".equals(addList.get(i))) {
				continue;
			}
			result[i] = new InternetAddress(addList.get(i));
		}
		return result;
	}
}
