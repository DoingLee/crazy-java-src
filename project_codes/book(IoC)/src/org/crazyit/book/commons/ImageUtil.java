package org.crazyit.book.commons;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	//压缩图片的最大宽
	public final static int MAX_WIDTH = 220;
	//压缩图片的最大高
	public final static int MAX_HEIGHT = 240;
	/** 
	  * 经过原图片的压缩后, 生成一张新的图片 
	  * @param imageFile 图片文件
	  * @param url 原图相对路径
	  * @param formatName 生成图片的格式
	  * @param compress 是否进行压缩
	  * @return 生成的图片文件
	  */
	public static File makeImage(File imageFile, String url, String formatName, 
			boolean compress) {
		try {
			File output = new File(url);
			//读取图片
			BufferedImage bi = ImageIO.read(imageFile);
			//如果不进行压缩, 直接写入文件并返回
			if (!compress) {
				ImageIO.write(bi, formatName, new FileOutputStream(output));
				return output;
			}
			Image newImage = getImage(bi);
			//获取压缩后图片的高和宽
			int height = newImage.getHeight(null);
			int width = newImage.getWidth(null);
			//以新的高和宽构造一个新的缓存图片
			BufferedImage bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi2.getGraphics();
			//在新的缓存图片中画图
			g.drawImage(newImage, 0, 0, null);
			//输出到文件
			ImageIO.write(bi2, formatName, new FileOutputStream(output));
			return output;
		} catch (IOException e) {
			throw new UploadException("上传文件异常");
		}
	}
	
	//返回一个Image对象，当参数bi的宽比高大的时候，使用最大的宽来压缩图片。
	//当参数bi的高比宽大的时候，使用最大的高来压缩图片
	private static Image getImage(BufferedImage bi) {
		if (bi.getWidth() > bi.getHeight()) {
			return bi.getScaledInstance(MAX_WIDTH, -10, Image.SCALE_AREA_AVERAGING);
		} else {
			return bi.getScaledInstance(-10, MAX_HEIGHT, Image.SCALE_AREA_AVERAGING);
		}
	}
	
	//生成uuid，作为上传的文件名
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
