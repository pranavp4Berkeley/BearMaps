package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points = new ArrayList<>();

    public NaivePointSet(List<Point> points) {
        for (int x = 0; x < points.size(); x++) {
            this.points.add(points.get(x));
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point returnPoint = points.get(0);
        Point inputted = new Point(x, y);
        double shortDist = Point.distance(inputted, returnPoint);
        for (Point P : points) {
            if (Point.distance(P, inputted) < shortDist) {
                returnPoint = P;
                shortDist = Point.distance(P, inputted);
            }
        }
        return returnPoint;
    }
}
