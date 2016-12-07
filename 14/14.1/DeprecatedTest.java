

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
class Apple
{
	// 定义info方法已过时
	@Deprecated
	public void info()
	{
		System.out.println("Apple的info方法");
	}
}
public class DeprecatedTest
{
	public static void main(String[] args) 
	{
		// 下面使用info方法时将会被编译器警告
		new Apple().info();
	}
}

