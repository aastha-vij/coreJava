package InterviewQuestions;

public class _041_swapTwoNumbers {
    public static void main(String[] args) {
        int num1 = 12;
        int num2 = 24;
        _01_UsingTempVariable(num1, num2);
    }

    static void _01_UsingTempVariable (int num1, int num2) {
        // Time complexity: O(1)
        // Space complexity: O(1)

        System.out.println("Before Swap: ");
        System.out.println("num1: " + num1);
        System.out.println("num2: " + num2);

        int temp = num1;
        num1 = num2;
        num2 = temp;

        System.out.println("After Swap: ");
        System.out.println("num1: " + num1);
        System.out.println("num2: " + num2);
    }
}
