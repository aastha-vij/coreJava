package InterviewQuestions;

import java.util.Arrays;
import java.util.HashMap;

public class _005_indicesOfTwoNumbersThatGivesTarget {

	public static void main(String[] args) {
		int[] arr = new int[] { 2, 7, 11, 15 };
		int target = 9;
		System.out.println(Arrays.toString(_01_BFA(arr, target)));
		System.out.println(Arrays.toString(_02_UsingHashMap(arr, target)));
	}
	
	public static int[] _01_BFA(int[] arr, int target) {
		// Time complexity: O(n^2)
		// Space complexity: O(1)
		
		int[] indexArr = new int[2];
		for (int i = 0; i < arr.length; i++) {
			for (int j = i+1; j < arr.length; j++) {
				if(arr[i] + arr[j]==target) {
					indexArr[0] = i;
					indexArr[1] = j;
					return indexArr;
				}
			}
		}
		return new int[0];
	}
	
	public static int[] _02_UsingHashMap(int[] arr, int target) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		HashMap<Integer, Integer> hm = new HashMap<>();
		int otherNumber;
		for (int i = 0; i < arr.length; i++) {
			otherNumber = target - arr[i];
			
			if(hm.containsKey(otherNumber))
				return new int[] {hm.get(otherNumber), i};
			hm.put(arr[i], i);	
		}
		return new int[] {};
	}
}
