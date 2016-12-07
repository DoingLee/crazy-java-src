package org.crazyit.transaction.ui.table;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import org.crazyit.transaction.model.Transaction;

/**
 * 事务列表的TableModel
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TransactionTableModel extends DefaultTableModel {

	public final static String TS_ID = "id";
	
	public final static String TS_STATE = "状态";
	
	public final static String TS_TITLE = "标题";
	
	public final static String TS_CONTENT = "内容";
	
	public final static String TS_TARGETDATE = "目标完成时间";
	
	public final static String TS_CREATEDATE = "创建日期";
	
	public final static String INITIATOR = "发起人";
	
	public final static String TS_FACTDATE = "实际完成时间";
	
	public final static String CURRENT_HANDLER = "当前处理人";
	
	private List<Transaction> datas;
		
	private static final String[] columnNames = {
		TS_ID,
		TS_STATE,
		TS_TITLE,
		TS_CONTENT,
		TS_TARGETDATE,
		TS_CREATEDATE,
		CURRENT_HANDLER,
		INITIATOR,
		TS_FACTDATE
	};
	
	public TransactionTableModel() {
		super();
	}
	
	public void setDatas(List<Transaction> datas) {
		this.datas = datas;
	}
	
	public List<Transaction> getDatas() {
		return this.datas;
	}
	
	public int getRowCount() {
		if (this.datas != null) {
			return this.datas.size();
		}
		return 0;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		String columnName = this.getColumnName(column);
		if (this.datas != null) {
			Transaction t = this.datas.get(row);
			if (TS_ID.equals(columnName)) {
				return t.getID();
			} else if (TS_STATE.equals(columnName)) {
				return getStateImage(t.getTS_STATE());
			} else if (TS_TITLE.equals(columnName)) {
				return t.getTS_TITLE();
			} else if (TS_CONTENT.equals(columnName)) {
				return t.getTS_CONTENT();
			} else if (TS_TARGETDATE.equals(columnName)) {
				return t.getTS_TARGETDATE();
			} else if (TS_CREATEDATE.equals(columnName)) {
				return t.getTS_CREATEDATE();
			} else if (CURRENT_HANDLER.equals(columnName)) {
				return t.getHandler().getREAL_NAME();
			} else if (INITIATOR.equals(columnName)) {
				return t.getInitiator().getREAL_NAME();
			} else if (TS_FACTDATE.equals(columnName)) {
				return (t.getTS_FACTDATE() == null) ? "" : t.getTS_FACTDATE();
			}
		}
		return super.getValueAt(row, column);
	}
	
	/**
	 * 返回事务状态对应的图片
	 * @param state
	 * @return
	 */
	private ImageIcon getStateImage(String state) {
		if (State.PROCESS_STATE.getValue().equals(state)) {
			return State.PROCESS_IMAGE;
		} else if (State.FINISHED_STATE.getValue().equals(state)) {
			return State.FINISHED_IMAGE;
		} else if (State.FOR_A_WHILE_STATE.getValue().equals(state)) {
			return State.FOR_A_WHILE_IMAGE;
		} else if (State.NOT_TO_DO_STATE.getValue().equals(state)) {
			return State.NOT_TO_DO_IMAGE;
		} else if (State.INVALID_STATE.getValue().equals(state)) {
			return State.INVALID_IMAGE;
		}
		return null;
	}
}
