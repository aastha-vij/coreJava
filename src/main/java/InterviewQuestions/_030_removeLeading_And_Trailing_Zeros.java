package InterviewQuestions;

public class _030_removeLeading_And_Trailing_Zeros {
    public static void main(String[] args) {
        String str1 = "1230456000000"; // 1230456
        String str2 = "00001230456000"; // 1230456

        System.out.println(_01_Using_subString(str1));
        System.out.println(_01_Using_subString(str2));

        System.out.println(_02_Using_Two_Pointer(str1));
        System.out.println(_02_Using_Two_Pointer(str2));
    }

    static String _01_Using_subString(String str) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int start = 0;
        int end = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != '0'){
                start = i;
                break;
            }
        }
        for (int i = str.length()-1; i >= 0 ; i--) {
            if(str.charAt(i) != '0'){
                end = i;
                break;
            }
        }
        return str.substring(start, end+1);
    }

    static String _02_Using_Two_Pointer(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int start = 0;
        int end = str.length()-1;

        while(start<end && str.charAt(start) == '0')
            start++;

        while(end> start && str.charAt(end) == '0')
            end--;

        return str.substring(start, end+1);
    }
}
