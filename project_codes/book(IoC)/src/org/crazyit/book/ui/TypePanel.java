package org.crazyit.book.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.crazyit.book.service.TypeService;
import org.crazyit.book.vo.Type;

public class TypePanel extends CommonPanel {

	//业务对象
	private TypeService service;
	
	//列的集合
	private Vector columns;
	
	//种类介绍的文本域
	private JTextArea intro;
	
	//种类名称的文本框
	private JTextField typeName;
	
	//隐藏的种类id
	private JTextField typeId;
	
	//保存的按钮
	private JButton saveButton;
	
	//清空的按钮
	private JButton clearButton;
	
	//根据名字查询的输入框
	private JTextField queryByNameTextField;
	
	//查询按钮
	private JButton queryButton;
	
	//初始化列
	public void initColumns() {
		this.columns = new Vector();
		this.columns.add("id");
		this.columns.add("种类名称");
		this.columns.add("简介");
	}
	
	/*
	 * 实现父类方法，查询数据库并返回对应的数据格式, 调用父类的setDatas方法设置数据集合
	 */
	public void setViewDatas() {
		Vector<Type> types = (Vector<Type>)service.getAll();
		Vector<Vector> datas =  changeDatas(types);
		setDatas(datas);
	}
	
	public TypePanel(TypeService service) {
		this.service = service;
		//设置数据
		setViewDatas();
		//初始化列
		initColumns();
		//设置列表
		DefaultTableModel model = new DefaultTableModel(null, this.columns);
		JTable table = new CommonJTable(model);
		//设置父类的JTable对象
		setJTable(table);
		JScrollPane upPane = new JScrollPane(table);
		upPane.setPreferredSize(new Dimension(1000, 350));

		//设置添加, 修改的界面
		JPanel downPane = new JPanel();
		downPane.setLayout(new BoxLayout(downPane, BoxLayout.Y_AXIS));
		
		Box downBox1 = new Box(BoxLayout.X_AXIS);
		//存放id的文本框
		typeId = new JTextField();
		typeId.setVisible(false);
		downBox1.add(typeId);
		//列表下面的box
		downBox1.add(new JLabel("种类名称："));
		downBox1.add(Box.createHorizontalStrut(10));
		
		typeName = new JTextField(10);
		downBox1.add(typeName);
		downBox1.add(Box.createHorizontalStrut(100));

		Box downBox2 = new Box(BoxLayout.X_AXIS);
		downBox2.add(new JLabel("种类简介："));
		downBox2.add(Box.createHorizontalStrut(10));

		intro = new JTextArea("", 5, 5);
		JScrollPane introScrollPane = new JScrollPane(intro);
		intro.setLineWrap(true);
		downBox2.add(introScrollPane);
		downBox2.add(Box.createHorizontalStrut(100));
		/*******************************************************/
		Box downBox3 = new Box(BoxLayout.X_AXIS);
		
		saveButton = new JButton("保存");
		downBox3.add(saveButton);
		downBox3.add(Box.createHorizontalStrut(30));
		clearButton = new JButton("清空");
		downBox3.add(clearButton);
		downBox3.add(Box.createHorizontalStrut(30));

		downPane.add(getSplitBox());
		downPane.add(downBox1);
		downPane.add(getSplitBox());
		downPane.add(downBox2);
		downPane.add(getSplitBox());
		downPane.add(downBox3);
		
		//查询的JPanel
		JPanel queryPanel = new JPanel();
		Box queryBox = new Box(BoxLayout.X_AXIS);
		queryBox.add(new JLabel("名称："));
		queryBox.add(Box.createHorizontalStrut(30));
		queryByNameTextField = new JTextField(20);
		queryBox.add(queryByNameTextField);
		queryBox.add(Box.createHorizontalStrut(30));
		queryButton = new JButton("查询");
		queryBox.add(queryButton);
		queryPanel.add(queryBox);
		this.add(queryPanel);
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, downPane);
		split.setDividerSize(5);
		this.add(split);
		//添加监听器
		initListeners();
	}
	
	//添加监听器
	private void initListeners() {
		//表格选择监听器
		getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				//当选择行时鼠标释放时才执行
				if (!event.getValueIsAdjusting()) {
					//如果没有选中任何一行, 则返回
					if (getJTable().getSelectedRowCount() != 1) return;
					//调用父类的方法获得选择行的id
					String id = getSelectId(getJTable());
					view(id);
				}
			}
		});
		//清空按钮监听器
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		//保存按钮监听器
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//验证空值
				if (typeName.getText().trim().equals("")) {
					showWarn("请输入名称");
					return;
				}
				//判断种类id是否有值, 有值则为修改, 没有值为新增
				if (typeId.getText().equals("")) add();
				else update();
			}
		});
		queryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String name = queryByNameTextField.getText();
				query(name);
			}
		});
	}
	
	//查询方法
	private void query(String name) {
		//通过service方法查找结果
		Vector<Type> types = (Vector<Type>)service.query(name);
		//转换数据格式
		Vector<Vector> datas =  changeDatas(types);
		//设置数据
		setDatas(datas);
		//刷新列表
		refreshTable();
	}
	
	//清空
	public void clear() {
		refreshTable();
		typeId.setText("");
		typeName.setText("");
		intro.setText("");
	}
	
	//新增一个种类
	private void add() {
		String typeName = this.typeName.getText();
		String intro = this.intro.getText();
		Type type = new Type(null, typeName, intro);
		//调用业务方法新增一个种类
		type = service.add(type);
		//重新读取数据
		setViewDatas();
		//刷新列表
		clear();
	}
	
	//修改一个种类
	private void update() {
		String typeId = this.typeId.getText();
		String typeName = this.typeName.getText();
		String intro = this.intro.getText();
		Type type = new Type(typeId, typeName, intro);
		service.update(type);
		//重新读取数据
		setViewDatas();
		//刷新列表
		refreshTable();
	}

	
	//实现父类的方法, 设置列表的外观
	public void setTableFace() {
		//隐藏id列
		getJTable().getColumn("id").setMinWidth(-1);
		getJTable().getColumn("id").setMaxWidth(-1); 
		//设置简介列的最小宽度
		getJTable().getColumn("简介").setMinWidth(350);
		//设置表格的行宽
		getJTable().setRowHeight(30);
	}
	
	//查看一个种类
	public void view(String id) {
		Type data = service.get(id);
		typeId.setText(data.getID());
		typeName.setText(data.getTYPE_NAME());
		intro.setText(data.getTYPE_INTRO());
	}


	//实现父类的方法, 获得这个界面的列
	public Vector<String> getColumns() {
		return this.columns;
	}

	/**
	 * 将数据转换成视图表格的格式
	 * @param datas
	 * @return
	 */
	private Vector<Vector> changeDatas(Vector<Type> datas) {
		Vector<Vector> view = new Vector<Vector>();
		for (Type type : datas) {
			Vector v = new Vector();
			v.add(type.getID());
			v.add(type.getTYPE_NAME());
			v.add(type.getTYPE_INTRO());
			view.add(v);
		}
		return view;
	}

}
