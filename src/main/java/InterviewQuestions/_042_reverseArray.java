package InterviewQuestions;

public class _042_reverseArray {
    public static void main(String[] args) {
        char[] arr1 = {'a', 'b', 'c', 'd', 'e'}; // e d c b a
        System.out.println(_01_BFA(arr1));

        char[] arr2 = { 'a', 'b', 'c', 'd', 'e' }; // e d c b a
        System.out.println(_02_Using_Two_Pointer(arr2));
    }

    static char[] _01_BFA (char[] arr) {
        // Time complexity: O(n)
        // Space complexity: O(n)

        char[] reverse = new char[arr.length];
        for (int i = 0; i < arr.length; i++)
            reverse[i] = arr[arr.length-1-i];
        return reverse;
    }

    static char[] _02_Using_Two_Pointer (char[] arr) {
        // Time complexity: O(n)
        // Space complexity: O(1)

        int left = 0, right = arr.length-1;
        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
        return arr;
    }
}
