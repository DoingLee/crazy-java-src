package org.crazyit.book.vo;

import java.io.Serializable;

/**
 * 书本种类对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Type extends ValueObject {
	//名称
	private String TYPE_NAME;
	//简介
	private String TYPE_INTRO;

	public Type() {
	}
	
	public Type(String id, String type_name, String type_intro) {
		setID(id);
		TYPE_NAME = type_name;
		TYPE_INTRO = type_intro;
	}

	public String getTYPE_NAME() {
		return TYPE_NAME;
	}

	public void setTYPE_NAME(String type_name) {
		TYPE_NAME = type_name;
	}

	public String getTYPE_INTRO() {
		return TYPE_INTRO;
	}

	public void setTYPE_INTRO(String type_intro) {
		TYPE_INTRO = type_intro;
	}
	
}
