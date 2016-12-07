package org.crazyit.ioc.context.object;

public class BeanCreatorObject2 {

	private String name;
	
	private String value;

	public BeanCreatorObject2(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public BeanCreatorObject2(int a, String v) {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
