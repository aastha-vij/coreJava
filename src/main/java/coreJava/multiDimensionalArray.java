package coreJava;

public class multiDimensionalArray {

	public static int sortAndReturnMinOrMax(int arrayToSort[], Boolean findMinimum) {
	    int[] arr = arrayToSort.clone();
	    
		int minMax = arr[0];
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {

				if(arr[i] > arr[j]) {
					minMax = arr[i];
					arr[i] = arr[j];
					arr[j] = minMax;
				}
			}
		}
		
	 return findMinimum ? arr[0] : arr[arr.length-1];
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
	
	public static int minimumNumInMultiDimensional_01(int num[][]) {
		
		int min = sortAndReturnMinOrMax(num[0], true);
		
		for (int i = 0; i < num.length; i++) {
			
			int rowMin = sortAndReturnMinOrMax(num[i], true);
			
			if(rowMin<min) {
				min = rowMin;	
			}
		} 	
		return min;
	}
	
	public static int minimumNumInMultiDimensional_02(int num[][]) {
		
		int min = num[0][0];
		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < num[i].length; j++) {
				if(num[i][j]<min)
					min=num[i][j];
			}
		}
		return min;
	}
	
	public static int maximumNumInMultiDimensional_01(int num[][]) {
		
		int max = sortAndReturnMinOrMax(num[0], false);
		
		for (int i = 0; i < num.length; i++) {
			
			int rowMax = sortAndReturnMinOrMax(num[i], false);
			
			if(rowMax > max) {
				max = rowMax;	
			}
		} 	
		return max;
	}
	
	public static int maximumNumInMultiDimensional_02(int num[][]) {
		
		int max = num[0][0];
		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < num[i].length; j++) {
				if(num[i][j]>max)
					max=num[i][j];
			}
		}
		return max;
	}
	
	
	public static int minimumInRowMaximumInColumn(int num[][]) {
		
		int min = num[0][0];
		int minCol = 0;
		
		for (int i = 0; i < num.length; i++) {
			for ( int j = 0; j < num[i].length; j++) {
				if(num[i][j]<min) {
					min=num[i][j];
					minCol = j;
				}
			}
		}

		int max = num[0][minCol];
	    for (int i = 1; i < num.length; i++) {
	        if (num[i][minCol] > max) {
	            max = num[i][minCol];
	        }
	    }
		return max;
	}
	
	public static void main(String[] args) {
		basicDeclaration();
		
		System.out.println("---------------");
		
		int num[][] ={  {5, 	0,		7}, 
						{67, 	3, 		23}, 
						{96, 	75, 	1}
					 };
		
		
		System.out.println(minimumNumInMultiDimensional_01(num));
		
		System.out.println("---------------");
		
		System.out.println(minimumNumInMultiDimensional_02(num));
		
		System.out.println("---------------");

		System.out.println(maximumNumInMultiDimensional_01(num));
		
		System.out.println("---------------");

		System.out.println(maximumNumInMultiDimensional_02(num));
		
		System.out.println("---------------");
		
		System.out.println(minimumInRowMaximumInColumn(num));
	}

}
