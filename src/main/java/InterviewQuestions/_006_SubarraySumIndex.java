package InterviewQuestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _006_SubarraySumIndex {

	public static void main(String[] args) {
		int[] nums = { 5, 1, 2, 3, 4};
		int target = 9;
		Arrays.stream(_01_BFA(nums, target)).forEach(value -> System.out.print(value + " "));
		System.out.println();
		Arrays.stream(_02_UsingHashmap(nums, target)).forEach(value -> System.out.print(value + " "));
	}

	static int[] _01_BFA(int[] nums, int target) {
		// Time complexity: O(n^2)
		// Space complexity: O(1)
		
		for (int i = 0; i < nums.length; i++) {
			int sum=0;
			for (int j = i; j < nums.length; j++) {
				sum+=nums[j];
				if(sum == target)
					return new int[] {i,j};
			}
		}
		return new int[] {};
	}
	
	static int[] _02_UsingHashmap(int[] arr, int target) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
	    Map<Integer, Integer> hm = new HashMap<>();
	    int sum = 0;
	    hm.put(0, -1);
	    for (int i = 0; i < arr.length; i++) {
			sum+=arr[i];			
			if(hm.containsKey(sum - target))
				return new int[] {hm.get(sum- target)+1, i};
		    hm.put(sum, i);
		}
		return new int[] {};
	}
}
