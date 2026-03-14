package InterviewQuestions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _043_countCapitalizedWordsInString {
    public static void main(String[] args) {
        String str1 = "AasthaVij"; // 2
        String str2 = "UnderTaker Dead$%Man"; // 4
        System.out.println(_01_BFA(str1));
        System.out.println(_01_BFA(str2));

        System.out.println(_02_Using_RegX(str1));
        System.out.println(_02_Using_RegX(str2));

    }

    static int _01_BFA (String str) {
        // Time complexity: O(n)
        // Space complexity: O(n)

        int count = 0;
        for (char ch : str.toCharArray()) {
            if(Character.isUpperCase(ch))
                count++;
        }
        return count;
    }

    static int _02_Using_RegX (String str) {
        // Time complexity: O(n)
        // Space complexity: O(1)

        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher match = pattern.matcher(str);
        int count = 0;
        while (match.find())
            count++;
        return count;
    }
}
