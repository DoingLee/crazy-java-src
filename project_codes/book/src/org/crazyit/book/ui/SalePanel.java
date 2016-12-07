package org.crazyit.book.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.crazyit.book.commons.BusinessException;
import org.crazyit.book.service.BookService;
import org.crazyit.book.service.SaleRecordService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.BookSaleRecord;
import org.crazyit.book.vo.SaleRecord;

/**
 * 销售界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SalePanel extends CommonPanel {

	BookService bookService;
	//销售记录列
	Vector columns;
	//书的销售记录列
	Vector bookSaleRecordColumns;
	//销售记录的业务接口
	SaleRecordService saleRecordService;
	//书的交易记录列表
	JTable bookSaleRecordTable;
	//书本选择的下拉框
	JComboBox bookComboBox;
	//书的销售记录数据
	Vector<BookSaleRecord> bookSaleRecordDatas;
	//销售记录的id文本框
	JTextField saleRecordId;
	//销售记录总价
	JTextField totalPrice;
	//销售日期
	JTextField recordDate;
	//销售总数量
	JTextField amount;
	//清空按钮
	JButton clearButton;
	//书的单价
	JLabel singlePrice;
	//购买书的数量
	JTextField bookAmount;
	//书对应的库存
	JLabel repertorySize;
	//添加书的按钮
	JButton addBookButton;
	//删除书的按钮
	JButton deleteBookButton;
	//成交按钮
	JButton confirmButton;
	//查询按钮
	JButton queyrButton;
	//查询输入的日期
	JTextField queryDate;
	//日期格式
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//时间格式
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	
	private void initColumns() {
		//初始化销售记录列表的列
		this.columns = new Vector();
		this.columns.add("id");
		this.columns.add("购买书本");
		this.columns.add("总价");
		this.columns.add("交易日期");
		this.columns.add("总数量");
		//初始化销售记录中书列表的列
		this.bookSaleRecordColumns = new Vector();
		this.bookSaleRecordColumns.add("id");
		this.bookSaleRecordColumns.add("书名");
		this.bookSaleRecordColumns.add("单价");
		this.bookSaleRecordColumns.add("数量");
		this.bookSaleRecordColumns.add("bookId");
	}
	
	public SalePanel(BookService bookService, SaleRecordService saleRecordService) {
		this.bookService = bookService;
		this.saleRecordService = saleRecordService;
		//设置列表数据
		setViewDatas();
		//初始化列
		initColumns();
		//设置列表
		DefaultTableModel model = new DefaultTableModel(datas, columns);
		JTable table = new CommonJTable(model);
		setJTable(table);
		JScrollPane upPane = new JScrollPane(table);
		upPane.setPreferredSize(new Dimension(1000, 350));
		//设置添加, 修改的界面
		JPanel downPane = new JPanel();
		downPane.setLayout(new BoxLayout(downPane, BoxLayout.Y_AXIS));
		/*******************************************************/
		Box downBox1 = new Box(BoxLayout.X_AXIS);
		this.saleRecordId = new JTextField(10);
		downBox1.add(this.saleRecordId);
		this.saleRecordId.setVisible(false);
		//列表下面的box
		downBox1.add(new JLabel("总价："));
		this.totalPrice = new JTextField(10);
		this.totalPrice.setEditable(false);
		downBox1.add(this.totalPrice);
		downBox1.add(new JLabel("      "));
		downBox1.add(new JLabel("交易日期："));
		this.recordDate = new JTextField(10);
		this.recordDate.setEditable(false);
		//设置当前交易时间
		setRecordDate();
		downBox1.add(this.recordDate);
		downBox1.add(new JLabel("      "));
		downBox1.add(new JLabel("总数量："));
		this.amount = new JTextField(10);
		this.amount.setEditable(false);
		downBox1.add(this.amount);
		downBox1.add(new JLabel("      "));
		/*******************************************************/
		//书列表
		Box downBox2 = new Box(BoxLayout.X_AXIS);
		this.bookSaleRecordDatas = new Vector();
		DefaultTableModel bookModel = new DefaultTableModel(this.bookSaleRecordDatas, 
				this.bookSaleRecordColumns);
		this.bookSaleRecordTable = new CommonJTable(bookModel);
		//设置书本交易记录列表的样式
		setBookSaleRecordTableFace();
		JScrollPane bookScrollPane = new JScrollPane(this.bookSaleRecordTable);
		bookScrollPane.setPreferredSize(new Dimension(1000, 120));
		downBox2.add(bookScrollPane);
		/*******************************************************/
		Box downBox3 = new Box(BoxLayout.X_AXIS);
		downBox3.add(Box.createHorizontalStrut(100));
		downBox3.add(new JLabel("书本："));
		downBox3.add(Box.createHorizontalStrut(20));
		//创建界面中书的下拉框
		this.bookComboBox = new JComboBox();
		//为下拉框添加数据
		buildBooksComboBox();
		downBox3.add(this.bookComboBox);
		downBox3.add(Box.createHorizontalStrut(50));
		downBox3.add(new JLabel("数量："));
		downBox3.add(Box.createHorizontalStrut(20));
		this.bookAmount = new JTextField(10);
		downBox3.add(this.bookAmount);
		downBox3.add(Box.createHorizontalStrut(50));
		downBox3.add(new JLabel("单价："));
		downBox3.add(Box.createHorizontalStrut(20));
		this.singlePrice = new JLabel();
		downBox3.add(this.singlePrice);
		downBox3.add(Box.createHorizontalStrut(100));
		downBox3.add(new JLabel("库存："));
		downBox3.add(Box.createHorizontalStrut(20));
		this.repertorySize = new JLabel();
		downBox3.add(this.repertorySize);
		downBox3.add(Box.createHorizontalStrut(80));
		this.addBookButton = new JButton("添加");
		downBox3.add(this.addBookButton);
		downBox3.add(Box.createHorizontalStrut(30));
		this.deleteBookButton = new JButton("删除");
		downBox3.add(this.deleteBookButton);
		/*******************************************************/
		Box downBox4 = new Box(BoxLayout.X_AXIS);
		this.confirmButton = new JButton("成交");
		downBox4.add(this.confirmButton);
		downBox4.add(Box.createHorizontalStrut(120));
		clearButton = new JButton("清空");
		downBox4.add(clearButton);
		/*******************************************************/
		downPane.add(getSplitBox());
		downPane.add(downBox1);
		downPane.add(getSplitBox());
		downPane.add(downBox2);
		downPane.add(getSplitBox());
		downPane.add(downBox3);
		downPane.add(getSplitBox());
		downPane.add(downBox4);
		/*******************查询******************/
		JPanel queryPanel = new JPanel();
		Box queryBox = new Box(BoxLayout.X_AXIS);
		queryBox.add(new JLabel("日期："));
		queryBox.add(Box.createHorizontalStrut(30));
		this.queryDate = new JTextField(20);
		queryBox.add(this.queryDate);
		queryBox.add(Box.createHorizontalStrut(30));
		this.queyrButton = new JButton("查询");
		queryBox.add(this.queyrButton);
		queryPanel.add(queryBox);
		this.add(queryPanel);
		//列表为添加界面
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, downPane);
		split.setDividerSize(5);
		this.add(split);
		//初始化监听器
		initListeners();
	}
	
	//初始化监听器
	private void initListeners() {
		//表格选择监听器
		getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				//当选择行时鼠标释放时才执行
				if (!event.getValueIsAdjusting()) {
					//如果没有选中任何一行, 则返回
					if (getJTable().getSelectedRowCount() != 1) return;
					view();
				}
			}
		});
		//清空按钮监听器
		this.clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		//书本选择下拉监听器
		this.bookComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				changeBook();
			}
		});
		//设置显示书的单价
		changeBook();
		//向列表添加一条书的销售记录的按钮
		this.addBookButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				appendBook();
			}
		});
		//删除书的交易记录按钮
		this.deleteBookButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeBook();
			}
		});
		//成交按钮
		this.confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sale();
			}
		});
		//查询
		this.queyrButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				query();
			}
		});
	}
	
	private void query() {
		String date = this.queryDate.getText();
		Date d = null;
		try {
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			showWarn("请输入yyyy-MM-dd的格式日期");
			return;
		}
		//重新执行查询
		Vector<SaleRecord> records = (Vector<SaleRecord>)saleRecordService.getAll(d);
		Vector<Vector> datas = changeDatas(records);
		setDatas(datas);
		//刷新列表
		refreshTable();
	}
	
	//成交的方法
	private void sale() {
		if (!this.saleRecordId.getText().equals("")) {
			showWarn("请清空再进行操作"); 
			return;
		}
		//如果没有成交任何书, 返回
		if (this.bookSaleRecordDatas.size() == 0) {
			showWarn("没有出售任何的书, 不得成交");
			return;
		}
		SaleRecord r = new SaleRecord();
		r.setRECORD_DATE(this.recordDate.getText());
		r.setBookSaleRecords(this.bookSaleRecordDatas);
		try {
			saleRecordService.saveRecord(r);
		} catch (BusinessException e) {//此处的异常是业务异常
			showWarn(e.getMessage());
			return;
		}
		//重新读取数据
		setViewDatas();
		//清空界面
		clear();
	}
	
	//向列表添加一条书的销售记录
	private void appendBook() {
		if (!this.saleRecordId.getText().equals("")) {
			showWarn("请清空再进行操作"); 
			return;
		}
		if (this.bookAmount.getText().equals("")) {
			showWarn("请输入书的数量"); 
			return;
		}
		//获得选中的对象
		Book book = (Book)bookComboBox.getSelectedItem();
		String amount = this.bookAmount.getText();
		appendOrUpdate(book, amount);
		//刷新列表
		refreshBookSaleRecordTableData();
		//计算总价
		countTotalPrice();
		//计算总数量
		setTotalAmount();
	}
	
	//添加或者修改书本交易记录中的对象
	private void appendOrUpdate(Book book, String amount) {
		BookSaleRecord r = getBookSaleRecordFromView(book);
		//如果为空, 则为新添加的书, 非空, 则该书已经在列表中
		if (r == null) {
			//创建BookSaleRecord对象并添加到数据集合中
			BookSaleRecord record = new BookSaleRecord();
			record.setBook(book);
			record.setTRADE_SUM(amount);
			this.bookSaleRecordDatas.add(record);
		} else {
			int newAmount = Integer.valueOf(amount) + Integer.valueOf(r.getTRADE_SUM());
			r.setTRADE_SUM(String.valueOf(newAmount));
		}
	}
	
	//获取在列表中是否已经存在相同的书
	private BookSaleRecord getBookSaleRecordFromView(Book book) {
		for (BookSaleRecord r : this.bookSaleRecordDatas) {
			if (r.getBook().getID().equals(book.getID())) {
				return r;
			}
		}
		return null;
	}
	
	//设置总数量
	private void setTotalAmount() {
		int amount = 0;
		for (BookSaleRecord r : this.bookSaleRecordDatas) {
			amount += Integer.valueOf(r.getTRADE_SUM());
		}
		this.amount.setText(String.valueOf(amount));
	}
	
	//计算总价
	private void countTotalPrice() {
		double totalPrice = 0;
		for (BookSaleRecord r : this.bookSaleRecordDatas) {
			totalPrice += (Integer.valueOf(r.getTRADE_SUM()) * 
					Double.valueOf(r.getBook().getBOOK_PRICE()));
		}
		this.totalPrice.setText(String.valueOf(totalPrice));
	}
	
	//从列表中移除一条书的销售记录
	private void removeBook() {
		if (!this.saleRecordId.getText().equals("")) {
			showWarn("请清空再进行操作"); 
			return;
		}
		if (bookSaleRecordTable.getSelectedRow() == -1) {
			showWarn("请选择需要删除的行");
			return;
		}
		//在集合中删除对应的索引的数据
		this.bookSaleRecordDatas.remove(bookSaleRecordTable.getSelectedRow());
		//刷新列表
		refreshBookSaleRecordTableData();
		//重新计算总价和总数量
		setTotalAmount();
		countTotalPrice();
	}
	
	
	//当书本选择下拉框发生改变时, 执行该方法
	private void changeBook() {
		//获得选择的Book对象
		Book book = (Book)bookComboBox.getSelectedItem();
		if (book == null) return;
		//设置显示的书的价格
		this.singlePrice.setText(book.getBOOK_PRICE());
		this.repertorySize.setText(book.getREPERTORY_SIZE());
	}
	
	//清空
	public void clear() {
		//刷新主列表
		refreshTable();
		this.saleRecordId.setText("");
		this.totalPrice.setText("");
		//设置界面的交易时间为当前时间
		setRecordDate();
		this.amount.setText("");
		this.bookSaleRecordDatas.removeAll(this.bookSaleRecordDatas);
		refreshBookSaleRecordTableData();
		//刷新下拉
		this.bookComboBox.removeAllItems();
		buildBooksComboBox();
	}
	
	//设置视图数据
	public void setViewDatas() {
		Vector<SaleRecord> records = (Vector<SaleRecord>)saleRecordService.getAll(new Date());
		Vector<Vector> datas = changeDatas(records);
		setDatas(datas);
	}
	
	//将数据转换成主列表的数据格式
	private Vector<Vector> changeDatas(Vector<SaleRecord> records) {
		Vector<Vector> view = new Vector<Vector>();
		for (SaleRecord record : records) {
			Vector v = new Vector();
			v.add(record.getID());
			v.add(record.getBookNames());
			v.add(record.getTotalPrice());
			v.add(record.getRECORD_DATE());
			v.add(record.getAmount());
			view.add(v);
		}
		return view;
	}
	
	//创建界面中选择书的下拉框
	private void buildBooksComboBox() {
		Collection<Book> books = bookService.getAll();
		for (Book book : books) {
			this.bookComboBox.addItem(makeBook(book));
		}
	}
	
	//创建Book对象, 用于添加到下拉框中, 重写了equals和toString方法
	private Book makeBook(final Book source) {
		Book book = new Book(){
			public boolean equals(Object obj) {
				if (obj instanceof Book) {
					Book b = (Book)obj;
					if (getID().equals(b.getID())) return true; 
				}
				return false;
			}
			public String toString() {
				return getBOOK_NAME();
			}
		};
		book.setBOOK_NAME(source.getBOOK_NAME());
		book.setBOOK_PRICE(source.getBOOK_PRICE());
		book.setREPERTORY_SIZE(source.getREPERTORY_SIZE());
		book.setID(source.getID());
		return book;
	}
	
	//查看一条销售记录
	private void view() {
		String saleRecordId = getSelectId(getJTable());
		//得到书的交易记录
		SaleRecord record = saleRecordService.get(saleRecordId);
		//设置当前书本销售数据
		this.bookSaleRecordDatas = record.getBookSaleRecords();
		//刷新书本销售列表
		refreshBookSaleRecordTableData();
		this.saleRecordId.setText(record.getID());
		this.totalPrice.setText(String.valueOf(record.getTotalPrice()));
		this.recordDate.setText(record.getRECORD_DATE());
		this.amount.setText(String.valueOf(record.getAmount()));
	}
	
	//将书的销售记录转换成列表格式
	private Vector<Vector> changeBookSaleRecordDate(Vector<BookSaleRecord> records) {
		Vector<Vector> view = new Vector<Vector>();
		for (BookSaleRecord r : records) {
			Vector v = new Vector();
			v.add(r.getID());
			v.add(r.getBook().getBOOK_NAME());
			v.add(r.getBook().getBOOK_PRICE());
			v.add(r.getTRADE_SUM());
			v.add(r.getBook().getID());
			view.add(v);
		}
		return view;
	}
	
	//刷新书本销售记录的列表
	private void refreshBookSaleRecordTableData() {
		Vector<Vector> view = changeBookSaleRecordDate(this.bookSaleRecordDatas);
		DefaultTableModel tableModel = (DefaultTableModel)this.bookSaleRecordTable.getModel();
		//将数据设入表格Model中
		tableModel.setDataVector(view, this.bookSaleRecordColumns);
		//设置表格样式
		setBookSaleRecordTableFace();
	}
	
	//设置书本销售记录的样式
	private void setBookSaleRecordTableFace() {
		this.bookSaleRecordTable.setRowHeight(30);
		//隐藏销售记录id列
		this.bookSaleRecordTable.getColumn("id").setMinWidth(-1);
		this.bookSaleRecordTable.getColumn("id").setMaxWidth(-1);
		//隐藏对应的书id列
		this.bookSaleRecordTable.getColumn("bookId").setMinWidth(-1);
		this.bookSaleRecordTable.getColumn("bookId").setMaxWidth(-1);
	}
	
	@Override
	public Vector<String> getColumns() {
		return this.columns;
	}

	@Override
	public void setTableFace() {
		getJTable().getColumn("id").setMinWidth(-1);
		getJTable().getColumn("id").setMaxWidth(-1);
		getJTable().getColumn("购买书本").setMinWidth(350);
		getJTable().setRowHeight(30);
	}

	//设置界面显示的交易时间
	private void setRecordDate() {
		//设置成交日期为当前时间
		Date now = new Date();
		timeFormat.format(now);
		this.recordDate.setText(timeFormat.format(now));
	}
}
