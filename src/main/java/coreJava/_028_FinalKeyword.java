package coreJava;

public class _028_FinalKeyword {

	public static void main(String[] args) {

		final int a = 5;
		a = a++; // not allowed to update
		int i = 4;
		System.out.println(a);
		
	}

}
