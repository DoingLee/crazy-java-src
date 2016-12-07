package org.crazyit.transaction.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.crazyit.transaction.model.User;
import org.crazyit.transaction.ui.dialog.AddUserDialog;
import org.crazyit.transaction.ui.table.UserTable;
import org.crazyit.transaction.ui.table.UserTableModel;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

public class UserPanel extends BasePanel {
	
	private JScrollPane tableScrollPane;
	
	//数据列表
	private UserTable dataTable;
	private UserTableModel tableModel;
	
	//操作区域
	private Box handleBox = Box.createVerticalBox();
	
	//查询
	private Box queryBox = Box.createHorizontalBox();
	private JLabel userNameLabel = new JLabel("用户姓名: ");
	private JTextField realName = new JTextField(10);
	private JButton queryButton = new JButton("查询");
	
	//操作
	private Box operateBox = Box.createHorizontalBox();
	private JButton newButton = new JButton("新建用户");
	private JButton deleteButton = new JButton("删除用户");
	
	private AddUserDialog addUserDialog;
	
	public UserPanel() {
		this.addUserDialog = new AddUserDialog(this);
		BoxLayout mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(mainLayout);
		createTable();
		createQueryBox();
		createOperateBox();
		this.handleBox.add(Box.createVerticalStrut(20));
		this.handleBox.add(this.queryBox);
		this.handleBox.add(Box.createVerticalStrut(20));
		this.handleBox.add(this.operateBox);
		this.handleBox.add(Box.createVerticalStrut(20));
		this.add(this.handleBox);
		this.add(this.tableScrollPane);
		initListeners();
	}
	
	private void initListeners() {
		this.queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				query();
			}
		});
		this.newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addUser();
			}
		});
		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteUser();
			}
		});
	}
	
	//查询方法
	private void query() {
		String realName = this.realName.getText();
		List<User> users = ApplicationContext.userService.query(realName);
		this.tableModel.setDatas(users);
		this.dataTable.updateUI();
	}
	
	//点击打开添加用户的对话框触发的方法
	private void addUser() {
		this.addUserDialog.setVisible(true);
	}
	
	//删除用户
	private void deleteUser() {
		String userId = ViewUtil.getSelectValue(this.dataTable, "id");
		if (userId == null) {
			ViewUtil.showWarn("请选择需要操作的用户", this);
			return;
		}
		int result = ViewUtil.showConfirm("是否要删除?", this);
		if (result == 0) {
			try {
				ApplicationContext.userService.delete(userId);
				this.readData();
			} catch (Exception e) {
				ViewUtil.showWarn(e.getMessage(), this);
			}
		}
	}
	
	//创建事务列表
	private void createTable() {
		this.tableModel = new UserTableModel();
		this.dataTable = new UserTable(this.tableModel);
		this.dataTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
		this.tableScrollPane = new JScrollPane(this.dataTable);
	}
	
	//创建操作区域Box
	private void createOperateBox() {
		this.operateBox.add(this.newButton);
		this.operateBox.add(Box.createHorizontalStrut(30));
		this.operateBox.add(this.deleteButton);
		this.handleBox.add(this.operateBox);
	}
	
	//实现父类的抽象方法, 读取数据
	public void readData() {
		List<User> users = ApplicationContext.userService.getUsers();
		this.tableModel.setDatas(users);
		this.dataTable.updateUI();
	}

	//创建查询区域
	private void createQueryBox() {
		this.queryBox.add(Box.createHorizontalStrut(100));
		this.queryBox.add(this.userNameLabel);
		this.queryBox.add(this.realName);
		this.queryBox.add(Box.createHorizontalStrut(20));
		this.queryBox.add(this.queryButton);
		this.queryBox.add(Box.createHorizontalStrut(100));
		this.handleBox.add(this.queryBox);
	}
}
