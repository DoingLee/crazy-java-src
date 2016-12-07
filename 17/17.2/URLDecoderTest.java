
import java.net.*;
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
public class URLDecoderTest
{
	public static void main(String[] args) 
		throws Exception
	{
		// 将application/x-www-form-urlencoded字符串
		// 转换成普通字符串
		// 其中的字符串直接从图17.3所示窗口复制过来
		String keyWord = URLDecoder.decode(
			"%B7%E8%BF%F1java", "GBK");
		System.out.println(keyWord);
		// 将普通字符串转换成
		// application/x-www-form-urlencoded字符串
		String urlStr = URLEncoder.encode(
			"疯狂Android讲义" , "GBK");
		System.out.println(urlStr);
	}
}
