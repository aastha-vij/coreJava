package InterviewQuestions;

public class _048_maximumConsecutiveOnesInArray {
    public static void main(String[] args) {
        int[] arr = { 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1 }; // 5
        System.out.println(_01_BFA(arr));
    }

    static int _01_BFA(int[] arr){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        int count = 0;
        int maxCount = 0;
        for (int num : arr) {
            if (num == 1)
                count++;
            else
                count = 0;
            maxCount = Math.max(maxCount, count);
        }
        return maxCount;
    }
}
