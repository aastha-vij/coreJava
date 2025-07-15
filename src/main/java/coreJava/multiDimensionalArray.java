package coreJava;

public class multiDimensionalArray {

	public static int minMaxOfArray(int arrayToSort[], Boolean findMinimum, Boolean findMaximum) {
		int minMax = arrayToSort[0];
		for (int i = 0; i < arrayToSort.length; i++) {
			for (int j = i; j < arrayToSort.length; j++) {

				if(arrayToSort[i] > arrayToSort[j]) {
					minMax = arrayToSort[i];
					arrayToSort[i] = arrayToSort[j];
					arrayToSort[j] = minMax;
				}
			}

		}
		
		if(findMinimum)
			return arrayToSort[0];

		else
			return arrayToSort[arrayToSort.length-1];
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
		
		int min = minMaxOfArray(num[0], true, false);
		
		for (int i = 0; i < num.length; i++) {
			
			int rowMin = minMaxOfArray(num[i], true, false);
			
			if(rowMin<min) {
				min = rowMin;	
			}
		} 	
		System.out.println(min);
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
	
	public static void maximumNumInMultiDimensional_01(int num[][]) {
		
		int max = minMaxOfArray(num[0], false, true);
		
		for (int i = 0; i < num.length; i++) {
			
			int rowMax = minMaxOfArray(num[i], false, true);
			
			if(rowMax > max) {
				max = rowMax;	
			}
		} 	
		System.out.println(max);
	}
	
	public static void maximumNumInMultiDimensional_02(int num[][]) {
		
		int max = num[0][0];
		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < num.length; j++) {
				if(num[i][j]>max)
					max=num[i][j];
			}
		}
		System.out.println(max);
	}
	
	public static void main(String[] args) {
		basicDeclaration();
		
		System.out.println("---------------");
		
		int num[][] = {{5, 0, 7}, {67, -3, 23}, {964, 75, -1}};
		
		minimumNumInMultiDimensional_01(num);
		
		System.out.println("---------------");
		
		minimumNumInMultiDimensional_02(num);
		
		System.out.println("---------------");

		maximumNumInMultiDimensional_01(num);
		
		System.out.println("---------------");

		maximumNumInMultiDimensional_02(num);
	}

}
