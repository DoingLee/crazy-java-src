package org.crazyit.gamehall.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.crazyit.gamehall.commons.ErrorCode;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 服务器引擎线程, 用于处理服务器接受请求与作出响应
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ServerThread extends Thread {

	private Socket socket;
	
	private BufferedReader br;
	
	private String line;
	
	private PrintStream ps;

	//保存被创建的Action对象
	public Map<String, ServerAction> actions = new HashMap<String, ServerAction>();
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			while((this.line = br.readLine()) != null) {
				//得到请求对象
				Request request = getRequest(this.line);
				//从request中得到客户端处理类, 并且构造Response对象
				Response response = new Response(request.getClientActionClass());
				//将请求的参数都设置到Response中
				copyParameters(request, response);
				//如果字符串不能转换成Request对象, 则设置错误码并返回
				if (request == null) {
					response.setErrorCode(ErrorCode.REQUEST_ERROR);
					this.ps = new PrintStream(socket.getOutputStream());
					this.ps.println(getResponseXML(response));
					break;
				}
				//得到Server处理类
				ServerAction action = getAction(request.getServerActionClass());
				//如果找不到对应的Action, 返回错误信息, 找到则执行Action
				if (action == null) {
					response.setErrorCode(ErrorCode.COMMAND_NOT_FOUND);
					this.ps = new PrintStream(socket.getOutputStream());
					this.ps.println(getResponseXML(response));
				} else {
					action.execute(request, response, this.socket);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//将Request中的参数map设置到Response的data中
	private void copyParameters(Request request, Response response) {
		Map<String, Object> parameters = request.getParameters();
		for (String key : parameters.keySet()) {
			response.setData(key, parameters.get(key));
		}
	}
	
	//将一次服务器响应转换成XML字符串
	private String getResponseXML(Response response) {
		return XStreamUtil.toXML(response);
	}
	
	//将字符串转换成一个Request对象
	private Request getRequest(String xml) {
		try {
			Request r = (Request)XStreamUtil.fromXML(xml);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//从Map中得到Action对象, 如果拿不到, 则创建
	private ServerAction getAction(String className) {
		try {
			if (actions.get(className) == null) {
				ServerAction action = (ServerAction)Class.forName(className).newInstance();
				actions.put(className, action);
			}
			return actions.get(className);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
