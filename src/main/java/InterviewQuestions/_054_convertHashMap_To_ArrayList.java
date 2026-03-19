package InterviewQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class _054_convertHashMap_To_ArrayList {
    public static void main(String[] args) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("India", "New Delhi");
        hm.put("France", "Paris");
        hm.put("Germany", "Berlin");
        hm.put("Australia", "Canberra");

        System.out.println(_01_Convert_EntriesToArrayList(hm));
        System.out.println(_02_Convert_KeysToArrayList(hm));
        System.out.println(_03_Convert_ValuesToArrayList(hm));
    }

    static ArrayList<Map.Entry<String, String>> _01_Convert_EntriesToArrayList(HashMap<String, String> hm ){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return new ArrayList<>(hm.entrySet());
    }

    static ArrayList<String> _02_Convert_KeysToArrayList(HashMap<String, String> hm ){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return new ArrayList<>(hm.keySet());
    }

    static ArrayList<String> _03_Convert_ValuesToArrayList(HashMap<String, String> hm ){
        // Time Complexity: O(n)
        // Space Complexity: O(n)

        return new ArrayList<>(hm.values());
    }
}
