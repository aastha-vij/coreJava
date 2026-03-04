package InterviewQuestions;

import java.util.HashSet;
import java.util.Set;

public class _010_Set_longestConsecutiveSequence {

	public static int longestConsecutiveSequence(int[] nums) {
		// Time: O(n)
		// Space: O(n)

		if (nums == null || nums.length == 0)
			return 0;
		
		Set<Integer> set = new HashSet<>();
		
		for (int num : nums)
			set.add(num);
		
		int longestStreak=0;
		
		for (int num : set) {
			if(!set.contains(num-1)) {
				int currentNum = num;
				int currentStreak = 1;
				
				while(set.contains(currentNum+1)) {
					currentNum++;
					currentStreak++;
				}
				
				longestStreak = Math.max(longestStreak, currentStreak);
			}
		}
		return longestStreak;
	}

	public static void main(String[] args) {
		test("Consecutive Integers", new int[] { 1, 0, 3, 2, 5 }, 4);
		test("No Sequence", new int[] { 1, 3, 5, 7, 9 }, 1);
		test("Duplicates", new int[] { 1, 2, 2, 3, 4 }, 4);
		test("Negative Numbers", new int[] { 1, 0, -1, -2, -3 }, 5);
		test("Empty Array", new int[] {}, 0);
		test("Multiple Sequences", new int[] { 1, 2, 3, 10, 11, 12, 13 }, 4);
		test("Unordered", new int[] { 5, 1, 3, 4, 2 }, 5);
		test("Single Element", new int[] { 1 }, 1);
		test("All Same", new int[] { 2, 2, 2, 2 }, 1);
	}

	private static void test(String title, int[] nums, int expected) {
		int result = longestConsecutiveSequence(nums);
		String ok = result == expected ? "PASS" : "FAIL";
		System.out.println(title + " -> " + result + " (expected " + expected + ") " + ok);
	}

}