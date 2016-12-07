
import java.util.*;
import java.text.*;
/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class NumberFormatTest
{
	public static void main(String[] args) 
	{
	//需要被格式化的数字
	double db = 1234000.567;
	//创建四个Locale，分别代表中国、日本、德国、美国
	Locale[] locales = {Locale.CHINA, Locale.JAPAN
		, Locale.GERMAN,  Locale.US};
	NumberFormat[] nf = new NumberFormat[12];
	//为上面四个Locale创建12个NumberFormat对象
	//每个Locale分别有通用数值格式器、百分比格式器、货币格式器
	for (int i = 0 ; i < locales.length ; i++)
	{
		nf[i * 3] = NumberFormat.getNumberInstance(locales[i]);
		nf[i * 3 + 1] = NumberFormat.getPercentInstance(locales[i]);
		nf[i * 3 + 2] = NumberFormat.getCurrencyInstance(locales[i]);
	}
	for (int i = 0 ; i < locales.length ; i++)
	{
		switch (i)
		{
			case 0:
				System.out.println("-------中国的格式--------");
				break;
			case 1:
				System.out.println("-------日本的格式--------");
				break;	
			case 2:
				System.out.println("-------德国的格式--------");
				break;
			case 3:
				System.out.println("-------美国的格式--------");
				break;
			}
			System.out.println("通用数值格式："
				+ nf[i * 3].format(db));
			System.out.println("百分比数值格式："
				+ nf[i * 3 + 1].format(db));
			System.out.println("货币数值格式："
				+ nf[i * 3 + 2].format(db));
		}
	}
}
