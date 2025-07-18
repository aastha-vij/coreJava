package coreJava;

public class _007_StringClassAndMethods {

	public static void main(String[] args) {

		String a = " String-Literal";
		String b = new String ("String Class");
		
		System.out.println(a.charAt(2));
		System.out.println(b.charAt(0));
		System.out.println(a.indexOf("i"));
		System.out.println(a.substring(1, 7));
		System.out.println(a.substring(9));
		System.out.println(a.concat(b));
		System.out.println(a.length());
		System.out.println(a.trim());
		
		String[] arr = a.split("-");
		System.out.println(arr[0]);
		System.out.println(a.replace("S","Z" ));
		
		// == and .equals()
		
		String str1 = "Some Text";
		String str2 = "Some Text"; // Literal is referenced to same str1
		System.out.println(str1==str2); // compares reference  
		System.out.println(str1.equals(str2)); // compares content
		
		String str3 = new String("Some Text"); // new reference for each object
		String str4 = new String("Some Text");
		System.out.println(str3==str4);
		System.out.println(str3.equals(str4));
		
	}

}
