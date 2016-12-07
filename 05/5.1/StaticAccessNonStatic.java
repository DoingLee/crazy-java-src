

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
public class StaticAccessNonStatic
{
	public void info()
	{
		System.out.println("简单的info方法");
	}
	public static void main(String[] args) 
	{
		//因为main方法是静态方法，而info是非静态方法，
		//调用main方法的是该类本身，而不是该类的实例，
		//因此省略的this无法指向有效的对象
		info();
	}
}
