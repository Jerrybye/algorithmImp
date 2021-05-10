/* *****************************************************************************
 *  Name:Haoran Deng
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] lgs;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = Arrays.copyOf(points, points.length);
        int n = this.points.length;


        List<LineSegment> cache = new ArrayList<LineSegment>();
        Arrays.sort(this.points);
        for (int i = 0; i < n; i++) {
            if (i > 0 && this.points[i].slopeTo(this.points[i - 1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
        }

        for (Point p : this.points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }

            Point[] aux = Arrays.copyOf(this.points, n);

            Arrays.sort(aux, p.slopeOrder());
            int start = 1;
            int end = 1;
            double toLastSlope = p.slopeTo(aux[end]);

            for (int i = 2; i < aux.length; i++) {
                double slope = p.slopeTo(aux[i]);
                if (Double.compare(toLastSlope, slope) != 0) {
                    if (end - start >= 2) {
                        if (p.compareTo(aux[start]) < 0) {

                            /*
                            Let's say you have 5 points in their natural order a, b, c, d, e.
                            When you have b as the anchor and sort the remaining points by slopeOrder, you want points with the same slope to appear in their natural order (i.e. ... a, c, d, e, ...).
                            To avoid adding the subsegment (b, c, d, e), whenever you start seeing a new slope (i.e. at a), you just need to check if b is less than a in terms of natural order
                            - if it is not, it means b is not the first point in that segment, then you know you don't need to add it.
                             */
                            cache.add(new LineSegment(p, aux[end]));
                        }

                    }
                    toLastSlope = slope;
                    start = i;
                }
                end = i;
            }
            if (end - start >= 2) {
                if (p.compareTo(aux[start]) < 0) {
                    cache.add(new LineSegment(p, aux[end]));
                }
            }

        }
        lgs = cache.toArray(new LineSegment[cache.size()]);

    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lgs.length;
    }     // the number of line segments


    public LineSegment[] segments() {
        return Arrays.copyOf(lgs, lgs.length);
    }               // the line segments

    public static void main(String[] args) {
        {
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
            FastCollinearPoints collinear = new FastCollinearPoints(points);
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
}
