package coreJava;

/**
 * _001_Firstclass - Introduction to Java Classes and Objects
 * 
 * This class demonstrates fundamental concepts of Java:
 * - Class declaration and structure
 * - Method definition and calling
 * - Object creation and instantiation
 * - Package organization
 * - Basic Java syntax
 * 
 * Key Learning Points:
 * 1. Every Java class should be in a package
 * 2. Class names should follow PascalCase convention
 * 3. Methods can be instance methods (non-static) or class methods (static)
 * 4. Objects are created using the 'new' keyword
 * 
 * @author Core Java Learning Project
 * @version 1.0
 * @since 2024
 */
public class _001_Firstclass {

    // Instance variable - belongs to each object
    private String className = "First Class";
    
    // Static variable - shared across all objects
    public static final String PROJECT_NAME = "Core Java Learning";
    
    /**
     * Instance method that demonstrates basic method functionality
     * This method can only be called on an object instance
     */
    public void getData() {
        System.out.println("Inside getData Method in " + className);
        System.out.println("Project: " + PROJECT_NAME);
    }
    
    /**
     * Static method that can be called without creating an object
     * Demonstrates the difference between static and instance methods
     */
    public static void displayProjectInfo() {
        System.out.println("Project: " + PROJECT_NAME);
        System.out.println("This is a static method - no object needed!");
    }
    
    /**
     * Method demonstrating parameter passing and return values
     * @param message The message to display
     * @return A formatted response string
     */
    public String processMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "No message provided";
        }
        return "Processed: " + message.toUpperCase();
    }
    
    /**
     * Main method - entry point of the program
     * Demonstrates:
     * 1. Object creation
     * 2. Method calling
     * 3. Cross-class communication
     * 
     * @param args Command line arguments (unused in this example)
     */
    public static void main(String[] args) {
        System.out.println("=== Welcome to Core Java Learning Project ===");
        System.out.println("Project: " + PROJECT_NAME);
        
        // Demonstrate static method call
        displayProjectInfo();
        
        System.out.println("\n--- Object Creation and Method Calling ---");
        
        // Create object of current class
        _001_Firstclass fn = new _001_Firstclass();
        fn.getData();
        
        // Demonstrate parameter passing
        String result = fn.processMessage("hello world");
        System.out.println("Message processing result: " + result);
        
        // Test edge case
        String emptyResult = fn.processMessage("");
        System.out.println("Empty message result: " + emptyResult);
        
        System.out.println("\n--- Cross-Class Communication ---");
        
        // Create object of another class
        _002_Secondclass sc = new _002_Secondclass();
        sc.setData();
        
        // Demonstrate method chaining
        sc.setData().displayInfo();
        
        System.out.println("\n=== Program Execution Complete ===");
    }
}
