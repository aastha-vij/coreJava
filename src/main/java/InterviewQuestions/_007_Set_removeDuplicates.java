package InterviewQuestions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _007_Set_removeDuplicates {

	public static void main(String[] args) {
		List<Integer> myList = List.of(1, 2, 3, 4, 1, 2, 5, 6, 7, 3, 4, 8, 9, 5); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
		System.out.println(_01_UsingSet(myList));
	}

	static Set<Integer> _01_UsingSet(List<Integer> myList) {
		
		Set<Integer> set = new HashSet<>();
		for (Integer i : myList)
			set.add(i);
		return set;
	}
}
