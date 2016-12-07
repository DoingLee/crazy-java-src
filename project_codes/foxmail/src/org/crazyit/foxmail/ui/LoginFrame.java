package org.crazyit.foxmail.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.crazyit.foxmail.exception.LoginException;
import org.crazyit.foxmail.util.FileUtil;
import org.crazyit.foxmail.util.PropertiesUtil;

/**
 * 登录界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class LoginFrame extends JFrame {

	
	//用户名
	private JLabel userLabel = new JLabel("用户名：");	
	private JTextField userField = new JTextField(20);
	//确定按钮
	private JButton confirmButton = new JButton("确定");
	//取消按钮
	private JButton cancelButton = new JButton("取消");
	//按钮Box
	private Box buttonBox = Box.createHorizontalBox();
	//用户Box
	private Box userBox = Box.createHorizontalBox();
	//最大的Box
	private Box mainBox = Box.createVerticalBox();
	//系统主界面
	private MainFrame mainFrame;
	
	public LoginFrame() {
		this.userBox.add(Box.createHorizontalStrut(30));
		this.userBox.add(userLabel);
		this.userBox.add(Box.createHorizontalStrut(20));
		this.userBox.add(userField);
		this.userBox.add(Box.createHorizontalStrut(30));
		
		//按钮的Box
		this.buttonBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(this.confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(20));
		this.buttonBox.add(this.cancelButton);
		this.buttonBox.add(Box.createHorizontalStrut(30));
		
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.mainBox.add(this.userBox);
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.mainBox.add(this.buttonBox);
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.add(mainBox);
		this.setLocation(300, 200);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("邮件收发客户端");
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
				System.exit(0);
			}
		});
	}
	
	private void confirm() {
		String user = this.userField.getText();
		if (user.trim().equals("")) {
			JOptionPane.showConfirmDialog(this, "请输入用户名", "警告", 
					JOptionPane.OK_CANCEL_OPTION);
			return;
		}
		//得到用户名对应的目录
		File folder = new File(FileUtil.DATE_FOLDER + user);
		//该用户第一次进入系统， 创建目录
		if (!folder.exists()) {
			folder.mkdir();
		}
		//得到配置文件
		File config = new File(folder.getAbsolutePath() + FileUtil.CONFIG_FILE);
		try {
			//没有对应的配置文件，则创建
			if (!config.exists()) {
				config.createNewFile();
			}
			//读取配置并转换为MailContext对象
			MailContext ctx = PropertiesUtil.createContext(config);
			//设置MailContext的user属性
			ctx.setUser(user);
			//创建系统界面主对象
			this.mainFrame = new MainFrame(ctx);
			this.mainFrame.setVisible(true);
			this.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
			throw new LoginException("配置文件错误");
		}
	}
	

	

}
