package coreJava;

public class multiDimensionalArray {

	public static int minimumOfArray(int arrayToSort[]) {
		int min = arrayToSort[0];
		for (int i = 0; i < arrayToSort.length; i++) {
			for (int j = i; j < arrayToSort.length; j++) {
				if(arrayToSort[i] > arrayToSort[j]) {
					min = arrayToSort[i];
					arrayToSort[i] = arrayToSort[j];
					arrayToSort[j]= min;
				}
			}

		}
		return arrayToSort[0];
	}
	
	public static void basicDeclaration() {
		int row = 2;
		int col = 3;
		int a[][] = new int [row][col];
		a[0][0] = 1;
		a[0][1] = 2;
		a[0][2] = 3;
		a[1][0] = 4;
		a[1][1] = 5;
		a[1][2] = 6;
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(a[i][j] +" ");
			}
			System.out.println();
		} 
	}
	
	public static void minimumNumInMultiDimensional_01(int num[][]) {
		
		int min = minimumOfArray(num[0]);
		
		for (int i = 0; i < num.length; i++) {
			
			int rowMin = minimumOfArray(num[i]);
			
			if(rowMin<min) {
				min = rowMin;	
			}
		} 	
		System.out.println( min );
	}
	
	public static void minimumNumInMultiDimensional_02(int num[][]) {
		
		int min = num[0][0];
		for (int i = 0; i < num.length; i++) {
			
			for (int j = 0; j < num.length; j++) {
				if(num[i][j]<min)
					min=num[i][j];
			}
		}
		System.out.println(min);
	}
	
	public static void main(String[] args) {
		basicDeclaration();
		
		System.out.println("---------------");
		
		int num[][] = {{5, 0, 7}, {67, 3, 23}, {96, 75, 1}};
		
		minimumNumInMultiDimensional_01(num);
		
		System.out.println("---------------");
		
		minimumNumInMultiDimensional_02(num);


	}

}
