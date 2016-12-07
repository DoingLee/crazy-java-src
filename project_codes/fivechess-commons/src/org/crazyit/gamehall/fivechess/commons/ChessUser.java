package org.crazyit.gamehall.fivechess.commons;

import org.crazyit.gamehall.commons.User;

/**
 * 五子棋玩家对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ChessUser extends User {

		
	//是否已经准备游戏
	private boolean ready;

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public String toString() {
		return this.getName();
	}
	
	//判断玩家是否在座位上, 如果玩家已经在座位上, 返回true, 否则返回false
	public boolean hasSitDown(Table[][] tables) {
		for (int i = 0; i < tables.length; i++) {
			for (int j = 0; j < tables[i].length; j++) {
				Table t = tables[i][j];
				Seat ls = t.getLeftSeat();
				Seat rs = t.getRightSeat();
				if (ls.getUser() != null) {
					if (this.getId().equals(ls.getUser().getId())) return true;
				}
				if (rs.getUser() != null) {
					if (this.getId().equals(rs.getUser().getId())) return true;
				}
			}
		}
		return false;
	}
}
