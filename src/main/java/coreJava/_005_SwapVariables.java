package coreJava;

/**
 * _005_SwapVariables - Different Methods to Swap Variable Values
 * 
 * This class demonstrates various techniques for swapping variable values:
 * - Using temporary variable (most common and readable)
 * - Arithmetic operations (addition/subtraction)
 * - Bitwise XOR operation (efficient for integers)
 * - One-liner using arithmetic
 * - Performance and readability considerations
 * 
 * Key Learning Points:
 * 1. Different swap methods have different trade-offs
 * 2. Temporary variable method is most readable and safe
 * 3. Arithmetic methods can cause overflow with large numbers
 * 4. XOR method is efficient but only works for integers
 * 5. Consider readability vs performance in your choice
 * 
 * @author Core Java Learning Project
 * @version 1.0
 * @since 2024
 */
public class _005_SwapVariables {

    /**
     * Swaps two integers using a temporary variable
     * 
     * Advantages:
     * - Most readable and understandable
     * - Works with any data type
     * - No risk of overflow
     * - Easy to debug
     * 
     * @param a First integer
     * @param b Second integer
     */
    public static void swapWithTempVariable(int a, int b) {
        System.out.println("=== Swap Using Temporary Variable ===");
        System.out.println("Before swap: a = " + a + ", b = " + b);
        
        int temp = a;
        a = b;
        b = temp;
        
        System.out.println("After swap: a = " + a + ", b = " + b);
        System.out.println();
    }
    
    /**
     * Swaps two integers using arithmetic operations
     * 
     * Advantages:
     * - No extra memory needed
     * - Works with integers
     * 
     * Disadvantages:
     * - Risk of integer overflow
     * - Less readable
     * - Only works with numeric types
     * 
     * @param a First integer
     * @param b Second integer
     */
    public static void swapWithoutTempVariable(int a, int b) {
        System.out.println("=== Swap Using Arithmetic Operations ===");
        System.out.println("Before swap: a = " + a + ", b = " + b);
        
        // Step-by-step explanation
        System.out.println("Step 1: a = a + b (" + a + " + " + b + " = " + (a + b) + ")");
        a = a + b;
        System.out.println("Step 2: b = a - b (" + a + " - " + b + " = " + (a - b) + ")");
        b = a - b;
        System.out.println("Step 3: a = a - b (" + a + " - " + b + " = " + (a - b) + ")");
        a = a - b;
        
        System.out.println("After swap: a = " + a + ", b = " + b);
        System.out.println();
    }
    
    /**
     * Swaps two integers using XOR bitwise operation
     * 
     * Advantages:
     * - No extra memory needed
     * - Very efficient
     * - No risk of overflow
     * 
     * Disadvantages:
     * - Less readable
     * - Only works with integers
     * - Can be confusing for beginners
     * 
     * @param a First integer
     * @param b Second integer
     */
    public static void swapUsingXOR(int a, int b) {
        System.out.println("=== Swap Using XOR Operation ===");
        System.out.println("Before swap: a = " + a + ", b = " + b);
        System.out.println("Binary: a = " + Integer.toBinaryString(a) + ", b = " + Integer.toBinaryString(b));
        
        // XOR swap algorithm
        a = a ^ b;
        System.out.println("Step 1: a = a ^ b = " + a + " (binary: " + Integer.toBinaryString(a) + ")");
        
        b = a ^ b;
        System.out.println("Step 2: b = a ^ b = " + b + " (binary: " + Integer.toBinaryString(b) + ")");
        
        a = a ^ b;
        System.out.println("Step 3: a = a ^ b = " + a + " (binary: " + Integer.toBinaryString(a) + ")");
        
        System.out.println("After swap: a = " + a + ", b = " + b);
        System.out.println();
    }
    
    /**
     * Swaps two integers using one-liner arithmetic
     * 
     * @param a First integer
     * @param b Second integer
     */
    public static void swapOneLiner(int a, int b) {
        System.out.println("=== Swap Using One-Liner ===");
        System.out.println("Before swap: a = " + a + ", b = " + b);
        
        // One-liner swap using arithmetic
        a = (a + b) - (b = a);
        
        System.out.println("After swap: a = " + a + ", b = " + b);
        System.out.println();
    }
    
    /**
     * Demonstrates overflow issue with arithmetic swap method
     * Shows why temporary variable method is safer
     */
    public static void demonstrateOverflow() {
        System.out.println("=== Overflow Demonstration ===");
        
        int a = Integer.MAX_VALUE - 100;
        int b = 200;
        
        System.out.println("Large numbers: a = " + a + ", b = " + b);
        System.out.println("a + b = " + (a + b) + " (This will overflow!)");
        
        // This will cause overflow
        try {
            int temp = a + b;
            System.out.println("Result after addition: " + temp);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Swaps two strings using temporary variable
     * Demonstrates that temporary variable method works with any data type
     * 
     * @param str1 First string
     * @param str2 Second string
     */
    public static void swapStrings(String str1, String str2) {
        System.out.println("=== Swap Strings ===");
        System.out.println("Before swap: str1 = \"" + str1 + "\", str2 = \"" + str2 + "\"");
        
        String temp = str1;
        str1 = str2;
        str2 = temp;
        
        System.out.println("After swap: str1 = \"" + str1 + "\", str2 = \"" + str2 + "\"");
        System.out.println();
    }
    
    /**
     * Swaps two objects of any type using temporary variable
     * Generic method that works with any data type
     * 
     * @param <T> The type of objects to swap
     * @param obj1 First object
     * @param obj2 Second object
     */
    public static <T> void swapGeneric(T obj1, T obj2) {
        System.out.println("=== Generic Swap ===");
        System.out.println("Before swap: obj1 = " + obj1 + ", obj2 = " + obj2);
        
        T temp = obj1;
        obj1 = obj2;
        obj2 = temp;
        
        System.out.println("After swap: obj1 = " + obj1 + ", obj2 = " + obj2);
        System.out.println();
    }
    
    /**
     * Performance comparison of different swap methods
     * Shows execution time for each method
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int a = 100, b = 200;
        int iterations = 1000000;
        
        // Test temporary variable method
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            int temp = a;
            a = b;
            b = temp;
        }
        long tempTime = System.nanoTime() - startTime;
        
        // Test XOR method
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            a = a ^ b;
            b = a ^ b;
            a = a ^ b;
        }
        long xorTime = System.nanoTime() - startTime;
        
        System.out.println("Temporary variable method: " + tempTime + " nanoseconds");
        System.out.println("XOR method: " + xorTime + " nanoseconds");
        System.out.println("Difference: " + Math.abs(tempTime - xorTime) + " nanoseconds");
        System.out.println();
    }
    
    /**
     * Main method demonstrating all swap techniques
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("=== Variable Swapping Techniques ===\n");
        
        int a = 5;
        int b = 7;
        
        // Demonstrate different swap methods
        swapWithTempVariable(a, b);
        swapWithoutTempVariable(a, b);
        swapUsingXOR(a, b);
        swapOneLiner(a, b);
        
        // Demonstrate with different data types
        swapStrings("Hello", "World");
        swapGeneric("Generic", "Example");
        
        // Show potential issues
        demonstrateOverflow();
        
        // Performance analysis
        performanceComparison();
        
        System.out.println("=== Summary ===");
        System.out.println("1. Temporary variable: Most readable and safe");
        System.out.println("2. Arithmetic: No extra memory, but risk of overflow");
        System.out.println("3. XOR: Efficient, no overflow, but less readable");
        System.out.println("4. Choose method based on your priorities (readability vs performance)");
    }
}
