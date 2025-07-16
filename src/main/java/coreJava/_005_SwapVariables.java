package coreJava;

public class _005_SwapVariables {

	public static void swapWithTempVariable(int a, int b) {
		int temp=0;
		temp = a;
		a = b;
		b = temp;
		
		System.out.println(a + ","+ b);
	}
	
	public static void swapWithoutTempVariable(int a, int b) {
		
		 a = a + b;
		 b = a - b;
		 a = a - b;
		
		//5+7 a= 12 , b=7
		// b = 12-7 = 5
		// a = 12 - 5
		
			System.out.println(a + ","+ b);
	}
	
	public static void main(String[] args) {
		
		int a = 5;
		int b = 7;
		
		swapWithTempVariable(a, b);
		System.out.println("-----------------");
		
		swapWithoutTempVariable(a, b);

	}

}
