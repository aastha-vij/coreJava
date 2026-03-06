package InterviewQuestions;

public class _019_secondLargestNumberArray {
    public static void main(String[] args){
        int[] arr1 = { 2, 100, 10, 100, 2, 10, 11, 2, 11, 2 }; // 11
        System.out.println(_01_UsingLinearScan(arr1));
    }

    static int _01_UsingLinearScan(int[] arr1){
        // Time complexity: O(n)
        // Space complexity: O(1)

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : arr1){
            if(num>largest){
                secondLargest = largest;
                largest = num;
            } else if (num>secondLargest && num!=largest)
                secondLargest = num;
        }
        return  secondLargest;
    }
}
