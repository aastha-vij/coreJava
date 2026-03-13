package InterviewQuestions;

import java.util.HashSet;

public class _040_duplicatesInString {
    public static void main(String[] args) {
        String str = "Great responsibility"; // r e t s i
        System.out.println(_01_BFA(str));
    }

    static HashSet<Character> _01_BFA (String str) {
        // Time complexity: O(n^2)
        // Space complexity: O(n)

        HashSet<Character> hs = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = i+1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j))
                    hs.add(str.charAt(i));
            }
        }
        return hs;
    }
}
