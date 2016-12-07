package org.crazyit.book.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.crazyit.book.service.UserService;

/**
 * 登录的JFrame
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class LoginFrame extends JFrame {
	
	//定义"帐号"和"密码"的标签
	private JLabel acc = new JLabel("帐号 ");
	private JLabel pass = new JLabel("密码 ");
	
	//定义存放用户帐号和密码的文本项
	private JTextField accText = new JTextField();
	private JPasswordField passText = new JPasswordField();

	//定义登录界面的Box容器，以便使用BoxLayout布局器
	private Box up = Box.createHorizontalBox();
	private Box center = Box.createHorizontalBox();
	private Box upCenter = Box.createVerticalBox();
	private Box down = Box.createHorizontalBox();
	
	UserService userService;
    
	//定义登录按钮
	private JButton login = new JButton("登录");
	
	public LoginFrame(UserService userService)
	{
		this.userService = userService;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//布局各容器，并设置各容器的水平和垂直间距
		up.add(Box.createHorizontalStrut(50));
		up.add(acc);
		up.add(Box.createHorizontalStrut(10));
		up.add(accText);
		up.add(Box.createHorizontalStrut(100));
				
		center.add(Box.createHorizontalStrut(50));
		center.add(pass);
		center.add(Box.createHorizontalStrut(10));
		center.add(passText);
		center.add(Box.createHorizontalStrut(100));

		upCenter.add(Box.createVerticalStrut(20));
		upCenter.add(up);
		upCenter.add(Box.createVerticalStrut(20));
		upCenter.add(center);
		upCenter.add(Box.createVerticalStrut(20));

		down.add(Box.createHorizontalStrut(270));
		down.add(login, BorderLayout.EAST);
		down.add(Box.createHorizontalStrut(30));
		down.add(Box.createVerticalStrut(50));

		this.add(upCenter, BorderLayout.CENTER);
		this.add(down, BorderLayout.SOUTH);
		this.setBounds(300, 250, 350, 200);
		this.pack();
		this.setVisible(true);
		initListeners();
	}
	
	public void initListeners() {
		this.login.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				login();
			}
			
		});
	}
	
	public void login() {
		String name = this.accText.getText().trim();
		char[] passes = this.passText.getPassword();
		StringBuffer password = new StringBuffer();
		for (char c : passes) {
			password.append(c);
		}
		try {
			userService.login(name, password.toString());
			new MainFrame();
			this.setVisible(false);
		} catch (Exception e) {
			showWarn(e.getMessage());
		}
		
	}
	
	
	
	//显示警告
	protected int showWarn(String message) {
		return JOptionPane.showConfirmDialog(this, message, "警告", 
				JOptionPane.OK_CANCEL_OPTION);
	}
}
