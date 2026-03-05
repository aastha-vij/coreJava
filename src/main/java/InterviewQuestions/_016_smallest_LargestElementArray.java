package InterviewQuestions;

public class _016_smallest_LargestElementArray {
    public static void main(String[] args) {
        int[] arr = { 2, 10, 10, 100, 2, 10, 11, 2, 1, 2 }; // smallest : 1, Largest : 100
        _01_UsingLinearScan(arr);
    }

    static void _01_UsingLinearScan(int[] arr) {
        // Time complexity: O(n)
        // Space complexity: O(1)

        int smallest = Integer.MAX_VALUE;
        int largest = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num > largest)
                largest = num;
            if (num < smallest)
                smallest = num;
        }
        System.out.println("Smallest: "+ smallest + ", "+ "Largest: "+ largest);
    }
}
