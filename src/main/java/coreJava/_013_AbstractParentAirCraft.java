package coreJava;

public abstract class _013_AbstractParentAirCraft { 
	// class must contain 'abstract' keyword if there are any unimplemented methods

	public void engine() {
		System.out.println("Engine Guidelines");
	}
	
	public void safety() {
		System.out.println("Safety Guidelines");
	}
	
	public abstract void color(); // Partial Abstraction: Method with no Body 

}
