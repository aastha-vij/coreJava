package InterviewQuestions;

public class _044_longestConsecutiveOccurrenceOfIntegersInArray {
    public static void main(String[] args) {
        int[] arr = {4, 3, 25, 6, 7, 8, 9, 2, 3, 10}; // 4
        System.out.println(_01_BFA(arr));
    }

    static int _01_BFA(int[] arr) {
        // Time complexity: O(n)
        // Space complexity: O(1)

        int max = 0;
        int count = 0;
        for (int i = 0; i < arr.length-1 ; i++) {
            if(arr[i] +1 == arr[i+1])
                count++;
            else
                count = 0;
            max = Math.max(max, count+1);
        }
        return max;
    }
}
