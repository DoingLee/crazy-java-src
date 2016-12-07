package org.crazyit.gamehall.fivechess.client.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 聊天的JPanel, 可重用
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ChatPanel extends JPanel {

	//聊天内容的文本域
	private JTextArea contentArea;
	
	//输入内容的文本框
	private JTextField conentField;
	
	//聊天对象
	private JComboBox target;
	
	//发送按钮
	private JButton sendButton;
	
	private JScrollPane scrollPane;
	
	private List<ChessUser> users;
	
	private ChessUser user;
	
	//聊天的服务器处理类
	private String serverAction;
	
	//聊天的客户端处理类
	private String clientAction;
	
	public ChatPanel(List<ChessUser> users, ChessUser user, String serverAction, 
			String clientAction) {
		this.users = users;
		this.user = user;
		this.serverAction = serverAction;
		this.clientAction = clientAction;
		this.contentArea = new JTextArea(22, 25);
		//设置不可编辑
		this.contentArea.setEditable(false);
		//设置换行
		this.contentArea.setLineWrap(true);
		this.conentField = new JTextField();
		this.target = new JComboBox();
		//创建下拉元素
		addTargets();
		this.sendButton = new JButton("发送");
		this.scrollPane = new JScrollPane(this.contentArea);
		
		Box sendBox = Box.createHorizontalBox();
		sendBox.add(this.conentField);
		sendBox.add(this.sendButton);
		Box targetBox = Box.createHorizontalBox();
		targetBox.add(new JLabel("选择聊天对象："));
		targetBox.add(this.target);
		Box mainBox = Box.createVerticalBox();
		mainBox.add(this.scrollPane);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(targetBox);
		mainBox.add(Box.createVerticalStrut(5));
		mainBox.add(sendBox);
		this.add(mainBox);
		this.sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
	}
		
	//创建聊天对象下拉的选择
	private void addTargets() {
		ChessUser all = new ChessUser();
		all.setName("所有人");
		this.target.addItem(all);
		for (ChessUser cu : this.users) {
			if (!cu.getId().equals(this.user.getId())) {
				this.target.addItem(cu);
			}
		}
	}
	
	//刷新下拉
	public void refreshJComboBox() {
		this.target.removeAllItems();
		addTargets();
	}
	
	public void appendContent(String content) {
		if (this.contentArea.getText().equals("")) this.contentArea.append(content);
		else this.contentArea.append("\n" + content);
	}
	
	//发送信息
	public void send() {
		//得到发送的内容
		String content = this.conentField.getText();
		//得到接收玩家
		ChessUser receiver = (ChessUser)this.target.getSelectedItem();
		//构造请求
		Request request = new Request(this.serverAction, this.clientAction);
		//设置参数
		request.setParameter("receiverId", receiver.getId());
		request.setParameter("senderId", this.user.getId());
		request.setParameter("content", content);
		//发送请求
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
		appendContent("你对 " + receiver.getName() + " 说: " + content);
	}
	
}
