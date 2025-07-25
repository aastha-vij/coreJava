package coreJava;

public class _020_Constructor {
	
	public _020_Constructor() { 
		
		/* Block of code when object is created
		 * Does not return any value
		 * Constructor Name is always same as Class Name
		 */
		
		System.out.println("Default Constructor");
	}
	
	public _020_Constructor(int a, int b) { 
		int c = a+b;
		System.out.println("Parametarized Constructor: " + c);
	}

	public _020_Constructor(String a) { 
		System.out.println("Parametarized Constructor: " + a);
	}
	
	public void getData() {
		System.out.println("Method getData");
	}
	
	////////////////////////this Keyword///////////////////////
	
	int value = 5; // global variable
	
	public void getData(int value) {
		this.value= value; // 'this.value' refers to the instance variable
		System.out.println(value); // 'value' refers to the constructor parameter
	}
	
	public void getDataThis() {
		int value = 3; // local variable
		System.out.println(value); 
		System.out.println(this.value); //this refers to current object - scope is class level
		
		int globalLocalSum = value+ this.value;
		System.out.println("globalLocalSum: "+globalLocalSum);

	}
	
	///////////////////////////this Keyword///////////////////////
	
	public static void main(String[] args) {

		_020_Constructor obj  = new _020_Constructor(); // When object is created, constructor is called
		_020_Constructor obj2  = new _020_Constructor(5, 6); // Parametarized Constructor
		_020_Constructor obj3  = new _020_Constructor("Hello"); // Parametarized Constructor
		obj.getDataThis();
	}
}
