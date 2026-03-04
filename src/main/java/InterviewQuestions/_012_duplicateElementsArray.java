package InterviewQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class _012_duplicateElementsArray {

	public static void main(String[] args) {
		int[] arr = { 2, 10, 10, 100, 2, 10, 11, 2, 11, 2 }; // 2 10 11
		System.out.println(_01_BFA(arr));
		System.out.println(_02_UsingHashMap(arr));
		System.out.println(_03_UsingHashSet(arr));
	}

	static List<Integer> _01_BFA(int[] arr) {
		// Time complexity: O(n^2)
		// Space complexity: O(n)
		
		List<Integer> duplicates = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			for (int j = i+1; j < arr.length; j++) {
				if(arr[i]==arr[j]) {
					if(!duplicates.contains(arr[i]))
						duplicates.add(arr[i]);
				}
			}
		}
		return duplicates;
	}
	
	static List<Integer> _02_UsingHashMap(int[] arr) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		List<Integer> duplicates = new ArrayList<>();
		HashMap<Integer, Integer> hm = new HashMap<>();
		
		for (int i : arr)
			hm.put(i, hm.getOrDefault(i, 0)+1);
		
		for(int key: hm.keySet()) {
			if(hm.get(key)>1)
				duplicates.add(key);
		}
		return duplicates;
	}
	
	static HashSet<Integer> _03_UsingHashSet(int[] arr) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		HashSet<Integer> duplicates = new HashSet<>();
		HashSet<Integer> hs = new HashSet<>();

		for (int num : arr) {
			if(!hs.add(num))
				duplicates.add(num);
		}
		return duplicates;
	}
}
