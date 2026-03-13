package InterviewQuestions;

public class _036_sum_Of_Digits_Given_Number {
    public static void main(String[] args) {
        int num1 = 123; // 6
        int num2 = 69; // 15

        System.out.println(_01_BFA(num1));
        System.out.println(_01_BFA(num2));
    }

    static int _01_BFA(int num){
        // Time complexity: O(n)
        // Space complexity: O(1)

        int sum = 0;
        while(num!=0){
            int digit = num % 10;
            sum += digit;
            num /= 10;
        }
        return sum;
    }
}
