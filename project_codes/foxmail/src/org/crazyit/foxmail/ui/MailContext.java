package org.crazyit.foxmail.ui;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import org.crazyit.foxmail.exception.MailConnectionException;

/**
 * 邮箱上下文
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailContext {
	//系统用户
	private String user;
	//用户帐号
	private String account;
	//密码
	private String password;
	//smtp邮件服务器
	private String smtpHost;
	//smtp端口
	private int smtpPort;
	//pop3邮件服务器
	private String pop3Host;
	//pop3的端口
	private int pop3Port;
	//是否进行重置信息
	private boolean reset = false;
	
	public MailContext(String user, String account, String password, String smtpHost,
			int smtpPort, String pop3Host, int pop3Port) {
		super();
		this.user = user;
		this.account = account;
		this.password = password;
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.pop3Host = pop3Host;
		this.pop3Port = pop3Port;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getPop3Host() {
		return pop3Host;
	}

	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	public int getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}

	private Store store;
	
	public Store getStore() {
		//重置了信息, 设置session为null
		if (this.reset) {
			this.store = null;
			this.session = null;
			this.reset = false;
		}
		if (this.store == null || !this.store.isConnected()) {
			try {
				Properties props = System.getProperties();
				if (isGmail()) {
					props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
				}
				//创建mail的Session
				Session session = Session.getDefaultInstance(props, getAuthenticator());
				//使用pop3协议接收邮件
				URLName url = new URLName("pop3", getPop3Host(), getPop3Port(), null,   
						getAccount(), getPassword());
				//得到邮箱的存储对象
				Store store = session.getStore(url);
				store.connect();
				this.store = store;
			} catch (Exception e) {
				e.printStackTrace();
				throw new MailConnectionException("连接邮箱异常，请检查配置");
			}
		}
		return this.store;
	}
	
	private Session session;
	
	private final static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	public Session getSession() {
		//重置了信息, 设置session为null
		if (this.reset) {
			this.session = null;
			this.store = null;
			this.reset = false;
		}
		if (this.session == null) {
			Properties props = System.getProperties();
			if (isGmail()) {
				props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			}
			System.out.println(this.getSmtpPort());
			props.put("mail.smtp.host", this.getSmtpHost());  
			props.put("mail.smtp.port", this.getSmtpPort());
			props.put("mail.smtp.auth", true);
			Session sendMailSession = Session.getDefaultInstance(props, getAuthenticator());
			this.session = sendMailSession;
		}
		return this.session;
	}
	
	private boolean isGmail() {
		if (this.account == null || this.account.trim().equals("")) return false;
		if (this.account.lastIndexOf("@gmail.com") != -1) {
			return true;
		}
		return false;
	}
	
	private Authenticator getAuthenticator() {
		return new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getAccount(), getPassword());
			}
		};
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
}
