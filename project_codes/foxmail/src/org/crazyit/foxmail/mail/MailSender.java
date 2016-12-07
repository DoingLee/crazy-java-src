package org.crazyit.foxmail.mail;

import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.ui.MailContext;

/**
 * 发送邮件接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface MailSender {

	/**
	 * 发送一封邮件并返回该邮件对象
	 * @param mail
	 * @param ctx
	 * @return
	 */
	Mail send(Mail mail, MailContext ctx);
}
