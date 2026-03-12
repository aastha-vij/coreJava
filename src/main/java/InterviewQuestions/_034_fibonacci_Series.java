package InterviewQuestions;

import java.util.ArrayList;
import java.util.List;

public class _034_fibonacci_Series {
    public static void main(String[] args) {
        int num = 6; // 0,1,1,2,3,5
        System.out.print(_01_BFA(num));
    }

    static List<Integer> _01_BFA(int num){
        // Time complexity: O(n)
        // Space complexity: O(1)
        
        int sum = 0, a = 1, b = 1;
        List<Integer> fib = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            fib.add(sum);
            if(i==2)
                sum = 1;
            else {
                sum+=a;
                a= b;
                b = sum;
            }
        }
        return fib;
    }
}