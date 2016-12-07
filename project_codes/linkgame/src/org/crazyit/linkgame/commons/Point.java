package org.crazyit.linkgame.commons;

/**
 * 连接点对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Point {
	// 记录这个点对象的x坐标
	private int x;

	// 记录这个点对象的y坐标
	private int y;

	// 提供构造器
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 这里需要重写Object的equals方法, 用于判断两个点是否为同一个
	public boolean equals(Object object) {
		// 如果object是Point类型
		if (object instanceof Point) {
			// 将参数强制转成Point对象
			Point p = (Point) object;
			// 当两个Point对象的x坐标和y坐标同时相等的时候, 表示它们是同一个点
			return (p.getX() == this.x && p.getY() == this.y) ? true : false;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
