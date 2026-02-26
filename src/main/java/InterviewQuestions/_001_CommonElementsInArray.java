package InterviewQuestions;

import java.util.HashMap;

public class _001_CommonElementsInArray {

	public static void main(String[] args) {
        int[] array1 = {4, 3, 2};
        int[] array2 = {2, 1, 7, 4};
        
        _01_BFA(array1, array2);
        _02_commonItems(array1, array2);
	}
	
	public static void _01_BFA(int[] array1, int[] array2){
		//Time Complexity: O(n × m) for nested loops
		//Space Complexity: O(1) as no data structure is used
		
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array2.length; j++) {
				if(array1[i]==array2[j])
					System.out.print(array1[i]+" ");
			}
		}
	}
	
	public static void _02_commonItems(int[] array1, int[] array2) {
		// Time Complexity: O(n+m) if array1.length!=array2.length, else O(n)
		// Space Complexity: O(min(n,m)) if array1.length==array2.length O(n)
		
		if(array1.length>array2.length)
			_02_commonItems(array2, array1);
		
		HashMap<Integer, Boolean> hm = new HashMap<>();
		for (int i : array1)
			hm.put(i, true);
		for (int i : array2) {
			if(hm.containsKey(i))
				System.out.print(i+ " ");
		}
	}	
}
