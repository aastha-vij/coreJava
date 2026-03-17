package InterviewQuestions;

public class _050_printNumbers1to100_WithoutUsingAnyLoop {

    public static void main(String[] args) {
        _01_Using_Recursion(1);

    }

    static void _01_Using_Recursion(int n) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        if(n<=100){
            System.out.println(n);
            _01_Using_Recursion(n+1);
        }
    }
}
