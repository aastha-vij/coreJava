package coreJava;

public class _008_Palindrome {

	public static Boolean palindrome(String str) {
		
		String temp = "";
		for (int i = str.length()-1; i>=0 ; i--) {
			temp = temp + str.charAt(i);
		}
		
		if(str.equals(temp))
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		
		System.out.println(palindrome("madam"));
	}

}
