package InterviewQuestions;

public class _037_reverse_Number {
    public static void main(String[] args) {
        int num1 = 456; // 654
        int num2 = 899; // 998
        System.out.println(_01_BFA(num1));
        System.out.println(_01_BFA(num2));
    }

    static int _01_BFA(int num){
        // Time complexity: O(n)
        // Space complexity: O(1)
        
        int reverseNum = 0;
        while (num != 0){
            int remainder = num % 10;
            reverseNum = reverseNum * 10 + remainder;
            num /= 10;
        }
        return reverseNum;
    }
}
