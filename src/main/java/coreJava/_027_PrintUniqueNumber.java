package coreJava;

import java.util.ArrayList;
import java.util.HashMap;

public class _027_PrintUniqueNumber{
	
	public static void viaNestedLoop(int[] a) {
		
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			
			int count = 0;
			
			if(!al.contains(a[i])) {
				al.add(a[i]);
				count++;

				for (int j = i+1; j < a.length; j++) {
					if(a[i]==a[j]) {
						count++;
					}
				}
				
				if(count==1) {
					System.out.println("Unique: "+a[i]);

				}
			}
		}
	}
	
	public static void doWhileHashMap(int[] a) {
		
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			al.add(a[i]);
		}
		
		do {
			
			HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
			int first = al.get(0);
			int count = 0;
			
			for (int i = 0; i < al.size(); i++) {
				if(first == al.get(i)) {
					count++;
				}
				
				hm.put(first, count);
			}
			
			al.removeIf(n -> n == first);
			
			if(count==1)
				System.out.println("Unique: "+first);
		}
		
		while(al.size()!=0);
	}

	public static void main(String[] args) {

		int a[] = {4,5,5,5,4,6,6,9,4};
		
		/* 4 is repeated 3 times 
		 * 5 is repeated 3 times 
		 * 6 is repeated 2 times 
		 * 9 is repeated 1 times 
		 * Find unique number - 9 (Non repeated)
		 */

		doWhileHashMap(a);
		viaNestedLoop(a);
		
		}
	}
