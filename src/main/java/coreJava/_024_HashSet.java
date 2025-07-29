package coreJava;

import java.util.HashSet;

public class _024_HashSet {

	 /* Set Collection - HashSet, TreeSet, LinkedHashSet: Doesn't accept duplicates
	 	HashSet: Dynamic size, No order
	 */
	public static void main(String[] args) {

		HashSet<String> hs = new HashSet<String>();
		hs.add("test01");
		hs.add("test02");
		hs.add("test02"); // duplicates are removed
		System.out.println(hs); // [test02, test01] --> No order
		System.out.println(hs.remove("test02"));
		System.out.println(hs.isEmpty());
		System.out.println(hs.size());
	}

	//Iterator

}
