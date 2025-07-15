package coreJava;

public class A03_NestedForLoop {

	public static void invertedPyramid() {
		int temp = 1;
		for (int i = 1; i <=4; i++) {
			
			for (int j = i; j <= 4; j++) {
				
				System.out.print(temp + " ");
				temp++;
			}
			System.out.println();
		}
	}
	
	public static void increasingPyramid() {
		int temp = 1;
		for (int i = 1; i <=4; i++) {
			
			for (int j = 1; j <= i; j++) {
				
				System.out.print(temp + " ");
				temp++;
			}
			System.out.println();
		}
	}
	
	public static void pyramidRevisit() {
		for (int i = 1; i <=4; i++) {
			
			for (int j = 1; j <= i; j++) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		invertedPyramid();
		System.out.println("---------------------");
		increasingPyramid();
		System.out.println("---------------------");		
		pyramidRevisit();
	}

}
