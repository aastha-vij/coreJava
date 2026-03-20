package InterviewQuestions;

import java.util.Arrays;
import java.util.Stack;

public class _055_Reverse_First_Half_Array {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4, 5, 6, 7}; // {3,2,1,4,5,6,7}
        int[] arr2 = new int[] { 1, 2, 3, 4, 5, 6 }; // {3,2,1,4,5,6}
        System.out.println(Arrays.toString(_01_BFA(arr1)));
        System.out.println(Arrays.toString(_01_BFA(arr2)));

        System.out.println(Arrays.toString(_02_Using_Stack(arr1)));
        System.out.println(Arrays.toString(_02_Using_Stack(arr2)));
    }

    static int[] _01_BFA(int[] arr){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int mid = arr.length/2-1;
        int[] result = new int[arr.length];
        int index = 0;
        for (int i = mid; i >=0; i--) {
            result[index++] = arr[i];
        }
        for (int i = mid+1; i <arr.length ; i++) {
            result[index++] = arr[i];
        }
        return result;
    }

    static int[] _02_Using_Stack(int[] arr){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int mid = arr.length/2-1;
        int index = 0;
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i <= mid; i++)
            st.push(arr[i]);
        while(!st.empty())
            arr[index++] = st.pop();
        return arr;
    }
}
