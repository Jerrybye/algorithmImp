package DFS;

import java.util.*;

public class Solution17 {
    public List<String> letterCombinations(String digits) {
        Map<Character, HashSet<String>> mapping = new HashMap<>();

        mapping.put('2', new HashSet<String>(Arrays.asList("a", "b", "c")));
        mapping.put('3', new HashSet<String>(Arrays.asList("d", "e", "f")));
        mapping.put('4', new HashSet<String>(Arrays.asList("g", "h", "i")));
        mapping.put('5', new HashSet<String>(Arrays.asList("j", "k", "l")));
        mapping.put('6', new HashSet<String>(Arrays.asList("m", "n", "o")));
        mapping.put('7', new HashSet<String>(Arrays.asList("p", "q", "r", "s")));
        mapping.put('8', new HashSet<String>(Arrays.asList("t", "u", "v")));
        mapping.put('9', new HashSet<String>(Arrays.asList("w", "x", "y", "z")));

        char[] numbers = digits.toCharArray();
        List<String> ans = new ArrayList<>();
        HashSet<String> seen = new HashSet<>();

        ArrayList<String> union = new ArrayList<>();
        for (char digit : numbers) {
            union.addAll(mapping.get(digit));
        }
        int size = digits.length();
        while () {

        }

    }

    public void dfs(ArrayList<String> Union, HashSet<String> seen, Character c) {
        if (Union.size() == 1) {
            
        }
    }

}
