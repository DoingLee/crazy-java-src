package org.crazyit.book.ui;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import org.crazyit.book.dao.BookDao;
import org.crazyit.book.dao.BookInRecordDao;
import org.crazyit.book.dao.BookSaleRecordDao;
import org.crazyit.book.dao.ConcernDao;
import org.crazyit.book.dao.InRecordDao;
import org.crazyit.book.dao.SaleRecordDao;
import org.crazyit.book.dao.TypeDao;
import org.crazyit.book.dao.impl.BookDaoImpl;
import org.crazyit.book.dao.impl.BookInRecordDaoImpl;
import org.crazyit.book.dao.impl.BookSaleRecordDaoImpl;
import org.crazyit.book.dao.impl.ConcernDaoImpl;
import org.crazyit.book.dao.impl.InRecordDaoImpl;
import org.crazyit.book.dao.impl.SaleRecordDaoImpl;
import org.crazyit.book.dao.impl.TypeDaoImpl;
import org.crazyit.book.service.BookService;
import org.crazyit.book.service.ConcernService;
import org.crazyit.book.service.InRecordService;
import org.crazyit.book.service.SaleRecordService;
import org.crazyit.book.service.TypeService;
import org.crazyit.book.service.impl.BookServiceImpl;
import org.crazyit.book.service.impl.ConcernServiceImpl;
import org.crazyit.book.service.impl.InRecordServiceImpl;
import org.crazyit.book.service.impl.SaleRecordServiceImpl;
import org.crazyit.book.service.impl.TypeServiceImpl;

public class MainFrame extends JFrame{
	
	SalePanel salePanel;
	
	RepertoryPanel repertoryPanel;
	
	BookPanel bookPanel;
	
	ConcernPanel concernPanel;
	
	TypePanel typePanel;
	
	CommonPanel currentPanel;
	
	private Action sale = new AbstractAction("销售管理", new ImageIcon("images/sale.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(salePanel);
		}
	};
	
	private Action repertory = new AbstractAction("库存管理", new ImageIcon("images/repertory.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(repertoryPanel);
		}
	};

	private Action book = new AbstractAction("书本管理", new ImageIcon("images/book.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(bookPanel);
			bookPanel.initImage();
			bookPanel.repaint();
		}
	};
	
	private Action type = new AbstractAction("种类管理", new ImageIcon("images/type.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(typePanel);
		}
	};
	
	private Action concern = new AbstractAction("出版社管理", new ImageIcon("images/concern.gif")) {
		public void actionPerformed(ActionEvent e) {
			changePanel(concernPanel);
		}
	};
	
	public MainFrame(SalePanel salePanel) {
		this.salePanel = salePanel;
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("系统");
		menuBar.add(menu);
		
		menu.add(sale).setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		menu.add(repertory).setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));
		menu.add(book).setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK));
		menu.add(type).setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
		menu.add(concern).setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));

		//让销售界面作为第一显示界面
		this.add(salePanel);
		this.currentPanel = salePanel;
		//初始化销售界面的数据
		this.salePanel.initData();
		
		this.setJMenuBar(menuBar);
		this.setTitle("图书进存销管理系统");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void setRepertoryPanel(RepertoryPanel repertoryPanel) {
		this.repertoryPanel = repertoryPanel;
	}

	public void setBookPanel(BookPanel bookPanel) {
		this.bookPanel = bookPanel;
	}

	public void setConcernPanel(ConcernPanel concernPanel) {
		this.concernPanel = concernPanel;
	}

	public void setTypePanel(TypePanel typePanel) {
		this.typePanel = typePanel;
	}

	//切换各个界面
	private void changePanel(CommonPanel commonPanel) {
		//移除当前显示的JPanel
		this.remove(currentPanel);
		//添加需要显示的JPanel
		this.add(commonPanel);
		//设置当前的JPanel
		this.currentPanel = commonPanel;
		this.repaint();
		this.setVisible(true);
		//重新读取数据
		commonPanel.setViewDatas();
		//清空表单
		commonPanel.clear();
	}
	
}
