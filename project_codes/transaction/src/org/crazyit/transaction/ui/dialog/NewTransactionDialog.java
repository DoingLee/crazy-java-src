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

import org.crazyit.transaction.model.Transaction;
import org.crazyit.transaction.model.TransactionState;
import org.crazyit.transaction.ui.TransactionManagePanel;
import org.crazyit.transaction.ui.handler.impl.NewTransactionUserSelectHandler;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

public class NewTransactionDialog extends JDialog {

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
	private JLabel handlerLabel = new JLabel("处理人: ");
	private JTextField handler = new JTextField(10);
	//处理人id(隐藏)
	private JTextField handlerId = new JTextField();
	//处理人选择按钮
	private JButton handlerSelectButton = new JButton("选择处理人");
	
	//发起人(系统)
	private JLabel initiatorLabel = new JLabel("发起人: ");
	private JTextField initiator = new JTextField(ApplicationContext.loginUser.getREAL_NAME());
	
	//按钮
	private JButton confirmButton = new JButton("确定");
	private JButton cancelButton = new JButton("取消");
	
	//选择用户的对话框
	private SelectUserDialog userDialog;
	
	private TransactionManagePanel managePanel;
	
	//选择用户处理类
	private NewTransactionUserSelectHandler selectHandler;
	
	public NewTransactionDialog(TransactionManagePanel managePanel) {
		this.managePanel = managePanel;
		this.selectHandler = new NewTransactionUserSelectHandler(this);
		//创建用户选择对话框
		this.userDialog = new SelectUserDialog(this.selectHandler);
		this.handlerId.setVisible(false);
		this.handler.setEditable(false);
		this.initiator.setEditable(false);
		this.content.setLineWrap(true);
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
		//完成时间
		Box targetDateBox = Box.createHorizontalBox();
		targetDateBox.add(Box.createHorizontalStrut(4));
		targetDateBox.add(this.targetDateLabel);
		targetDateBox.add(this.targetDate);
		targetDateBox.add(Box.createHorizontalStrut(230));
		//处理人
		Box handlerBox = Box.createHorizontalBox();
		handlerBox.add(Box.createHorizontalStrut(17));
		handlerBox.add(this.handlerLabel);
		handlerBox.add(this.handler);
		handlerBox.add(this.handlerId);
		handlerBox.add(Box.createHorizontalStrut(230));
		Box selectButtonBox = Box.createHorizontalBox();
		selectButtonBox.add(this.handlerSelectButton);
		selectButtonBox.add(Box.createHorizontalStrut(312));
		//发起人
		Box initiatorBox = Box.createHorizontalBox();
		initiatorBox.add(Box.createHorizontalStrut(17));
		initiatorBox.add(this.initiatorLabel);
		initiatorBox.add(this.initiator);
		initiatorBox.add(Box.createHorizontalStrut(312));
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
		mainBox.add(targetDateBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(handlerBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(selectButtonBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(initiatorBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(buttonBox);
		mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);	
		this.pack();
		this.setResizable(false);
		this.setTitle("创建新事务");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/5);
		initListeners();
	}
	
	//为处理人文本框提供getter方法, 让选择用户的界面调用
	public JTextField getHandlerField() {
		return this.handler;
	}
	
	//为处理人ID文本框提供getter方法, 让选择用户的界面调用
	public JTextField getHandlerIdField() {
		return this.handlerId;
	}
	
	private void initListeners() {
		this.handlerSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userDialog.setVisible(true);
			}
		});
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
	}
	
	//确认新增, 保存
	private void save() {
		//验证信息
		if (this.title.getText().equals("") || this.targetDate.getText().equals("")
				|| this.handler.getText().equals("")) {
			ViewUtil.showWarn("请输入完整的信息", this);
			return;
		}
		Transaction t = getTransaction();
		ApplicationContext.transactionService.save(t);
		this.setVisible(false);
		this.managePanel.readData();
	}
	
	//将界面元素值封装成一个Transaction对象
	private Transaction getTransaction() {
		//得到界面的各个值
		String title = this.title.getText();
		String content = this.content.getText();
		String targetDate = this.targetDate.getText();
		String handlerId = this.handlerId.getText();
		String initiatorId = ApplicationContext.loginUser.getID();
		String createDate = ViewUtil.formatDate(new Date());
		//创建Transaction对象
		Transaction t = new Transaction();
		t.setTS_TITLE(title);
		t.setTS_CONTENT(content);
		t.setTS_CREATEDATE(createDate);
		t.setTS_TARGETDATE(targetDate);
		t.setHANDLER_ID(handlerId);
		t.setINITIATOR_ID(initiatorId);
		t.setTS_STATE(TransactionState.PROCESSING);
		return t;
	}
	
	
}
