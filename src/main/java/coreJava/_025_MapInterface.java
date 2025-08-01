package coreJava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class _025_MapInterface {

	public static void main(String[] args) {

		/* 
		 * Key : Value pairs
		 * Async and Not Thread Safe (Keeps updating)
		   Allows null:  1 key : Any number of values
		   Iterates using iterator
		*/
		HashMap<Integer, String> hm = new HashMap<Integer, String >();
		
		hm.put(0, "Hello");
		hm.put(1, "Bie");
		hm.put(2, "Morning");
		hm.put(3, "Evening");
		hm.put(4, "");
		hm.put(5, null);

		System.out.println(hm);
		System.out.println(hm.get(2));
		hm.remove(2);
		System.out.println(hm.get(2));
		
		Set set = hm.entrySet();
		System.out.println(set);
		
		Iterator i = set.iterator(); 
		while(i.hasNext())
		{
			Map.Entry mp = (Map.Entry)i.next();
			System.out.println(mp.getKey() + " : "+ mp.getValue());
		}
	}

}
