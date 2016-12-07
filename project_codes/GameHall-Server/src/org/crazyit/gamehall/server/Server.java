package org.crazyit.gamehall.server;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.crazyit.gamehall.server.exception.ServerException;

/**
 * 服务器对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Server {

	ServerSocket serverSocket;
	
	public Server() {
		try {
			//创建ServerSocket对象, 端口为12000
			this.serverSocket = new ServerSocket(12000);
			while(true) {
				//监听Socket的连接
				Socket s = this.serverSocket.accept();
				//启动服务器线程
				new ServerThread(s).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("创建服务器异常: " + e.getMessage());
		}
	}

}
