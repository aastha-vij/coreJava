package InterviewQuestions;

import java.util.HashSet;
import java.util.Set;

public class _044_longestConsecutiveOccurrenceOfIntegersInArray {
    public static void main(String[] args) {
        int[] arr = {4, 3, 25, 6, 7, 8, 9, 2, 3, 10}; // 5
        System.out.println(_01_BFA(arr));
        System.out.println(_02_UsingHashSet(arr));
    }

    static int _01_BFA(int[] arr) {
        // Time complexity: O(n)
        // Space complexity: O(1)

        int max = 0;
        int count = 0;
        for (int i = 0; i < arr.length-1 ; i++) {
            if(arr[i] +1 == arr[i+1])
                count++;
            else
                count = 1;
            max = Math.max(max, count+1);
        }
        return max;
    }

    static int _02_UsingHashSet (int[] arr) {
        // Time: O(n)
        // Space: O(n)

        Set<Integer> set = new HashSet<>();

        for (int num : arr)
            set.add(num);

        int longestStreak=0;

        for (int num : set) {
            if(!set.contains(num-1)) {
                int currentNum = num;
                int currentStreak = 1;

                while(set.contains(currentNum+1)) {
                    currentNum++;
                    currentStreak++;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;
    }
}
