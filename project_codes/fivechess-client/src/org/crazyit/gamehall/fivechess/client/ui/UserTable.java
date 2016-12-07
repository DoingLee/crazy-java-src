package org.crazyit.gamehall.fivechess.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.crazyit.gamehall.fivechess.commons.ChessUser;

/**
 * 玩家信息的JPanel
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UserTable extends JTable {

	//其他玩家(不包括当前玩家)
	private List<ChessUser> users;
	
	//当前进入大厅的玩家
	private ChessUser user;
	
	//头像
	private Map<String, Icon> heads;
	
	public UserTable(List<ChessUser> users, ChessUser user) {
		this.users = users;
		this.user = user;
		//将自己加到玩家列表最前面
		this.users.add(0, user);
		this.heads = new HashMap<String, Icon>();
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.setDataVector(getDatas(), getColumns());
		setTableFaces();
	}
	
	//设置列表样式
	private void setTableFaces() {
		this.getColumn("id").setMinWidth(0);
		this.getColumn("id").setMaxWidth(0);
		this.getColumn("头像").setCellRenderer(new UserTableCellRenderer());
		this.getColumn("头像").setMaxWidth(40);
		this.getColumn("名称").setCellRenderer(new UserTableCellRenderer());
		this.getColumn("性别").setMaxWidth(28);
		this.getColumn("性别").setCellRenderer(new UserTableCellRenderer());
		this.setRowHeight(34);
	}
	
	//重新设置玩家列表
	public void setUsers(List<ChessUser> users) {
		this.users = users;
		refresh();
	}
	
	public void addUser(ChessUser user) {
		this.users.add(user);
		refresh();
	}
	
	public void refresh() {
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.setDataVector(getDatas(), getColumns());
		setTableFaces();
	}
	
	//得到玩家列表的数据格式
	private Vector<Vector> getDatas() {
		Vector<Vector> result = new Vector<Vector>();
		for (ChessUser user : this.users) {
			Vector v = new Vector();
			v.add(user.getId());
			v.add(getHead(user.getHeadImage()));
			v.add(user.getName());
			v.add(getSex(user.getSex()));
			result.add(v);
		}
		return result;
	}
	
	private String getUserState(int state) {
		if (state == 0) return "";
		else return "游戏中";
	}
	
	private String getSex(int sex) {
		return (sex == 0) ? "男" : "女";
	}
	
	//从Map中得到头像, 没有得根据路径读取
	private Icon getHead(String url) {
		if (this.heads.get(url) == null) {
			try {
				ImageIcon head = new ImageIcon(url);
				this.heads.put(url, head);
			} catch (Exception e) {
				return null;
			}
		}
		return this.heads.get(url);
	}
	
	//返回JTable的列
	private Vector getColumns() {
		Vector columns = new Vector();
		columns.add("id");
		columns.add("头像");
		columns.add("名称");
		columns.add("性别");
		return columns;
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
