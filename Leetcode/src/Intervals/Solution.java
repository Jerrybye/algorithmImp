package Intervals;

import java.util.*;

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

    public int minMeetingRooms(int[][] intervals) {
        /**/
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<LinkedList<int[]>> schedules = new ArrayList<LinkedList<int[]>>();

        for (int[] interval : intervals) {
            if (schedules.isEmpty()) {
                LinkedList<int[]> newSchedule = new LinkedList<int[]>();
                newSchedule.add(interval);
                schedules.add(newSchedule);
            } else {
                boolean flag = false;

                /*
                 * This part is ugly and not efficient at all, iterating every schedule is not ideal
                 * TODO: learn min-heap data structure and come back optimize
                 * */
                for (LinkedList<int[]> schedule : schedules) {
                    if (!isConflicted(schedule, interval)) {
                        //if true then interval has been merged into that schdule
                        flag = true;
                        schedule.add(interval);
                        break;
                    }
                }

                if (!flag) {
                    //if flag is false after loop then this interval is conflict with all existing schedule, adding a new room
                    LinkedList<int[]> newSchedule = new LinkedList<int[]>();
                    newSchedule.add(interval);
                    schedules.add(newSchedule);
                }
            }
        }

        return schedules.size();

    }

    public int minMeetingRoomsOptimized(int[][] intervals) {
        //Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int N = intervals.length;
        Integer[] start = new Integer[N];
        Integer[] end = new Integer[N];

        for (int i = 0; i < N; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        Arrays.sort(end, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        int startPointer = 0;
        int endPointer = 0;
        int usedRooms = 0;

        while (startPointer < N) {
            if (start[startPointer] >= end[endPointer]) {
                usedRooms -= 1;
                endPointer += 1;
            }

            usedRooms++;
            startPointer++;
        }

        return usedRooms;

    }

    public boolean isConflicted(LinkedList<int[]> schedule, int[] interval) {

        if (schedule.getLast()[1] <= interval[0]) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * trip[i] = [num_passengers, start_location, end_location]
     *
     *  trips.length <= 1000
        trips[i].length == 3
        1 <= trips[i][0] <= 100
        0 <= trips[i][1] < trips[i][2] <= 1000
        1 <= capacity <= 100000
     * */
    public boolean carPooling(int[][] trips, int capacity) {

        int N = trips.length;

        int[][] sLocationAndPassengers = new int[N][2];

        int[][] eLocationAndPassengers = new int[N][2];


        for (int i = 0; i < N; i++) {
            sLocationAndPassengers[i][0] = trips[i][1];
            eLocationAndPassengers[i][0] = trips[i][2];

            sLocationAndPassengers[i][1] = trips[i][0];
            eLocationAndPassengers[i][1] = trips[i][0];
        }

        Arrays.sort(sLocationAndPassengers, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(eLocationAndPassengers, (a, b) -> Integer.compare(a[0], b[0]));

        int sp = 0;
        int ep = 0;

        int seats = capacity;
        while (sp < N) {
            if (sLocationAndPassengers[sp][0] < eLocationAndPassengers[ep][0]) {
                seats = seats - sLocationAndPassengers[sp][1];
                sp++;
            } else {
                seats = seats + eLocationAndPassengers[ep][1];
                ep++;
            }

            if (seats < 0) {
                return false;
            }
        }


        return true;
    }

    public int[][] kClosest(int[][] points, int k) {
        int res[][] = new int[k][2];
        PriorityQueue<int[]> topK = new PriorityQueue<>(k, (a, b) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);

        for (int i = 0; i < points.length; i++) {
            if (points[i].length > 0) {
                topK.add(points[i]);
            }
        }

        for (int i = 0; i < res.length; i++) {
            res[i] = topK.poll();
        }

        return res;
    }
}

