package coreJava;

public class _006_SortArray {

	public static void sortArray(int[] arr) {
		int temp=0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i+1; j < arr.length; j++) {
				if(arr[i]>arr[j]) {
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
	}
	
	public static void main(String[] args) {

		int [] array = {5,7,33,6,8,1,-7} ;
		sortArray(array);
	}
}
