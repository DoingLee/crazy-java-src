

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
public class A2 extends Apple
{
	// 重写父类的方法
	public String getInfo()
	{
		// super.getInfo()方法返回值是Object类型，
		// 所以加toString()才返回String类型
		return super.getInfo().toString();
	}
}

