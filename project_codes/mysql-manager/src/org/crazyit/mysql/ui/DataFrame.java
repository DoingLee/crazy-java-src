package org.crazyit.mysql.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.crazyit.mysql.exception.QueryException;
import org.crazyit.mysql.object.QueryObject;
import org.crazyit.mysql.object.table.DataCell;
import org.crazyit.mysql.object.table.DataColumn;
import org.crazyit.mysql.ui.table.DataTable;
import org.crazyit.mysql.util.MySQLUtil;

/**
 * 显示数据的界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DataFrame extends JFrame {

	private Box mainBox = Box.createVerticalBox();
	
	private JScrollPane tablePane;
	
	private DataTable table;
	
	private DefaultTableModel model;
	
	private JToolBar toolBar = new JToolBar();
	
	//刷新数据
	private Action refresh = new AbstractAction("刷新数据", new ImageIcon("images/refresh-data.gif")){
		public void actionPerformed(ActionEvent e) {
			refresh();
		}
	};
	
	//降序
	private Action sortDesc = new AbstractAction("降序", new ImageIcon("images/sort-desc.gif")){
		public void actionPerformed(ActionEvent e) {
			desc();
		}
	};
	
	//升序
	private Action sortAsc = new AbstractAction("升序", new ImageIcon("images/sort-asc.gif")){
		public void actionPerformed(ActionEvent e) {
			asc();
		}
	};
	
	//查询对象
	private QueryObject queryObject;
	//当前的ResultSet对象
	private ResultSet rs;
	//当前列表的列集合
	private List<DataColumn> columns;
	//排序字符串
	private String orderString;
	
	public DataFrame(QueryObject queryObject) {
		this.queryObject = queryObject;
		this.rs = queryObject.getDatas(this.orderString);
		this.table = createTable();
		this.tablePane = new JScrollPane(this.table);
		this.tablePane.setPreferredSize(new Dimension(600, 400));
		this.mainBox.add(this.tablePane);
		this.add(this.mainBox);
		initToolBar();
		this.setLocation(200, 100);
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setTitle(queryObject.getQueryName());
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//释放本窗口所有内存
				dispose();
			}
		});
	}
	
	//初始化工具条
	private void initToolBar() {
		this.toolBar.add(this.refresh).setToolTipText("刷新");
		this.toolBar.add(this.sortDesc).setToolTipText("降序");
		this.toolBar.add(this.sortAsc).setToolTipText("升序");
	}
	
	//刷新数据
	private void refresh() {
		//更新数据
		this.rs = this.queryObject.getDatas(this.orderString);
		//得到全部列
		this.columns = getColumns(this.rs);
		//设置数据到列表中
		this.model.setDataVector(getDatas(), this.columns.toArray());
		setTableColumn(this.table);
	}
	
	//降序
	private void desc() {
		String column = getSelectColumnIdentifier();
		//没有选中整列, 不排序
		if (column == null) return;
		this.orderString = column + " " + MySQLUtil.DESC;
		//刷新列表
		refresh();
	}
	
	//升序
	private void asc() {
		String column = getSelectColumnIdentifier();
		if (column == null) return;
		this.orderString = column + " " + MySQLUtil.ASC;
		//刷新列表
		refresh();
	}
	
	//返回列的名称
	private String getSelectColumnIdentifier() {
		//得到选中列索引
		int selectIndex = this.table.getSelectColumn();
		if (selectIndex == -1) return null;
		DefaultTableColumnModel colModel = (DefaultTableColumnModel)this.table.getColumnModel();
		return (String)colModel.getColumn(selectIndex).getIdentifier();
	}
	
	//创建列表
	private DataTable createTable() {
		//得到全部列
		this.columns = getColumns(this.rs);
		//构造JTable模型，初始化数据
		this.model = new DefaultTableModel(getDatas(), 
				columns.toArray());
		DataTable table = new DataTable(this.model);
		setTableColumn(table);
		return table;
	}
	
	
	//设置每列的宽
	private void setTableColumn(JTable table) {
		DefaultTableColumnModel model = (DefaultTableColumnModel)table.getColumnModel();
		for (int i = 0; i < table.getColumnCount(); i++) {
			//得到列对象
			TableColumn column = model.getColumn(i);
			//得到列名
			String columnName = (String)column.getIdentifier();
			//设置最小宽度
			int width = columnName.length() * 10;
			//设置列宽最小为150
			int prefectWidth = (width < 150) ? 150 : width;
			column.setMinWidth(prefectWidth);
		}
	}
	
	//获得数据
	@SuppressWarnings("unchecked")
	private DataCell[][] getDatas() {
		int columnCount = this.columns.size();
		//集合中存放一个DataCell数组，最后转换成一个二维数组
		List<DataCell[]> datas = new ArrayList<DataCell[]>();
		try {
			int i = 0;
			while (this.rs.next()) {
				DataCell[] data = new DataCell[columnCount];
				//遍历列, 创建每一个单元格对象
				for (int j = 0; j < columnCount; j++) {
					//得到具体的某一列
					DataColumn column = this.columns.get(j);
					//创建单元格对象
					DataCell dc = new DataCell(i, column, 
							this.rs.getString(column.getText()));
					data[j] = dc;
				}
				datas.add(data);
			}
			//取完数据，将当前的ResultSet对象关闭
			this.rs.close();
			return datas.toArray(EMPTY_DATAS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("读取数据错误：" + this.queryObject.getQueryName());
		}
	}
	
	private final static DataCell[][] EMPTY_DATAS = new DataCell[0][0];
	
	//获得列
	@SuppressWarnings("unchecked")
	private List<DataColumn> getColumns(ResultSet rs) {
		try {
			List<DataColumn> columns = new ArrayList<DataColumn>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columns.add(new DataColumn(i, metaData.getColumnLabel(i)));
			}
			return columns;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("创建列异常：" + 
					this.queryObject.getQuerySQL(this.orderString));
		}	
	}
	
}
