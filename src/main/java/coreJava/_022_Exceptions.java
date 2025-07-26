package coreJava;

public class _022_Exceptions {
	
	public static void main(String[] args) {

		try {
			System.out.println(4/0);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
