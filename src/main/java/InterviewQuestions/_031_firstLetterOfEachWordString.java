package InterviewQuestions;

public class _031_firstLetterOfEachWordString {
    public static void main(String[] args) {
        String str = "  geeks for geeks   "; // gfg
        _01_BFA(str);
        System.out.println();
        System.out.print(_02_Using_String_Scan(str));
    }

    static void _01_BFA(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        String[] splitStr = str.trim().split(" ");
        for (String s : splitStr)
            System.out.print(s.charAt(0));

    }
    
    static String _02_Using_String_Scan(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if((i==0 && str.charAt(i) != ' ') ||
                    (i>0 && str.charAt(i-1)==' ' && str.charAt(i)!=' '))
                sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
