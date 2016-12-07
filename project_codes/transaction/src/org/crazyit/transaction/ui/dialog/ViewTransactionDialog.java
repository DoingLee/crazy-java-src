package org.crazyit.transaction.ui.dialog;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.crazyit.transaction.model.Log;
import org.crazyit.transaction.model.Transaction;

public class ViewTransactionDialog extends JDialog {

	//标题
	private JLabel titleLabel = new JLabel("标题: ");
	private JTextField title = new JTextField(10);
	
	//内容
	private JLabel contentLabel = new JLabel("内容: ");
	private JTextArea content = new JTextArea(10, 40);
	private JScrollPane contentPane = new JScrollPane(content);
	
	//目标完成时间
	private JLabel targetDateLabel = new JLabel("完成时间: ");
	private JTextField targetDate = new JTextField(10);
	
	//处理人
	private JLabel handlerLabel = new JLabel("当前处理人: ");
	private JTextField handler = new JTextField(10);
	//处理人id(隐藏)
	private JTextField handlerId = new JTextField();
	
	//发起人(系统)
	private JLabel initiatorLabel = new JLabel("发起人: ");
	private JTextField initiator = new JTextField(10);
	
	//按钮
	private JButton confirmButton = new JButton("关闭");
	
	private JLabel processLabel = new JLabel("处理过程");
	private JTextArea processArea = new JTextArea(5, 40);
	private JScrollPane processScrollPane;
	
	private Transaction transaction;
	
	public ViewTransactionDialog() {
		this.handlerId.setVisible(false);
		this.content.setEditable(false);
		this.title.setEditable(false);
		this.targetDate.setEditable(false);
		this.initiator.setEditable(false);
		this.handler.setEditable(false);
		this.processArea.setEditable(false);
		this.processScrollPane = new JScrollPane(this.processArea);
		//标题
		Box titleBox = Box.createHorizontalBox();
		titleBox.add(Box.createHorizontalStrut(43));
		titleBox.add(this.titleLabel);
		titleBox.add(this.title);
		titleBox.add(Box.createHorizontalStrut(30));
		//内容
		Box contentBox = Box.createHorizontalBox();
		contentBox.add(Box.createHorizontalStrut(43));
		contentBox.add(this.contentLabel);
		contentBox.add(this.contentPane);
		contentBox.add(Box.createHorizontalStrut(30));
		//完成时间
		Box targetDateBox = Box.createHorizontalBox();
		targetDateBox.add(Box.createHorizontalStrut(17));
		targetDateBox.add(this.targetDateLabel);
		targetDateBox.add(this.targetDate);
		targetDateBox.add(Box.createHorizontalStrut(230));
		//处理人
		Box handlerBox = Box.createHorizontalBox();
		handlerBox.add(Box.createHorizontalStrut(4));
		handlerBox.add(this.handlerLabel);
		handlerBox.add(this.handler);
		handlerBox.add(this.handlerId);
		handlerBox.add(Box.createHorizontalStrut(230));
		//处理过程
		Box processTextBox = Box.createHorizontalBox();
		processTextBox.add(this.processLabel);
		//发起人
		Box initiatorBox = Box.createHorizontalBox();
		initiatorBox.add(Box.createHorizontalStrut(30));
		initiatorBox.add(this.initiatorLabel);
		initiatorBox.add(this.initiator);
		initiatorBox.add(Box.createHorizontalStrut(312));
		//按钮
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(this.confirmButton);
		
		Box mainBox = Box.createVerticalBox();
		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(titleBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(contentBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(targetDateBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(handlerBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(initiatorBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(processTextBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(this.processScrollPane);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);	
		this.pack();
		this.setResizable(false);
		this.setTitle("创建新事务");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/7);
		initListeners();
		
	}
	
	private void initListeners() {
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
	}
	
	public void setTransaction(Transaction t) {
		this.transaction = t;
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
		if (!b) return;
		//如果当前界面的Transaction对象不为空,则设置相应的值
		if (this.transaction != null) {
			this.title.setText(this.transaction.getTS_TITLE());
			this.content.setText(this.transaction.getTS_CONTENT());
			this.targetDate.setText(this.transaction.getTS_TARGETDATE());
			this.handler.setText(this.transaction.getHandler().getREAL_NAME());
			this.initiator.setText(this.transaction.getInitiator().getREAL_NAME());
			this.processArea.setText("");
			for (Log log : this.transaction.getLogs()) {
				this.processArea.append(log.getHandler().getREAL_NAME() + " 于 " 
						+ log.getLOG_DATE() + " 将事务 " + log.getTS_DESC() + ": " 
						+ log.getComment().getCM_CONTENT() + "\n");
			}
		}
	}
	
	
	
}
