package coreJava;

/**
 * _002_Secondclass - Demonstrating Class Interaction and Method Chaining
 * 
 * This class shows how classes can work together and demonstrates:
 * - Method chaining (fluent interface pattern)
 * - Class interaction and communication
 * - Return value utilization
 * - Basic object-oriented principles
 * 
 * Key Learning Points:
 * 1. Methods can return objects to enable chaining
 * 2. Classes can interact through object references
 * 3. Method chaining improves code readability
 * 4. Each method call can build upon previous results
 * 
 * @author Core Java Learning Project
 * @version 1.0
 * @since 2024
 */
public class _002_Secondclass {
    
    // Instance variables to track state
    private int callCount = 0;
    private String lastOperation = "None";
    
    /**
     * Basic method that prints a message
     * @return this object for method chaining
     */
    public _002_Secondclass setData() {
        callCount++;
        lastOperation = "setData";
        System.out.println("Inside setData for Second Class (Call #" + callCount + ")");
        return this; // Return 'this' to enable method chaining
    }
    
    /**
     * Method that displays information about the class
     * @return this object for method chaining
     */
    public _002_Secondclass displayInfo() {
        callCount++;
        lastOperation = "displayInfo";
        System.out.println("Second Class Information:");
        System.out.println("  - Total method calls: " + callCount);
        System.out.println("  - Last operation: " + lastOperation);
        System.out.println("  - Class purpose: Demonstrate method chaining");
        return this;
    }
    
    /**
     * Method that performs a calculation and shows result
     * @param value The value to process
     * @return this object for method chaining
     */
    public _002_Secondclass processValue(int value) {
        callCount++;
        lastOperation = "processValue";
        int result = value * 2 + 1;
        System.out.println("Processed value " + value + " -> " + result);
        return this;
    }
    
    /**
     * Method that resets the call counter
     * @return this object for method chaining
     */
    public _002_Secondclass reset() {
        callCount = 0;
        lastOperation = "reset";
        System.out.println("Call counter reset to 0");
        return this;
    }
    
    /**
     * Getter method for call count
     * @return current call count
     */
    public int getCallCount() {
        return callCount;
    }
    
    /**
     * Getter method for last operation
     * @return last operation performed
     */
    public String getLastOperation() {
        return lastOperation;
    }
    
    /**
     * Demonstrates the power of method chaining
     * This method shows how multiple operations can be performed in one line
     */
    public void demonstrateChaining() {
        System.out.println("\n=== Method Chaining Demonstration ===");
        
        // Perform multiple operations in a single chain
        this.setData()
            .displayInfo()
            .processValue(5)
            .processValue(10)
            .displayInfo()
            .reset()
            .displayInfo();
            
        System.out.println("=== Chaining Complete ===");
    }
    
    /**
     * Main method to demonstrate the class functionality independently
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("=== Second Class Independent Demo ===");
        
        _002_Secondclass sc = new _002_Secondclass();
        
        // Demonstrate individual method calls
        sc.setData();
        sc.displayInfo();
        
        // Demonstrate method chaining
        sc.demonstrateChaining();
        
        System.out.println("=== Demo Complete ===");
    }
}
