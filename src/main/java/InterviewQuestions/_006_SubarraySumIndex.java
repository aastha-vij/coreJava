package InterviewQuestions;

import java.util.Arrays;

public class _006_SubarraySumIndex {

	public static void main(String[] args) {
		int[] nums = { 5, 1, 2, 3, 4};
		int target = 9;
		Arrays.stream(_01_BFA(nums, target)).forEach(value -> System.out.print(value + " "));
	}

	private static int[] _01_BFA(int[] nums, int target) {
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
}
