package org.crazyit.transaction.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.crazyit.transaction.model.Transaction;
import org.crazyit.transaction.model.TransactionState;
import org.crazyit.transaction.model.User;
import org.crazyit.transaction.ui.dialog.HandleTransactionDialog;
import org.crazyit.transaction.ui.dialog.TransferTransactionDialog;
import org.crazyit.transaction.ui.dialog.ViewTransactionDialog;
import org.crazyit.transaction.ui.handler.TransactionHandler;
import org.crazyit.transaction.ui.handler.impl.FinishHandler;
import org.crazyit.transaction.ui.handler.impl.ForAWhileHandler;
import org.crazyit.transaction.ui.handler.impl.NotToDoHandler;
import org.crazyit.transaction.ui.table.State;
import org.crazyit.transaction.ui.table.TransactionTable;
import org.crazyit.transaction.ui.table.TransactionTableModel;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

/**
 * 我的事务界面
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MyTransactionPanel extends BasePanel {
	
	private JScrollPane tableScrollPane;
	
	//数据列表
	private TransactionTable dataTable;
	private TransactionTableModel tableModel;
	
	//操作区域
	private Box handleBox = Box.createVerticalBox();
	
	//查询
	private Box queryBox = Box.createHorizontalBox();
	private JComboBox stateSelect = new JComboBox();
	private JButton queryButton = new JButton("查询");
	
	//操作
	private Box operateBox = Box.createHorizontalBox();
	private JButton finishButton = new JButton("完成");
	private JButton transferButton = new JButton("转发");
	private JButton forAWhileButton = new JButton("暂时不做");
	private JButton notToDoButton = new JButton("不做");
	
	//查看事务界面
	private ViewTransactionDialog vtDialog;
	//处理事务界面
	private HandleTransactionDialog htDialog;
	
	private String currentState = TransactionState.PROCESSING;

	public MyTransactionPanel() {
		this.vtDialog = new ViewTransactionDialog();
		this.htDialog = new HandleTransactionDialog(this);
		this.transferDialog = new TransferTransactionDialog(this);
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
	
	//初始化按钮监听器
	private void initListeners() {
		this.queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				query();
			}
		});
		this.finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();
			}
		});
		this.transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				transfer();
			}
		});
		this.forAWhileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				forAWhile();
			}
		});
		this.notToDoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				notToDo();
			}
		});
	}
	
	private void query() {
		State state = (State)this.stateSelect.getSelectedItem();
		this.currentState = state.getValue();
		readData();
	}
	
	//转发事务对话框
	private TransferTransactionDialog transferDialog;
	
	//转发事务
	private void transfer() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择需要操作的事务", this);
			return;
		}
		//得到事务对象
		Transaction t = ApplicationContext.transactionService.get(id);
		//显示处理对话框		
		this.transferDialog.setTransaction(t);
		this.transferDialog.setVisible(true);
	}
	
	//暂时不做处理类
	private TransactionHandler forAWhileHandler = new ForAWhileHandler();
	
	//暂时不做
	private void forAWhile() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择需要操作的事务", this);
			return;
		}
		//得到事务对象
		Transaction t = ApplicationContext.transactionService.get(id);
		//显示处理对话框
		this.htDialog.setTransaction(t);
		this.htDialog.setHandler(this.forAWhileHandler);
		this.htDialog.setVisible(true);
	}
	
	//决定不做处理类
	private TransactionHandler notToDoHandler = new NotToDoHandler();
	
	private void notToDo() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择需要操作的事务", this);
			return;
		}
		//得到事务对象
		Transaction t = ApplicationContext.transactionService.get(id);
		//显示处理对话框
		this.htDialog.setTransaction(t);
		this.htDialog.setHandler(this.notToDoHandler);
		this.htDialog.setVisible(true);
	}
	
	//创建事务列表
	private void createTable() {
		this.tableModel = new TransactionTableModel();
		this.dataTable = new TransactionTable(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.dataTable);		
		this.dataTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					view();
				}
			}
		});
		//由于我的事务界面是最先展现的界面, 因此需要读取数据
		readData();
	}
	
	//从数据库中读取数据
	private List<Transaction> getDatas() {
		User loginUser = ApplicationContext.loginUser;
		List<Transaction> datas = ApplicationContext.transactionService.getHandlerTransaction(loginUser, currentState);
		return datas;
	}
	
	//我的事务界面读取数据, 实现父类的抽象方法
	public void readData() {
		List<Transaction> datas = getDatas();
		this.tableModel.setDatas(datas);
		this.dataTable.updateUI();
	}

	private void createOperateBox() {
		this.operateBox.add(Box.createHorizontalStrut(160));
		this.operateBox.add(new JLabel("选择操作: "));
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.finishButton);
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.transferButton);
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.forAWhileButton);
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.notToDoButton);
		this.operateBox.add(Box.createHorizontalStrut(160));
	}
	
	private TransactionHandler finishHandler = new FinishHandler();
	
	//将事务置为完成状态
	private void finish() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择需要操作的事务", this);
			return;
		}
		//得到事务对象
		Transaction t = ApplicationContext.transactionService.get(id);
		//显示处理对话框
		this.htDialog.setTransaction(t);
		this.htDialog.setHandler(this.finishHandler);
		this.htDialog.setVisible(true);
	}
	
	//创建查询区域
	private void createQueryBox() {
		this.stateSelect.addItem(State.PROCESS_STATE);
		this.stateSelect.addItem(State.FINISHED_STATE);
		this.stateSelect.addItem(State.TRANSFER_STATE);
		this.stateSelect.addItem(State.FOR_A_WHILE_STATE);
		this.stateSelect.addItem(State.NOT_TO_DO_STATE);
		this.stateSelect.addItem(State.INVALID_STATE);
		this.queryBox.add(Box.createHorizontalStrut(320));
		this.queryBox.add(this.stateSelect);
		this.queryBox.add(Box.createHorizontalStrut(40));
		this.queryBox.add(this.queryButton);
		this.queryBox.add(Box.createHorizontalStrut(320));
	}
	
	private void view() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		Transaction t = ApplicationContext.transactionService.view(id);
		this.vtDialog.setTransaction(t);
		this.vtDialog.setVisible(true);
	}
		
}
