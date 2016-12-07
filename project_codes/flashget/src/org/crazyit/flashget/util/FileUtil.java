package org.crazyit.flashget.util;

import java.io.File;
import java.util.List;

import org.crazyit.flashget.object.Resource;
import org.crazyit.flashget.object.Part;

public class FileUtil {

	public final static File SERIALIZABLE_FILE = new File("serializable.txt");
	
	/**
	 * 删除该下载文件的.part文件, 
	 * @param file
	 */
	public static void deletePartFiles(Resource resource) {
		List<Part> parts = resource.getParts();
		for (Part part : parts) {
			File partFile = new File(getPartFilePath(resource, part));
			if (!partFile.exists()) continue;
			//只要有一份文件删除失败, 再递归删除, 直到可以删除为止
			if (partFile.delete() == false) {
				deletePartFiles(resource);
			}
		}
	}
	
	public static String getFileName(String address) {
		if (address.indexOf("/") != -1) {
			return address.substring(address.lastIndexOf("/") + 1, 
					address.length());
		}
		return null;
	}
	
	/**
	 * 得到.part文件的绝对路径
	 * @param resource
	 * @param part
	 * @return
	 */
	public static String getPartFilePath(Resource resource, Part part) {
		return resource.getFilePath() + File.separator + part.getPartName();
	}
}
