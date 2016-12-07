package org.crazyit.ioc.context.object.interfaces;

public class TestImplement {

	private Interface1 interface1;

	public TestImplement(Interface1 interface1) {
		
	}
	
	public TestImplement(Interface1 in1, Interface2 in2) {
		
	}
	
	public Interface1 getInterface1() {
		return interface1;
	}

	public void setInterface1(Interface1 interface1) {
		this.interface1 = interface1;
	}
	
	public void setInterface1(Object obj) {
		
	}
}
