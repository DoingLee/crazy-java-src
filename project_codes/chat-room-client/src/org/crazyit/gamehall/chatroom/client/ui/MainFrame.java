package org.crazyit.gamehall.chatroom.client.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.util.XStreamUtil;

public class MainFrame extends JFrame {

	//显示聊天信息的文本域
	private JTextArea textArea = new JTextArea(30, 10);
	
	//聊天室用户列表
	private JList list;
	
	//显示自己信息的JLabel
	private JLabel infoLabel;
	
	//输入聊天内容的文本域
	private JTextField field = new JTextField(20);
	
	//发送按钮
	private JButton sendButton = new JButton("发送");
	
	//当前的用户
	private User user;
	
	private List<User> users;
	
	//所有人
	private User allUser = new User();
	
	public MainFrame(User user, List<User> users) {
		this.user = user;
		this.users = users;
		this.users.add(0, this.allUser);
		this.allUser.setName("所有人");
		this.allUser.setId("all");
		//将自己从用户列表中删除
		removeSelf();
		this.infoLabel = new JLabel(user.getName());
		this.infoLabel.setIcon(new ImageIcon(user.getHeadImage()));
		//创建用户列表
		createUserList();
		this.textArea.setEditable(false);
		JScrollPane contentPane = new JScrollPane(this.textArea);
		contentPane.setMinimumSize(new Dimension(400, 200));
		Box infoBox = Box.createHorizontalBox();
		infoBox.add(this.infoLabel);
		
		Box sendBox = Box.createHorizontalBox();
		sendBox.add(this.field);
		sendBox.add(this.sendButton);
		
		Box contentBox = Box.createVerticalBox();
		contentBox.add(infoBox);
		contentBox.add(contentPane);
		contentBox.add(sendBox);
		
		JScrollPane listPane = new JScrollPane(this.list);
		listPane.setMinimumSize(new Dimension(150, 200));
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contentBox, listPane);
		mainPanel.setDividerLocation(400);
		mainPanel.setDividerSize(3);
		this.sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		this.add(mainPanel);
		this.setTitle("测试聊天室");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setSize(550, 630);
		this.setLocation(200, 50);
		this.setVisible(true);
	}
	
	
	//将自己从用户列表中删除
	private void removeSelf() {
		for (Iterator it = this.users.iterator(); it.hasNext();) {
			User u = (User)it.next();
			if (u.getId().equals(this.user.getId())) {
				it.remove();
			}
		}
	}
	
	//创建用户列表
	private void createUserList() {
		this.list = new JList();
		this.list.setListData(this.users.toArray());
		this.list.setFixedCellHeight(40);
		this.list.setCellRenderer(new UserListCellRenderer());
	}
	
	//添加一个新进入的用户
	public void addUser(User newUser) {
		this.users.add(newUser);
		appendContent(newUser.getName() + " 进来了");
		refresh();
	}
	
	public void appendContent(String content) {
		if (this.textArea.getText().equals("")) this.textArea.append(content);
		else this.textArea.append("\n" + content);
	}
	
	//刷新列表
	private void refresh() {
		this.list.setListData(this.users.toArray());
	}
	
	//发送信息
	private void send() {
		User selectUser = (User)this.list.getSelectedValue();
		if (selectUser == null) {
			selectUser = this.allUser;
		}
		Request request = new Request("org.crazyit.gamehall.chatroom.server.action.UserSendAction", 
				"org.crazyit.gamehall.chatroom.client.action.ReceiveMessageAction");
		request.setParameter("content", this.field.getText());
		request.setParameter("receiverId", selectUser.getId());
		request.setParameter("senderId", this.user.getId());
		appendContent("你对 " + selectUser.getName() + " 说: " + this.field.getText());
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setName("boxiong");
		user.setHeadImage("images/heads/3.gif");
		
		List<User> users = new ArrayList<User>();
		User u1 = new User();
		u1.setHeadImage("images/heads/1.gif");
		u1.setName("user1");
		User u2 = new User();
		u2.setName("user2");
		u2.setHeadImage("images/heads/2.gif");
		users.add(u1);
		users.add(u2);
		MainFrame m = new MainFrame(user, users);
	}
}


