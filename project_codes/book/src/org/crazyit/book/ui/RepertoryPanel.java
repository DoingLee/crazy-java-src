package org.crazyit.book.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.crazyit.book.service.BookService;
import org.crazyit.book.service.InRecordService;
import org.crazyit.book.vo.Book;
import org.crazyit.book.vo.BookInRecord;
import org.crazyit.book.vo.InRecord;

/**
 * 库存界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class RepertoryPanel extends CommonPanel {

	private InRecordService inRecordService;
	//入库记录列表的列集合
	Vector columns;
	//书的入库记录的列集合
	Vector bookInColumns;
	//书的入库列表JTable
	JTable bookInTable;
	//书的入库记录列表数据
	Vector<BookInRecord> bookInRecords;
	
	BookService bookService;
	//选择书本的下拉框
	JComboBox bookComboBox;
	JTextField amount;
	JTextField inDate;
	//保存具体某条入库记录的隐藏域
	JTextField inRecordId;
	//清空按钮
	JButton clearButton;
	//添加书的按钮
	JButton addBookButton;
	//删除书的按钮
	JButton deleteBookButton;
	//向书的入库列表添加书时的数量输入框
	JTextField bookAmount;
	//书本原有的数量
	JLabel repertorySize;
	//时间格式
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	JButton inButton;
	
	private void initColumns() {
		this.columns = new Vector();
		this.columns.add("id");
		this.columns.add("入库书本");
		this.columns.add("入库日期");
		this.columns.add("入库数量");
		this.bookInColumns = new Vector();
		this.bookInColumns.add("id");
		this.bookInColumns.add("书名");
		this.bookInColumns.add("单价");
		this.bookInColumns.add("数量");
		this.bookInColumns.add("bookId");
	}
	
	public RepertoryPanel(InRecordService inRecordService, BookService bookService) {
		this.inRecordService = inRecordService;
		this.bookService = bookService;
		initColumns();
		setViewDatas();
		//设置列表
		DefaultTableModel model = new DefaultTableModel(getDatas(), this.columns);
		JTable table = new CommonJTable(model);
		setJTable(table);
		
		JScrollPane upPane = new JScrollPane(table);
		upPane.setPreferredSize(new Dimension(1000, 350));
		

		//设置添加, 修改的界面
		JPanel downPane = new JPanel();
		downPane.setLayout(new BoxLayout(downPane, BoxLayout.Y_AXIS));
		
	
		/*******************************************************/
		//
		Box downBox1 = new Box(BoxLayout.X_AXIS);
		//保存入库记录的隐藏域
		this.inRecordId = new JTextField();
		downBox1.add(this.inRecordId);
		inRecordId.setVisible(false);
				
		downBox1.add(new JLabel("入库日期："));
		this.inDate = new JTextField(10);
		this.inDate.setEditable(false);
		setInDate();
		downBox1.add(this.inDate);
		downBox1.add(new JLabel("      "));
		
		downBox1.add(new JLabel("总数量："));
		this.amount = new JTextField(10);
		this.amount.setEditable(false);
		downBox1.add(this.amount);
		downBox1.add(new JLabel("      "));

		/*******************************************************/
		//书列表
		Box downBox2 = new Box(BoxLayout.X_AXIS);
		
		this.bookInRecords = new Vector<BookInRecord>();
		DefaultTableModel bookModel = new DefaultTableModel(this.bookInRecords, 
				this.bookInColumns);
		this.bookInTable = new CommonJTable(bookModel);
		setBookInRecordFace();
		
		JScrollPane bookScrollPane = new JScrollPane(this.bookInTable);
		bookScrollPane.setPreferredSize(new Dimension(1000, 120));
		downBox2.add(bookScrollPane);

		/*******************************************************/
		Box downBox3 = new Box(BoxLayout.X_AXIS);
		downBox3.add(Box.createHorizontalStrut(300));
		
		downBox3.add(new JLabel("书本："));
		downBox3.add(Box.createHorizontalStrut(20));
		this.bookComboBox = new JComboBox();
		buildBooksComboBox();
		downBox3.add(bookComboBox);
		
		downBox3.add(Box.createHorizontalStrut(50));
		
		downBox3.add(new JLabel("数量："));
		downBox3.add(Box.createHorizontalStrut(20));
		this.bookAmount = new JTextField(10);
		downBox3.add(this.bookAmount);
		downBox3.add(Box.createHorizontalStrut(50));
		
		downBox3.add(new JLabel("现有："));
		downBox3.add(Box.createHorizontalStrut(20));
		this.repertorySize = new JLabel();
		downBox3.add(this.repertorySize);
		downBox3.add(Box.createHorizontalStrut(50));
		
		this.addBookButton = new JButton("添加");
		downBox3.add(this.addBookButton);

		downBox3.add(Box.createHorizontalStrut(30));
		
		this.deleteBookButton = new JButton("删除");
		downBox3.add(this.deleteBookButton);
		
		/*******************************************************/
		Box downBox4 = new Box(BoxLayout.X_AXIS);
		
		this.inButton = new JButton("入库");
		downBox4.add(this.inButton);
		
		downBox4.add(Box.createHorizontalStrut(130));
		
		this.clearButton = new JButton("清空");
		downBox4.add(this.clearButton);
		
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
		queryBox.add(new JTextField(20));
		queryBox.add(Box.createHorizontalStrut(30));
		queryBox.add(new JButton("查询"));
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
		//书本选择下拉监听器
		this.bookComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				changeBook();
			}
		});
		//设置显示书的已有数量
		changeBook();
		//清空按钮
		this.clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		//向列表添加一条书的入库记录的按钮
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
		//入库按钮
		this.inButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				in();
			}
		});
	}
	
	//入库的方法
	private void in() {
		if (!this.inRecordId.getText().equals("")) {
			showWarn("请清空再进行操作"); 
			return;
		}
		//如果没有成交任何书, 返回
		if (this.bookInRecords.size() == 0) {
			showWarn("没有任何书入库");
			return;
		}
		InRecord r = new InRecord();
		r.setRECORD_DATE(this.inDate.getText());
		r.setBookInRecords(this.bookInRecords);
		inRecordService.save(r);
		//重新读取数据
		setViewDatas();
		//清空界面
		clear();
	}
	
	//从列表中移除一条书的入库记录
	private void removeBook() {
		if (!this.inRecordId.getText().equals("")) {
			showWarn("请清空再进行操作"); 
			return;
		}
		if (this.bookInTable.getSelectedRow() == -1) {
			showWarn("请选择需要删除的行");
			return;
		}
		//在集合中删除对应的索引的数据
		this.bookInRecords.remove(this.bookInTable.getSelectedRow());
		//刷新列表
		refreshBookInRecordTableData();
		//重新计算总数量
		countAmount();
	}
	
	//向书的入库记录列表中添加一本书
	private void appendBook() {
		if (!this.inRecordId.getText().equals("")) {
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
		refreshBookInRecordTableData();
		//计算总数
		countAmount();
	}
	
	//当书本选择下拉框发生改变时, 执行该方法
	private void changeBook() {
		//获得选择的Book对象
		Book book = (Book)bookComboBox.getSelectedItem();
		if (book == null) return;
		this.repertorySize.setText(book.getREPERTORY_SIZE());
	}
	
	//计算一次入库书的总数
	private void countAmount() {
		int amount = 0;
		for (BookInRecord r : this.bookInRecords) {
			amount += Integer.valueOf(r.getIN_SUM());
		}
		this.amount.setText(String.valueOf(amount));
	}
	
	//添加或者修改书本交易记录中的对象
	private void appendOrUpdate(Book book, String amount) {
		BookInRecord r = getBookInRecordFromView(book);
		//如果为空, 则为新添加的书, 非空, 则该书已经在列表中
		if (r == null) {
			//创建BookSaleRecord对象并添加到数据集合中
			BookInRecord record = new BookInRecord();
			record.setBook(book);
			record.setIN_SUM(amount);
			this.bookInRecords.add(record);
		} else {
			int newAmount = Integer.valueOf(amount) + Integer.valueOf(r.getIN_SUM());
			r.setIN_SUM(String.valueOf(newAmount));
		}
	}
	
	//获取在列表中是否已经存在相同的书
	private BookInRecord getBookInRecordFromView(Book book) {
		for (BookInRecord r : this.bookInRecords) {
			if (r.getBook().getID().equals(book.getID())) {
				return r;
			}
		}
		return null;
	}
	
	//刷新表单方法
	public void clear() {
		//刷新主列表
		refreshTable();
		this.inRecordId.setText("");
		this.amount.setText("");
		this.inDate.setText("");
		//清空书的入库记录的数据
		this.bookInRecords.removeAll(this.bookInRecords);
		refreshBookInRecordTableData();
		//刷新下拉
		this.bookComboBox.removeAllItems();
		buildBooksComboBox();
		setInDate();
	}
	
	//设置书的入库记录的列表
	private void setBookInRecordFace() {
		this.bookInTable.setRowHeight(30);
		//隐藏书入库记录的id列
		this.bookInTable.getColumn("id").setMinWidth(-1);
		this.bookInTable.getColumn("id").setMaxWidth(-1);
		//隐藏书入库记录对应的书id列
		this.bookInTable.getColumn("bookId").setMinWidth(-1);
		this.bookInTable.getColumn("bookId").setMaxWidth(-1);
	}
	
	//查看一入入库记录
	private void view() {
		//获得入库记录的id
		String id = getSelectId(getJTable());
		//从数据库中查找
		InRecord r = inRecordService.get(id);
		//设置列表数据对应的集合
		this.bookInRecords = r.getBookInRecords();
		//刷新书的入库记录列表
		refreshBookInRecordTableData();
		//设置当前查看记录的隐藏域id
		this.inRecordId.setText(r.getID());
		//设置界面总数量和入库日期
		this.amount.setText(String.valueOf(r.getAmount()));
		this.inDate.setText(r.getRECORD_DATE());
	}

	@Override
	public Vector<String> getColumns() {
		return this.columns;
	}


	@Override
	public void setTableFace() {
		getJTable().getColumn("入库书本").setMinWidth(350);
		getJTable().setRowHeight(30);
		getJTable().getColumn("id").setMinWidth(-1);
		getJTable().getColumn("id").setMaxWidth(-1);
	}

	//重新读取数据
	public void setViewDatas() {
		Vector<InRecord> records = (Vector<InRecord>)inRecordService.getAll(new Date());
		Vector<Vector> datas = changeDatas(records);
		setDatas(datas);
	}
	
	//将数据转换成主列表的数据格式
	private Vector<Vector> changeDatas(Vector<InRecord> records) {
		Vector<Vector> view = new Vector<Vector>();
		for (InRecord r : records) {
			Vector v = new Vector();
			v.add(r.getID());
			v.add(r.getBookNames());
			v.add(r.getRECORD_DATE());
			v.add(r.getAmount());
			view.add(v);
		}
		return view;
	}
	
	//刷新书本入库记录的列表
	private void refreshBookInRecordTableData() {
		Vector<Vector> view = changeBookInRecordDate(this.bookInRecords);
		DefaultTableModel tableModel = (DefaultTableModel)this.bookInTable.getModel();
		//将数据设入表格Model中
		tableModel.setDataVector(view, this.bookInColumns);
		//设置表格样式
		setBookInRecordFace();
	}
	
	//将书的入库记录转换成列表格式
	private Vector<Vector> changeBookInRecordDate(Vector<BookInRecord> records) {
		Vector<Vector> view = new Vector<Vector>();
		for (BookInRecord r : records) {
			Vector v = new Vector();
			v.add(r.getID());
			v.add(r.getBook().getBOOK_NAME());
			v.add(r.getBook().getBOOK_PRICE());
			v.add(r.getIN_SUM());
			v.add(r.getBook().getID());
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
	
	//设置界面显示的交易时间
	private void setInDate() {
		//设置成交日期为当前时间
		Date now = new Date();
		timeFormat.format(now);
		this.inDate.setText(timeFormat.format(now));
	}

}
