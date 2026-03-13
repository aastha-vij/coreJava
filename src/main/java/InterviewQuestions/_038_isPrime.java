package InterviewQuestions;

public class _038_isPrime {
    public static void main(String[] args) {
        int num1 = 2; // true
        int num2 = 23; // true
        int num3 = 55; // false

        System.out.println(_01_BFA(num1));
        System.out.println(_01_BFA(num2));
        System.out.println(_01_BFA(num3));
    }

    static boolean _01_BFA(int num){
        // Time complexity: O(sqrt(n))
        // Space complexity: O(1)

        if (num == 1) return false;
        else if (num == 2) return true;
        else if (num % 2 == 0) return false;

        for (int i = 3; i <= Math.sqrt(num); i += 2)
            if (num % i == 0) return false;
        return true;
    }
}
