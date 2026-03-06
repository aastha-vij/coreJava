package InterviewQuestions;

import java.util.Arrays;

public class _020_secondSmallestNumberArray {
    public static void main(String[] args){
        int[] arr1 = { 3, 100, 10, 100, 2, 10, 11, 2, 11, 2 }; // 3
        System.out.println(_01_UsingLinearScan(arr1));

        int[] arr2 = { 500, 100, 10, 50, 300 }; // 50
        System.out.println(_02_UsingArraySort(arr2));
    }

    static int _01_UsingLinearScan(int[] arr1){
        // Time complexity: O(n)
        // Space complexity: O(1)

        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;
        for(int num : arr1){
            if(num<smallest){
                secondSmallest = smallest;
                smallest = num;
            } else if (num<secondSmallest && num!=smallest)
                secondSmallest = num;
        }
        return  secondSmallest;
    }

    static int _02_UsingArraySort(int[] arr){
        // Time complexity: O(nlogn)
        // Space complexity: O(1)

        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]!=arr[i+1])
                return arr[i+1];
        }
        return 0;
    }
}
