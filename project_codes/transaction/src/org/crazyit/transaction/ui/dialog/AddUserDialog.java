package org.crazyit.transaction.ui.dialog;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.crazyit.transaction.model.Role;
import org.crazyit.transaction.model.User;
import org.crazyit.transaction.ui.UserPanel;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

/**
 * 添加用户界面
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddUserDialog extends JDialog {

	//用户名
	private JLabel userNameLabel = new JLabel("用户名: ");
	private JTextField userName = new JTextField(10);
	
	//真实姓名
	private JLabel realNameLabel = new JLabel("真实姓名: ");
	private JTextField realName = new JTextField(10);
	
	//密码
	private JLabel passwordLabel = new JLabel("密码: ");
	private JPasswordField password = new JPasswordField(20);
	
	private JLabel roleLabel = new JLabel("角色: ");
	private JComboBox roleSelect = new JComboBox();
	
	//按钮
	private JButton confirmButton = new JButton("确定");
	private JButton cancelButton = new JButton("取消");
	
	private UserPanel userPanel;
	
	public AddUserDialog(UserPanel userPanel) {
		this.userPanel = userPanel;
		//用户名
		Box userNameBox = Box.createHorizontalBox();
		userNameBox.add(Box.createHorizontalStrut(30));
		userNameBox.add(this.userNameLabel);
		userNameBox.add(this.userName);
		userNameBox.add(Box.createHorizontalStrut(30));
		//真实姓名
		Box realNameBox = Box.createHorizontalBox();
		realNameBox.add(Box.createHorizontalStrut(17));
		realNameBox.add(this.realNameLabel);
		realNameBox.add(this.realName);
		realNameBox.add(Box.createHorizontalStrut(30));
		//密码
		Box passwdBox = Box.createHorizontalBox();
		passwdBox.add(Box.createHorizontalStrut(43));
		passwdBox.add(this.passwordLabel);
		passwdBox.add(this.password);
		passwdBox.add(Box.createHorizontalStrut(30));
		//角色选择
		Box roleSelectBox = Box.createHorizontalBox();
		roleSelectBox.add(Box.createHorizontalStrut(43));
		roleSelectBox.add(this.roleLabel);
		roleSelectBox.add(this.roleSelect);
		roleSelectBox.add(Box.createHorizontalStrut(30));
		//按钮
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(this.confirmButton);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(this.cancelButton);
		
		Box mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(userNameBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(realNameBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(passwdBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(roleSelectBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);	
		this.pack();
		this.setResizable(false);
		this.setTitle("新建用户");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/5);
		initListeners();
	}
	
	//创建角色下拉
	private void createRoleSelect() {
		this.roleSelect.removeAllItems();
		List<Role> roles = ApplicationContext.roleService.getRoles();
		for (Role r : roles) {
			this.roleSelect.addItem(r);
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) createRoleSelect();
	}

	private void initListeners() {
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
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
	
	//添加用户
	private void add() {
		if (this.userName.getText().equals("") || this.realName.getText().equals("")
				|| getPassword().equals("")) {
			ViewUtil.showWarn("请输入完成的用户信息", this);
			return;
		}
		try {
			//调用业务接口添加用户
			ApplicationContext.userService.addUser(getUser());
			this.setVisible(false);
			this.userPanel.readData();
			clean();
		} catch (Exception e) {
			e.printStackTrace();
			ViewUtil.showWarn(e.getMessage(), this);
		}
	}
	
	//清空界面元素
	private void clean() {
		this.userName.setText("");
		this.realName.setText("");
		this.password.setText("");
	}
	
	//从界面元素中得到各个值, 并创建User对象
	private User getUser() {
		String userName = this.userName.getText();
		String realName = this.realName.getText();
		String passwd = getPassword();
		Role role = (Role)this.roleSelect.getSelectedItem();
		User user = new User();
		user.setUSER_NAME(userName);
		user.setREAL_NAME(realName);
		user.setPASS_WD(passwd);
		user.setROLE_ID(role.getID());
		return user;
	}
}
