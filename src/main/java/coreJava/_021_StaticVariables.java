package coreJava;

public class _021_StaticVariables {

	// Instance variables
	String name;
	String address;
	// Instance variables

	static String city = "Some City"; // Doesn't depend on instance - Class variable
	static int a = 0;
	
	public _021_StaticVariables(String name, String address) {
		// Local variables --> name, address
		// Instance variables --> this.name, this.address
		
		this.name = name;
		this.address = address;
		a++;
		System.out.println(a);
	}
	
	public void getAddress() {
		System.out.println(address + " "+ city);
	}
	
	public void getName() {
		System.out.println(name);
	}

	
	public static void main(String[] args) {

		_021_StaticVariables obj = new _021_StaticVariables("Some Name", "Some Address");
		obj.getName();
		obj.getAddress();

		_021_StaticVariables obj2 = new _021_StaticVariables("Some Other Name", "Some Other Address");
		obj2.getName();
		obj2.getAddress();

	}

}
