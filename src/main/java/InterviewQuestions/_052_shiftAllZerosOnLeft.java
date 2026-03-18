package InterviewQuestions;

import java.util.Arrays;

public class _052_shiftAllZerosOnLeft {
    public static void main(String[] args) {
        int[] arr1 = {1, 0, 2, 0, 3, 0, 0, 0}; // 0, 0, 0, 0, 0, 1, 2, 3
        int[] arr2 = { 1, 0, 2, 0, 3, 0, 0, 0 }; // 0, 0, 0, 0, 0, 1, 2, 3

        System.out.println(Arrays.toString(_01_BFA(arr1)));
        System.out.println(Arrays.toString(_02_Using_InPlaceApproach(arr2)));
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

    private static int[] _02_Using_InPlaceApproach(int[] arr) {
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        int index = arr.length-1;
        for (int i = arr.length-1; i >=0 ; i--) {
            if(arr[i] != 0)
                arr[index--] = arr[i];
        }
        while (index >=0)
            arr[index--] = 0;
        return arr;
    }
}
