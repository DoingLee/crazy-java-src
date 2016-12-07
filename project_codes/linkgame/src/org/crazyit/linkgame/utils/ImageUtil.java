package org.crazyit.linkgame.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.crazyit.linkgame.commons.GameException;

import java.io.IOException;
import java.util.Random;


/**
 * 图片工具类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ImageUtil {

	/**
	 * 用于获取某个文件夹下面的所有图片
	 * 
	 * @param folder
	 *            目标文件夹
	 * @param subfix
	 *            图片后缀
	 * @return 返回图片集合，其元素为BufferedImage
	 * @throws IOException
	 *             如果读取不了图片或者读取图片出错抛出异常
	 */
	public static List<BufferedImage> getImages(File folder, String subfix)
			throws IOException {
		// 从目标文件夹中获取文件列表
		File[] items = folder.listFiles();
		// 创建结果集合对象
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		// 对文件列表进行遍历
		for (File file : items) {
			// 如果该文件符合指定的文件后缀, 则加到结果集中
			if (file.getName().endsWith(subfix)) {
				result.add(ImageIO.read(file));
			}
		}
		return result;
	}

	/**
	 * 随机从sourceImages的集合中获取size张图片
	 * 
	 * @param sourceImages
	 *            源图片的对象集合
	 * @param size
	 *            需要获取的图片数量
	 * @return 返回随机获取的图片集合
	 */
	public static List<BufferedImage> getRandomImages(
			List<BufferedImage> sourceImages, int size) {
		// 创建一个随机数生成器
		Random random = new Random();
		// 创建结果集合
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		for (int i = 0; i < size; i++) {
			try {
				// 随机获取一个数字，包括0，不包括源图片集合的size
				int index = random.nextInt(sourceImages.size());
				// 从源图片集合中获取该图片对象
				BufferedImage image = sourceImages.get(index);
				// 添加到结果集中
				result.add(image);
			} catch (IndexOutOfBoundsException e) {
				// 当源图片集合的size为0时，会发生数组越界，直接返回结果集
				return result;
			}

		}
		return result;
	}

	/**
	 * 随机打乱sourceImages
	 * 
	 * @param sourceImages
	 *            需要被打乱的图片集合
	 * @return
	 */
	public static List<BufferedImage> randomImages(
			List<BufferedImage> sourceImages) {
		// 创建一个随机数生成器
		Random random = new Random();
		// 创建一个存放数字的集合
		List<Integer> numbers = new ArrayList<Integer>();
		// 获取一个集合, 里面是一些被打乱的数字
		for (int i = 0; i < sourceImages.size(); i++) {
			// 随机创建一个数字，范围是0到参数sourceImage的size, 包括0不包括size
			Integer temp = random.nextInt(sourceImages.size());
			// 为了确保数字没有重复，如果该数字已经在存放数字的集合中，重新再获取一次数字
			if (!numbers.contains(temp)) {
				// 存放数字的集合中没有该随机数，添加集合中
				numbers.add(temp);
			} else {
				// 该数字已经存在于集合中，i - 1执行循环
				i--;
				continue;
			}
		}
		// 创建一个结果集合
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		// 对源图片集合进行遍历
		for (int i = 0; i < sourceImages.size(); i++) {
			// 从数字集合中获取已经被打乱的索引，源图片集合获取这个索引的值
			result.add(sourceImages.get(numbers.get(i)));
		}
		return result;
	}

	/**
	 * 从参数folder中读取size张，其中size为游戏数量，必须可以被2整除
	 * 
	 * @param folder
	 *            需要被打乱的图片集合
	 * @param size
	 *            需要被打乱的图片集合
	 * @return
	 */
	public static List<BufferedImage> getPlayImages(File folder, int size) {
		if (size % 2 != 0) {// 如果该数除2有余数，抛出运行时异常
			throw new GameException("image size error");
		}
		try {
			// 先从目标文件夹中获取全部以.gif结尾的图片
			List<BufferedImage> images = getImages(folder, ".gif");
			// 再从所有的图片中随机获取size的一半数量
			List<BufferedImage> playImages = getRandomImages(images, size / 2);
			// 将这上面的图片集合打乱，并构成另外一个图片集合
			List<BufferedImage> randomImages = randomImages(playImages);
			// 前面的图片加上后面打乱的图片，组成结果集
			playImages.addAll(randomImages);
			return playImages;
		} catch (IOException e) {
			// 读取图片出错，抛出自定义的运行时异常
			throw new GameException("read images error");
		}
	}

	public static BufferedImage getImage(String imagePath) {
		try {
			// 使用ImageIO读取图片
			return ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// 读取图片发生异常，抛出GameException
			throw new GameException("read image error");
		}
	}
}
