

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
class DataWrap
{
	public int a;
	public int b;
}
public class ReferenceTransferTest
{
	public static void swap(DataWrap dw)
	{
		//下面三行代码实现dw的a、b两个Field值交换。
		//定义一个临时变量来保存dw对象的a Field的值
		int tmp = dw.a;
		//把dw对象的b Field值赋给a Field
		dw.a = dw.b;
		//把临时变量tmp的值赋给dw对象的b Field
		dw.b = tmp;
		System.out.println("swap方法里，a Field的值是"
			+ dw.a + "；b Field的值是" + dw.b);
		//把dw直接赋为null，让它不再指向任何有效地址。
		dw = null;
	}
	public static void main(String[] args) 
	{
		DataWrap dw = new DataWrap();
		dw.a = 6;
		dw.b = 9;
		swap(dw);
		System.out.println("交换结束后，a Field的值是" 
			+ dw.a + "；b Field的值是" + dw.b);
	}
}
