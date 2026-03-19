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
        _02_Using_KeySet(hm);
        _03_Using_Values(hm);
        _04_Using_ForEach(hm);
    }

    static void _01_Using_EntrySet(HashMap<String, String> hm){
        // Time Complexity: O(n)
        // Space Complexity: O(1)
        for (Map.Entry<String, String> entry : hm.entrySet())
            System.out.println("Key: "+entry.getKey() + ", Value: "+entry.getValue());
    }

    static void _02_Using_KeySet(HashMap<String, String> hm){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        for (String key : hm.keySet())
            System.out.println("Key: "+key + ", Value: "+hm.get(key));
    }

    static void _03_Using_Values(HashMap<String, String> hm){
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        for (String value : hm.values())
            System.out.println("Value: "+value);
    }

    static void _04_Using_ForEach(HashMap<String, String> hm) {
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        hm.forEach((key, value)-> System.out.println("Key: "+key +", Value: "+value));
    }
}

