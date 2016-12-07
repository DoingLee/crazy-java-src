package org.crazyit.ioc.context.object;

public class XmlApplicationContextObject2 {

	private String name;
	
	private int age;
	
	private XmlApplicationContextObject1 object1;

	public XmlApplicationContextObject2(String name, int age, 
			XmlApplicationContextObject1 object1) {
		this.name = name;
		this.age = age;
		this.object1 = object1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public XmlApplicationContextObject1 getObject1() {
		return object1;
	}

	public void setObject1(XmlApplicationContextObject1 object1) {
		this.object1 = object1;
	}
	

}
