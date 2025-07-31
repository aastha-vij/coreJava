package coreJava;

import java.util.ArrayList;
import java.util.HashMap;

public class _027_PrintUniqueNumber {

	public static void main(String[] args) {

		int a[] = {4,5,5,5,4,6,6,9,4};
		
		/* 4 is repeated 3 times 
		 * 5 is repeated 3 times 
		 * 6 is repeated 2 times 
		 * 9 is repeated 1 times 
		 * Find unique number - 9 (Non repeated)
		 */
		
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			al.add(a[i]);
		}
		System.out.println(al);

		int first = al.get(0);
		int count = 1;
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < al.size(); i++) {
			if(first==al.get(i)) {
				hm.put(al.get(i), count++);
			}	
		}
		System.out.println(hm);

		}
	}
