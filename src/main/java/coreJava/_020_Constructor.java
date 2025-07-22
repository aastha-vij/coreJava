package coreJava;

public class _020_Constructor {
	
	public _020_Constructor(){
		
		/* Block of code when object is created
		 * Does not return any value
		 * Constructor Name is always same as Class Name
		 */
		
		System.out.println("Constructor");
	}
	

	public void getData() {
		System.out.println("Method getData");
	}
	
	
	public static void main(String[] args) {

		_020_Constructor obj  = new _020_Constructor(); // When object is created, constructor is called
	}
}
