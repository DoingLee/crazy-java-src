package org.crazyit.gamehall.fivechess.client.object;

import org.crazyit.gamehall.fivechess.commons.Table;

/**
 * 游戏大厅信息对象, 该对象由服务器的响应创建
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameHallInfo {

	//桌子信息
	private Table[][] tables;
	
	private int tableColumnSize;
	
	private int tableRowSize;
	
	public GameHallInfo() {
		
	}

	public GameHallInfo(Table[][] tables, int tableColumnSize, int tableRowSize) {
		this.tables = tables;
		this.tableColumnSize = tableColumnSize;
		this.tableRowSize = tableRowSize;
	}

	public Table[][] getTables() {
		return tables;
	}

	public void setTables(Table[][] tables) {
		this.tables = tables;
	}

	public int getTableColumnSize() {
		return tableColumnSize;
	}

	public void setTableColumnSize(int tableColumnSize) {
		this.tableColumnSize = tableColumnSize;
	}

	public int getTableRowSize() {
		return tableRowSize;
	}

	public void setTableRowSize(int tableRowSize) {
		this.tableRowSize = tableRowSize;
	}
	
	
}
