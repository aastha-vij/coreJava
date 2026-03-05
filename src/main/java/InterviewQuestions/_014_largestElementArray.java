package InterviewQuestions;
public class _014_largestElementArray {

	public static void main(String[] args) {
		int[] arr = { 10, 10, 10, 100, 2, 10, 11, 2, 11, 2 }; // 100
		System.out.println(_01_BFA(arr));
		System.out.println(_02_BFA(arr));
	}
	
	static int _01_BFA(int[] arr) {
		// Time: O(n^2)
		// Space: O(1)

		for (int i = 0; i < arr.length; i++) {
			for (int j = i+1; j < arr.length; j++) {
				if(arr[i]>arr[j]) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr[arr.length-1];
	}

	static int _02_BFA(int[] arr) {
		// Time: O(n^2)
		// Space: O(1)

		for (int i = 0; i < arr.length; i++) {
			boolean largest = true;
			for (int j = 0; j < arr.length; j++) {
				if(arr[i] < arr[j]){
					largest = false;
					break;
				}
			}
			if(largest)
				return arr[i];
		}
		return -1;
	}
}