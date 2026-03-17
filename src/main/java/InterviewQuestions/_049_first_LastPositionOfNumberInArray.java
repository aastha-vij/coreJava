package InterviewQuestions;

import java.util.Arrays;

public class _049_first_LastPositionOfNumberInArray {
    public static void main(String[] args) {
        int[] arr = {5, 7, 7, 8, 8, 10};
        int target = 8; // Output: [3, 4]
        System.out.println(Arrays.toString(_01_BFA(arr, target)));
        System.out.println(Arrays.toString(_02_UsingLinearIteration(arr, target)));
    }

    static int[] _01_BFA(int[] arr, int target){
        // Time Complexity: O(n^2)
        // Space Complexity: O(1)

        for (int i = 0; i < arr.length; i++) {
            if(arr[i]==target) {
                for (int j = i+1; j < arr.length ; j++) {
                    if(arr[i]== arr[j])
                        return new int[] {i,j};
                }
            }
        }
        return null;
    }

    static int[] _02_UsingLinearIteration (int[] arr, int target) {
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        int first = -1, last = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                if (first == -1)
                    first = i;
                last = i;
            }
        }
        return new int[]{first, last};
    }
}
