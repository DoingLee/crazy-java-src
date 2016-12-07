package org.crazyit.mysql.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.crazyit.mysql.object.GlobalContext;
import org.crazyit.mysql.object.tree.ServerConnection;
import org.crazyit.mysql.util.FileUtil;

/**
 * 新建连接的界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("serial")
public class ConnectionFrame extends JFrame {

	private Box mainBox = Box.createVerticalBox();
	private Box connectionNameBox = Box.createHorizontalBox();
	private Box ipBox = Box.createHorizontalBox();
	private Box portBox = Box.createHorizontalBox();
	private Box usernameBox = Box.createHorizontalBox();
	private Box passwordBox = Box.createHorizontalBox();
	private Box buttonBox = Box.createHorizontalBox();
	//连接名称
	private JLabel connectionNameLabel = new JLabel("连接名称：");
	private JTextField connectionNameField = new JTextField(20);
	//ip
	private JLabel ipLabel = new JLabel("连接IP：");
	private JTextField ipField = new JTextField(20);
	//端口
	private JLabel portLabel = new JLabel("端口："); 
	private JTextField portField = new JTextField("3306", 20);
	//用户名
	private JLabel usernameLabel = new JLabel("用户名：");
	private JTextField userNameField = new JTextField(20);
	//密码
	private JLabel passwordLabel = new JLabel("密码：");
	private JPasswordField passwordField = new JPasswordField();
	//按钮
	private JButton confirmButton = new JButton("确定");
	private JButton cancelButton = new JButton("取消");
	private JButton testButton = new JButton("测试连接");
	
	//全局上下文对象
	private GlobalContext ctx;
	//主界面对象
	private MainFrame mainFrame;
	
	public ConnectionFrame(GlobalContext ctx, MainFrame mainFrame) {
		this.ctx = ctx;
		this.mainFrame = mainFrame;
		this.connectionNameBox.add(Box.createHorizontalStrut(30));
		this.connectionNameBox.add(connectionNameLabel);
		this.connectionNameBox.add(Box.createHorizontalStrut(10));
		this.connectionNameBox.add(connectionNameField);
		this.connectionNameBox.add(Box.createHorizontalStrut(30));
		this.ipBox.add(Box.createHorizontalStrut(30));
		this.ipBox.add(ipLabel);
		this.ipBox.add(Box.createHorizontalStrut(25));
		this.ipBox.add(ipField);
		this.ipBox.add(Box.createHorizontalStrut(30));
		this.portBox.add(Box.createHorizontalStrut(30));
		this.portBox.add(portLabel);
		this.portBox.add(Box.createHorizontalStrut(36));
		this.portBox.add(portField);
		this.portBox.add(Box.createHorizontalStrut(30));
		this.usernameBox.add(Box.createHorizontalStrut(30));
		this.usernameBox.add(usernameLabel);
		this.usernameBox.add(Box.createHorizontalStrut(23));
		this.usernameBox.add(userNameField);
		this.usernameBox.add(Box.createHorizontalStrut(30));
		this.passwordBox.add(Box.createHorizontalStrut(30));
		this.passwordBox.add(passwordLabel);
		this.passwordBox.add(Box.createHorizontalStrut(36));
		this.passwordBox.add(passwordField);
		this.passwordBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(testButton);
		this.buttonBox.add(Box.createHorizontalStrut(40));
		this.buttonBox.add(confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(36));
		this.buttonBox.add(cancelButton);
		
		
		this.mainBox.add(Box.createVerticalStrut(30));
		this.mainBox.add(connectionNameBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(ipBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(portBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(usernameBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(passwordBox);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.mainBox.add(buttonBox);
		this.mainBox.add(Box.createVerticalStrut(30));
		
		this.add(mainBox);
		this.setLocation(350, 200);
		this.setTitle("新建连接");
		this.setResizable(false);
		this.pack();
		initListeners();
	}
	
	private void hideFrame() {
		this.setVisible(false);
	}
	
	private void initListeners() {
		this.confirmButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				saveConnection();
			}
		});
		this.cancelButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				hideFrame();
			}
		});
		this.testButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				checkConnection();
			}
		});
	}
	
	//测试连接
	private void checkConnection() {
		//从界面中得到连接信息
		ServerConnection conn = getDataConnectionFromView();
		if (!passValidate(conn)) {
			showMessage("请输入完整的信息", "错误");
			return;
		}
		try {
			conn.connect();
			showMessage("成功连接", "成功");
		} catch (Exception e) {
			showMessage(e.getMessage(), "警告");
		}
	}
	
	//保存连接
	private void saveConnection() {
		//得到用户输入的信息并返回一个ServerConnection对象
		ServerConnection conn = getDataConnectionFromView();
		if (!passValidate(conn)) {
			showMessage("请输入完整的信息", "错误");
			return;
		}
		//判断连接名称是否重复
		if (this.ctx.getConnection(conn.getConnectionName()) != null) {
			showMessage("已经存在相同名字的连接", "错误");
			return;
		}
		//直接保存, 不需要创建任何的连接
		this.ctx.addConnection(conn);
		//保存到属性文件
		this.ctx.getPropertiesHandler().saveServerConnection(conn);
		this.mainFrame.addConnection(conn);
		this.setVisible(false);
	}
	
	//验证界面信息是否有填写
	private boolean passValidate(ServerConnection conn) {
		if (conn.getConnectionName().trim().equals("")) return false;
		if (conn.getHost().trim().equals("")) return false;
		if (conn.getPort().trim().equals("")) return false;
		if (conn.getUsername().trim().equals("")) return false;
		if (conn.getPassword().trim().equals("")) return false;
		return true;
	}

	//从界面取得相关的信息
	private ServerConnection getDataConnectionFromView() {
		String connectionName = this.connectionNameField.getText();
		String host = this.ipField.getText();
		String port = this.portField.getText();
		String username = this.userNameField.getText();
		String password = getPassword();
		return new ServerConnection(connectionName, username, password, 
				host, port);
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	//返回密码字符串
	private String getPassword() {
		char[] passes = this.passwordField.getPassword();
		StringBuffer password = new StringBuffer();
		for (char c : passes) {
			password.append(c);
		}
		return password.toString();
	}
}
