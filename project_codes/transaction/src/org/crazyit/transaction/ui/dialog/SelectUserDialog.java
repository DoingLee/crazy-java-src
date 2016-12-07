package org.crazyit.transaction.ui.dialog;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.crazyit.transaction.model.User;
import org.crazyit.transaction.ui.handler.UserSelectHandler;
import org.crazyit.transaction.ui.table.UserTable;
import org.crazyit.transaction.ui.table.UserTableModel;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

/**
 * 用户选择对话框
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SelectUserDialog extends JDialog {

	private JScrollPane tableScrollPane;
	//用户查询
	private JLabel realNameLabel = new JLabel("真实姓名: ");
	private JTextField realName = new JTextField(10);
	private JButton queryButton = new JButton("查询");
	
	//用户列表
	private UserTable userTable;
	private UserTableModel tableModel;
	
	//按钮
	private JButton confirmButton = new JButton("确定");
	private JButton cancelButton = new JButton("取消");
	
	//用户选择处理类
	private UserSelectHandler selectHandler;
	
	public SelectUserDialog(UserSelectHandler selectHandler) {
		this.selectHandler = selectHandler;
		Box queryBox = Box.createHorizontalBox();
		queryBox.add(Box.createHorizontalStrut(130));
		queryBox.add(this.realNameLabel);
		queryBox.add(this.realName);
		queryBox.add(Box.createHorizontalStrut(30));
		queryBox.add(this.queryButton);
		queryBox.add(Box.createHorizontalStrut(130));
		createTable();
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(this.confirmButton);
		buttonBox.add(Box.createHorizontalStrut(50));
		buttonBox.add(this.cancelButton);
		
		Box mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(queryBox);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(this.tableScrollPane);
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);
		this.pack();
		this.setResizable(false);
		this.setTitle("选择用户");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/5);
		initListeners();
	}
	
	//初始化按钮监听器
	private void initListeners() {
		this.queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				query();
			}
		});
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
	}
	
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (!b) return;
		//从数据库中查询全部的用户
		List<User> users = ApplicationContext.userService.getUsers();
		refreshDate(users);
	}

	//查询方法
	private void query() {
		String realName = this.realName.getText();
		//调用用户业务接口进行用户模糊查询
		List<User> users = ApplicationContext.userService.query(realName);
		refreshDate(users);
	}
	
	//点击确定执行的方法
	private void confirm() {
		String id = ViewUtil.getSelectValue(this.userTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择一个用户", this);
			return;
		}
		//得到真实姓名
		String realName = ViewUtil.getSelectValue(this.userTable, UserTableModel.REAL_NAME);
		//调用用户选择处理类的方法
		this.selectHandler.confirm(id, realName);
		this.setVisible(false);
	}
	
	//工具方法, 用于刷新列表
	private void refreshDate(List<User> users) {
		this.tableModel.setDatas(users);
		this.userTable.updateUI();
	}

	//创建事务列表
	private void createTable() {
		this.tableModel = new UserTableModel();
		this.userTable = new UserTable(this.tableModel);
		this.userTable.setPreferredScrollableViewportSize(new Dimension(500, 280)); 
		this.tableScrollPane = new JScrollPane(this.userTable);
	}
}
