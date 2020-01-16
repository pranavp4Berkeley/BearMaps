package bearmaps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


import edu.princeton.cs.algs4.Stopwatch;

import java.util.Random;
import java.util.PriorityQueue;


public class ArrayHeapMinPQTest {
    @Test
    public void testInsert() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 1);
        pq.add("b", 2);
        pq.add("g", 7);
        pq.add("f", 6);
        pq.add("z", 4);
        assertEquals(5, pq.size());
        assertEquals("a", pq.removeSmallest());
        assertEquals("b", pq.removeSmallest());
        assertEquals("z", pq.removeSmallest());
        assertEquals("f", pq.removeSmallest());
        assertEquals("g", pq.removeSmallest());
    }

    @Test
    public void time() {
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> pq2 = new NaiveMinPQ<>();
        PriorityQueue<Integer> pq3 = new PriorityQueue<>();

        for (int i = 0; i < 10000000; i += 1) {
            pq.add(i, 1);

        }

        Stopwatch sw = new Stopwatch();
//        pq.contains(1000000);
        for (int z = 0; z < 1000; z++) {
            pq.removeSmallest();
//            pq.changePriority(pq.getSmallest(), z / 4);
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() + " seconds.");


        for (int x = 0; x < 10000000; x += 1) {
            pq2.add(x, 1);
        }
        Stopwatch sw2 = new Stopwatch();
//        pq2.contains(1000000);
        for (int q = 0; q < 1000; q++) {
            pq2.removeSmallest();
//            pq2.changePriority(pq2.getSmallest(), q / 4);
        }
        System.out.println("Total time elapsed: " + sw2.elapsedTime() + " seconds.");

    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<Integer> testHeap = new ArrayHeapMinPQ<>();
        testHeap.add(1, 0.5);
        testHeap.add(4, 2.0);
        testHeap.add(3, 0.4); // this is 3.0
        testHeap.add(2, 1.0); // this is 0.1
        testHeap.add(5, 1.2);
        testHeap.add(6, 1.5);
        testHeap.add(7, 1.4);  // this is 0.6

        testHeap.changePriority(7, 0.6);
        testHeap.changePriority(2, 0.2);
        testHeap.changePriority(3, 3.0);

        testHeap.add(8, 6.0);
        testHeap.changePriority(8, 0.0);

        int[] removeTest = new int[]{8, 2, 1, 7, 5, 6, 4, 3};
        testWithRemove(removeTest, testHeap);
    }

    @Test
    public void initTest() {
        ArrayHeapMinPQ<Integer> testHeap = new ArrayHeapMinPQ<>();
        testHeap.add(1, 0.5);
        testHeap.add(4, 2.0);
        testHeap.add(3, 0.4);
        testHeap.add(2, 1.0);
        testHeap.add(5, 1.2);
        testHeap.add(6, 1.5);
        testHeap.add(7, 1.4);

        int[] actualRemove = new int[]{3, 1, 2, 5, 7, 6, 4};
        testWithRemove(actualRemove, testHeap);
    }

    private void testWithRemove(int[] actual, ArrayHeapMinPQ<Integer> predicted) {
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], (int) predicted.removeSmallest());
        }
    }

    @Test
    public void randomTest() {
        NaiveMinPQ<Integer> test = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        Random rand = new Random(10000);
        for (int x = 0; x < 10000; x++) {
            int next = Math.abs(rand.nextInt() % 3);
            if (next == 0) {
                test.add(x, x);
                tester.add(x, x);
            } else if (next == 1 && test.size() > 0) {
                assertEquals(test.removeSmallest(), tester.removeSmallest());
                continue;
            } else if (next == 2 && tester.size() > 0) {
                test.changePriority(test.getSmallest(), x);
                tester.changePriority(tester.getSmallest(), x);
            }

        }
    }

    @Test
    public void testEdge() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        tester.add(5, 5);
        tester.add(7, 4);
        tester.add(10, 1);
        tester.add(54, 0);
        tester.add(11, 11);
        tester.changePriority(11, -1);
//        for(int x=0; x<4; x++){
//            tester.removeSmallest();
//        }
        int test = tester.removeSmallest();
        assertEquals(test, 11);
    }

//    @Test
//    public void resizeTest(){
//        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
//        for(int x =0; x< 100; x++){
//            tester.add(x,x);
//        }
//        System.out.println(tester.mainLength());
//        for(int x=0; x<75; x++){
//            tester.removeSmallest();
//        }
//        System.out.println(tester.mainLength());
//
//    }


}
