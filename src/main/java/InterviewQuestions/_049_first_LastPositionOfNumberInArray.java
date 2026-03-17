package InterviewQuestions;

import java.util.Arrays;

public class _049_first_LastPositionOfNumberInArray {
    public static void main(String[] args) {
        int[] arr = {5, 7, 7, 8, 8, 10};
        int target = 8; // Output: [3, 4]
        System.out.println(Arrays.toString(_01_BFA(arr, target)));
    }

    static int[] _01_BFA(int[] arr, int target){
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
}
