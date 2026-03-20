package InterviewQuestions;

import java.util.Arrays;

public class _056_Reverse_Second_Half_Array {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4, 5, 6, 7}; // {1, 2, 3, 7, 6, 5, 4 }
        int[] arr2 = new int[]{1, 2, 3, 4, 5, 6}; // {1, 2, 3, 6, 5, 4 }
        System.out.println(Arrays.toString(_01_BFA(arr1)));
        System.out.println(Arrays.toString(_01_BFA(arr2)));
    }

    private static int[] _01_BFA(int[] arr) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int mid = arr.length /2 -1;
        int[] result = new int[arr.length];
        int index = mid+1;
        for (int i = 0; i <= mid; i++)
            result[i] = arr[i];
        for (int i = arr.length-1; i >mid; i--) {
            result[index++] = arr[i];
        }
        return result;
    }
}
