package InterviewQuestions;

public class _045_stringOutputAsPerIntStrCombination {
    public static void main(String[] args) {
        String str = "a2b3c4"; // aabbbcccc
        System.out.println(_01_BFA(str));
    }

    static String _01_BFA (String str) {
        // Time Complexity: O(n^2)
        // Space Complexity: O(n)

        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if(Character.isAlphabetic(str.charAt(i)))
                result+=str.charAt(i);
            else {
                int num = Character.getNumericValue(str.charAt(i));
                for (int j = 1; j < num; j++) {
                    result+= str.charAt(i-1);
                }
            }
        }
        return result;
    }
}
