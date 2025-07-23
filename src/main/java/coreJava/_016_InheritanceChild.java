package coreJava;

public class _016_InheritanceChild extends _015_InheritanceParent{
	
	int i = 20;
	public void color() {
		System.out.println(color);
	}
	
	///////////////////////// Super Keyword/////////////////////
	
	String name = "Child String name";
	
	public void getStringData() {
		System.out.println(name);
		System.out.println(super.name); // refers to parent class
	}
	
	public void gear() {
		super.gear();
		System.out.println("Gear implementation child class");
	}
	
	public _016_InheritanceChild() {
		super(); // super should always be at first line
		System.out.println("Child class constructor");
	}
	///////////////////////// Super Keyword/////////////////////

	public static void main(String[] args) {
		
		_016_InheritanceChild obj = new _016_InheritanceChild();

		obj.gear();
		obj.engine();
		obj.color();
		obj.gear();
		
		obj.getStringData();
		System.out.println(obj.i);
		
		_015_InheritanceParent obj2 = new _015_InheritanceParent();
		System.out.println(obj2.i);
		
		_015_InheritanceParent obj3 = new _016_InheritanceChild();
		System.out.println(obj3.i);

	}


}
