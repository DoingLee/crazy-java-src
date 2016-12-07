

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class ThreeTest
{
	public static void main(String[] args) 
	{
		String str = 5 > 3 ? "5大于3" : "5不大于3";
		System.out.println(str);

		String str2 = null;
		if (5 > 3)
		{
			str2 = "5大于3";
		}
		else
		{
			str2 = "5不大于3";
		}
	}
}
