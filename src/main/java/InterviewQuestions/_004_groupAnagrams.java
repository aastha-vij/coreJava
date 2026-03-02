package InterviewQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _004_groupAnagrams {

	public static void main(String[] args) {
		String[] str = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};

		System.out.println(_01_BFA(str));
		System.out.println(_02_UsingFrequencyArray(str));
	}
	
	public static List<List<String>> _01_BFA(String[] str) {
		// Time complexity: O(n^2 × k log k)
		// Space complexity: O(n)
		
        List<List<String>> result = new ArrayList<>();

		boolean[] visited = new boolean[str.length];
		for (int i = 0; i < str.length; i++) {
			if (visited[i]) continue;
			
			List<String> group = new ArrayList<>();
			group.add(str[i]);
			visited[i] = true;
			
			for (int j = 0; j < str.length; j++) {
				if(!visited[j] && isAnagram(str[i], str[j])) {
					group.add(str[j]);
					visited[j] = true;
				}	
			}
            result.add(group);
		}
		return result;
	}
	
	public static boolean isAnagram(String a, String b) {

		if(a.length()!=b.length()) return false;
		
		char[] c1 = a.toCharArray();
		char[] c2 = b.toCharArray();
		
		Arrays.sort(c1);
		Arrays.sort(c2);
		
		return Arrays.equals(c1, c2);
	}
	
	public static Collection<List<String>> _02_UsingFrequencyArray(String[] str) {
		// Time complexity: O(n*k)
		// Space complexity: O(n)
		
		Map<String, List<String>> map = new HashMap<>();
		for (String word : str) {
			int[] count = new int[26];
			for (char ch : word.toCharArray())
				count[ch-'a']++;
			
			String key = Arrays.toString(count);
			map.putIfAbsent(key, new ArrayList<String>());
			map.get(key).add(word);
		}
		return map.values();
	}
}
