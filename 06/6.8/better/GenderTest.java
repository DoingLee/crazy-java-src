

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
public class GenderTest
{
	public static void main(String[] args) 
	{
		Gender g = Enum.valueOf(Gender.class , "FEMALE");
		g.setName("女");
		System.out.println(g + "代表:" + g.getName());
		//此时设置name值时将会提示参数错误。
		g.setName("男");
		System.out.println(g + "代表:" + g.getName());
	}
}
