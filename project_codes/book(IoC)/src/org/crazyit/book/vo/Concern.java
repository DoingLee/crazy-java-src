package org.crazyit.book.vo;

public class Concern extends ValueObject {
	//出版社名称
	private String PUB_NAME;
	//出版社电话
	private String PUB_TEL;
	//联系人
	private String PUB_LINK_MAN;
	//简介
	private String PUB_INTRO;
	
	public Concern() {
		
	}

	public Concern(String id, String pub_name, String pub_tel, String pub_link_man,
			String pub_intro) {
		setID(id);
		PUB_NAME = pub_name;
		PUB_TEL = pub_tel;
		PUB_LINK_MAN = pub_link_man;
		PUB_INTRO = pub_intro;
	}

	public String getPUB_NAME() {
		return PUB_NAME;
	}

	public void setPUB_NAME(String pub_name) {
		PUB_NAME = pub_name;
	}

	public String getPUB_TEL() {
		return PUB_TEL;
	}

	public void setPUB_TEL(String pub_tel) {
		PUB_TEL = pub_tel;
	}

	public String getPUB_LINK_MAN() {
		return PUB_LINK_MAN;
	}

	public void setPUB_LINK_MAN(String pub_link_man) {
		PUB_LINK_MAN = pub_link_man;
	}

	public String getPUB_INTRO() {
		return PUB_INTRO;
	}

	public void setPUB_INTRO(String pub_intro) {
		PUB_INTRO = pub_intro;
	}
	
}
