/* *****************************************************************************
 *  Name: HAORAN DENG
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final Point[] imPoints;
    private final LineSegment[] imLgs;

    public BruteCollinearPoints(Point[] points) {
        /*
         *  check whether the three slopes between p and q, between p and r, and between p and s are all equal.
         * Throw an IllegalArgumentException if the argument to the constructor is null, if any point in the array is null, or if the argument to the constructor contains a repeated point.
              1
         *   1
         *  1
         * 1
         * */
        if (points == null) {
            throw new IllegalArgumentException();

        }

        imPoints = Arrays.copyOf(points, points.length);


        int n = imPoints.length;

        // aux = points;
        Arrays.sort(imPoints);

        List<LineSegment> cacheLs = new ArrayList<LineSegment>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int h = k + 1; h < n; h++) {
                        int s1 = imPoints[i].slopeOrder().compare(imPoints[j], imPoints[k]);
                        int s2 = imPoints[i].slopeOrder().compare(imPoints[k], imPoints[h]);
                        if (s1 == s2 && s1 == 0) {
                            LineSegment ls = new LineSegment(imPoints[i], imPoints[h]);
                            cacheLs.add(ls);
                        }

                    }


                }

            }
        }
        imLgs = new LineSegment[cacheLs.size()];
        cacheLs.toArray(imLgs);


    } // finds all line segments containing 4 points


    public int numberOfSegments() {
        return imLgs.length;
    }        // the number of line segments

    public LineSegment[] segments() {

        return Arrays.copyOf(imLgs, imLgs.length);
    }                // the line segments

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        collinear.numberOfSegments();
        for (LineSegment segment : collinear.segments()) {
            if (segment != null) {
                StdOut.println(segment);
                segment.draw();
            }

        }
        StdDraw.show();
    }
}
