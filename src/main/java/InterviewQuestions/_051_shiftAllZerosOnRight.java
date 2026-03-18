package InterviewQuestions;

import java.util.Arrays;

public class _051_shiftAllZerosOnRight {
    public static void main(String[] args) {
        int[] arr1 = {1, 0, 2, 0, 3, 0, 0, 0}; // 1, 2, 3, 0, 0, 0, 0, 0
        int[] arr2 = { 1, 0, 2, 0, 3, 0, 0, 0 }; // 1, 2, 3, 0, 0, 0, 0, 0

        System.out.println(Arrays.toString(_01_BFA(arr1)));
        System.out.println(Arrays.toString(_02_Using_InPlaceApproach(arr2)));
    }

    static int[] _01_BFA(int[] arr ){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int index = 0;
        int[] shiftedArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != 0)
                shiftedArr[index++] = arr[i];
        }
        return shiftedArr;
    }

    static int[] _02_Using_InPlaceApproach(int[] arr){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != 0){
                arr[index++] = arr[i];
            }
        }
        while(index < arr.length)
            arr[index++] = 0;
        return arr;
    }
}
