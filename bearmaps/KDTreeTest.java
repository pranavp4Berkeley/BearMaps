package bearmaps;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class KDTreeTest {

    /**
     * @Source Joshua Hug's Video
     */
    @Test
    public void joshuaHugTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);


        KDTree test = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        NaivePointSet tester = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));

        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            double randX = rand.nextInt() % 10 - 2.0;
            double randY = rand.nextInt() % 10 - 2.0;

            assertEquals(test.nearest(randX, randY), tester.nearest(randX, randY));
        }
    }

    @Test
    public void randomTest() {
        ArrayList<Point> points = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            Point p = new Point(rand.nextDouble() * 100000, rand.nextDouble() * 100000);
            points.add(p);
        }
        KDTree test = new KDTree(points);
        NaivePointSet tester = new NaivePointSet(points);
        for (int i = 0; i < 10000; i++) {
            double randX = rand.nextDouble() * 100000;
            double randY = rand.nextDouble() * 100000;

            assertEquals(test.nearest(randX, randY), tester.nearest(randX, randY));
        }

    }

    /**
     * @Source Joshua Hug's video on Randomized testing
     */
    @Test
    public void timingTest() {
        ArrayList<Point> points = new ArrayList<>();
        Random rand = new Random(100000);
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
            points.add(p);
        }

        KDTree test = new KDTree(points);
        NaivePointSet tester = new NaivePointSet(points);

        Stopwatch s1 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            double randX = rand.nextDouble() * 10000;
            double randY = rand.nextDouble() * 10000;
            test.nearest(randX, randY);
        }
        System.out.println(s1.elapsedTime());

        Stopwatch s2 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            double randX = rand.nextDouble() * 10000;
            double randY = rand.nextDouble() * 10000;
            tester.nearest(randX, randY);
        }
        System.out.println(s2.elapsedTime());

        for (int i = 0; i < 10000; i++) {
            double randX = rand.nextDouble() * 10000;
            double randY = rand.nextDouble() * 10000;
            assertEquals(test.nearest(randX, randY),tester.nearest(randX, randY));
        }
    }

    @Test
    public void nearestTester() {
        Random rand = new Random(500000);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            points.add(new Point(rand.nextDouble(), rand.nextDouble()));
        }
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        List<Point> points2 = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            points2.add(new Point(rand.nextDouble(), rand.nextDouble()));
        }
        for (Point p : points2) {
            Point expected = naive.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }
}
