package InterviewQuestions;

public class _020_secondSmallestNumberArray {
    public static void main(String[] args){
        int[] arr1 = { 3, 100, 10, 100, 2, 10, 11, 2, 11, 2 }; // 3
        System.out.println(_01_UsingLinearScan(arr1));
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
}
