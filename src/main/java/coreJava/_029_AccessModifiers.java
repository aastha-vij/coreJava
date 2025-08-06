package coreJava;

final class _029_AccessModifiers { 

	// Default: Access anywhere only in package 
	void getData() { 
	}
	
	int a = 0;
	
	// Public: Access anywhere inside/outside package 
	public void getData(int a) { 
	}
	
	public int b = 0;
	
	// Private: Can't even access outside class
	private void getData(int a, int b) { 
	}
	
	private int c = 0;
	
	// Protected: Access to sub-classes of same package and other package via extends
	protected void getData(String a) { 
	}
	
	protected int d = 0;

}
