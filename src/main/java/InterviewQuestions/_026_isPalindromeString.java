package InterviewQuestions;

public class _026_isPalindromeString {
    public static void main(String[] args) {
        String str1 = "Madam"; // true
        String str2 = "create"; // false
        String str3 = "A man a plan a canal Panama"; // true

        System.out.println(_01_BFA(str1));
        System.out.println(_01_BFA(str2));
        System.out.println(_01_BFA(str3));

        System.out.println(_02_Using_Two_Pointer_Technique(str1));
        System.out.println(_02_Using_Two_Pointer_Technique(str2));
        System.out.println(_02_Using_Two_Pointer_Technique(str3));
    }

    static boolean _01_BFA(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

    str = str.trim().replaceAll("\\s+","").toLowerCase();
    StringBuilder reverse = new StringBuilder();
        for (int i = str.length()-1; i >=0 ; i--)
            reverse.append(str.charAt(i));
        return str.contentEquals(reverse);
    }

    static boolean _02_Using_Two_Pointer_Technique(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        str = str.trim().replaceAll("\\s+", "").toLowerCase();
        int left = 0;
        int right = str.length()-1;
        while(left< right){
            if(str.charAt(left++)!=str.charAt(right--))
                return false;
        }
        return true;
    }
}
