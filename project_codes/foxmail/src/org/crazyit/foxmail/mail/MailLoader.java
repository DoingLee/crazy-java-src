package org.crazyit.foxmail.mail;

import java.util.List;

import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.ui.MailContext;

/**
 * 读取邮件信息的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface MailLoader {
	
	/**
	 * 得到INBOX的所有邮件
	 * @param ctx 邮箱的上下文
	 * @return
	 */
	List<Mail> getMessages(MailContext ctx);
	
}
