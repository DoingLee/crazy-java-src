package org.crazyit.gamehall.fivechess.client.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.fivechess.client.object.GameHallInfo;
import org.crazyit.gamehall.fivechess.client.util.ImageUtil;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.fivechess.commons.util.FiveChessErrorCode;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 大厅的JPanel
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class HallPanel extends JPanel {

	private Table[][] tables;
	
	private static Image tableImage = ImageUtil.getImage("images/fivechess/table.jpg");
	
	private static Image seatSelectImage = ImageUtil.getImage("images/fivechess/selected.gif");
	
	//大厅桌子列数, 每行桌子数
	public final static int TABLE_COLUMN_SIZE = 5;
	
	//大厅桌子行数
	public final static int TABLE_ROW_SIZE = 10;
	
	//手形的鼠标指针
	private final static Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	//默认的鼠标指针
	private final static Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
	
	private static Map<String, Image> heads = new HashMap<String, Image>();
	
	//当前界面的玩家
	private ChessUser user;
	
	//其他玩家
	private List<ChessUser> users;
	
	//初始化游戏大厅时需要所有桌子信息
	public HallPanel(GameHallInfo gameHallInfo, ChessUser user, List<ChessUser> users) {
		this.tables = gameHallInfo.getTables();
		this.user = user;
		this.users = users;
	    this.setPreferredSize(new Dimension(gameHallInfo.getTableColumnSize() * Table.DEFAULT_IMAGE_WIDTH, 
	    		gameHallInfo.getTableRowSize() * Table.DEFAULT_IMAGE_HEIGHT));
	    this.revalidate();
	    this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				moveMouse(e);
			}
	    });
	    this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				sitDown(e);
			}
	    });
	}
	
	//坐下的桌子的方法
	private void sitDown(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//获得桌子
		Table table = getTable(x, y);
		if (table != null) {
			//得到座位
			Seat seat = getSeat(table, x, y);
			if (seat != null) {
				//判断玩家是否已经坐下
				if (this.user.hasSitDown(this.tables)) {
					UIContext.showMessage(FiveChessErrorCode.getMessage(FiveChessErrorCode.HAS_SIT_DOWN));
					return;
				}
				if (seat.getUser() != null) {
					//座位上有人
					UIContext.showMessage(FiveChessErrorCode.getMessage(FiveChessErrorCode.SEAT_HAS_USER));
				} else {
					seat.setUser(this.user);
					//没人向服务器发送请求
					sendServerSitDown(table, seat.getSide());
				}
			}
		}
	}
		
	//得到当前x, y座标的座位
	private Seat getSeat(Table table, int x, int y) {
		if (table.getLeftSeat().getRange().contains(x, y)) {
			return table.getLeftSeat();
		} else if (table.getRightSeat().getRange().contains(x, y)) {
			return table.getRightSeat();
		}
		return null;
	}
	
	//向服务器发送请求, 告诉服务器自己已经坐下
	private void sendServerSitDown(Table table, String side) {
		//创建Request对象并设置相关的参数
		Request request = new Request("org.crazyit.gamehall.fivechess.server.action.UserSitDownAction", 
				"org.crazyit.gamehall.fivechess.client.action.ReceiveUserSitDownAction");
		request.setParameter("tableNumber", table.getTableNumber());
		request.setParameter("side", side);
		request.setParameter("userId", this.user.getId());
		//设置启动游戏的类
		request.setParameter("beginClass", 
				"org.crazyit.gamehall.fivechess.client.action.game.EnterGameAction");
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
		this.repaint();
	}
	
	//移动鼠标时的方法
	private void moveMouse(MouseEvent e) {
		Graphics g = this.getGraphics();
		int x = e.getX();
		int y = e.getY();
		//得到选中的桌子
		Table table = getTable(x, y);
		if (table != null) {
			if (table.getLeftSeat().getRange().contains(x, y)) {
				//左边座位
				this.setCursor(HAND_CURSOR);
				//如果位置上没有人才更换图片
				if (table.getLeftSeat().getUser() == null) {
					g.drawImage(seatSelectImage, table.getLeftSeatBeginX(), 
							table.getLeftSeatBeginY(), this);
				}
			} else if (table.getRightSeat().getRange().contains(x, y)) {
				//右边座位
				this.setCursor(HAND_CURSOR);
				//如果位置上没有人才更换图片
				if (table.getRightSeat().getUser() == null) {
					g.drawImage(seatSelectImage, table.getRightSeatBeginX(),
							table.getRightSeatBeginY(), this);
				}
			} else {
				this.setCursor(DEFAULT_CURSOR);
				this.repaint();
			}
		}
	}
	
	//取得当前鼠标经过时的Table对象
	private Table getTable(int x, int y) {
		for (int i = 0; i < this.tables.length; i++) {
			for (int j = 0; j < this.tables[i].length; j++) {
				Table t = this.tables[i][j];
				if (t.getRange().contains(x, y)) return t;
			}
		}
		return null;
	}
	
	//读取一张头像图片, 并放置到Map中
	private Image getHeadImage(String url) {
		if (heads.get(url) == null) {
			Image head = ImageUtil.getImage(url);
			heads.put(url, head);
		}
		return heads.get(url);
	}
	
	//当桌子上有人的时候, 就弹出提示
	public void seatHasUser() {
		JOptionPane.showConfirmDialog(this, "该座位已经有玩家", "警告", 
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	/**
	 * 返回大厅中的桌子数组
	 * @return
	 */
	public Table[][] getTables() {
		return this.tables;
	}
	
	//新玩家坐下, tableNumber为桌子编号, side为左右位置
	public void newUserSitDown(int tableNumber, String side, String userId) {
		//得到桌子对象
		Table table = getTable(tableNumber);
		//得到座位
		Seat seat = table.getSeat(side);
		//得到坐下的玩家
		ChessUser newUser = getUser(userId);
		seat.setUser(newUser);
		this.repaint();
	}
	
	//根据玩家的id从当前大厅所有的玩家中得到玩家对象
	public ChessUser getUser(String userId) {
		for (ChessUser u : this.users) {
			if (u.getId().equals(userId)) return u;
		}
		return null;
	}
	
	
	public void paint(Graphics g) {
		for (int i = 0; i < this.tables.length; i++) {
			for (int j = 0; j < this.tables[i].length; j++) {
				Table table = this.tables[i][j];
				Seat leftSeat = table.getLeftSeat();
				Seat rightSeat = table.getRightSeat();
				//画桌子的图片
				g.drawImage(tableImage, table.getBeginX(), 
						table.getBeginY(), this);
				//画左边座位的玩家
				if (leftSeat.getUser() != null) {
					Image head = getHeadImage(leftSeat.getUser().getHeadImage());
					g.drawImage(head, table.getLeftSeatBeginX(),
							table.getLeftSeatBeginY(), this);
				}
				//画右边座位的玩家
				if (rightSeat.getUser() != null) {
					Image head = getHeadImage(rightSeat.getUser().getHeadImage());
					g.drawImage(head, table.getRightSeatBeginX(),
							table.getRightSeatBeginY(), this);
				}
			}
		}
	}

	//根据桌子编号取得桌子对象
	public Table getTable(Integer tableNumber) {
		for (int i = 0; i < this.tables.length; i++) {
			for (int j = 0; j < this.tables[i].length; j++) {
				Table table = this.tables[i][j];
				if (tableNumber == table.getTableNumber()) return table;
			}
		}
		return null;
	}
}
