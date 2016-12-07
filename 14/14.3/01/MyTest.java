

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
public class MyTest
{
	// 使用@Testable标记注释指定该方法是可测试的
	@Testable
	public static void m1() 
	{
	}
	public static void m2() 
	{
	}
	// 使用@Testable标记注释指定该方法是可测试的
	@Testable
	public static void m3() 
	{
		throw new RuntimeException("Boom");  
	}
	public static void m4()
	{
	}
	// 使用@Testable标记注释指定该方法是可测试的
	@Testable
	public static void m5()
	{
	}
	public static void m6()
	{
	}
	// 使用@Testable标记注释指定该方法是可测试的
	@Testable
	public static void m7()
	{
		throw new RuntimeException("Crash");   
	}
	public static void m8()
	{
	}
}
