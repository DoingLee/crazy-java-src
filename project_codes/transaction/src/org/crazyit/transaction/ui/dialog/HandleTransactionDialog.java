package org.crazyit.transaction.ui.dialog;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.crazyit.transaction.model.Comment;
import org.crazyit.transaction.model.Transaction;
import org.crazyit.transaction.ui.MyTransactionPanel;
import org.crazyit.transaction.ui.handler.TransactionHandler;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

/**
 * 处理事务界面
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class HandleTransactionDialog extends JDialog {

	//标题
	private JLabel titleLabel = new JLabel("标题: ");
	private JTextField title = new JTextField(10);
	//ID
	private JTextField transationId = new JTextField();
	
	//内容
	
	private JLabel contentLabel = new JLabel("说明: ");
	private JTextArea content = new JTextArea(10, 40);
	private JScrollPane contentPane = new JScrollPane(content);
	
	//按钮
	private JButton confirmButton = new JButton("处理");
	private JButton cancelButton = new JButton("取消");
	
	private Transaction t;
	
	//事务处理类
	private TransactionHandler handler;
	
	private MyTransactionPanel myPanel;
	
	public HandleTransactionDialog(MyTransactionPanel myPanel) {
		this.myPanel = myPanel;
		this.title.setEditable(false);
		this.transationId.setVisible(false);
		//标题
		Box titleBox = Box.createHorizontalBox();
		titleBox.add(Box.createHorizontalStrut(30));
		titleBox.add(this.titleLabel);
		titleBox.add(this.title);
		titleBox.add(Box.createHorizontalStrut(30));
		//内容
		Box contentBox = Box.createHorizontalBox();
		contentBox.add(Box.createHorizontalStrut(30));
		contentBox.add(this.contentLabel);
		contentBox.add(this.contentPane);
		contentBox.add(Box.createHorizontalStrut(30));
		//按钮
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(this.confirmButton);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(this.cancelButton);
		
		Box mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(titleBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(contentBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);	
		this.pack();
		this.setResizable(false);
		this.setTitle("处理事务");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/5);
		initListeners();
	}
	
	//让外界去设置事务处理类
	public void setHandler(TransactionHandler handler) {
		this.handler = handler;
	}
	
	//让外界设置相应的事务对象
	public void setTransaction(Transaction t) {
		this.t = t;
	}
	
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (!b) return;
		if (this.t != null) {
			this.transationId.setText(t.getID());
			this.title.setText(t.getTS_TITLE());
		}
	}

	//初始化按钮监听器
	private void initListeners() {
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	//确定进行事务处理
	private void confirm() {
		Comment comment = new Comment();
		comment.setCM_CONTENT(this.content.getText());
		comment.setCM_DATE(ViewUtil.formatDate(new Date()));
		comment.setTRANSACTION_ID(this.transationId.getText());
		comment.setUSER_ID(ApplicationContext.loginUser.getID());
		try {
			//处理评论与设置事务状态
			this.handler.handler(comment);
			//刷新界面列表
			this.myPanel.readData();
			this.setVisible(false);
		} catch (Exception e) {
			ViewUtil.showWarn(e.getMessage(), this);
		}
	}
	
	
}
