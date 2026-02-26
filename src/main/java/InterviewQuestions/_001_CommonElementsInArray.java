package InterviewQuestions;

import java.util.HashMap;

public class _001_CommonElementsInArray {

	public static void main(String[] args) {
        int[] array1 = {4, 3, 2};
        int[] array2 = {2, 1, 7, 4};
        _02_commonItems(array1, array2);
	}
	
	public static void _02_commonItems(int[] array1, int[] array2) {
		// Time complexity: O(n+m) if array1.length!=array2.length, else O(n)
		// Space complexity: O(min(n,m)) if array1.length==array2.length O(n)
		
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
