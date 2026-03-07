package InterviewQuestions;

import java.util.Arrays;

public class _021_secondSmallest_LargestNumberArray {
        public static void main(String[] args) {
            int[] arr = {2, 100, 10, 100, 3, 10, 11, 2, 11, 2}; // SecondSmallest: 3 , SecondLargest: 11
            System.out.println(Arrays.toString(_01_Using_Linear_Scan(arr)));
        }

        static int[] _01_Using_Linear_Scan(int[] arr){
            // Time complexity: O(n)
            // Space complexity: O(1)

            int smallest = Integer.MAX_VALUE;
            int secondSmallest = Integer.MAX_VALUE;

            int largest = Integer.MIN_VALUE;
            int secondLargest = Integer.MIN_VALUE;

            for (int num: arr){
                if(num<smallest){
                    secondSmallest = smallest;
                    smallest = num;
                }
                else if(num<secondSmallest && num!=smallest)
                    secondSmallest = num;

                else if(num> largest){
                    secondLargest = largest;
                    largest = num;
                } else if (num>secondLargest && num!=largest)
                    secondLargest = num;
            }
            return new int[]{secondSmallest, secondLargest};
        }
}
