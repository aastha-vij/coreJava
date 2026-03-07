package InterviewQuestions;

import java.sql.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

public class _025_countNumberOfWordsInString {
    public static void main(String[] args) {
        String str = "    India Is My Country";
        System.out.println("Word Count using Spilt: " + _01_Using_Split_Array(str));
        System.out.println("Word Count using Stream: " + _02_UsingStream(str));
        System.out.println("Word Count using StringTokenizer: " + _03_UsingStringTokenizer(str));
    }

    static int _01_Using_Split_Array(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        String[] words = str.trim().split(" ");
        return words.length;
    }

    static long _02_UsingStream(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return Arrays.stream(str.trim().split("\\s+")).count();
    }

    static int _03_UsingStringTokenizer(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        StringTokenizer st = new StringTokenizer(str);
        return st.countTokens();
    }
}
