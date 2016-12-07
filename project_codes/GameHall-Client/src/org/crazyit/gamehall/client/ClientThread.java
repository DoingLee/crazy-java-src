package org.crazyit.gamehall.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 客户端引擎线程, 处理客户端接收服务器响应
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ClientThread extends Thread {

	private User user;
	
	private String line;
	
	private Map<String, ClientAction> actions = new HashMap<String, ClientAction>();
	
	public ClientThread(User user) {
		this.user = user;
	}
	
	public void run() {
		try {
			InputStream is = this.user.getSocket().getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((this.line = br.readLine()) != null) {
				Response response = getResponse(this.line);
				//得到客户端的处理类
				ClientAction action = getClientAction(response.getActionClass());
				//执行客户端处理类
				action.execute(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//得到服务器响应中的客户端处理类
	private ClientAction getClientAction(String className) {
		try {
			if (actions.get(className) == null) {
				ClientAction action = (ClientAction)Class.forName(className).newInstance();
				actions.put(className, action);
			}
			return actions.get(className);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//将服务器响应的xml转换成Response对象
	private Response getResponse(String xml) {
		return (Response)XStreamUtil.fromXML(xml);
	}
}
