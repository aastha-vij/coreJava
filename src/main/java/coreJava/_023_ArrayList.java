package coreJava;

import java.util.ArrayList;

public class _023_ArrayList {

	public static void main(String[] args) {

		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(6);
		al.add(8);
		al.add(6);
		System.out.println(al);
		al.add(0, 9);
		System.out.println(al);
		al.remove(1);
		System.out.println(al);
		al.remove(al.get(2));
		System.out.println(al);
		System.out.println(al.contains(9));

	}

}
