package InterviewQuestions;

import java.util.HashMap;

public class _011_frequencyOfEachElementArray {

	public static void main(String[] args) {
		int[] arr = { 10, 20, 20, 10, 10, 20, 5, 20 }; // 10: 3, 20: 4, 5: 1
		_01_BFA(arr);
		System.out.println(_02_UsingHashMap(arr));
	}

	static void _01_BFA(int[] arr) {
		// Time complexity: O(n^2)
		// Space complexity: O(n)
		
		boolean[] visited = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if(!visited[i]) {
				int count = 1;
				for (int j = i+1; j < arr.length; j++) {
					if(arr[i]==arr[j]) {
						visited[j] = true;
						count++;
					}
				}
				System.out.println(arr[i] + " "+ count);	
			}
		}
	}
	
	static HashMap<Integer, Integer> _02_UsingHashMap(int[]arr) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		HashMap<Integer, Integer> hm = new HashMap<>();
		for (int i : arr)
				hm.put(i, hm.getOrDefault(i, 0)+1);
		return hm;
	}
}
