package org.crazyit.gamehall.fivechess.client.ui;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * 客户端界面上下文, 保存所有UI界面对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UIContext {

	/**
	 * 存放界面所有的组件
	 */
	public static Map<String, Component> modules = new HashMap<String, Component>();
	
	//游戏大厅的Panel(HallPanel类)
	public final static String HALL_PANEL = "hallPanel";
	
	//游戏大厅中聊天的界面(ChatPanel类)
	public final static String HALL_CHAT_PANEL = "hallChatPanel";
	
	//游戏大厅中的玩家列表(UserTable类)
	public final static String HALL_USER_TABLE = "hallUserTable";
	
	//游戏大厅的整个容器(GameHallFrame类)
	public final static String HALL_FRAME = "hallFrame";
	
	//游戏中的玩家列表(UserTable类), 与HALL_USER_TABLE使用同一个类, 但在UIContext是两个实例
	public final static String GAME_USER_TABLE = "gameUserTable";
	
	//游戏中的聊天界面(ChatPanel类), 与HALL_CHAT_PANEL使用同一个类, 但在UIContext是两个实例
	public final static String GAME_CHAT_PANEL = "gameChatPanel";
	
	//游戏界面(GamePanel类)
	public final static String GAME_PANEL = "gamePanel";
	
	//整个游戏容器(GameFrame类)
	public final static String GAME_FRAME = "gameFrame";
	
	//用于显示错误信息
	public static int showMessage(String message) {
		return JOptionPane.showConfirmDialog(null, message, "提示", 
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	public static int showConfirm(String message) {
		return JOptionPane.showConfirmDialog(null, message, "询问", 
				JOptionPane.YES_NO_OPTION);
	}
}
