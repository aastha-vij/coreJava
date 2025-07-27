package coreJava;

public class _022_Exceptions {
	
	public static void main(String[] args) {
		
		try {
			
			int a[] = {1,2,3,4};
			System.out.println(a[6]);
			 
			System.out.println(4/0);

		}
		
		// one try can be followed by multiple catch blocks
		// catch should be immediate after try
		
		catch(IndexOutOfBoundsException iob) {
			System.out.println("Out of Bound: "+iob); //java.lang.ArrayIndexOutOfBoundsException: Index 6 out of bounds for length 4
		}
		
		catch(ArithmeticException ae){
			System.out.println("Arithmetic: "+ae); // java.lang.ArithmeticException: / by zero
		}
		
		catch(Exception e){
			System.out.println("Generic: "+e); 
		}
		
		finally {
			
			// Executes irrespective of Exceptions
			// Always executed except JVM stopped forcefully
			System.out.println("Finally Block");
		}
	}

}
