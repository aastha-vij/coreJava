package coreJava;

/**
 * _004_MultiDimensionalArray - Working with 2D Arrays and Matrix Operations
 * 
 * This class demonstrates various operations on multi-dimensional arrays:
 * - Array declaration and initialization
 * - Finding minimum and maximum values
 * - Matrix operations and algorithms
 * - Performance considerations for large matrices
 * - Common matrix patterns and use cases
 * 
 * Key Learning Points:
 * 1. 2D arrays are arrays of arrays (matrix structure)
 * 2. Matrix operations often require nested loops
 * 3. Different algorithms have different time complexities
 * 4. Matrix operations are fundamental in many applications
 * 
 * @author Core Java Learning Project
 * @version 1.0
 * @since 2024
 */
public class _004_MultiDimensionalArray {

    /**
     * Sorts a 1D array and returns min or max value
     * Time Complexity: O(n²) - Bubble Sort
     * 
     * @param arrayToSort The array to sort
     * @param findMinimum If true, return minimum; if false, return maximum
     * @return The minimum or maximum value after sorting
     */
    public static int sortAndReturnMinOrMax(int arrayToSort[], Boolean findMinimum) {
        if (arrayToSort == null || arrayToSort.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int[] arr = arrayToSort.clone(); // Create a copy to avoid modifying original
        
        // Bubble sort implementation
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    // Swap elements
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        
        return findMinimum ? arr[0] : arr[arr.length - 1];
    }
    
    /**
     * Demonstrates basic 2D array declaration and initialization
     * Shows different ways to create and populate 2D arrays
     */
    public static void basicDeclaration() {
        System.out.println("=== Basic 2D Array Declaration ===");
        
        // Method 1: Declare and initialize separately
        int row = 2;
        int col = 3;
        int a[][] = new int[row][col];
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;
        a[1][0] = 4;
        a[1][1] = 5;
        a[1][2] = 6;
        
        System.out.println("Method 1 - Separate initialization:");
        printMatrix(a);
        
        // Method 2: Declare and initialize in one statement
        int b[][] = {
            {1, 2, 3},
            {4, 5, 6}
        };
        
        System.out.println("\nMethod 2 - Inline initialization:");
        printMatrix(b);
        
        // Method 3: Using loops to populate
        int c[][] = new int[3][3];
        int value = 1;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                c[i][j] = value++;
            }
        }
        
        System.out.println("\nMethod 3 - Loop initialization:");
        printMatrix(c);
    }
    
    /**
     * Finds minimum value in a 2D array using sorting approach
     * Time Complexity: O(n²) per row, where n is row length
     * 
     * @param num The 2D array to search
     * @return The minimum value found
     */
    public static int minimumNumInMultiDimensional_01(int num[][]) {
        if (num == null || num.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }
        
        int min = sortAndReturnMinOrMax(num[0], true);
        
        for (int i = 1; i < num.length; i++) {
            int rowMin = sortAndReturnMinOrMax(num[i], true);
            if (rowMin < min) {
                min = rowMin;
            }
        }
        return min;
    }
    
    /**
     * Finds minimum value in a 2D array using direct comparison
     * Time Complexity: O(n*m) where n and m are matrix dimensions
     * More efficient than sorting approach
     * 
     * @param num The 2D array to search
     * @return The minimum value found
     */
    public static int minimumNumInMultiDimensional_02(int num[][]) {
        if (num == null || num.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }
        
        int min = num[0][0];
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                if (num[i][j] < min) {
                    min = num[i][j];
                }
            }
        }
        return min;
    }
    
    /**
     * Finds maximum value in a 2D array using sorting approach
     * Time Complexity: O(n²) per row
     * 
     * @param num The 2D array to search
     * @return The maximum value found
     */
    public static int maximumNumInMultiDimensional_01(int num[][]) {
        if (num == null || num.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }
        
        int max = sortAndReturnMinOrMax(num[0], false);
        
        for (int i = 1; i < num.length; i++) {
            int rowMax = sortAndReturnMinOrMax(num[i], false);
            if (rowMax > max) {
                max = rowMax;
            }
        }
        return max;
    }
    
    /**
     * Finds maximum value in a 2D array using direct comparison
     * Time Complexity: O(n*m) - more efficient
     * 
     * @param num The 2D array to search
     * @return The maximum value found
     */
    public static int maximumNumInMultiDimensional_02(int num[][]) {
        if (num == null || num.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }
        
        int max = num[0][0];
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                if (num[i][j] > max) {
                    max = num[i][j];
                }
            }
        }
        return max;
    }
    
    /**
     * Finds the minimum value in a row and then finds the maximum value in that column
     * This is a common matrix algorithm pattern
     * 
     * @param num The 2D array to process
     * @return The maximum value in the column containing the minimum value
     */
    public static int minimumInRowMaximumInColumn(int num[][]) {
        if (num == null || num.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }
        
        // Find minimum value and its column
        int min = num[0][0];
        int minCol = 0;
        
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                if (num[i][j] < min) {
                    min = num[i][j];
                    minCol = j;
                }
            }
        }

        // Find maximum value in that column
        int max = num[0][minCol];
        for (int i = 1; i < num.length; i++) {
            if (num[i][minCol] > max) {
                max = num[i][minCol];
            }
        }
        return max;
    }
    
    /**
     * Transposes a matrix (rows become columns, columns become rows)
     * 
     * @param matrix The matrix to transpose
     * @return The transposed matrix
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        
        return transposed;
    }
    
    /**
     * Checks if a matrix is symmetric (equal to its transpose)
     * 
     * @param matrix The matrix to check
     * @return true if symmetric, false otherwise
     */
    public static boolean isSymmetric(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        if (rows != cols) {
            return false; // Non-square matrices can't be symmetric
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Utility method to print a 2D array in matrix format
     * 
     * @param matrix The matrix to print
     */
    public static void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Empty matrix");
            return;
        }
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Main method demonstrating all matrix operations
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("=== Multi-Dimensional Array Operations ===\n");
        
        // Basic operations
        basicDeclaration();
        
        System.out.println("=== Matrix Analysis ===");
        
        int num[][] = {
            {5, 0, 7},
            {67, 3, 23},
            {96, 75, 1}
        };
        
        System.out.println("Input Matrix:");
        printMatrix(num);
        
        System.out.println("\n--- Finding Minimum Values ---");
        System.out.println("Method 1 (Sorting): " + minimumNumInMultiDimensional_01(num));
        System.out.println("Method 2 (Direct): " + minimumNumInMultiDimensional_02(num));
        
        System.out.println("\n--- Finding Maximum Values ---");
        System.out.println("Method 1 (Sorting): " + maximumNumInMultiDimensional_01(num));
        System.out.println("Method 2 (Direct): " + maximumNumInMultiDimensional_02(num));
        
        System.out.println("\n--- Special Algorithm ---");
        System.out.println("Min in row, Max in column: " + minimumInRowMaximumInColumn(num));
        
        System.out.println("\n--- Matrix Operations ---");
        System.out.println("Transposed Matrix:");
        int[][] transposed = transposeMatrix(num);
        printMatrix(transposed);
        
        System.out.println("\nIs Original Matrix Symmetric? " + isSymmetric(num));
        
        // Test with symmetric matrix
        int[][] symmetricMatrix = {
            {1, 2, 3},
            {2, 4, 5},
            {3, 5, 6}
        };
        
        System.out.println("\nSymmetric Test Matrix:");
        printMatrix(symmetricMatrix);
        System.out.println("Is Symmetric? " + isSymmetric(symmetricMatrix));
        
        System.out.println("\n=== Performance Analysis ===");
        System.out.println("Method 1 (Sorting): O(n²) per row");
        System.out.println("Method 2 (Direct): O(n*m) total");
        System.out.println("Recommendation: Use Method 2 for better performance");
    }
}
