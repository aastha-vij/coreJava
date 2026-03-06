package InterviewQuestions;

import java.util.Arrays;

public class _018_AscendingOrderArray {
    public static void main(String[] args){
        int[] arr1 = new int[] { 5, 2, 8, 7, 1 }; // 1 2 5 7 8
        Arrays.stream(_01_BFA(arr1)).forEach(value -> System.out.print(value+ " "));
    }

    static int[] _01_BFA(int[] arr){
        // Time complexity: O(n^2)
        // Space complexity: O(1)

        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i]>arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
}
