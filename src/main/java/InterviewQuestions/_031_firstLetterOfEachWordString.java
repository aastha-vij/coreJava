package InterviewQuestions;

public class _031_firstLetterOfEachWordString {
    public static void main(String[] args) {
        String str = "  geeks for geeks   "; // gfg
        _01_BFA(str);
    }

    static void _01_BFA(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        String[] splitStr = str.trim().split(" ");
        for (String s : splitStr)
            System.out.print(s.charAt(0));

    }
}
