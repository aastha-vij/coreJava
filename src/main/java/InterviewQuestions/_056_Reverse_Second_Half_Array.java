package InterviewQuestions;

import java.util.Arrays;
import java.util.Stack;

public class _056_Reverse_Second_Half_Array {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4, 5, 6, 7}; // {1, 2, 3, 7, 6, 5, 4 }
        int[] arr2 = new int[]{1, 2, 3, 4, 5, 6}; // {1, 2, 3, 6, 5, 4 }

        System.out.println(Arrays.toString(_01_BFA(arr1)));
        System.out.println(Arrays.toString(_01_BFA(arr2)));

        System.out.println(Arrays.toString(_02_Using_Stack(arr1)));
        System.out.println(Arrays.toString(_02_Using_Stack(arr2)));

        System.out.println(Arrays.toString(_03_Using_Two_Pointer(arr1)));
        System.out.println(Arrays.toString(_03_Using_Two_Pointer(arr2)));
    }

    static int[] _01_BFA(int[] arr) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        int mid = arr.length /2;
        int[] result = new int[arr.length];
        int index = mid;
        for (int i = 0; i <= mid; i++)
            result[i] = arr[i];
        for (int i = arr.length-1; i >=mid; i--) {
            result[index++] = arr[i];
        }
        return result;
    }

    static int[] _02_Using_Stack(int[] arr) {
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        Stack<Integer> st = new Stack<>();
        int mid = arr.length/2;
        for (int i = mid; i < arr.length; i++)
            st.push(arr[i]);
        while(!st.empty())
            arr[mid++] = st.pop();
        return arr;
    }

    static int[] _03_Using_Two_Pointer(int[] arr){
        int start = arr.length/2;
        int last = arr.length-1;
        int temp = 0;
        while(start< last){
            temp = arr[start];
            arr[start] = arr[last];
            arr[last] = temp;
            start++;
            last--;
        }
        return arr;
    }
}
