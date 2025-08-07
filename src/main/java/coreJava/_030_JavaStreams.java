package coreJava;

import java.util.ArrayList;

public class _030_JavaStreams {

	
	public static int countNamesWithStartingLetter(ArrayList<String> names) {

		int count = 0;
		for (int i = 0; i < names.size(); i++) {
			if(names.get(i).startsWith("A")) {
				count++;
			}
		}
		return count;
	}
	
	public static void main(String[] args) {
		// Count number of names starting with letter 'A'
		
		ArrayList<String> names = new ArrayList<String>();
		names.add("Abhi");
		names.add("Dash");
		names.add("Alekhya");
		names.add("Adam");
		names.add("Ram");
		
		System.out.println(countNamesWithStartingLetter(names));

	}
}