package org.crazyit.foxmail.system;

import java.util.List;

import org.crazyit.foxmail.object.Mail;
import org.crazyit.foxmail.ui.MailContext;

/**
 * 加载本地的邮件的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface SystemLoader {

	/**
	 * 根据MailContext得到对应的收件箱邮件(在本地系统中获取)
	 * @param ctx
	 * @return
	 */
	List<Mail> getInBoxMails(MailContext ctx);
	
	/**
	 * 根据MailContext得到对应的发件箱邮件(在本地系统中获取)
	 * @param ctx
	 * @return
	 */
	List<Mail> getOutBoxMails(MailContext ctx);
	
	/**
	 * 根据MailContext得到对应的已发送的邮件(在本地系统中获取)
	 * @param ctx
	 * @return
	 */
	List<Mail> getSentBoxMails(MailContext ctx);
	
	/**
	 * 根据MailContext得到对应的草稿箱的邮件(在本地系统中获取)
	 * @param ctx
	 * @return
	 */
	List<Mail> getDraftBoxMails(MailContext ctx);
	
	/**
	 * 根据MailContext得到对应的垃圾箱的邮件(在本地系统中获取)
	 * @param ctx
	 * @return
	 */
	List<Mail> getDeletedBoxMails(MailContext ctx);
}
