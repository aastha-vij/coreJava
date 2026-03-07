package InterviewQuestions;

public class _023_removeAllWhiteSpacesFromString {
    public static void main(String[] args) {
        String str = "Hello World 123";
        System.out.println(_01_UsingReplace(str));
        System.out.println(_02_UsingReplaceAll(str));
    }

    static String _01_UsingReplace(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return str.replace(" ", "");
    }

    static String _02_UsingReplaceAll(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return str.replaceAll("\\s", "");
    }
}
