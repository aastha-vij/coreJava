package InterviewQuestions;

public class _043_countCapitalizedWordsInString {
    public static void main(String[] args) {
        String str1 = "AasthaVij"; // 2
        String str2 = "UnderTaker Dead$%Man"; // 4
        System.out.println(_01_BFA(str1));
        System.out.println(_01_BFA(str2));
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
}
