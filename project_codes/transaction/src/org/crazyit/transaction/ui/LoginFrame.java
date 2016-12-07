package org.crazyit.transaction.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

public class LoginFrame extends JFrame {

	//用户名
	private JLabel userNameLabel = new JLabel("用户名: ");
	private JTextField userName = new JTextField(20);
	//密码
	private JLabel passwordLabel = new JLabel("密码: ");
	private JPasswordField password = new JPasswordField(20);
	//按钮
	private JButton confirmButton = new JButton("确定");
	private JButton cancelButton = new JButton("取消");
	
	public LoginFrame() {
		//用户名
		Box userNameBox = Box.createHorizontalBox();
		userNameBox.add(Box.createHorizontalStrut(50));
		userNameBox.add(this.userNameLabel);
		userNameBox.add(this.userName);
		userNameBox.add(Box.createHorizontalStrut(50));
		//密码
		Box passwordBox = Box.createHorizontalBox();
		passwordBox.add(Box.createHorizontalStrut(50));
		passwordBox.add(this.passwordLabel);
		passwordBox.add(Box.createHorizontalStrut(13));
		passwordBox.add(this.password);
		passwordBox.add(Box.createHorizontalStrut(50));
		//按钮
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(this.confirmButton);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(this.cancelButton);
		//主Box
		Box mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(30));
		mainBox.add(userNameBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(passwordBox);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		//获得屏幕大小
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.add(mainBox);
		this.setLocation((int)screen.getWidth()/3, (int)screen.getHeight()/3);
		this.pack();
		this.setTitle("登录事务跟踪系统");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initListeners();
	}
	
	//初始化监听器
	private void initListeners() {
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	//返回密码字符串
	private String getPassword() {
		char[] passes = this.password.getPassword();
		StringBuffer password = new StringBuffer();
		for (char c : passes) {
			password.append(c);
		}
		return password.toString();
	}
	
	//点击确定按钮触发的方法
	private void login() {
		//得到用户名
		String userName = this.userName.getText();
		//得到密码
		String passwd = getPassword();
		//进行登录
		try {
			ApplicationContext.userService.login(userName, passwd);
			this.setVisible(false);
			MainFrame mf = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
			ViewUtil.showWarn(e.getMessage(), this);
		}
	}
	

}
