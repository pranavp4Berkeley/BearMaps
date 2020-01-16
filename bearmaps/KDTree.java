package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(root, p, HORIZONTAL);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root.p);
    }

    public Point nearest(Node n, Point goalPoint, Point bestPoint) {
        if (n == null) {
            return bestPoint;
        }
        Point nPoint = n.p;
        if (nPoint.distance(nPoint, goalPoint) < bestPoint.distance(bestPoint, goalPoint)) {
            bestPoint = nPoint;
        }
        int compare = compare(goalPoint, n.p, n.orientation);
        Node goodSide;
        Node badSide;
        if (compare < 0) {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }
        bestPoint = nearest(goodSide, goalPoint, bestPoint);
        if (worthwhile(n, goalPoint, bestPoint.distance(bestPoint, goalPoint))) {
            bestPoint = nearest(badSide, goalPoint, bestPoint);
        }

        return bestPoint;

    }

    private boolean worthwhile(Node n, Point goal, double dist) {
        boolean returner = false;
        if (n.orientation == HORIZONTAL) {
            if ((n.p.getX() - goal.getX()) * (n.p.getX() - goal.getX()) < dist) {
                returner = true;
            }
        } else {
            if ((n.p.getY() - goal.getY()) * (n.p.getY() - goal.getY()) < dist) {
                returner = true;
            }
        }
        return returner;
    }

    private class Node {

        private Point p;
        private boolean orientation;
        private Node leftChild;
        private Node rightChild;

        private Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }
    }

    private Node add(Node n, Point P, boolean orientation) {
        if (n == null) {
            return new Node(P, orientation);
        }
        if (P.equals(n.p)) {
            return n;
        }
        int compare = compare(P, n.p, orientation);
        if (compare < 0) {
            n.leftChild = add(n.leftChild, P, !orientation);
        } else if (compare >= 0) {
            n.rightChild = add(n.rightChild, P, !orientation);
        }
        return n;
    }

    private int compare(Point A, Point B, boolean pO) {
        if (pO == HORIZONTAL) {
            return Double.compare(A.getX(), B.getX());
        } else {
            return Double.compare(A.getY(), B.getY());
        }
    }


}
