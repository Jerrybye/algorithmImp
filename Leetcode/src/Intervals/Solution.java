package Intervals;

import java.util.Arrays;
import java.util.LinkedList;

class Solution {

    public int[][] merge(int[][] intervals) {

        /*
        * without lambda
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }

        });
        * */
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        LinkedList<int[]> merged = new LinkedList<int[]>();

        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }


        }

        return merged.toArray(new int[merged.size()][]);

    }

    public int[][] insert(int[][] intervals, int[] newInterval) {


        LinkedList<int[]> merged = new LinkedList<int[]>();
        int N = intervals.length;
        /**/
        if (N == 0) {
            merged.add(newInterval);
            return merged.toArray(new int[merged.size()][]);
        }

        int i = 0;

        while (i < N && intervals[i][0] <= newInterval[0]) {
            merged.add(intervals[i]);
            i++;
        }

        if (i == 0) {
            merged.add(newInterval);
        }

        merge(merged, newInterval);
        while (i < N) {

            merge(merged, intervals[i]);

            i++;
        }

        return merged.toArray(new int[merged.size()][]);
    }

    public void merge(LinkedList<int[]> merged, int[] toMerge) {

        if (merged.getLast()[1] < toMerge[0]) {
            merged.add(toMerge);
        } else {
            merged.getLast()[1] = Math.max(merged.getLast()[1], toMerge[1]);
        }
    }

}
