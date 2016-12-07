package org.crazyit.gamehall.fivechess.client.ui.game;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.util.ImageUtil;
import org.crazyit.gamehall.fivechess.commons.Chess;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 五子棋游戏区域
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GamePanel extends JPanel {

	
	//棋盘中的二维数组
	private Chess[][] chessArray;
	
	private Table table;
	
	private ChessUser user;
	
	//棋盘图片
	private static Image chessboard = ImageUtil.getImage("images/fivechess/fiveStoneBoard.jpg");
	
	//背景图片
	private static Image background = ImageUtil.getImage("images/fivechess/background.gif");
	
	//准备
	private static Image ready = ImageUtil.getImage("images/fivechess/ready.gif");
	
	//开始
	private static Image tool_begin = ImageUtil.getImage("images/fivechess/tool-begin.gif");
	
	//求和与认输
	private static Image tool_drawAndLost = ImageUtil.getImage("images/fivechess/tool-draw-lost.gif");
	
	//准备游戏工具条
	private static Image tool_ready = ImageUtil.getImage("images/fivechess/tool-ready.gif");
	
	//当前的工具图片
	private Image currentToolImage;
	
	//手形的鼠标指针
	private final static Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	//默认的鼠标指针
	private final static Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
	
	//开始菜单的区域
	private Rectangle startRange = new Rectangle(318, 630, 73, 40);
	
	//求和菜单的区域
	private Rectangle drawRange = new Rectangle(185, 630, 73, 40);
	
	//认输菜单的区域
	private Rectangle lostRange = new Rectangle(460, 630, 73, 40);
	
	//工具栏图片的区域
	private Rectangle toolRange = new Rectangle(160, 630, 400, 40);
	
	//桌子左边的玩家
	private Image leftUserHead;
	
	//桌子右边的玩家
	private Image rightUserHead;
	
	//是否正在进行游戏
	private boolean gaming;
	
	private static Image BLACK_DISK = ImageUtil.getImage("images/fivechess/blackDisk.gif");
	
	private static Image WHITE_DISK = ImageUtil.getImage("images/fivechess/whiteDisk.gif");
	
	//黑棋图片
	public final static Image BLACK_CHESS = ImageUtil.getImage("images/fivechess/black.gif");
	
	//白棋图片
	public final static Image WHITE_CHESS = ImageUtil.getImage("images/fivechess/white.gif");
	
	//开始游戏的时候提示图片
	private Image gameStartImage;
	
	//开始游戏时显示开始图片的定时器
	private StartGameTask startGameTask;
	
	private Timer timer;
	
	//棋盘边缘X坐标
	public final static int CHESS_BOARD_X = 85 + 23;
	
	//棋盘边缘Y坐标
	public final static int CHESS_BOARD_Y = 80 + 23;
	
	public Rectangle chessBoardRange = new Rectangle(85, 80, 535, 536);
	
	//棋盘单元格的宽
	public final static int CHESS_BOARD_CELL_WIDTH = 35;
	//棋盘单元格的高
	public final static int CHESS_BOARD_CELL_HEIGHT = 35;
	
	//是否轮到当前玩家下棋
	private boolean myTurn = false;
	
	//下棋前的黑色棋选择图片
	private final static Image BLACK_SELECT_PLAY_IMAGE = ImageUtil.getImage("images/fivechess/black-play-select.png");
	
	//下棋前的白色棋选择图片
	private final static Image WHITE_SELECT_PLAY_IMAGE = ImageUtil.getImage("images/fivechess/white-play-select.png");
	
	//当前下棋前的选择图片
	private Image selectImage;
	
	//选中图片的X坐标
	private int selectImageX = 0;
	
	//选中图片的Y坐标
	private int selectImageY = 0;
	
	public GamePanel(Table table, ChessUser user) {
		this.table = table;
		this.user = user;
		this.setMaximumSize(new Dimension(710, 700));
		this.setMinimumSize(new Dimension(710, 700));
		this.currentToolImage = tool_begin;
	    this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseMove(e);
			}
	    });
	    this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mouseClick(e);
			}
	    });
	}
	
	//鼠标移动
	private void mouseMove(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//判断是否游戏状态
		if (this.gaming) {
			setCursor(this.drawRange.contains(x, y) || this.lostRange.contains(x, y));
			//判断是否轮到自己下棋
			if (this.myTurn) {
				//判断鼠标指针是否在棋盘上
				if (this.chessBoardRange.contains(x, y)) {
					this.selectImageX = x - Chess.IMAGE_WIDTH / 2;
					this.selectImageY = y - Chess.IMAGE_HEIGHT / 2;
					this.repaint();
				}
			}
		} else {
			//游戏没有开始, 判断玩家是否已经准备
			if (!this.user.isReady()) setCursor(this.startRange.contains(x, y));
			else setCursor(false);
		}

	}
	
	//获得当前x, y坐标对应的Chess对象
	private Chess getSelectChess(int x, int y) {
		for (int i = 0; i < this.chessArray.length; i++) {
			for (int j = 0; j < this.chessArray[i].length; j++) {
				Chess c = this.chessArray[i][j];
				if (c.getRange().contains(x, y)) {
					return c;
				}
			}
		}
		return null;
	}
	
	//根据状态设置鼠标指针
	private void setCursor(boolean state) {
		if (state) setCursor(HAND_CURSOR);
		else setCursor(DEFAULT_CURSOR);
	}
	
	private void mouseClick(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (this.toolRange.contains(x, y)) {
			if (this.gaming) {
				//游戏已经开始
				//判断是否点击了求和
				if (this.drawRange.contains(x, y)) requestDraw();
				//判断是否点击了认输
				if (this.lostRange.contains(x, y)) requestLost();
			} else {
				//游戏没有开始
				//判断点击位置
				if (this.startRange.contains(x, y)) ready();
			}
		}
		if (this.gaming) {
			//判断是否轮到自己下棋
			if (this.myTurn) {
				//判断是否鼠标点击了棋盘
				if (this.chessBoardRange.contains(x, y)) takeChess(x, y);
			}
		}
	}
	
	//下棋的方法
	private void takeChess(int x, int y) {
		Chess chess = getSelectChess(x, y);
		if (chess != null) {
			//当前位置有棋子
			if (chess.getColor() != null) {
				UIContext.showMessage("该位置已经有棋子");
			} else {
				chess.setColor(getChessColor());
				this.myTurn = false;
				this.selectImage = null;
				requestTakeChess(chess);
				this.repaint();
			}
		}
	}
	
	//告诉服务器自己下棋了
	private void requestTakeChess(Chess chess) {
		Request request = new Request(
			"org.crazyit.gamehall.fivechess.server.action.TakeChessAction", 
			"org.crazyit.gamehall.fivechess.client.action.game.TakeChessAction");
		request.setParameter("i", chess.getI());
		request.setParameter("j", chess.getJ());
		request.setParameter("userId", this.user.getId());
		request.setParameter("tableNumber", this.table.getTableNumber());
		request.setParameter("color", chess.getColor());
		//设置处理胜利的Action
		request.setParameter("winAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.WinAction");
		//设置处理输的Action
		request.setParameter("lostAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.LostAction");
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	//胜利调用的方法
	public void win() {
		endGame();
		UIContext.showMessage("你赢了");
	}
	
	//失败调用的方法
	public void lost() {
		endGame();
		UIContext.showMessage("你输了");
	}
	
	//结束游戏
	private void endGame() {
		this.selectImage = null;
		this.gaming = false;
		this.myTurn = false;
		this.user.setReady(false);
		this.currentToolImage = tool_begin;
		ChessUser opponent = getOtherChessUser();
		if (opponent != null) {
			opponent.setReady(false);
		}
		this.repaint();
	}
 	
	//玩家准备游戏
	private void ready() {
		if (this.user.isReady()) return;
		this.user.setReady(true);
		this.currentToolImage = tool_ready;
		this.repaint();
		//发送信息给服务器, 告诉服务器已经准备好了
		Request request = new Request("org.crazyit.gamehall.fivechess.server.action.ReadyAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.StartGameAction");
		request.setParameter("userId", this.user.getId());
		request.setParameter("tableNumber", this.table.getTableNumber());
		//如果对手没有准备, 则设置对手的处理类
		request.setParameter("opponentAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.OpponentReadyAction");
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	//显示开始图片
	public void showStartImage() {
		if (this.startImageY >= 330) {
			this.timer.cancel();
			this.timer = null;
			this.gameStartImage = null;
			this.startImageY = 230;
		}
		this.startImageY += 2;
		this.repaint();
	}
	
	//请求求和
	private void requestDraw() {
		int result = UIContext.showConfirm("你确定要求和吗?");
		if (result == 0) {
			Request request = new Request(
					"org.crazyit.gamehall.fivechess.server.action.DrawAction", 
			"org.crazyit.gamehall.fivechess.client.action.game.DrawAction");
			request.setParameter("userId", this.user.getId());
			request.setParameter("tableNumber", this.table.getTableNumber());
			this.user.getPrintStream().println(XStreamUtil.toXML(request));
		} else {
			return;
		}
	}
	
	//发送认输的请求
	public void sendLostRequest() {
		Request request = new Request(
		"org.crazyit.gamehall.fivechess.server.action.LostAction", 
		"org.crazyit.gamehall.fivechess.client.action.game.OpponentLostAction");
		request.setParameter("userId", this.user.getId());
		request.setParameter("tableNumber", this.table.getTableNumber());
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	//接收到对手求和信息的处理方法
	public void opponentRequestDraw(String userName) {
		int result = UIContext.showConfirm(userName + " 向你求和, 是否同意?");
		if (result == 0) agreeDraw();
		if (result == 1) refuseDraw();
	}
	
	//对手拒绝求和
	public void handleRefuseDraw(String userName) {
		UIContext.showMessage("对方拒绝你的求和请求");
	}
	
	//拒绝求和
	private void refuseDraw() {
		//告诉服务器拒绝和棋
		Request request = new Request("org.crazyit.gamehall.fivechess.server.action.RefuseDrawAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.RefuseDrawAction");
		request.setParameter("userId", this.user.getId());
		request.setParameter("tableNumber", this.table.getTableNumber());
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	//同意求和
	private void agreeDraw() {
		draw();
		//告诉服务器同意求和
		Request request = new Request("org.crazyit.gamehall.fivechess.server.action.AgreeDrawAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.AgreeDrawAction");
		request.setParameter("userId", this.user.getId());
		request.setParameter("tableNumber", this.table.getTableNumber());
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	//双方同意和棋后的处理
	public void draw() {
		UIContext.showMessage("和棋");
		endGame();
	}
	
	//请求认输
	private void requestLost() {
		int result = UIContext.showConfirm("确定是否要认输");
		if (result == 0) {
			lost();
			sendLostRequest();
		}
	}
	
	//对方认输了
	public void opponentLost() {
		win();
	}
	
	public Table getTable() {
		return this.table;
	}
	
	//开始游戏的图片Y坐标
	private int startImageY = 250;
	
	//设置游戏状态
	public void startGame() {
		this.gaming = true;
		this.currentToolImage = tool_drawAndLost;
		//设置开始游戏的状态
		if (getUserSide().equals(Seat.LEFT)) {//自己先下棋
			this.myTurn = true;
			this.gameStartImage = ImageUtil.getImage("images/fivechess/start-game-you-first.gif");
		} else {//对手先下棋
			this.gameStartImage = ImageUtil.getImage("images/fivechess/start-game-opponent-first.gif");
		}
		this.selectImage = getSelectImage();
		this.startGameTask = new StartGameTask(this);
		this.timer = new Timer();
		timer.schedule(this.startGameTask, 0, 20);
	}
	
	//轮到自己下棋
	public void myTurn() {
		this.selectImage = getSelectImage();
		this.myTurn = true;
		this.repaint();
	}
	
	//返回选中棋子的图片
	private Image getSelectImage() {
		String side = getUserSide();
		if (side.equals(Seat.LEFT)) return BLACK_SELECT_PLAY_IMAGE;
		else return WHITE_SELECT_PLAY_IMAGE;
	}
	
	//获得当前玩家的棋子颜色
	private String getChessColor() {
		String side = getUserSide();
		if (side.equals(Seat.LEFT)) return Chess.BLACK;
		else return Chess.WHITE;
	}
	
	//获得当前玩家的左右位置
	private String getUserSide() {
		Seat seat = this.table.getUserSeat(this.user);
		return seat.getSide();
	}
	
	/**
	 * 返回对手
	 * @return
	 */
	public ChessUser getOtherChessUser() {
		Seat seat = this.table.getUserSeat(this.user);
		Seat otherSeat = this.table.getAnotherSeat(seat);
		return otherSeat.getUser();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(chessboard, 85, 80, this);
		ChessUser lu = this.table.getLeftSeat().getUser();
		ChessUser ru = this.table.getRightSeat().getUser();
		//设置左边玩家的头像
		this.leftUserHead = getUserHead(this.leftUserHead, lu);
		//设置右边玩家的头像
		this.rightUserHead = getUserHead(this.rightUserHead, ru);
		//画头像
		g.drawImage(this.leftUserHead, 30, 300, this);
		g.drawImage(this.rightUserHead, 645, 300, this);
		//画左边的玩家
		drawLeftUser(g, lu);
		//画右边的玩家
		drawRightUser(g, ru);
		//画工具栏
		g.drawImage(this.currentToolImage, 160, 630, this);
		//游戏开始的一瞬间画开始图片
		g.drawImage(this.gameStartImage, 208, this.startImageY, this);
		//画棋盘的棋子
		paintChessBoard(g);
		//判断是否自己下棋
		if (this.myTurn) {
			g.drawImage(this.selectImage, selectImageX, selectImageY, this);
		}
	}
	
	//根据棋盘数组画棋盘的棋子
	private void paintChessBoard(Graphics g) {
		if (this.chessArray != null) {
			for (int i = 0; i < this.chessArray.length; i++) {
				for (int j = 0; j < this.chessArray[i].length; j++) {
					Chess chess = this.chessArray[i][j];
					if (chess.getColor() != null) {
						g.drawImage(getChessImage(chess.getColor()), 
								chess.getBeginX(), chess.getBeginY(), null);
					}
				}
			}
		}
	}
	
	//返回棋子图片
	private Image getChessImage(String color) {
		if (color.equals(Chess.BLACK)) return BLACK_CHESS;
		else return WHITE_CHESS;
	}
	
	//画左边的玩家
	private void drawLeftUser(Graphics g, ChessUser lu) {
		if (lu != null) {
			g.drawString(lu.getName(), 30, 360);//画名字
			if (!this.gaming) {//非游戏中才画准备图片
				if (lu.isReady()) g.drawImage(ready, 5,360, this);//准备图片
			}
			g.drawImage(BLACK_DISK, 25, 230, this);//画黑白棋图标
		}
	}
	
	//画右边的玩家
	private void drawRightUser(Graphics g, ChessUser ru) {
		if (ru != null) {
			g.drawString(ru.getName(), 645, 360);//画名字
			if (!this.gaming) {//非游戏中才画准备图片
				if (ru.isReady()) g.drawImage(ready, 630,360, this);//准备图片	
			}
			g.drawImage(WHITE_DISK, 640, 230, this);
		}
	}
	
	//获得玩家的头像图片, 由于User对象中保存的只是一个图片URL
	private Image getUserHead(Image userHead, ChessUser user) {
		//判断座位是否有玩家
		if (user != null) {
			//判断是否已经初始化头像图片, 保证不会多次读取
			if (userHead == null) {
				userHead = ImageUtil.getImage(user.getHeadImage());
			}
		} else {
			//座位没有玩家, 则设置图片为空
			userHead = null;
		}
		return userHead;
	}
	
	public void setChessArray(Chess[][] chessArray) {
		this.chessArray = chessArray;
	}
	
	public Chess[][] getChessArray() {
		return this.chessArray;
	}
	
	public boolean isGaming() {
		return this.gaming;
	}
}

class StartGameTask extends TimerTask {

	private GamePanel gamePanel;
	
	public StartGameTask(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void run() {
		this.gamePanel.showStartImage();
	}
	
}
