package InterviewQuestions;

public class _025_countNumberOfWordsInString {
    public static void main(String[] args) {
        String str = "    India Is My Country";
        System.out.println("Word Count using spilt: " + _01_Using_Split_Array(str));
    }

    static int _01_Using_Split_Array(String str){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        String[] words = str.trim().split(" ");
        return words.length;
    }
}
