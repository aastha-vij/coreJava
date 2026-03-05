package InterviewQuestions;

public class _015_smallestElementArray {
    public static void main(String[] args) {
        int[] arr = { 2, 10, 10, 100, 2, 10, 11, 2, 1, 2 }; // 1
        System.out.println(_01_BFA(arr));
    }

    static int _01_BFA(int[] arr){
        // Time complexity: O(n^2)
        // Space complexity: O(1)

        for (int i : arr) {
            boolean smallest = true;
            for (int j : arr) {
                if (i > j) {
                    smallest = false;
                    break;
                }
            }
            if (smallest)
                return i;
        }
        return 0;
    }
}
