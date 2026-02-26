package coreJava;

/**
 * _003_NestedForLoop - Understanding Nested Loops and Pattern Printing
 * 
 * This class demonstrates various nested loop patterns commonly used in:
 * - Pattern printing (pyramids, triangles, etc.)
 * - Matrix operations
 * - Algorithm implementations
 * - Data processing
 * 
 * Key Learning Points:
 * 1. Nested loops have O(n²) complexity - be careful with large inputs
 * 2. Inner loop completes fully before outer loop increments
 * 3. Pattern printing helps understand loop control flow
 * 4. Different patterns require different loop conditions
 * 
 * @author Core Java Learning Project
 * @version 1.0
 * @since 2024
 */
public class _003_NestedForLoop {

    /**
     * Prints an inverted pyramid pattern with incrementing numbers
     * Pattern:
     * 1 2 3 4
     * 5 6 7
     * 8 9
     * 10
     */
    public static void invertedPyramid() {
        System.out.println("=== Inverted Pyramid Pattern ===");
        int temp = 1;
        for (int i = 1; i <= 4; i++) {
            // Print spaces for alignment (optional)
            for (int space = 1; space < i; space++) {
                System.out.print("  ");
            }
            
            for (int j = i; j <= 4; j++) {
                System.out.print(temp + " ");
                temp++;
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Prints an increasing pyramid pattern with incrementing numbers
     * Pattern:
     * 1
     * 2 3
     * 4 5 6
     * 7 8 9 10
     */
    public static void increasingPyramid() {
        System.out.println("=== Increasing Pyramid Pattern ===");
        int temp = 1;
        for (int i = 1; i <= 4; i++) {
            // Print spaces for alignment
            for (int space = 1; space <= 4 - i; space++) {
                System.out.print("  ");
            }
            
            for (int j = 1; j <= i; j++) {
                System.out.print(temp + " ");
                temp++;
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Prints a pyramid pattern with row numbers
     * Pattern:
     * 1
     * 1 2
     * 1 2 3
     * 1 2 3 4
     */
    public static void pyramidRevisit() {
        System.out.println("=== Row Number Pyramid Pattern ===");
        for (int i = 1; i <= 4; i++) {
            // Print spaces for alignment
            for (int space = 1; space <= 4 - i; space++) {
                System.out.print("  ");
            }
            
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Prints a diamond pattern
     * Pattern:
     *    *
     *   ***
     *  *****
     * *******
     *  *****
     *   ***
     *    *
     */
    public static void diamondPattern() {
        System.out.println("=== Diamond Pattern ===");
        int n = 4;
        
        // Upper half of diamond
        for (int i = 1; i <= n; i++) {
            // Print spaces
            for (int space = 1; space <= n - i; space++) {
                System.out.print(" ");
            }
            // Print stars
            for (int star = 1; star <= 2 * i - 1; star++) {
                System.out.print("*");
            }
            System.out.println();
        }
        
        // Lower half of diamond
        for (int i = n - 1; i >= 1; i--) {
            // Print spaces
            for (int space = 1; space <= n - i; space++) {
                System.out.print(" ");
            }
            // Print stars
            for (int star = 1; star <= 2 * i - 1; star++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Prints a number triangle with specific pattern
     * Pattern:
     * 1
     * 2 3
     * 4 5 6
     * 7 8 9 10
     */
    public static void numberTriangle() {
        System.out.println("=== Number Triangle Pattern ===");
        int num = 1;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(num + " ");
                num++;
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Demonstrates matrix traversal using nested loops
     * Shows how to access elements in a 2D array
     */
    public static void matrixTraversal() {
        System.out.println("=== Matrix Traversal Demo ===");
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("Matrix elements:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nMatrix elements (reverse order):");
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[i].length - 1; j >= 0; j--) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Demonstrates the concept of loop complexity
     * Shows how nested loops affect performance
     */
    public static void demonstrateComplexity() {
        System.out.println("=== Loop Complexity Demo ===");
        int n = 5;
        int operations = 0;
        
        System.out.println("Single loop (O(n)):");
        for (int i = 0; i < n; i++) {
            operations++;
            System.out.print(i + " ");
        }
        System.out.println("\nOperations: " + operations);
        
        operations = 0;
        System.out.println("\nNested loop (O(n²)):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                operations++;
                System.out.print("(" + i + "," + j + ") ");
            }
            System.out.println();
        }
        System.out.println("Operations: " + operations);
        System.out.println();
    }
    
    /**
     * Main method demonstrating all patterns and concepts
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("=== Nested For Loop Patterns and Examples ===\n");
        
        // Basic patterns
        invertedPyramid();
        increasingPyramid();
        pyramidRevisit();
        
        // Advanced patterns
        diamondPattern();
        numberTriangle();
        
        // Practical applications
        matrixTraversal();
        demonstrateComplexity();
        
        System.out.println("=== All Patterns Displayed ===");
        System.out.println("\nKey Takeaways:");
        System.out.println("1. Nested loops are powerful but can be expensive");
        System.out.println("2. Pattern printing helps understand loop control");
        System.out.println("3. Matrix operations heavily use nested loops");
        System.out.println("4. Always consider the complexity of nested loops");
    }
}
