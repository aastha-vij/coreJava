package coreJava;

public class _021_StaticVariables {

	// Instance variables
	String name;
	String address;
	// Instance variables

	static String city; // Doesn't depend on instance - Class variable
	static int a; // shared with updated value in instance
	
	static {
		city = "Some city";
		a = 0;		
	}
	
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

	////// static methods accept static variables
	public static void getCity() { 
		System.out.println(city);
	}
	
	public static void main(String[] args) {

		_021_StaticVariables obj = new _021_StaticVariables("Some Name", "Some Address");
		obj.getName();
		obj.getAddress();

		_021_StaticVariables obj2 = new _021_StaticVariables("Some Other Name", "Some Other Address");
		obj2.getName();
		obj2.getAddress();
		
		_021_StaticVariables.getCity(); // static is independent of object (Belongs to class)
		getCity();
		System.out.println(a=3);
	}

}
