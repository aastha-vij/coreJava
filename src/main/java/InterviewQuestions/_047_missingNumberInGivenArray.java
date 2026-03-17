package InterviewQuestions;

import java.util.HashSet;

public class _047_missingNumberInGivenArray {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 5, 6}; // 4
        System.out.println(_01_BFA(arr));
        System.out.println(_02_Using_HashSet(arr));
    }

    static int _01_BFA( int[] arr ){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        int n = arr.length+1;
        int expectedSum = n*(n+1)/2;
        int actualSum = 0;
        for (int num : arr)
            actualSum+=num;
        return expectedSum - actualSum;
    }

    static int _02_Using_HashSet(int[] arr){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        HashSet<Integer> hs = new HashSet<>();
        for (int num : arr)
            hs.add(num);
        for (int num : hs){
            if(!hs.contains(num+1))
                return num+1;
        }
        return 0;
    }
}
