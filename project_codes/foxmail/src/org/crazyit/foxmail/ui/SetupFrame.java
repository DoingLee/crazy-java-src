package org.crazyit.foxmail.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.crazyit.foxmail.exception.ValidateException;
import org.crazyit.foxmail.util.FileUtil;
import org.crazyit.foxmail.util.PropertiesUtil;

/**
 * 邮箱配置界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SetupFrame extends JFrame {

	//帐号的JLabel
	private JLabel accountLabel = new JLabel("邮箱地址：");
	//密码Jabel
	private JLabel passwordLabel = new JLabel("邮箱密码：");
	//邮件地址输入框
	private JTextField accountField = new JTextField(20);
	//密码输入框
	private JPasswordField passwordField = new JPasswordField(20);
	//确定按钮
	private JButton confirmButton = new JButton("确定");
	//取消按钮
	private JButton cancelButton = new JButton("取消");
	//邮箱帐号Box
	private Box accountBox = Box.createHorizontalBox();
	//密码的Box
	private Box passwordBox = Box.createHorizontalBox();
	//发送邮件服务器的Box
	private Box smtpBox = Box.createHorizontalBox();
	//接收邮件服务器的Box
	private Box pop3Box = Box.createHorizontalBox();
	//按钮Box
	private Box buttonBox = Box.createHorizontalBox();
	//加入到JFrame的Box
	private Box main = Box.createVerticalBox();
	//邮件系统主界面
	private MainFrame mainFrame;
	//发送邮件服务器(SMTP)
	private JLabel smtpLabel = new JLabel("发送邮件服务器（SMTP）：");
	private JTextField smtpField = new JTextField(10);
	private JLabel smtpPortLabel = new JLabel("端口：");
	private JTextField smtpPortField = new JTextField(5);
	//接收邮件服务器（POP3）
	private JLabel pop3Label = new JLabel("接收邮件服务器（POP3）：");
	private JTextField pop3Field = new JTextField(10);
	private JLabel pop3PortLabel = new JLabel("端口：");
	private JTextField pop3PortField = new JTextField(5);
	
	public SetupFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		//初始化界面组件
		initFrame(this.mainFrame.getMailContext());
		//初始化监听器
		initListener();
	}
	
	//初始化监听器
	private void initListener() {
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideFrame();
			}
		});
	}
	
	/*
	 * 隐藏
	 */
	private void hideFrame() {
		this.setVisible(false);
	}
	
	/*
	 * 返回密码字符串
	 */
	private String getPassword() {
		char[] passes = this.passwordField.getPassword();
		StringBuffer password = new StringBuffer();
		for (char c : passes) {
			password.append(c);
		}
		return password.toString();
	}
	
	//确定按钮
	private void confirm() {
		try {
			//重新设置系统上下文的信息
			MailContext ctx = getMailContext(this.mainFrame.getMailContext());
			//设置已经对信息进行了重新设定
			ctx.setReset(true);
			//将新的上下文写入配置文件中
			PropertiesUtil.store(ctx);
			//设置主界面的MailContext对象
			this.mainFrame.setMailContext(ctx);
			//创建存放邮件的目录(并非用户的目录, 一个用户可能有多个邮箱地址)
			FileUtil.createFolder(ctx);
			this.setVisible(false);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(this, e.getMessage(), "警告", 
					JOptionPane.OK_CANCEL_OPTION);
			return;
		}
	}
	
	/*
	 * 根据界面的值封装MailContext
	 */
	private MailContext getMailContext(MailContext ctx) {
		String account = this.accountField.getText();
		String password = getPassword();
		String smtpHost = this.smtpField.getText();
		String smtpPortStr = this.smtpPortField.getText();
		String pop3Host = this.pop3Field.getText();
		String pop3PortStr = this.pop3PortField.getText();
		String[] values = new String[]{account, password, smtpHost, smtpPortStr, 
				pop3Host, pop3Host, pop3PortStr};
		//验证必填输入
		validateRequire(values);
		//验证端口数字
		validateLegal(new String[]{smtpPortStr, pop3PortStr});
		Integer smtpPort = Integer.valueOf(smtpPortStr);
		Integer pop3Port = Integer.valueOf(pop3PortStr);
		ctx.setAccount(account);
		ctx.setPassword(password);
		ctx.setSmtpHost(smtpHost);
		ctx.setSmtpPort(smtpPort);
		ctx.setPop3Host(pop3Host);
		ctx.setPop3Port(pop3Port);
		//由于重新设置了连接信息, 因此设置MailContext的reset值为true
		ctx.setReset(true);
		return ctx;
	}
	
	
	/*
	 * 验证数字是否合法
	 */
	private void validateLegal(String[] values) {
		try {
			for (String s : values) {
				Integer.valueOf(s);
			}
		} catch (NumberFormatException e) {
			throw new ValidateException("请输入正确的数字");
		}
	}
	
	/*
	 * 验证必填输入
	 */
	private void validateRequire(String[] values) {
		for (String s :values) {
			if (s.trim().equals("")) {
				throw new ValidateException("请输入完整的信息");
			}
		}
	}
	
	
	//初始化JFrame
	private void initFrame(MailContext ctx) {
		//邮箱帐号的Box
		this.accountBox.add(Box.createHorizontalStrut(30));
		this.accountBox.add(this.accountLabel);
		this.accountBox.add(Box.createHorizontalStrut(13));
		this.accountBox.add(this.accountField);
		this.accountBox.add(Box.createHorizontalStrut(30));
		//邮箱密码的Box
		this.passwordBox.add(Box.createHorizontalStrut(30));
		this.passwordBox.add(this.passwordLabel);
		this.passwordBox.add(Box.createHorizontalStrut(13));
		this.passwordBox.add(this.passwordField);
		this.passwordBox.add(Box.createHorizontalStrut(30));
		//发送邮件服务器的Box
		this.smtpBox.add(Box.createHorizontalStrut(30));
		this.smtpBox.add(this.smtpLabel);
		this.smtpBox.add(this.smtpField);
		this.smtpBox.add(Box.createHorizontalStrut(5));
		this.smtpBox.add(this.smtpPortLabel);
		this.smtpBox.add(this.smtpPortField);
		this.smtpBox.add(Box.createHorizontalStrut(30));
		//接收邮件服务器的Box
		this.pop3Box.add(Box.createHorizontalStrut(31));
		this.pop3Box.add(this.pop3Label);
		this.pop3Box.add(this.pop3Field);
		this.pop3Box.add(Box.createHorizontalStrut(5));
		this.pop3Box.add(this.pop3PortLabel);
		this.pop3Box.add(this.pop3PortField);
		this.pop3Box.add(Box.createHorizontalStrut(30));
		//按钮的Box
		this.buttonBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(this.confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(20));
		this.buttonBox.add(this.cancelButton);
		this.buttonBox.add(Box.createHorizontalStrut(30));
		//放置于JFrame中的Box
		this.main.add(Box.createVerticalStrut(20));
		this.main.add(this.accountBox);
		this.main.add(Box.createVerticalStrut(10));
		this.main.add(this.passwordBox);
		this.main.add(Box.createVerticalStrut(10));
		this.main.add(this.smtpBox);
		this.main.add(Box.createVerticalStrut(10));
		this.main.add(this.pop3Box);
		this.main.add(Box.createVerticalStrut(10));
		this.main.add(this.buttonBox);
		this.main.add(Box.createVerticalStrut(20));
		//初始化各个配置
		this.accountField.setText(ctx.getAccount());
		this.passwordField.setText(ctx.getPassword());
		this.smtpField.setText(ctx.getSmtpHost());
		this.pop3Field.setText(ctx.getPop3Host());
		this.smtpPortField.setText(String.valueOf(ctx.getSmtpPort()));
		this.pop3PortField.setText(String.valueOf(ctx.getPop3Port()));
		//设置JFrame的各个属性
		this.setTitle("邮件收发客户端");
		this.setLocation(300, 200); 
		this.setResizable(false);
		this.add(this.main);
		this.pack();
	}
	
	
}
