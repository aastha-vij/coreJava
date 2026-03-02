package InterviewQuestions;

import java.util.HashMap;

public class _003_firstNonRepeatingChar {

	public static void main(String[] args) {

		String str = "haabfebecd";
		System.out.println(_01_BFA(str));
		System.out.println(_02_UsingHashMap(str));
		System.out.println(_03_Using_FrequencyArray(str));

	}
	
	public static char _01_BFA(String str) {
		// Time complexity: O(n^2)
		// Space complexity: O(1)
		
		for (int i = 0; i < str.length(); i++) {
			Boolean isRepeated = false;
			for (int j = 0; j < str.length(); j++) {
				if(i!=j && str.charAt(i)== str.charAt(j)) {
					isRepeated= true;
					break;
				}
			}
			if(!isRepeated)
				return str.charAt(i);
		}
		return '\0';
	}
	
	public static char _02_UsingHashMap(String str) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		HashMap<Character, Integer> hm = new HashMap<>();
		for (char ch : str.toCharArray())
			hm.put(ch, hm.getOrDefault(ch, 0)+1);
		for (char ch : str.toCharArray()) 
			if(hm.get(ch)==1)
				return ch;
		return '\0';
	}
	
	public static char _03_Using_FrequencyArray(String str) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		int[] count = new int[128];
		for (char ch : str.toCharArray()) {
			count[ch-'a']++;
		}
		for (char ch : str.toCharArray()) {
			if(count[ch-'a']==1)
				return ch;
		}
		return 0;
	}
}
