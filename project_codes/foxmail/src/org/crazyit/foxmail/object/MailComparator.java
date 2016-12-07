package org.crazyit.foxmail.object;

import java.util.Comparator;
import java.util.Date;

/**
 * 邮件排序类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MailComparator implements Comparator {
	//实现compare方法
	public int compare(Object o1, Object o2) {
		try {
			//将参数强转为Mail对象
			Mail m1 = (Mail)o1;
			Mail m2 = (Mail)o2;
			//获得两个日期进行比较
			Date d1 = m1.getReceiveDate();
			Date d2 = m2.getReceiveDate();
			return d2.compareTo(d1);
		} catch (Exception e) {
			return -1;
		}
	}

}
