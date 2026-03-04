package InterviewQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _009_Set_findPairsFromTwoArrays {

	public static void main(String[] args) {
		int[] arr1 = { 1, 2, 3, 4, 5 };
		int[] arr2 = { 2, 4, 6, 8, 10 };
		int target = 7; // [5, 2] [3, 4] [1, 6]
		
		List<int[]> result = _01_BFA(arr1, arr2, target);
		for (int[] pair : result) {
			System.out.print(Arrays.toString(pair));
		}
	}

	static List<int[]> _01_BFA(int[] arr1, int[] arr2, int target ) {
		// Time: O(n*m) 
		// Space: O(k)
		
		List<int[]> pairs = new ArrayList<>();
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr2.length; j++) {
				if(arr1[i]+arr2[j]== target)
					pairs.add(new int[] {arr1[i], arr2[j]});
			}
		}
		return pairs;
	}
}
