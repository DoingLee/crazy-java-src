package org.crazyit.gamehall.fivechess.client.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	
	public static BufferedImage getImage(String imagePath) {
    	try {
    		//使用ImageIO读取图片
    		return ImageIO.read(new File(imagePath));
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
}
