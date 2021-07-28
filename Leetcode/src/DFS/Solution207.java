package DFS;

import java.util.Arrays;
import java.util.LinkedList;

public class Solution207 {
    /*
    *   1 <= numCourses <= 105
        0 <= prerequisites.length <= 5000
        prerequisites[i].length == 2
        0 <= ai, bi < numCourses
    * */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Arrays.sort(prerequisites, (a, b) -> Integer.compare(a[0], b[0]));
        LinkedList<int[]> schedule = new LinkedList<>();

        for (int[] p : prerequisites) {
            if (schedule.isEmpty() || schedule.getLast()[1] != p[0]) {
                schedule.add(p);
            } else {
                if (p[1] == schedule.getLast()[0]) {
                    return false;
                } else {
                    schedule.getLast()[1] = Math.max(p[1], schedule.getLast()[1]);
                }

            }
        }
        return schedule.size() == 1;
    }

}
