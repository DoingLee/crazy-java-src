
import java.util.*;
import java.text.*;
import static java.text.DateFormat.*;
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
public class DateFormatTest
{
	public static void main(String[] args) 
		throws ParseException
	{
		//需要被格式化的时间
		Date dt = new Date();
		//创建两个Locale，分别代表中国、美国
		Locale[] locales = {Locale.CHINA, Locale.US};
		DateFormat[] df = new DateFormat[16];
		//为上面两个Locale创建16个DateFormat对象
		for (int i = 0 ; i < locales.length ; i++)
		{
			df[i * 8] = DateFormat.getDateInstance(SHORT, locales[i]);
			df[i * 8 + 1] = DateFormat.getDateInstance(MEDIUM, locales[i]);
			df[i * 8 + 2] = DateFormat.getDateInstance(LONG, locales[i]);
			df[i * 8 + 3] = DateFormat.getDateInstance(FULL, locales[i]);
			df[i * 8 + 4] = DateFormat.getTimeInstance(SHORT, locales[i]);
			df[i * 8 + 5] = DateFormat.getTimeInstance(MEDIUM , locales[i]);
			df[i * 8 + 6] = DateFormat.getTimeInstance(LONG , locales[i]);
			df[i * 8 + 7] = DateFormat.getTimeInstance(FULL , locales[i]);
		}
		for (int i = 0 ; i < locales.length ; i++)
		{
			switch (i)
			{
				case 0:
					System.out.println("-------中国日期格式--------");
					break;
				case 1:
					System.out.println("-------美国日期格式--------");
					break;
			}
			System.out.println("SHORT格式的日期格式："
				+ df[i * 8].format(dt));
			System.out.println("MEDIUM格式的日期格式："
				+ df[i * 8 + 1].format(dt));
			System.out.println("LONG格式的日期格式："
				+ df[i * 8 + 2].format(dt));
			System.out.println("FULL格式的日期格式："
				+ df[i * 8 + 3].format(dt));
			System.out.println("SHORT格式的时间格式："
				+ df[i * 8 + 4].format(dt));
			System.out.println("MEDIUM格式的时间格式："
				+ df[i * 8 + 5].format(dt));
			System.out.println("LONG格式的时间格式："
				+ df[i * 8 + 6].format(dt));
			System.out.println("FULL格式的时间格式："
				+ df[i * 8 + 7].format(dt));
		}

		String str1 = "2007-12-12";
		String str2 = "2007年12月10日";
		//下面输出 Wed Dec 12 00:00:00 CST 2007
		System.out.println(DateFormat.getDateInstance().parse(str1));
		//下面输出 Mon Dec 10 00:00:00 CST 2007
		System.out.println(DateFormat.getDateInstance(LONG).parse(str2));
		//下面抛出 ParseException异常
		System.out.println(DateFormat.getDateInstance().parse(str2));

	}
}
