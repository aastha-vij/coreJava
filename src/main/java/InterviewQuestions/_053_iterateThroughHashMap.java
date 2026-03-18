package InterviewQuestions;

import java.util.HashMap;
import java.util.Map;

public class _053_iterateThroughHashMap {
    public static void main(String[] args) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("India", "New Delhi");
        hm.put("France", "Paris");
        hm.put("Germany", "Berlin");
        hm.put("Australia", "Canberra");
        _01_Using_EntrySet(hm);
    }

    static void _01_Using_EntrySet(HashMap<String, String> hm){
        // Time Complexity: O(n)
        // Space Complexity: O(1)
        for (Map.Entry<String, String> entry : hm.entrySet())
            System.out.println("Key: "+entry.getKey() + ", Value: "+entry.getValue());
    }
}
