package InterviewQuestions;

import java.util.Arrays;

public class _039_countNumber_Of_Characters_In_String {
    public static void main(String[] args) {
        String str = "The best of both worlds"; // 19
        System.out.println(_01_BFA(str));
    }

    static int _01_BFA(String str){
        // Time complexity: O(n)
        // Space complexity: O(1)

        int count = 0;
        for (char ch : str.toCharArray()) {
            if(ch != ' ')
                count++;
        }
        return count;
    }
}
