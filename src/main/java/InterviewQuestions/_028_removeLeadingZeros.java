package InterviewQuestions;

public class _028_removeLeadingZeros {
    public static void main(String[] args) {
        String str1 = "00000123569";// 123569
        String str2 = "000012356090";// 12356090

        System.out.println(_01_BFA(str1));
        System.out.println(_01_BFA(str2));

        System.out.println(_02_Using_Substring(str1));
        System.out.println(_02_Using_Substring(str2));

        System.out.println(_03_Using_RegX(str1));
        System.out.println(_03_Using_RegX(str2));
    }

    static StringBuilder _01_BFA(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int start = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i)!='0') {
                start = i;
                break;
            }
        }
        for (int i = start; i < str.length(); i++) {
            sb.append(str.charAt(i));
        }
        return sb;
    }

    static String _02_Using_Substring(String str) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != '0')
                return str.substring(i);
        }
        return str;
    }

    static String _03_Using_RegX(String str) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return str.replaceFirst("^0+", "");
    }
}
