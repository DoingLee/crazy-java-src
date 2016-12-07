package org.crazyit.mysql.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.crazyit.mysql.object.list.TableData;
import org.crazyit.mysql.table.object.Field;
import org.crazyit.mysql.table.object.ForeignField;
import org.crazyit.mysql.table.object.UpdateField;
import org.crazyit.mysql.table.object.UpdateForeignField;
import org.crazyit.mysql.ui.table.FieldTable;
import org.crazyit.mysql.ui.table.ForeignTable;
import org.crazyit.mysql.util.ImageUtil;

/**
 * 新增(修改)表界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("serial")
public class TableFrame extends CommonFrame {

	private Box mainBox = Box.createVerticalBox();
	
	//字段的列表
	private JScrollPane fieldPane;
	private FieldTable fieldTable;
	private Box fieldBox = Box.createHorizontalBox();
	private JLabel defaultLabel = new JLabel("默认值：");
	private JTextField defaultField = new JTextField(20);
	private JCheckBox isAutoIncrementBox = new JCheckBox("自动增长");
	private Box fieldButtonBox = Box.createHorizontalBox();
	private JButton newFieldButton = new JButton("新字段");
	private JButton inserFieldButton = new JButton("插入字段");
	private JButton deleteFieldButton = new JButton("删除字段");
	//外键列表
	private JScrollPane foreignPane;
	private ForeignTable foreignTable;
	private Box foreignButtonBox = Box.createHorizontalBox();
	private JButton newForeignButton = new JButton("新外键");
	private JButton deleteForeignButton = new JButton("删除外键");
	//工具栏
	private JToolBar toolBar = new JToolBar();
	
	private Action save = new AbstractAction("保存", new ImageIcon("images/save-table.gif")) {
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	private TableData table;
	
	//保存当前全部字段的集合
	private List<Field> fields = new ArrayList<Field>();;
	
	//保存外键的集合
	private List<ForeignField> foreignFields = new ArrayList<ForeignField>();
	
	//数据库下面所有的表集合
	private List<TableData> allTables;
	
	/*****以下为修改表所需要的集合****/
	//数据库中的字段集合
	private List<Field> sourceFields = new ArrayList<Field>();
	//数据库中的外键集合
	private List<ForeignField> sourceForeignFields = new ArrayList<ForeignField>();
	//被删除的字段集合
	private List<Field> dropFields = new ArrayList<Field>();
	//添加的字段集合
	private List<Field> addFields = new ArrayList<Field>();
	//添加的外键集合
	private List<ForeignField> addForeignFields = new ArrayList<ForeignField>();
	//被删除的外键集合
	private List<ForeignField> dropForeignFields = new ArrayList<ForeignField>();
	
	
	//输入表名的JFrame
	private NameFrame nameFrame;
	
	private MainFrame mainFrame;
	
	public TableFrame(TableData table, MainFrame mainFrame) {
		this.nameFrame = new NameFrame(this);
		this.mainFrame = mainFrame;
		//得到全部的表
		this.allTables = table.getDatabase().getTables();
		this.table = table;
		//如果table.getName为空，则为新建表
		if (table.getName() != null) {
			//得到各个字段的集合
			this.fields = table.readFields();
			//得到外键的集合
			this.foreignFields = table.getForeignFields(this.fields);
			//初始化两个源数据集合
			this.sourceFields = table.readFields();
			this.sourceForeignFields = table.getForeignFields(this.sourceFields);
			setFieldUUID();
			setForeignFieldUUID();
		}
		this.toolBar.add(save).setToolTipText("保存");
		this.toolBar.setFloatable(false);
		
		this.fieldBox.add(Box.createHorizontalStrut(120));
		this.fieldBox.add(this.defaultLabel);
		this.fieldBox.add(this.defaultField);
		this.fieldBox.add(Box.createHorizontalStrut(20));
		this.fieldBox.add(this.isAutoIncrementBox);
		this.fieldBox.add(Box.createHorizontalStrut(120));
		this.fieldButtonBox.add(this.newFieldButton);
		this.fieldButtonBox.add(Box.createHorizontalStrut(20));
		this.fieldButtonBox.add(this.inserFieldButton);
		this.fieldButtonBox.add(Box.createHorizontalStrut(20));
		this.fieldButtonBox.add(this.deleteFieldButton);
		this.foreignButtonBox.add(this.newForeignButton);
		this.foreignButtonBox.add(Box.createHorizontalStrut(20));
		this.foreignButtonBox.add(this.deleteForeignButton);
		
		this.fieldTable = createFieldTable();
		this.foreignTable = createForeignTable();
		this.fieldPane = new JScrollPane(fieldTable);
		this.fieldPane.setPreferredSize(new Dimension(750, 200)); 
		this.foreignPane = new JScrollPane(foreignTable);
		this.foreignPane.setPreferredSize(new Dimension(750, 200)); 
		
		this.mainBox.add(this.fieldPane);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(this.fieldBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(fieldButtonBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(this.foreignPane);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(this.foreignButtonBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.add(this.mainBox);
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setLocation(150, 50);
		this.setTitle("新表");
		this.setResizable(false);
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//释放本窗口所有内存
				dispose();
			}
		});
		initListeners();
	}
	
	//设置界面Field集合和实际字段数据的uuid值
	private void setFieldUUID() {
		for (int i = 0; i < this.fields.size(); i++) {
			String uuid = UUID.randomUUID().toString();
			Field newField = this.fields.get(i);
			Field sourceField = this.sourceFields.get(i);
			newField.setUuid(uuid);
			sourceField.setUuid(uuid);
		}
	}
	
	//设置界面ForeignField集合和实际字段数据的uuid值
	private void setForeignFieldUUID() {
		for (int i = 0; i < this.foreignFields.size(); i++) {
			String uuid = UUID.randomUUID().toString();
			ForeignField newField = this.foreignFields.get(i);
			ForeignField sourceField = this.sourceForeignFields.get(i);
			newField.setUuid(uuid);
			sourceField.setUuid(uuid);
		}
	}
	
	//初始化监听器
	private void initListeners() {
		this.newFieldButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				newField();
			}
		});
		this.newForeignButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				newForeignField();
			}
		});
		this.deleteForeignButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				deleteForeignField();
			}
		});
		this.inserFieldButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				insertField();
			}
		});
		this.deleteFieldButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				deleteField();
			}
		});
		this.defaultField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				changeDefaultValue();
			}
		});
		this.isAutoIncrementBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickIsAutoIncrementBox();
			}
		});
	}
	
	public List<TableData> getAllTables() {
		return this.allTables;
	}
	
	//实现父类的确认方法，该confirm方法由NameFrame调用
	protected void confirm(String name) {
		add(name);
	}
	
	//保存一个表
	private void save() {
		for (ForeignField ff : this.foreignFields) {
			if (ff.getField() == null || ff.getReferenceField() == null) {
				showMessage("请删除没有用到的外键字段数据", "错误");
				return;
			}
		}
		//停止列表编辑
		if (this.fieldTable.getCellEditor() != null) {
			this.fieldTable.getCellEditor().stopCellEditing();
		}
		//判断本类中的TableData是否有名称
		if (this.table.getName() == null)this.nameFrame.setVisible(true);
		else update();
	}
	
	//添加一个表
	private void add(String name) {
		try {
			this.table.setName(name);
			this.table.addTable(this.fields, this.foreignFields);
			this.setVisible(false);
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			this.table.setName(null);
			showMessage(e.getMessage(), "错误");
		}
		this.nameFrame.setVisible(false);
	}
	
	//修改一个表
	private void update() {
		try {
			//创建修改的字段集合
			List<UpdateField> updateFields = getUpdateFieldList();
			//创建修改的外键集合
			List<UpdateForeignField> updateFF = getUpdateForeignFieldList();
			this.table.updateTable(this.addFields, updateFields, this.dropFields, 
					this.addForeignFields, updateFF, this.dropForeignFields);
			this.setVisible(false);
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "错误");
		}
	}
	
	//找出修改的字段集合
	private List<UpdateField> getUpdateFieldList() {
		//找出数据库中的字段集合与界面中的字段集合的并集
		List<UpdateField> fields = new ArrayList<UpdateField>();
		for (Field newField : this.fields) {
			for (Field sourceField : this.sourceFields) {
				if (newField.uuidEquals(sourceField)) {
					fields.add(new UpdateField(sourceField, newField));
				}
			}
		}
		return fields;
	}
	
	//找出修改的外键集合
	private List<UpdateForeignField> getUpdateForeignFieldList() {
		List<UpdateForeignField> result = new ArrayList<UpdateForeignField>();
		for (ForeignField newField : this.foreignFields) {
			for (ForeignField sourceField : this.sourceForeignFields) {
				if (newField.uuidEquals(sourceField)) {
					result.add(new UpdateForeignField(sourceField, newField));
				}
			}
		}
		return result;
	}
	
	//加入新字段
	private void newField() {
		Field field = new Field();
		this.fields.add(field);
		refreshFieldTable();
		//如果是修改状态，则添加addFields集合中
		if (this.table.getName() != null) this.addFields.add(field);
	}
	
	//插入新字段
	private void insertField() {
		int selectRow = this.fieldTable.getSelectedRow();
		if (selectRow == -1) {
			//没有选中，调用加新字段方法，加入新字段
			newField();
			return;
		}
		Field field = new Field();
		this.fields.add(selectRow, field);
		refreshFieldTable();
		//如果是修改状态，则添加addFields集合中
		if (this.table.getName() != null) this.addFields.add(field);
	}
	
	//删除字段
	private void deleteField() {
		//得到选中的行
		int selectRow = this.fieldTable.getSelectedRow();
		if (selectRow == -1) return;
		Field field = this.fields.get(selectRow);
		if (field == null) return;
		//从字段集合中删除
		this.fields.remove(field);
		//刷新列表
		refreshFieldTable();
		//判断是否在添加的集合中
		if (isInFields(field, this.addFields)) this.addFields.remove(field);
		//添加到删除的集合中
		if (isInFields(field, this.sourceFields)) this.dropFields.add(field);
	}
	
	
	//判断一个字段是否存在于集合中
	private boolean isInFields(Field field, List<Field> fields) {
		for (Field f : fields) {
			if (f.uuidEquals(field)) return true;
		}
		return false;
	}
	
	//新增一个外键字段
	private void newForeignField() {
		ForeignField foreignField = new ForeignField();
		this.foreignFields.add(foreignField);
		//设置该外键的constraintName，用UUID设置
		foreignField.setConstraintName(UUID.randomUUID().toString());
		refreshForeignFieldTable();
		//如果是修改状态，加到添加的外键集合中
		if (this.table.getName() != null) this.addForeignFields.add(foreignField);
	}
	
	//删除一个字段
	private void deleteForeignField() {
		//得到选中的行
		int selectRow = this.foreignTable.getSelectedRow();
		if (selectRow == -1) return;
		ForeignField field = this.foreignFields.get(selectRow);
		if (field == null) return;
		//从字段集合中删除
		this.foreignFields.remove(field);
		refreshForeignFieldTable();
		//判断是否在添加的集合中
		if (isInForeignFields(field, this.addForeignFields)) this.addForeignFields.remove(field);
		//判断是否在数据库的集合中
		if (isInForeignFields(field, this.sourceForeignFields)) this.dropForeignFields.add(field);
	}
	
	//判断一个外键对象是否存在于参数集合中
	private boolean isInForeignFields(ForeignField foreignField, List<ForeignField> ffs) {
		for (ForeignField ff : ffs) {
			if (ff.uuidEquals(foreignField)) return true;
		}
		return false;
	}
	
	//外键列表改变了字段，让ForeignTable调用该方法
	public void changeForeignField(int row, Field field) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setField(field);
	}
	
	//约束字段发生了改变则调用这个方法，设置外键字段集合的值，让ForeignTable调用该方法
	public void changeReferenceField(int row, Field field) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setReferenceField(field);
	}
	
	//当ON DELETE发生改变时，触发该方法
	public void changeOnDelete(int row, String value) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setOnDelete(value);
	}
	
	//当ON DELETE发生改变时，触发该方法
	public void changeOnUpdate(int row, String value) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setOnUpdate(value);
	}
	
	//刷新字段列表
	public void refreshFieldTable() {
		DefaultTableModel tableModel = (DefaultTableModel)this.fieldTable.getModel();
		//设置数据
		tableModel.setDataVector(getFieldDatas(), this.fieldTable.getFieldTableColumn());
		//设置列表样式
		this.fieldTable.setTableFace();
	}
	
	//刷新外键字段列表
	public void refreshForeignFieldTable() {
		//设置外键数据
		DefaultTableModel tableModel = (DefaultTableModel)this.foreignTable.getModel();
		tableModel.setDataVector(getForeignDatas(), this.foreignTable.getForeignColumns());
		//设置外键列表的样式
		this.foreignTable.setTableFace();
	}
	
	//返回外键字段数据
	@SuppressWarnings("unchecked")
	public Vector getForeignDatas() {
		Vector datas = new Vector();
		for (int i = 0; i < this.foreignFields.size(); i++) {
			ForeignField foreignField = this.foreignFields.get(i);
			Vector data = new Vector();
			//字段名称
			if (foreignField.getField() == null) data.add(null);
			else data.add(foreignField.getField().getFieldName());
			if (foreignField.getReferenceField() == null) {
				data.add(null);
				data.add(null);
			} else {
				data.add(foreignField.getReferenceField().getTable().getName());
				data.add(foreignField.getReferenceField().getFieldName());
			}
			data.add(foreignField.getOnDelete());
			data.add(foreignField.getOnUpdate());
			datas.add(data);
		}
		return datas;
	}
	
	//创建外键列表
	private ForeignTable createForeignTable() {
		DefaultTableModel model = new DefaultTableModel();
		ForeignTable table = new ForeignTable(model, this);
		return table;
	}
	
	//返回字段的数据
	@SuppressWarnings("unchecked")
	public Vector getFieldDatas() {
		Vector datas = new Vector();
		for (int i = 0; i < this.fields.size(); i++) {
			Field field = this.fields.get(i);
			Vector data = new Vector();
			data.add(field.getFieldName());//字段名称
			data.add(field.getType());//字段类型
			data.add(getNullIcon(field));//获得是否允许空的图片
			data.add(getPrimaryKeyIcon(field));//获得主键图片
			datas.add(data);
		}
		return datas;
	}
	
	//该方法由FieldTable调用，当点击了某个单元格时，就设置该单元格所行对应的字段默认值
	public void setDefaultValue(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		this.defaultField.setText(field.getDefaultValue());
	}
	
	//该方法由FieldTable调用，点击某行Field时，就设置该行的自动增长的CheckBox
	public void setIsAutoIncrement(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		if (field.isAutoIncrement()) this.isAutoIncrementBox.setSelected(true);
		else this.isAutoIncrementBox.setSelected(false);
	}
	
	//点击自动增长checkBox的方法
	private void clickIsAutoIncrementBox() {
		//得到字段列表中所选中的行索引
		int row = this.fieldTable.getSelectedRow();
		if (row == -1) return;
		//得到当前所选择了Field对象
		Field field = this.fields.get(row);
		//设置Field对象中的自动增长属性
		if (this.isAutoIncrementBox.isSelected()) field.setAutoIncrement(true);
		else field.setAutoIncrement(false);
	}
	
	/**
	 * 改变字段的默认值
	 */
	public void changeDefaultValue() {
		//得到选中的行
		int selectRow = this.fieldTable.getSelectedRow();
		if (selectRow == -1) return;
		//取得默认值
		String defaultValue = this.defaultField.getText();
		//取得当前编辑的Field对象
		Field field = this.fields.get(selectRow);
		//设置字段默认值
		field.setDefaultValue(defaultValue);
	}

	/**
	 * 改变允许空字段的值（已经改变了图片显示，该方法改变对应的Field对象的值）
	 * @param row
	 */
	public void changeAllowNullValue(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		if (field.isAllowNull()) field.setAllowNull(false);
		else field.setAllowNull(true);
	}
	
	/**
	 * 字段列表的字段名称，同步去修改字段集合中的元素值
	 * @param row
	 * @param value
	 */
	public void changeFieldName(int row, String value) {
		Field field = this.fields.get(row);
		if (field == null) return;
		field.setFieldName(value);
	}
	
	/**
	 * 字段列表的字段类型，同步去修改字段集合中的值
	 * @param row
	 * @param value
	 */
	public void changeFieldType(int row, String value) {
		Field field = this.fields.get(row);
		if (field == null) return;
		field.setType(value);
	}
	
	/**
	 * 字段列表的字段长度，同步去修改字段集合中的值
	 * @param row
	 * @param value
	 */
//	public void changeFieldLength(int row, String value) {
//		Field field = this.fields.get(row);
//		if (field == null) return;
//		try {
//			if (value.trim().equals("")) value = "10";
//			int length = Integer.parseInt(value);
//			field.setLength(length);
//		} catch (Exception e) {
//			//如果用户输入的非数字，则catch异常提示
//			showMessage("字段长度请输入数字", "错误");
//			return;
//		}
//	}
	
	public List<Field> getFields() {
		return this.fields;
	}
	
	/**
	 * 改变主键的值（改变图片显示，该方法改变对应的Field对象的值）
	 * @param row
	 */
	public void changePrimaryKeyValue(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		if (field.isPrimaryKey()) field.setPrimaryKey(false);
		else field.setPrimaryKey(true);
	}

	//判断参数字段是否为主键，返回不同的图片
	private Icon getPrimaryKeyIcon(Field field) {
		if (field.isPrimaryKey()) return ImageUtil.PRIMARY_KEY;
		return ImageUtil.PRIMARY_KEY_BLANK;
	}

	
	/*
	 * 判断字段是否可以为空，返回不同图片
	 */
	private Icon getNullIcon(Field field) {
		if (field.isAllowNull()) return ImageUtil.CHECKED_ICON;
		return ImageUtil.UN_CHECKED_ICON;
	}
	
	//创建放置字段的表格
	private FieldTable createFieldTable() {
		DefaultTableModel model = new DefaultTableModel();
		FieldTable table = new FieldTable(model, this);
		return table;
	}

	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
}
