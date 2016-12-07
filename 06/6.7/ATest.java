

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
interface A
{
	void test();
}
public class ATest
{
	public static void main(String[] args) 
	{
		int age = 0;
		A a = new A()
		{
			public void test()
			{
				//下面语句将提示错误：
				//匿名内部类内访问局部变量必须使用final修饰
				System.out.println(age);
			}
		};
	}
}
