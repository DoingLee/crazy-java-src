package org.crazyit.gamehall.fivechess.client.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.fivechess.client.object.GameHallInfo;
import org.crazyit.gamehall.fivechess.client.ui.game.ChessFrame;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 游戏大厅的JFrame
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameHallFrame extends JFrame {

	private HallPanel hallPanel;
	
	private ChatPanel chatPanel;
	
	private UserTable userTable;
	
	//当前界面的玩家
	private ChessUser user;
	
	//大厅聊天的服务器处理类
	private final static String CHAT_SERVER_ACTION = "org.crazyit.gamehall.fivechess.server.action.SendMessageAction";
	
	//大厅聊天的客户端处理类
	private final static String CHAT_CLIENT_ACTION = "org.crazyit.gamehall.fivechess.client.action.ReceiveMessageAction";
	
	/**
	 * 构造一个游戏大厅主界面对象
	 * @param gameHallInfo 游戏大厅的信息, 包括桌子
	 * @param user
	 * @param users
	 */
	public GameHallFrame(GameHallInfo gameHallInfo, ChessUser user, 
			List<ChessUser> users) {
		this.user = user;
		//从用户集合中去掉当前用户, 将自己从集合中删除
		sortUsers(users);
		//创建游戏大厅的JPanel
		this.hallPanel = new HallPanel(gameHallInfo, this.user, users);
		//注意这里ChatPanel的后两个参数
		this.chatPanel = new ChatPanel(users, user, CHAT_SERVER_ACTION, 
				CHAT_CLIENT_ACTION);
		//创建用户信息列表的JPanel
		this.userTable = new UserTable(users, this.user);
		JScrollPane hallScrollPane = new JScrollPane(this.hallPanel);
		hallScrollPane.setMinimumSize(new Dimension(750, 1024 - 305));
		JScrollPane chatScrollPane = new JScrollPane(this.chatPanel);
		chatScrollPane.setMinimumSize(new Dimension(300, 490));
		JScrollPane userScrollPane = new JScrollPane(this.userTable);
		userScrollPane.setMinimumSize(new Dimension(300, 300));
		//创建右边的上下分隔
		JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userScrollPane, chatScrollPane);
		rightPane.setMinimumSize(new Dimension(300, 400));
		rightPane.setDividerLocation(768 - 550);
		rightPane.setDividerSize(3);
		//创建整个界面的左右分隔
		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, hallScrollPane, 
				rightPane);
		mainPane.setDividerSize(3);
		mainPane.setDividerLocation(1024 - 305);
		this.add(mainPane);
		//设置不可改变大小
		this.setResizable(false);
		int perfectWidth = 1024;
		int perfectHeight = 768 - 30;
		this.setPreferredSize(new Dimension(perfectWidth, perfectHeight));
		this.pack();
		this.setTitle("五子棋游戏大厅");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setVisible(true);
		initUIContext();
	}
	
	//将自己提到用户列表的最前面
	private void sortUsers(List<ChessUser> users) {
		if (this.user == null) return;
		Iterator it = users.iterator(); 
		while (it.hasNext()) {
			ChessUser u = (ChessUser)it.next();
			if (u.getId().equals(this.user.getId())) it.remove();
		}
	}
	
	//初始化UIContext类
	private void initUIContext() {
		UIContext.modules.put(UIContext.HALL_PANEL, this.hallPanel);
		UIContext.modules.put(UIContext.HALL_CHAT_PANEL, this.chatPanel);
		UIContext.modules.put(UIContext.HALL_USER_TABLE, this.userTable);
		UIContext.modules.put(UIContext.HALL_FRAME, this);
	}
	
	//向服务器发送用户进入大厅的请求
	public void sendUserIn() {
		//构造一次请求, 告诉服务器用户进入大厅, 服务器响应处理类是ReceiveInAction
		Request req = new Request("org.crazyit.gamehall.fivechess.server.action.NewUserInAction", 
				"org.crazyit.gamehall.fivechess.client.action.ReceiveInAction");
		req.setParameter("userId", this.user.getId());
		//得到用户的Socket并发送请求, 告诉服务器用户进入了大厅
		this.user.getPrintStream().println(XStreamUtil.toXML(req));
	}
	
	public static void main(String[] args) {
		int TABLE_COLUMN_SIZE = 5;
		int TABLE_ROW_SIZE = 10;
		//初始化桌子信息
		Table[][] tables = new Table[TABLE_COLUMN_SIZE][TABLE_ROW_SIZE];
		int tableNumber = 0;
		for (int i = 0; i < tables.length; i++) {
			for (int j = 0; j < tables[i].length; j++) {
				Table table = new Table(Table.DEFAULT_IMAGE_WIDTH*i, 
						Table.DEFAULT_IMAGE_HEIGHT*j, tableNumber);
				tables[i][j] = table;
				tableNumber++;
			}
		}
		List<ChessUser> users = new ArrayList<ChessUser>();
		ChessUser u1 = new ChessUser();
		u1.setId("abc");
		u1.setName("玩家1");
		u1.setSex(0);
		u1.setHeadImage("images/fivechess/heads/1.gif");
		
		ChessUser u2 = new ChessUser();
		u2.setId("abc");
		u2.setName("玩家2");
		u2.setSex(0);
		u2.setHeadImage("images/fivechess/heads/2.gif");
		
		users.add(u1);
		users.add(u2);
		
		ChessUser user = new ChessUser();
		user.setId("abc");
		user.setName("boxiong");
		user.setHeadImage("images/fivechess/heads/1.gif");
		GameHallFrame g = new GameHallFrame(new GameHallInfo(tables, 
				TABLE_COLUMN_SIZE, TABLE_ROW_SIZE), user, users);
	}

}
