package coreJava;

public class _009_Immutable {

	public static void main(String[] args) {

		String a = "hello";
		String b = "hello";
		a.concat("world"); //Immutable
		System.out.println(a);
		String c = a.concat("world");
		System.out.println(c);
		
		StringBuffer sb = new StringBuffer("hello"); //Thread safe, sync
		sb.append("world"); //Mutable
		System.out.println(sb);
		sb.insert(0, "Hello");
		System.out.println(sb);
		System.out.println(sb.replace(0, 5, ""));
		System.out.println(sb.deleteCharAt(sb.length()-1));
		System.out.println(sb.reverse());	
		
		StringBuilder sbb = new StringBuilder("hello"); //Not thread safe, async, but fast
	}

}
