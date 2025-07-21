package coreJava;

public class _017_FunctionOverloading{
	
	/* 1. Overloading: Method Name is same
	 * 2. To remove duplicate method, either:
	 * 		- Argument count should be different
	 * 		- Argument data type should be different
	 * 3. Eg: Payments() via: Credit, Debit, UPI
	 */

	public void getData(int a) {
		System.out.println(a);
	}
	
	public void getData(String a) {
		System.out.println(a);

	}
	
	public void getData(int a, int b) {
		System.out.println(a+ ","+ b);

	}
	
	public static void main(String[] args) {
		_017_FunctionOverloading obj = new _017_FunctionOverloading();
		obj.getData(5);
		obj.getData("Str");
		obj.getData(5, 6);
	}

}
