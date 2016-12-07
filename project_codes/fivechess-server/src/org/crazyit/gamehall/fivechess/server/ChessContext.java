package org.crazyit.gamehall.fivechess.server;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.gamehall.fivechess.commons.Chess;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;

/**
 * 五子棋上下文, 保存在服务器端
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ChessContext {

	public static Map<String, ChessUser> users = new HashMap<String, ChessUser>();
	
	//大厅桌子列数, 每行桌子数
	public final static int TABLE_COLUMN_SIZE = 5;
	
	//大厅桌子行数
	public final static int TABLE_ROW_SIZE = 10;
	
	//保存桌子信息
	public static Table[][] tables = new Table[TABLE_COLUMN_SIZE][TABLE_ROW_SIZE];
	
	//游戏界面的棋子信息, map中的key为桌子号, value是该桌子上的二维数组
	public static Map<Integer, Chess[][]> tableChesses = new HashMap<Integer, Chess[][]>();

	public final static int LINK_COUNT = 5;
	
	static {
		//初始化桌子信息
		tables = new Table[TABLE_COLUMN_SIZE][TABLE_ROW_SIZE];
		int tableNumber = 0;
		for (int i = 0; i < tables.length; i++) {
			for (int j = 0; j < tables[i].length; j++) {
				Table table = new Table(Table.DEFAULT_IMAGE_WIDTH*i, 
						Table.DEFAULT_IMAGE_HEIGHT*j, tableNumber);
				tables[i][j] = table;
				tableNumber++;
			}
		}
	}
	
	/**
	 * 得到玩家所在的桌子
	 * @param user
	 * @return
	 */
	public static Table getTable(String userId) {
		for (int i = 0; i < tables.length; i++) {
			for (int j = 0; j < tables[i].length; j++) {
				Table table = tables[i][j];
				Seat ls = table.getLeftSeat();
				if (ls.getUser() != null) {
					ChessUser u = ls.getUser();
					if (u.getId().equals(userId)) return table;
				}
				Seat rs = table.getRightSeat();
				if (rs.getUser() != null) {
					ChessUser u = rs.getUser();
					if (u.getId().equals(userId)) return table;
				}
			}
		}
		return null;
	}

}
