package org.crazyit.gamehall.chatroom.client.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.crazyit.gamehall.chatroom.client.ClientContext;
import org.crazyit.gamehall.chatroom.client.ui.MainFrame;
import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.User;

/**
 * 用户登录后启动聊天室
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class StartAction implements ClientAction {

	public void execute(Response response) {
		Map<String, User> usersMap = (Map<String, User>)response.getData("users");
		List<User> users = getUser(usersMap);
		MainFrame mainFrame = new MainFrame(ClientContext.user, users);
		ClientContext.mainFrame = mainFrame;
	}
	
	/**
	 * 将Map转换成List
	 * @param usersMap
	 * @return
	 */
	private List<User> getUser(Map<String, User> usersMap) {
		List<User> result = new ArrayList<User>();
		for (String key : usersMap.keySet()) {
			User user = usersMap.get(key);
			result.add(user);
		}
		return result;
	}

}
