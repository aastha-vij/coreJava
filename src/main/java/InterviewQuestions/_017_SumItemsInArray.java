package InterviewQuestions;

public class _017_SumItemsInArray {

    public static void main(String[] args) {
        int[] arr = { 15, 12, 13, 10 }; // 50
        System.out.println(_01_UsingForLoop(arr));
        System.out.println(_02_UsingWhileLoop(arr));
    }

    static int _01_UsingForLoop(int[] arr){
        // Time complexity: O(n)
        // Space complexity: O(1)

        int sum = 0;
        for (int num : arr)
            sum += num;
        return sum;
    }

    static int _02_UsingWhileLoop(int[] arr){
        // Time complexity: O(n)
        // Space complexity: O(1)

        int sum = 0;
        int i = 0;
        while(i<arr.length){
            sum+=arr[i];
            i++;
        }
        return sum;
    }
}
