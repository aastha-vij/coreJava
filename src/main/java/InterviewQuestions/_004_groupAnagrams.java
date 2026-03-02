package InterviewQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class _004_groupAnagrams {

	public static void main(String[] args) {
		String[] str = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};

		System.out.println(_01_BFA(str));
	}
	
	public static List<List<String>> _01_BFA(String[] str) {
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
}
