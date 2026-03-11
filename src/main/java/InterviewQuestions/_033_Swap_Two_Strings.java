package InterviewQuestions;

public class _033_Swap_Two_Strings {
    public static void main(String[] args) {
        String s1 = "Aastha";
        String s2 = "Vij";
        _01_Without_Using_Temp_Variable(s1, s2);
        _02_Using_Temp_Variable(s1, s2);
        _03_Using_Array(s1, s2);
    }

    static void _01_Without_Using_Temp_Variable(String str1, String str2){
        // Time complexity: O(n)
        // Space complexity: O(n)

        System.out.println("Before Swap: "+ "S1: "+ str1 + " S2: "+str2);

        str1 = str1 + str2; // AasthaVij
        str2 = str1.substring(0, str1.length()-str2.length()); // Aastha
        str1 = str1.substring(str2.length());

        System.out.println("After Swap: "+ "S1: "+ str1 + " S2: "+str2);
    }

    static  void _02_Using_Temp_Variable(String str1, String str2){
        // Time complexity: O(1)
        // Space complexity: O(1)

        System.out.println("Before Swap: "+ "S1: "+ str1 + " S2: "+str2);

        String temp = str1;
        str1 = str2;
        str2 = temp;

        System.out.println("After Swap: "+ "S1: "+ str1 + " S2: "+str2);
    }

    static void _03_Using_Array(String str1, String str2){
        // Time complexity: O(1)
        // Space complexity: O(1)

        System.out.println("Before Swap: "+ "S1: "+ str1 + " S2: "+str2);
        String[] arr = {str1, str2};
        str2 =  arr[0];
        str1 = arr[1];
        System.out.println("After Swap: "+ "S1: "+ str1 + " S2: "+str2);
    }
}
