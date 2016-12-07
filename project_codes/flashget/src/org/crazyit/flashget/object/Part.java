package org.crazyit.flashget.object;

import java.io.Serializable;
import java.util.UUID;

/**
 * 下载文件拆分的块 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Part implements Serializable {

	//下载的开始位置
	private int begin;
	//这个part文件的长度
	private int length;
	//已经下载的文件长度
	private int currentLength;
	//每个Part对应的文件
	private String partName;

	public Part(int begin, int length, int currentLength) {
		this.begin = begin;
		this.length = length;
		this.currentLength = currentLength;
		this.partName = UUID.randomUUID() + ".part";
	}
	
	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCurrentLength() {
		return currentLength;
	}

	public  void setCurrentLength(int currentLength) {
		this.currentLength = currentLength;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	
	
}
