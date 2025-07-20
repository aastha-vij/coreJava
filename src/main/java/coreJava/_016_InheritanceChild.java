package coreJava;

public class _016_InheritanceChild extends _015_InheritanceParent{
	
	int i = 20;
	public void color() {
		System.out.println(color);
	}
	
	public static void main(String[] args) {
		
		_016_InheritanceChild obj = new _016_InheritanceChild();

		obj.gear();
		obj.engine();
		obj.color();
		
		System.out.println(obj.i);
		
		_015_InheritanceParent obj2 = new _015_InheritanceParent();
		System.out.println(obj2.i);
		
		_015_InheritanceParent obj3 = new _016_InheritanceChild();
		System.out.println(obj3.i);

	}


}
