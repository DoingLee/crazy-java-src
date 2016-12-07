package org.crazyit.cal;

import java.math.BigDecimal;

/**
 * 四则运算类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class MyMath {
	// 保留小数点位数
	public static final int DEFAULT_SCALE = 20;

	/**
	 * 加法
	 * 
	 * @param num1
	 *            double
	 * @param num2
	 *            double
	 * @param reutrn
	 *            double 两者相加的结果
	 */
	public static double add(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.add(second).doubleValue();
	}
	
	/**
	 * 为一个数字创建BigDecimal对象
	 * @param number
	 * @return
	 */
	private static BigDecimal getBigDecimal(double number) {
		return new BigDecimal(number);
	}

	/**
	 * 减法
	 * 
	 * @param num1
	 *            double
	 * @param num2
	 *            double
	 * @param reutrn
	 *            double 两者相减的结果
	 */
	public static double subtract(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.subtract(second).doubleValue();
	}

	/**
	 * 乘法
	 * 
	 * @param num1
	 *            double
	 * @param num2
	 *            double
	 * @param reutrn
	 *            double 两者相乘的结果
	 */
	public static double multiply(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.multiply(second).doubleValue();
	}

	/**
	 * 除法
	 * 
	 * @param num1
	 *            double
	 * @param num2
	 *            double
	 * @param reutrn
	 *            double 两者相除的结果
	 */
	public static double divide(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.divide(second, DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

}