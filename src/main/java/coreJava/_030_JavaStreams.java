package coreJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
	
	public static void streamNamesGreaterLength(ArrayList<String> names) {
		long count =names.stream()
		.filter(s-> s.length()>4)
		.peek(s -> System.out.println(s))
		.count();
	    System.out.println("Count: " + count);
	}
	
	public static long streamNamesAndCount(ArrayList<String> names) {
		long count = names.stream()
		.filter(s-> s.startsWith("A"))
		.peek(s -> System.out.println(s))
		.count();

		return count;
	}
	
	public static void streamNamesLimit(ArrayList<String> names) {
		names.stream()
		.filter(s-> s.length()>4)
		.filter(s-> s.startsWith("A")).limit(1)
		.forEach(s -> System.out.println(s));
	}
	
	public static void streamNamesInCaps(ArrayList<String> names) {
		names.stream()
		.filter(s->s.endsWith("m"))
		.map(s->s.toUpperCase())
		.forEach(s-> System.out.println(s));
	}
	
	public static void streamNamesInSort(ArrayList<String> names) {
		names.stream()
		.filter(s->s.startsWith("A"))
		.sorted()
		.map(s->s.toUpperCase())
		.forEach(s-> System.out.println(s));
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
		
		System.out.println("-------------------------------------");
		
		System.out.println(streamNamesAndCount(names));
		
		System.out.println("-------------------------------------");

		streamNamesGreaterLength(names);
		
		System.out.println("-------------------------------------");

		streamNamesLimit(names);
		
		System.out.println("-------------------------------------");

		// Print names ends with letter 'm' in UpperCase
		streamNamesInCaps(names);

		System.out.println("-------------------------------------");

		// Print names starts with letter 'a' in Sorting
		streamNamesInSort(names);

		System.out.println("-------------------------------------");

		List<String> names2 = Arrays.asList("Abhishek", "Rama", "Ajita");
		
		Stream<String> concatedStream = Stream.concat(names.stream(), names2.stream());
		concatedStream.sorted().forEach(s-> System.out.println(s));
	}
}
