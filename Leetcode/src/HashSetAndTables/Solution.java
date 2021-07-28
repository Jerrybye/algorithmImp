package HashSetAndTables;

import java.util.*;

public class Solution {
    /*
     * Interview with capgemini
     * pesudocode
     * Question: write a method that find the common set from a List of arrays
     *
     * Failed because don't know Hashset retainAll()
     * */

    public Iterator<Integer> commonIntersection(List<List<Integer>> input) {

        final HashSet<Integer> intersection = new HashSet<Integer>();
        for (List<Integer> subList : input) {
            if (intersection.size() == 0) {
                intersection.addAll(subList);
            } else {
                intersection.retainAll(subList);
            }

        }

        return intersection.iterator();
    }

    public static void main(String[] args) {

        List<List<Integer>> input = new ArrayList<>();

        List<Integer> i1 = Arrays.asList(1, 2, 3, 4);
        List<Integer> i2 = Arrays.asList(1, 2, 3);
        List<Integer> i3 = Arrays.asList(1, 2, 3, 4, 5);
        input.add(i1);
        input.add(i2);
        input.add(i3);

        Solution s = new Solution();

        Iterator<Integer> it = s.commonIntersection(input);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
