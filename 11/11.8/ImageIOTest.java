
import javax.imageio.*;
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
public class ImageIOTest
{
	public static void main(String[] args) 
	{
		String[] readFormat = ImageIO.getReaderFormatNames();
		System.out.println("-----Image能读的所有图形文件格式-----");
		for (String tmp : readFormat)
		{
			System.out.println(tmp);
		}
		String[] writeFormat = ImageIO.getWriterFormatNames();
		System.out.println("-----Image能写的所有图形文件格式-----");
		for (String tmp : writeFormat)
		{
			System.out.println(tmp);
		}
	}
}
