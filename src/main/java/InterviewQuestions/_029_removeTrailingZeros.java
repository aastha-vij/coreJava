package InterviewQuestions;

public class _029_removeTrailingZeros {
    public static void main(String[] args) {
        String str1 = "1230456000";   //1230456
        String str2 = "00001230456000";   //00001230456

        System.out.println(_01_Using_subString(str1));
        System.out.println(_01_Using_subString(str2));
    }

    static String _01_Using_subString(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int last = 0;
        for (int i = str.length()-1; i >=0; i--) {
            if(str.charAt(i) != '0'){
                last = i;
                break;
            }
        }
        return str.substring(0, last+1);
    }
}
