package InterviewQuestions;

import java.util.Arrays;

public class _013_ReverseArray {

	public static void main(String[] args) {
		int[] arr = new int[] { 1, 2, 3, 4, 5 }; // 5, 4, 3, 2, 1
		
		Arrays.stream(_01_BFA(arr)).forEach(value-> System.out.print(value+" "));
	}

	static int[] _01_BFA(int[] arr) {
		// Time complexity: O(n)
		// Space complexity: O(n)
		
		int[] reverseArray = new int[arr.length];
		
		for (int i = 0; i < arr.length; i++)
			reverseArray[i] = arr[arr.length-1-i];
		return reverseArray;
	}
}
