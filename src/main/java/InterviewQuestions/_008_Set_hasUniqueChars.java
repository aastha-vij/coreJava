package InterviewQuestions;

import java.util.HashMap;
import java.util.HashSet;

public class _008_Set_hasUniqueChars {

	public static void main(String[] args) {
		String str1 = "abcdefg"; // should return true
		String str2 = "hello"; // should return false
		String str3 = ""; // should return true
		String str4 = "0123456789"; // should return true
		String str5 = "abacadaeaf"; // should return false
		
		System.out.println(_01_BFA(str1));
		System.out.println(_01_BFA(str2));
		System.out.println(_01_BFA(str3));
		System.out.println(_01_BFA(str4));
		System.out.println(_01_BFA(str5));
		
		System.out.println(_02_UsingHashmap(str1));
		System.out.println(_02_UsingHashmap(str2));
		System.out.println(_02_UsingHashmap(str3));
		System.out.println(_02_UsingHashmap(str4));
		System.out.println(_02_UsingHashmap(str5));
		
		System.out.println(_03_UsingHashset(str1));
		System.out.println(_03_UsingHashset(str2));
		System.out.println(_03_UsingHashset(str3));
		System.out.println(_03_UsingHashset(str4));
		System.out.println(_03_UsingHashset(str5));
	}

	static boolean _01_BFA(String str) {
		// Time: O(n^2) 
		// Space: O(1)

		for (int i = 0; i < str.length(); i++) {
			for (int j = 0; j < str.length(); j++) {
				if(i!=j && str.charAt(i)==str.charAt(j))
					return false;
			}
		}
		return true;
	}
	
	static boolean _02_UsingHashmap(String str) {
		// Time: O(n)
		// Space: O(n)
		
		HashMap<Character, Integer> hm = new HashMap<>();
		for (char ch : str.toCharArray())
			hm.put(ch, hm.getOrDefault(ch, 0)+1);
		
		for (char ch : str.toCharArray())
			if(hm.get(ch)>1)
				return false;
		return true;
	}
	
	static boolean _03_UsingHashset(String str) {
		// Time: O(n) 
		// Space: O(n)

		HashSet<Integer> hs = new HashSet<>();
		for (int i : str.toCharArray()) {
			if(!hs.add(i))
				return false;
		}
		return true;
	}
}