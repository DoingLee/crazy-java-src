package org.crazyit.image;

import javax.swing.JFrame;

/**
 * 
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>利嫋: <a href="http://www.crazyit.org">決髄Java選男</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Main {
	public static void main(String[] args) {
		ImageFrame f = new ImageFrame();
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}