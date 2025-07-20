package coreJava;

public class _016_InheritanceChild extends _015_InheritanceParent{
	
	public void color() {
		System.out.println(color);
	}
	
	public static void main(String[] args) {
		
		_016_InheritanceChild obj = new _016_InheritanceChild();

		obj.gear();
		obj.engine();
		obj.color();
	}


}
