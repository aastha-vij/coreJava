package InterviewQuestions;

import java.util.Arrays;

public class _046_productOfAnArrayExceptSelf {
    public static void main(String[] args) {
        int[] arr = {10, 3, 5, 6, 2}; // Output: prod[] = {180, 600, 360, 300, 900}
        System.out.println(Arrays.toString(_01_BFA(arr)));
    }

    static int[] _01_BFA(int[] arr){
        // Time Complexity: O(n^2)
        // Space Complexity: O(n)

        int[] prod = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int product = 1;
            for (int j = 0; j < arr.length ; j++) {
                if(i!=j)
                    product*= arr[j];
            }
            prod[i] = product;
        }
        return prod;
    }
}
