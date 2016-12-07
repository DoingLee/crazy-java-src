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
import org.crazyit.transaction.ui.dialog.NewTransactionDialog;
import org.crazyit.transaction.ui.dialog.ViewTransactionDialog;
import org.crazyit.transaction.ui.table.State;
import org.crazyit.transaction.ui.table.TransactionTable;
import org.crazyit.transaction.ui.table.TransactionTableModel;
import org.crazyit.transaction.util.ApplicationContext;
import org.crazyit.transaction.util.ViewUtil;

/**
 * 事务管理界面
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TransactionManagePanel extends BasePanel {
	
	private JScrollPane tableScrollPane;
	
	//数据列表
	private TransactionTable dataTable;
	private TransactionTableModel tableModel;
	
	//操作区域
	private Box handleBox = Box.createVerticalBox();
	
	//查询
	private Box queryBox = Box.createHorizontalBox();
	private JLabel stateLabel = new JLabel("状态: ");
	private JComboBox stateSelect = new JComboBox();
	private JButton queryButton = new JButton("查询");
	
	//操作
	private Box operateBox = Box.createHorizontalBox();
	private JButton newButton = new JButton("新建事务");
	private JButton hurryButton = new JButton("催办");
	private JButton invalidButton = new JButton("置为无效");
	
	private NewTransactionDialog newTransactionDialog;
	
	//记录当前界面状态(事务状态)
	private String currentState = TransactionState.PROCESSING;
	
	//查看事务界面
	private ViewTransactionDialog vtDialog;
	
	public TransactionManagePanel() {
		this.vtDialog = new ViewTransactionDialog();
		this.newTransactionDialog = new NewTransactionDialog(this);
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
	
	//创建事务列表
	private void createTable() {
		this.tableModel = new TransactionTableModel();
		this.dataTable = new TransactionTable(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.dataTable);
	}
	
	private void initListeners() {
		this.queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				query();
			}
		});
		this.newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newTransactionDialog.setVisible(true);
			}
		});
		this.hurryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hurry();
			}
		});
		this.invalidButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				invalid();
			}
		});
		this.dataTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					view();
				}
			}
		});
	}
	
	//查看事务
	private void view() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		Transaction t = ApplicationContext.transactionService.view(id);
		this.vtDialog.setTransaction(t);
		this.vtDialog.setVisible(true);
	}
	
	//催办事务
	private void hurry() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择需要催办的事务", this);
			return;
		}
		try {
			ApplicationContext.transactionService.hurry(id);
			readData();
		} catch (Exception e) {
			ViewUtil.showWarn(e.getMessage(), this);
		}
	}
	
	//将事务置为无效
	private void invalid() {
		String id = ViewUtil.getSelectValue(this.dataTable, "id");
		if (id == null) {
			ViewUtil.showWarn("请选择需要操作的事务", this);
			return;
		}
		try {
			ApplicationContext.transactionService.invalid(id);
			readData();
		} catch (Exception e) {
			ViewUtil.showWarn(e.getMessage(), this);
		}
	}
	
	//查询
	private void query() {
		State state = (State)this.stateSelect.getSelectedItem();
		this.currentState = state.getValue();
		readData();
	}
	
	//创建操作Box
	private void createOperateBox() {
		this.operateBox.add(Box.createHorizontalStrut(220));
		this.operateBox.add(new JLabel("选择操作: "));
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.newButton);
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.hurryButton);
		this.operateBox.add(Box.createHorizontalStrut(40));
		this.operateBox.add(this.invalidButton);
		this.operateBox.add(Box.createHorizontalStrut(220));
	}
	
	//从数据库中读取数据
	private List<Transaction> getDatas() {
		User loginUser = ApplicationContext.loginUser;
		List<Transaction> datas = ApplicationContext.transactionService
		.getInitiatorTransaction(loginUser, currentState);
		return datas;
	}
	
	//事务管理界面读取数据, 实现父类的抽象方法
	public void readData() {
		List<Transaction> datas = getDatas();
		this.tableModel.setDatas(datas);
		this.dataTable.updateUI();
	}
	
	//创建查询区域
	private void createQueryBox() {
		this.stateSelect.addItem(State.PROCESS_STATE);
		this.stateSelect.addItem(State.FINISHED_STATE);
		this.stateSelect.addItem(State.FOR_A_WHILE_STATE);
		this.stateSelect.addItem(State.NOT_TO_DO_STATE);
		this.stateSelect.addItem(State.INVALID_STATE);
		this.queryBox.add(Box.createHorizontalStrut(250));
		this.queryBox.add(Box.createHorizontalStrut(20));
		this.queryBox.add(this.stateLabel);
		this.queryBox.add(this.stateSelect);
		this.queryBox.add(Box.createHorizontalStrut(40));
		this.queryBox.add(this.queryButton);
		this.queryBox.add(Box.createHorizontalStrut(250));
	}
}
