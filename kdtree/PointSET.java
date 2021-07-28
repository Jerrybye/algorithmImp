/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/*
 * Throw an IllegalArgumentException if any argument is null.
 * */
public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<Point2D>();
    }                            // construct an empty set of points

    public boolean isEmpty() {
        return points.isEmpty();
    }                   // is the set empty?

    public int size() {
        return points.size();
    }                   // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }       // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }      // does the set contain point p?

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }      // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new IllegalArgumentException();
        }

        double xMax = rect.xmax();
        double xMin = rect.xmin();
        double yMax = rect.ymax();
        double yMin = rect.ymin();
        /*
         * POINT ARE INSIDE THE RECTANGLE IF r.xmin<p.x<r.xmax && r.ymin<p.y<r.ymax
         * */
        List<Point2D> rPoints = new ArrayList<Point2D>();
        for (Point2D point : points) {
            double px = point.x();
            double py = point.y();
            if (px <= xMax && px >= xMin && py >= yMin && py <= yMax) {
                rPoints.add(point);
            }
        }

        return rPoints;

    }   // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        double min = points.first().distanceSquaredTo(p);
        Point2D nPoint = null;
        for (Point2D point : points) {
            double distance = point.distanceSquaredTo(p);
            if (distance <= min) {
                min = distance;
                nPoint = point;
            }
        }

        return nPoint;
    }      // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        PointSET ps = new PointSET();
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.2, 0.2);
        Point2D p3 = new Point2D(0.3, 0.3);
        ps.insert(p1);
        ps.insert(p2);
        ps.insert(p3);

        RectHV rs = new RectHV(0.0, 0.0, 0.25, 0.25);

        for (Point2D p : ps.range(rs)) {
            System.out.println(p.x() + "   " + p.y());
        }

        System.out.println(ps.nearest(new Point2D(0.0, 0.0)));
    }          // unit testing of the methods (optional)
}
