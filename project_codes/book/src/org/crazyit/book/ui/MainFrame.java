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

/**
 * 主界面的JFrame
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MainFrame extends JFrame{
	
	SalePanel salePanel;
	
	RepertoryPanel repertoryPanel;
	
	BookPanel bookPanel;
	
	ConcernPanel concernPanel;
	
	TypePanel typePanel;
	
	CommonPanel currentPanel;
	
	//业务接口
	TypeService typeService;
	
	ConcernService concernService;
	
	BookService bookService;
	
	SaleRecordService saleRecordService;
	
	InRecordService inRecordService;
	
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
	
	public MainFrame() {
		TypeDao typeDao = new TypeDaoImpl();
		ConcernDao concernDao = new ConcernDaoImpl();
		BookDao bookDao = new BookDaoImpl();
		SaleRecordDao saleRecordDao = new SaleRecordDaoImpl();
		BookSaleRecordDao bookSaleRecordDao = new BookSaleRecordDaoImpl();
		InRecordDao inRecordDao = new InRecordDaoImpl();
		BookInRecordDao bookInRecordDao = new BookInRecordDaoImpl();
		this.typeService = new TypeServiceImpl(typeDao);
		this.concernService = new ConcernServiceImpl(concernDao);
		this.bookService = new BookServiceImpl(bookDao, typeDao, concernDao);
		this.saleRecordService = new SaleRecordServiceImpl(saleRecordDao, 
				bookSaleRecordDao, bookDao);
		this.inRecordService = new InRecordServiceImpl(inRecordDao, bookInRecordDao, bookDao);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("系统");
		menuBar.add(menu);
		
		menu.add(sale).setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		menu.add(repertory).setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));
		menu.add(book).setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK));
		menu.add(type).setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
		menu.add(concern).setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
		
		//让界面作为第一显示界面
		this.salePanel = new SalePanel(this.bookService, this.saleRecordService);
		this.add(salePanel);
		this.currentPanel = salePanel;
		//初始化销售界面的数据
		this.salePanel.initData();
		
		//初始化库存管理界面
		repertoryPanel = new RepertoryPanel(this.inRecordService, this.bookService);
		//初始化书本管理界面
		bookPanel = new BookPanel(this.bookService, this.typeService, 
				this.concernService);
		//初始化出版社管理界面
		concernPanel = new ConcernPanel(this.concernService);
		//初始化种类管理界面
		typePanel = new TypePanel(this.typeService);
		
		this.setJMenuBar(menuBar);
		this.setTitle("图书进存销管理系统");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
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
		//刷新列表
		commonPanel.clear();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
