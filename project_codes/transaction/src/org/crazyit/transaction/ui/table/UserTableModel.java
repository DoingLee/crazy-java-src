package org.crazyit.transaction.ui.table;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.crazyit.transaction.model.User;

public class UserTableModel extends DefaultTableModel {

	public final static String USER_ID = "id";
	
	public final static String USER_NAME = "用户名";
	
	public final static String REAL_NAME = "真实姓名";
	
	public final static String ROLE = "角色";
	
	private List<User> datas;
	
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
	
	public void setDatas(List<User> datas) {
		this.datas = datas;
	}
	
	private static final String[] columnNames = {
		USER_ID,
		USER_NAME,
		REAL_NAME,
		ROLE
	};
	
	@Override
	public Object getValueAt(int row, int column) {
		String columnName = this.getColumnName(column);
		if (this.datas != null) {
			User user = this.datas.get(row);
			if (USER_ID.equals(columnName)) {
				return user.getID();
			} else if (USER_NAME.equals(columnName)) {
				return user.getUSER_NAME();
			} else if (REAL_NAME.equals(columnName)) {
				return user.getREAL_NAME();
			} else if (ROLE.equals(columnName)) {
				return user.getRole().getROLE_NAME();
			}
		}
		return super.getValueAt(row, column);
	}
}
