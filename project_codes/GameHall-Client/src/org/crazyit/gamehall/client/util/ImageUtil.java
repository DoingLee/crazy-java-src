package org.crazyit.gamehall.client.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.crazyit.gamehall.client.exception.ClientException;

/**
 * 图片工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageUtil {

	//头像图片存放目录
	public final static String HEAD_FOLDER = "images/heads";
	
	//返回一个Map, key是文件的路径
	public static Map<String, ImageIcon> getHeads() {
		try {
			File folder = new File(HEAD_FOLDER);
			File[] files = folder.listFiles();
			Map<String, ImageIcon> heads = new HashMap<String, ImageIcon>();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				heads.put(file.getPath(), new ImageIcon(file.getAbsolutePath()));
			}
			return heads;
		} catch (Exception e) {
			throw new ClientException("读取头像文件出错");
		}
	}
}
