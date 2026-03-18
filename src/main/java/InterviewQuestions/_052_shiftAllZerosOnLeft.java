package InterviewQuestions;

import java.util.Arrays;

public class _052_shiftAllZerosOnLeft {
    public static void main(String[] args) {
        int[] arr1 = {1, 0, 2, 0, 3, 0, 0, 0}; // 0, 0, 0, 0, 0, 1, 2, 3
        System.out.println(Arrays.toString(_01_BFA(arr1)));
    }

    static int[] _01_BFA (int[] arr){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int[] shiftedArr = new int[arr.length];
        int index = arr.length-1;
        for (int i = arr.length-1; i >=0 ; i--) {
            if(arr[i] != 0)
                shiftedArr[index--] = arr[i];
        }
        return shiftedArr;
    }
}
