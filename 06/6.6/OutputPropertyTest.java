
package yeeku;
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

public class OutputPropertyTest
{
	public static void main(String[] args) 
	{
		//访问另一个包中的Output接口的MAX_CACHE_LINE
		System.out.println(lee.Output.MAX_CACHE_LINE);
		//下面语句将引起"为final变量赋值"的编译异常
		//lee.Output.MAX_CACHE_LINE = 20;
	}
}
