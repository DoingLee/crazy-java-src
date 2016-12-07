package org.crazyit.transaction.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.crazyit.transaction.model.User;
import org.crazyit.transaction.util.ApplicationContext;

public class MainFrame extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu tsMenu = new JMenu("事务");
	
	//当前的界面
	private BasePanel currentPanel;
	
	//我的事务
	private MyTransactionPanel myTransactionPanel;
	
	//事务管理
	private TransactionManagePanel transactionManagePanel;
	
	//用户管理
	private UserPanel userPanel;
		
	//我的事务
	private Action myTransaction = new AbstractAction("我的事务", new ImageIcon("images/menu/myTransaction.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(myTransactionPanel);
		}
	};
	
	//事务管理(管理员或者上级等分派任务)
	private Action transactionManage = new AbstractAction("事务管理", new ImageIcon("images/menu/transactionManage.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(transactionManagePanel);
		}
	};
	
	//用户管理
	private Action userManage = new AbstractAction("用户管理", new ImageIcon("images/menu/userManage.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(userPanel);
		}
	};
	
	//退出系统
	private Action exit = new AbstractAction("退出系统", new ImageIcon("images/menu/exit.gif")) {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	public MainFrame() {
		this.myTransactionPanel = new MyTransactionPanel();
		this.transactionManagePanel = new TransactionManagePanel();
		this.userPanel = new UserPanel();
		createMenu();
		this.add(this.myTransactionPanel);
		this.currentPanel = this.myTransactionPanel;
		this.pack();
		this.setTitle("事务跟踪系统");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)screen.getWidth()/10, (int)screen.getHeight()/10);
	}

	private void createMenu() {
		this.tsMenu.add(this.myTransaction);
		this.tsMenu.add(this.transactionManage);
		this.tsMenu.add(this.userManage);
		this.tsMenu.add(this.exit);
		//判断权限
		User loginUser = ApplicationContext.loginUser;
		System.out.println(loginUser.getRole().getROLE_NAME());
		if (loginUser.getRole().getROLE_NAME().equals("manager")) {
			this.tsMenu.remove(2);
		} else if (loginUser.getRole().getROLE_NAME().equals("employee")) {
			this.tsMenu.remove(2);
			this.tsMenu.remove(1);
		}
		this.menuBar.add(this.tsMenu);
		this.setJMenuBar(this.menuBar);
	}
	
	/**
	 * 点击菜单执行的方法
	 */
	private void changePanel(BasePanel panel) {
		//移除当前显示的JPanel
		this.remove(this.currentPanel);
		//添加需要显示的JPanel
		this.add(panel);
		this.currentPanel = panel;
		this.currentPanel.readData();
		this.pack();
		this.repaint();
		this.setVisible(true);
	}

}
