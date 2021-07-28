/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;
    private static final RectHV UNITSQUARE = new RectHV(0, 0, 1, 1);

    private class Node {
        private final Point2D key;
        private final boolean isVertical;
        private Node left, right;

        public Node(Point2D key, boolean isVertical) {
            this.key = key;
            this.isVertical = isVertical;
        }
    }


    public boolean isEmpty() {
        return root == null;
    }                     // is the set empty?

    public int size() {
        return size;
    }                        // number of points in the set

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, p, true);
    }

    private Node insert(Node x, Point2D p, boolean isVertical) {
        if (x == null) {
            size++;
            return new Node(p, isVertical);
        }
        int cmp = 0;
        if (isVertical) {
            cmp = Double.compare(p.x(), x.key.x());
        }
        else {
            cmp = Double.compare(p.y(), x.key.y());
        }

        if (cmp < 0) {
            x.left = insert(x.left, p, !isVertical);
        }
        else if (cmp > 0) {
            x.right = insert(x.right, p, !isVertical);
        }

        return x;

    }


    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p.x(), p.y());
    }

    private boolean contains(Node node, double x, double y) {
        if (node == null) {
            return false;
        }

        if (node.key.x() == x && node.key.y() == y) {
            return true;
        }

        if (node.isVertical && x < node.key.x() || !node.isVertical && y < node.key.y()) {
            return contains(node.left, x, y);
        }
        else {
            return contains(node.right, x, y);
        }
    }

    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        UNITSQUARE.draw();

        draw(root, UNITSQUARE);
    }

    private void draw(final Node node, final RectHV rect) {
        if (node == null) {
            return;
        }

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.key.x(), node.key.y()).draw();

        // get the min and max points of division line
        Point2D min, max;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.key.x(), rect.ymin());
            max = new Point2D(node.key.x(), rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.key.y());
            max = new Point2D(rect.xmax(), node.key.y());
        }

        // draw that division line
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children
        draw(node.left, leftRect(rect, node));
        draw(node.right, rightRect(rect, node));
    }

    private RectHV leftRect(final RectHV rect, final Node node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
        }
        else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
        }
    }

    private RectHV rightRect(final RectHV rect, final Node node) {
        if (node.isVertical) {
            return new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        else {
            return new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
        }
    }

    private void range(final Node node, final RectHV nrect,
                       final RectHV rect, final List<Point2D> rangeSet) {
        if (node == null)
            return;

        if (rect.intersects(nrect)) {
            final Point2D p = new Point2D(node.key.x(), node.key.y());
            if (rect.contains(p))
                rangeSet.add(p);
            range(node.left, leftRect(nrect, node), rect, rangeSet);
            range(node.right, rightRect(nrect, node), rect, rangeSet);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        final List<Point2D> rangeSet = new ArrayList<Point2D>();
        range(root, UNITSQUARE, rect, rangeSet);

        return rangeSet;
    }           // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(final Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return nearest(root, UNITSQUARE, p.x(), p.y(), null);
    }

    private Point2D nearest(final Node node, final RectHV rect,
                            final double x, final double y, final Point2D candidate) {
        if (node == null) {
            return candidate;
        }

        double dqn = 0.0;
        double drq = 0.0;
        RectHV left = null;
        RectHV rigt = null;
        final Point2D query = new Point2D(x, y);
        Point2D nearest = candidate;

        if (nearest != null) {
            dqn = query.distanceSquaredTo(nearest);
            drq = rect.distanceSquaredTo(query);
        }

        if (nearest == null || dqn > drq) {
            final Point2D point = new Point2D(node.key.x(), node.key.y());
            if (nearest == null || dqn > query.distanceSquaredTo(point))
                nearest = point;

            if (node.isVertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                rigt = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());

                if (x < node.key.x()) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                }
                else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            }
            else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                rigt = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());

                if (y < node.key.y()) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                }
                else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            }
        }

        return nearest;
    }

}
