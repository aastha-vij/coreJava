package InterviewQuestions;

public class _028_removeLeadingZeros {
    public static void main(String[] args) {
        String str1 = "00000123569";// 123569
        String str2 = "000012356090";// 12356090
        System.out.println(_01_Using_Substring(str1));
        System.out.println(_01_Using_Substring(str2));

        System.out.println(_02_Using_RegX(str1));
        System.out.println(_02_Using_RegX(str2));
    }

    static String _01_Using_Substring(String str) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != '0')
                return str.substring(i);
        }
        return str;
    }

    static String _02_Using_RegX(String str) {
        return str.replaceFirst("^0+", "");
    }
}
