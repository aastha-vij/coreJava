package InterviewQuestions;

import java.util.Stack;

public class _027_reverseString {
    public static void main(String[] args) {
        String str = "Geeks"; // skeeG
        System.out.println(_01_BFA(str));
        System.out.println(_02_Using_Stack(str));
    }

    static String _01_BFA(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        StringBuilder reverse = new StringBuilder();
        for (int i = str.length()-1; i >= 0 ; i--) {
            reverse.append(str.charAt(i));
        }
        return reverse.toString();
    }

    static StringBuilder _02_Using_Stack(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        StringBuilder reverse = new StringBuilder();
        Stack<Character> st = new Stack<>();

        for(char ch : str.toCharArray())
            st.add(ch);
        while(!st.empty())
            reverse.append(st.pop());
        return reverse;
    }
}
