package coreJava;

final class _028_FinalKeyword { // final classes can't be inherited


	final void getData() {
		// final methods can't be overridden
	}
	
	public static void main(String[] args) {

		final int a = 5;
		/*a = a++; --> final variables are not allowed to update
		a = 4;
		*/
	}

}
