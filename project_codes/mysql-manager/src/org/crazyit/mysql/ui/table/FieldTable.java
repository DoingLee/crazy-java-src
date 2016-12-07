package org.crazyit.mysql.ui.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.crazyit.mysql.ui.TableFrame;
import org.crazyit.mysql.util.ImageUtil;

/**
 * 字段列表的JTable
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FieldTable extends JTable {

	public final static String FIELD_NAME = "字段名";
	public final static String FIELD_TYPE = "类型";
	public final static String ALLOW_NULL = "允许空";
	public final static String PRIMARY_KEY = "主键";
	
	private TableFrame tableFrame;
	
	private FieldTableIconCellRenderer cellRenderer;
	
	public FieldTable(DefaultTableModel model, TableFrame tableFrame) {
		super(model);
		this.tableFrame = tableFrame;
		this.cellRenderer = new FieldTableIconCellRenderer();
		model.setDataVector(tableFrame.getFieldDatas(), getFieldTableColumn());
		setTableFace();
		//设置点击
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectCell();
			}
		});
	}
	
	public void setTableFace() {
		//设置行高
		this.setRowHeight(25);
		//设置选择一个单元格
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//设置各列的宽度
		this.getColumn(PRIMARY_KEY).setMaxWidth(50);
		this.getColumn(ALLOW_NULL).setMaxWidth(50);
		this.getColumn(ALLOW_NULL).setCellRenderer(this.cellRenderer);
		this.getColumn(PRIMARY_KEY).setCellRenderer(this.cellRenderer);
	}
	
	
	public boolean isCellEditable(int row, int column) {
		//"允许空"列不可以被编辑
		TableColumn notNullColumn = this.getColumn(ALLOW_NULL);
		if (notNullColumn.getModelIndex() == column) {
			return false;
		}
		TableColumn primaryKeyColumn = this.getColumn(PRIMARY_KEY);
		if (primaryKeyColumn.getModelIndex() == column) {
			return false;
		}
		return super.isCellEditable(row, column);
	}
	
	//鼠标在JTable中点击的时候触发该方法
	private void selectCell() {
		int column = this.getSelectedColumn();
		int row = this.getSelectedRow();
		if (column == -1 || row == -1) return;
		//修改图片列
		selectAllowNullColumn(row, column);
		selectPrimaryKeyColumn(row, column);
		//修改默认值
		this.tableFrame.setDefaultValue(row);
		//修改是否自动增长的checkbox
		this.tableFrame.setIsAutoIncrement(row);
		//设置点击后会改变的值，注意是点击，并不是输入，因此只会更改允许空和主键
		changeClickValue(row, column);
	}
	
	//当发生鼠标点击单元格事件的时候，改变值，一般只改变允许空和主键列
	private void changeClickValue(int row, int column) {
		TableColumn primaryColumn = this.getColumn(PRIMARY_KEY);
		if (primaryColumn.getModelIndex() == column) {
			this.tableFrame.changePrimaryKeyValue(row);
		}
		TableColumn allowNullColumn = this.getColumn(ALLOW_NULL);
		if (allowNullColumn.getModelIndex() == column) {
			this.tableFrame.changeAllowNullValue(row);
		}
	}
	
	//如果鼠标点击的列是"主键列"，去掉或者加上图标
	private void selectPrimaryKeyColumn(int row, int column) {
		//得到需要更改图片的列（主键列）
		TableColumn tc = this.getColumn(PRIMARY_KEY);
		if (tc.getModelIndex() == column) {
			Object obj = this.getValueAt(row, column);
			if (ImageUtil.PRIMARY_KEY_BLANK.equals(obj)) {
				this.setValueAt(ImageUtil.PRIMARY_KEY, row, column);
			} else {
				this.setValueAt(ImageUtil.PRIMARY_KEY_BLANK, row, column);
			}
		}
	}
	
	//如果鼠标点击的列是"允许空"列， 则更改图标
	private void selectAllowNullColumn(int row, int column) {
		//得到需要更改图片的列（允许空列）
		TableColumn tc = this.getColumn(ALLOW_NULL);
		if (tc.getModelIndex() == column) {
			Icon currentIcon = (Icon)this.getValueAt(row, column);
			if (ImageUtil.CHECKED_ICON.equals(currentIcon)) {
				//当前为选中状态
				this.setValueAt(ImageUtil.UN_CHECKED_ICON, row, column);
			} else {
				//当前为没选中状态
				this.setValueAt(ImageUtil.CHECKED_ICON, row, column);
			}
		}
	}
	
	//重写JTable的方法, 列表停止编辑的时候触发该方法
	public void editingStopped(ChangeEvent e) {
		int column = this.getEditingColumn();
		int row = this.getEditingRow();
		super.editingStopped(e);
		//获得当前编辑的列名
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		String columnName = model.getColumnName(column);
		//得到编辑后的单元格的值
		String value = (String)this.getValueAt(row, column);
		if (columnName.equals(FIELD_NAME)) {
			//更改字段名称
			this.tableFrame.changeFieldName(row, value);
		} else if (columnName.equals(FIELD_TYPE)) {
			//更改字段类型
			this.tableFrame.changeFieldType(row, value);
		}
	}
	
	

	//返回数据库列的列表的列
	@SuppressWarnings("unchecked")
	public Vector getFieldTableColumn() {
		Vector cols = new Vector();
		cols.add(FIELD_NAME);
		cols.add(FIELD_TYPE);
		cols.add(ALLOW_NULL);
		cols.add(PRIMARY_KEY);
		return cols;
	}
}
