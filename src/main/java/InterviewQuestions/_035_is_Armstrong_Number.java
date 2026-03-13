package InterviewQuestions;

import static java.lang.System.*;

public class _035_is_Armstrong_Number {
    /* num = 153
    / Sum of digits = 3
    / 1^3 + 5^3 +3^3 = 1+ 125 +27 = 153 == num : isArmstrongNum
    */

    public static void main(String[] args) {
        int num1 = 153;
        int num2 = 1634;
        int num3 = 5767;
        System.out.println(_01_BFA(num1));
        System.out.println(_01_BFA(num2));
        System.out.println(_01_BFA(num3));
    }

    static boolean _01_BFA(int num){
        // Time complexity: O(d) d-> digits in num
        // Space complexity: O(1)

        int numLen = Integer.toString(num).length();
        int originalNum = num;
        int sum = 0;
        while (num != 0) {
            int digit = num % 10;
            sum += (int)Math.pow(digit, numLen);
            num /= 10;
        }
        return originalNum == sum;
    }
}
