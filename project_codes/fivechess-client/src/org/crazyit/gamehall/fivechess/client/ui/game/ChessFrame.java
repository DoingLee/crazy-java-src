package org.crazyit.gamehall.fivechess.client.ui.game;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.fivechess.client.ui.ChatPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.UserTable;
import org.crazyit.gamehall.fivechess.commons.Chess;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 五子棋游戏JFrame对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ChessFrame extends JFrame {
		
	//玩家列表
	private UserTable userTable;
	
	//聊天
	private ChatPanel chatPanel;
	
	//游戏界面
	private GamePanel gamePanel;
	
	private Table table;
	
	private ChessUser user;
	
	private List<ChessUser> users;
	
	//聊天服务器处理类
	private final static String CHAT_SERVER_ACTION = "org.crazyit.gamehall.fivechess.server.action.GameMessageAction";
	
	//聊天内容接收类
	private final static String CHAT_CLIENT_ACTION = "org.crazyit.gamehall.fivechess.client.action.game.ReceiveMessageAction";
	
	//table为桌子信息
	public ChessFrame(Table table, ChessUser user) {
		this.table = table;
		this.user = user;
		//游戏界面与聊天界面使用的集合
		this.users = getUsers(table, user);
		//创建玩家列表
		this.userTable = new UserTable(this.users, this.user);
		//创建聊天JPanel
		this.chatPanel = new ChatPanel(this.users, this.user, CHAT_SERVER_ACTION, 
				CHAT_CLIENT_ACTION);
		//创建游戏JPanel
		this.gamePanel = new GamePanel(this.table, this.user);
		//创建聊天界面
		JScrollPane chatScrollPane = new JScrollPane(this.chatPanel);
		chatScrollPane.setMinimumSize(new Dimension(300, 490));
		//创建玩家列表界面
		JScrollPane userScrollPane = new JScrollPane(this.userTable);
		userScrollPane.setMinimumSize(new Dimension(300, 300));
		JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userScrollPane, chatScrollPane);
		rightPane.setMinimumSize(new Dimension(300, 400));
		//创建主分割界面
		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				this.gamePanel, rightPane);
		this.add(mainPane);
		mainPane.setDividerLocation(1024 - 315);
		mainPane.setDividerSize(3);
		rightPane.setDividerLocation(768 - 550);
		rightPane.setDividerSize(3);
		//设置不可改变大小
		this.setResizable(false);
		int perfectWidth = 1024;
		int perfectHeight = 768 - 30;
		this.setPreferredSize(new Dimension(perfectWidth, perfectHeight));
		this.pack();
		initUIContext();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (gamePanel.isGaming()) {
					//正在游戏中退出
					int result = UIContext.showConfirm("你是否要退出游戏吗?");
					if (result == 1) {
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					} else {
						setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						//向服务器发送认输请求
						gamePanel.sendLostRequest();
						leave();
					}
				} else {
					leave();
				}
			}
		});
	}
	
	//向玩家集合中添加一个新玩家, 表示有新玩家进入
	public void newUserIn(ChessUser user) {
		this.users.add(user);
		refreshUI();
	}
	
	//玩家退出游戏
	public void userExit(ChessUser user) {
		//从集合中去掉当前玩家
		for (Iterator it = this.users.iterator(); it.hasNext();) {
			ChessUser u = (ChessUser)it.next();
			if (u.getId().equals(user.getId())) {
				it.remove();
			}
		}
		refreshUI();
	}
	
	//刷新界面组件
	public void refreshUI() {
		this.gamePanel.repaint();
		this.chatPanel.refreshJComboBox();
		this.userTable.refresh();
	}
	

	//玩家离开游戏, 向服务器发送请求
	public void leave() {
		int tableNumber = this.table.getTableNumber();
		String userId = this.user.getId();
		Request request = new Request("org.crazyit.gamehall.fivechess.server.action.LeaveGameAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.LeaveGameAction");
		request.setParameter("userId", userId);
		request.setParameter("tableNumber", tableNumber);
		//设置对手接收到退出后的处理类
		request.setParameter("exitAction", 
				"org.crazyit.gamehall.fivechess.client.action.game.OpponentExitAction");
		destroyUI();
		//设置玩家准备状态
		this.user.setReady(false);
		this.user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	//从桌子对象中得到玩家信息(不包括当前玩家)
	private List<ChessUser> getUsers(Table table, ChessUser user) {
		List<ChessUser> users = new ArrayList<ChessUser>();
		ChessUser u1 = table.getLeftSeat().getUser();
		ChessUser u2 = table.getRightSeat().getUser();
		if (u1 != null) users.add(u1);
		if (u2 != null) users.add(u2);
		//从集合中去掉当前玩家
		for (Iterator it = users.iterator(); it.hasNext();) {
			ChessUser u = (ChessUser)it.next();
			if (u.getId().equals(user.getId())) {
				it.remove();
			}
		}
		return users;
	}
	
	//将界面组件(只有游戏界面组件)加入到界面组件上下文中
	private void initUIContext() {
		UIContext.modules.put(UIContext.GAME_CHAT_PANEL, this.chatPanel);
		UIContext.modules.put(UIContext.GAME_USER_TABLE, this.userTable);
		UIContext.modules.put(UIContext.GAME_PANEL, this.gamePanel);
		UIContext.modules.put(UIContext.GAME_FRAME, this);
	}
	
	//从UIContext中销毁游戏界面的相关对象
	private void destroyUI() {
		UIContext.modules.remove(UIContext.GAME_CHAT_PANEL);
		UIContext.modules.remove(UIContext.GAME_USER_TABLE);
		UIContext.modules.remove(UIContext.GAME_PANEL);
		UIContext.modules.remove(UIContext.GAME_FRAME);
	}
	
}
