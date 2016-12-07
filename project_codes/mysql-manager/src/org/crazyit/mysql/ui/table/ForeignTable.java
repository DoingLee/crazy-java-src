package org.crazyit.mysql.ui.table;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.crazyit.mysql.object.list.TableData;
import org.crazyit.mysql.object.table.ForeignItem;
import org.crazyit.mysql.table.object.Field;
import org.crazyit.mysql.ui.TableFrame;

/**
 * 外键列表对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ForeignTable extends JTable {
	
	public final static String FIELD_NAME = "字段名称";
	public final static String REFERENCE_TABLE = "约束表";
	public final static String REFERENCE_FIELD = "约束字段";
	public final static String ON_DELETE = "级联删除";
	public final static String ON_UPDATE = "级联更新";

	//级联的各个值
	private ForeignItem RESTRICT = new ForeignItem("RESTRICT", "RESTRICT");
	private ForeignItem CASCADE = new ForeignItem("CASCADE", "CASCADE");
	private ForeignItem SETNULL  = new ForeignItem("SET NULL", "SET NULL");
	private ForeignItem NOACTION = new ForeignItem("NO ACTION", "NO ACTION");
	
	//级联删除的下拉
	private JComboBox foreignDelete = new JComboBox();
	//级联更新的下拉
	private JComboBox foreignUpdate = new JComboBox();
	
	private TableFrame tableFrame;
	
	//字段名称编辑器对象
	private DefaultCellEditor fieldNameEditor;
	private JComboBox fieldNameComboBox = new JComboBox();
	
	//约束表
	private DefaultCellEditor referenceTableEditor;
	private JComboBox referenceTableComboBox = new JComboBox();
	
	//约束字段
	private DefaultCellEditor referenceFieldEditor;
	private JComboBox referenceFieldComboBox = new JComboBox();
	
	//级联删除
	private DefaultCellEditor onDeleteEditor;
	
	//级联更新
	private DefaultCellEditor onUpdateEditor;
	
	
	public ForeignTable(DefaultTableModel model, TableFrame tableFrame) {
		super(model);
		this.tableFrame = tableFrame;
		model.setDataVector(tableFrame.getForeignDatas(), getForeignColumns());
		setTableFace();
		initListeners();
	}
	
	private void initComboBox() {
		createFieldNameComboBox();
		createReferenceTableComboBox();
		createReferenceFieldComboBox();
		createOnDeleteComboBox();
		createOnUpdateComboBox();
	}

	//初始化监听器
	private void initListeners() {
		this.fieldNameComboBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				createFieldNameComboBox();
			}			
		});
		this.fieldNameComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeField();
				}
			}
		});
		this.referenceTableComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					createReferenceFieldComboBox();
				}
			}
		});
		this.referenceFieldComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeReferenceField();
				}
			}
		});
		this.foreignDelete.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeOnDelete();
				}
			}
		});
		this.foreignUpdate.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeOnUpdate();
				}
			}
		});
	}
	
	//设置列表样式
	public void setTableFace() {
		this.setRowHeight(25);
		//初始化约束表和约束字段的下拉值
		initComboBox();
		//设置显示下拉
		this.fieldNameEditor = new DefaultCellEditor(this.fieldNameComboBox);
		this.referenceTableEditor = new DefaultCellEditor(this.referenceTableComboBox);
		this.referenceFieldEditor = new DefaultCellEditor(this.referenceFieldComboBox);
		this.onDeleteEditor = new DefaultCellEditor(this.foreignDelete);
		this.onUpdateEditor = new DefaultCellEditor(this.foreignUpdate);
		this.getColumn(FIELD_NAME).setCellEditor(this.fieldNameEditor);
		this.getColumn(REFERENCE_TABLE).setCellEditor(this.referenceTableEditor);
		this.getColumn(REFERENCE_FIELD).setCellEditor(this.referenceFieldEditor);
		this.getColumn(ON_DELETE).setCellEditor(this.onDeleteEditor);
		this.getColumn(ON_UPDATE).setCellEditor(this.onUpdateEditor);
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//设置列大小
		this.getColumn(ON_DELETE).setMinWidth(100);
		this.getColumn(ON_UPDATE).setMinWidth(100);
		this.getColumn(ON_DELETE).setMaxWidth(150);
		this.getColumn(ON_UPDATE).setMaxWidth(150);
	}
	
	//创建字段名称下拉
	private void createFieldNameComboBox() {
		this.fieldNameComboBox.removeAllItems();
		//使用字段列表中已经存在的字段创建下拉框
		List<Field> fields = this.tableFrame.getFields();
		for (Field f : fields) this.fieldNameComboBox.addItem(f);
	}
	
	//创建级联删除的下拉
	private void createOnDeleteComboBox() {
		this.foreignDelete.removeAllItems();
		this.foreignDelete.addItem(CASCADE);
		this.foreignDelete.addItem(SETNULL);
		this.foreignDelete.addItem(RESTRICT);
		this.foreignDelete.addItem(NOACTION);
	}
	
	//创建级联更新的下拉
	private void createOnUpdateComboBox() {
		this.foreignUpdate.removeAllItems();
		this.foreignUpdate.addItem(CASCADE);
		this.foreignUpdate.addItem(SETNULL);
		this.foreignUpdate.addItem(RESTRICT);
		this.foreignUpdate.addItem(NOACTION);
	}
	
	//创建约束表下拉
	private void createReferenceTableComboBox() {
		this.referenceTableComboBox.removeAllItems();
		//使用某个数据库下面所有的表来创建下拉框
		List<TableData> tables = this.tableFrame.getAllTables();
		for (TableData table : tables) {
			this.referenceTableComboBox.addItem(table);
		}
	}

	//创建约束字段下拉，当约束表的下拉发生改变时触发该方法
	private void createReferenceFieldComboBox() {
		TableData data = (TableData)this.referenceTableComboBox.getSelectedItem();
		if (data == null) return;
		this.referenceFieldComboBox.removeAllItems();
		List<Field> fields = data.readFields();
		for (Field f : fields) {
			this.referenceFieldComboBox.addItem(f);
		}
	}
	
	//当表字段发生改变时，触发该方法
	private void changeField() {
		Field field = (Field)this.fieldNameComboBox.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return;
		this.tableFrame.changeForeignField(row, field);
	}
	
	//当约束字段发生改变时，触发该方法
	private void changeReferenceField() {
		Field field = (Field)this.referenceFieldComboBox.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return;
		this.tableFrame.changeReferenceField(row, field);
	}
	
	//当ON DELETE发生改变时，触发该方法
	private void changeOnDelete() {
		ForeignItem item = (ForeignItem)this.foreignDelete.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return; 
		this.tableFrame.changeOnDelete(row, item.getValue());
	}
	
	//当ON UPDATE发生改变时，触发该方法
	private void changeOnUpdate() {
		ForeignItem item = (ForeignItem)this.foreignUpdate.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return; 
		this.tableFrame.changeOnUpdate(row, item.getValue());
	}
		
	//返回外键的数据列
	@SuppressWarnings("unchecked")
	public Vector getForeignColumns() {
		Vector cols = new Vector();
		cols.add(FIELD_NAME);
		cols.add(REFERENCE_TABLE);
		cols.add(REFERENCE_FIELD);
		cols.add(ON_DELETE);
		cols.add(ON_UPDATE);
		return cols;
	}
}

