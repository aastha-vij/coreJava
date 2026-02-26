package InterviewQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _002_findDuplicatesInArray {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 3, 2, 1, 4, 5, 4 };

		System.out.print(_01_Using_Set(nums));
		System.out.print(_02_BFA(nums));
	}

	public static Set<Integer> _01_Using_Set(int[] nums){
	    // Time Complexity: O(n)
	    // Space Complexity: O(n)

		HashMap<Integer, Boolean> hm = new HashMap<>();
		Set<Integer> duplicate = new HashSet<>();
		
		for (int num : nums) {
			if(hm.containsKey(num))
				duplicate.add(num);
			else
				hm.put(num, true);
		}
		return duplicate;
	}
	
	public static List<Integer> _02_BFA(int[]nums) {
	    // Time Complexity: O(n^2)
	    // Space Complexity: O(k) where k = number of duplicates

		List<Integer> duplicate = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			for (int j = i+1; j < nums.length; j++) {
				if(nums[i]==nums[j]) {
					if(!duplicate.contains(nums[i]))
						duplicate.add(nums[i]);
					break;
				}
			}
		}
		return duplicate;
	}
}
