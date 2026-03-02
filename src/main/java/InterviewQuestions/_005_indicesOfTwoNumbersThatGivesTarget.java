package InterviewQuestions;

import java.util.Arrays;
import java.util.HashMap;

public class _005_indicesOfTwoNumbersThatGivesTarget {

	public static void main(String[] args) {
		int[] arr = new int[] { 2, 7, 11, 15 };
		int target = 9;
		System.out.println(Arrays.toString(_01_BFA(arr, target)));
	}
	
	public static int[] _01_BFA(int[] arr, int target) {
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
}
