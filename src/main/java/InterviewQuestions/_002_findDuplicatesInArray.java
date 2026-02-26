package InterviewQuestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class _002_findDuplicatesInArray {

	public static void main(String[] args) {
		int[] nums = { 1, 2, 3, 2, 1, 4, 5, 4 };

		System.out.print(_01_Using_Set(nums));
	}

	public static Set<Integer> _01_Using_Set(int[] nums){
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
}
