

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
public class ComparableOperatorTest
{
	public static void main(String[] args) 
	{
		//输出true
		System.out.println("5是否大于 4.0：" + (5 > 4.0));
		//输出true
		System.out.println("5和5.0是否相等：" + (5 == 5.0));
		//输出true
		System.out.println("97和'a'是否相等：" + (97 == 'a'));
		//输出false
		System.out.println("true和false是否相等：" + (true == false));
		//创建2个ComparableOperatorTest对象，分别赋给t1和t2两个引用
		ComparableOperatorTest t1 = new ComparableOperatorTest();
		ComparableOperatorTest t2 = new ComparableOperatorTest();
		//t1和t2是同一个类的两个实例的引用，所以可以比较，
		//但t1和t2引用不同的对象，所以返回false
		System.out.println("t1是否等于t2：" + (t1 == t2));
		//直接将t1的值赋给t3，即让t3指向t1指向的对象
		ComparableOperatorTest t3 = t1;
		//t1和t3指向同一个对象，所以返回true
		System.out.println("t1是否等于t3：" + (t1 == t3));




		//通过new调用构造器创建的两个String实例
		String aStr = new String("Hello");
		String bStr = new String("Hello");
		//输出false
		System.out.println("通过两个内容相同的字符串new出来的String实例是否相等：" 
			+ (aStr == bStr));
		//通过直接量赋值创建的两个String实例
		String cStr = "Hello";
		String dStr = "Hello";
		//输出true
		System.out.println("直接把两个内容相同的字符串赋给String变量是否相等：" 
			+ (cStr == dStr));
	}
}
