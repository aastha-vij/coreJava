package coreJava;

public class _018_FunctionOverriding extends _015_InheritanceParent{
	
	/* 1. Overriding: Args, Name, signature is same
	 * 2. Methods lie in different classes (extends)
	 */
	
	public void audio() {
		System.out.println("New audio");
	}

	public static void main(String[] args) {
		_015_InheritanceParent obj = new _018_FunctionOverriding();
		obj.audio();
		
		_015_InheritanceParent obj2 = new _015_InheritanceParent();
		obj2.audio();
	}

}
