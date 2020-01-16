package bearmaps.proj2ab.ExtrinsicMinPQ;

import bearmaps.ExtrinsicMinPQ;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private Priority[] main;
    private int nextFirst;
    private int size;
    private HashMap<T, Integer> secChecker;

    public ArrayHeapMinPQ() {
        main = new ArrayHeapMinPQ.Priority[10];
        nextFirst = 1;
        size = 0;
        main[0] = null;
        secChecker = new HashMap<>();
    }

    private void resize(int k) {
        Priority[] temp = new ArrayHeapMinPQ.Priority[k];
        for (int i = 1; i < size + 1; i++) {
            temp[i] = main[i];
        }
        main = temp;
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (size + 1 == main.length) {
            resize(main.length * 2);
        }
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        main[nextFirst] = new Priority(item, priority);
        secChecker.put(item, nextFirst);
        swim(nextFirst);
        nextFirst += 1;
        size++;
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return secChecker.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return main[1].item;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (((double) main.length) / ((double) size) > 4.0 && main.length > 10) {
            resize(main.length / 2);
        }
        T removed = main[1].item;
        nextFirst--;
        size--;
        main[1] = main[nextFirst];
        main[nextFirst] = null;
        secChecker.remove(removed);
        if (size > 0) {
            secChecker.put(main[1].item, 1);
        }
        if (size == 0) {
            return removed;
        }
        sink(1);
        return removed;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = secChecker.get(item);
        main[index].priority = priority;
        if (size > 0) {
            swim(index);
            sink(index);
        }


    }

    private void swim(int k) {
        if (parent(k) == 0) {
            return;
        }
        if (main[parent(k)].priority > main[k].priority) {
            secChecker.put(main[k].item, parent(k));
            secChecker.put(main[parent(k)].item, k);
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    private void sink(int k) {
        int rightIndex = 0;
        int leftIndex = 0;
        if (2 * k > size || main[2 * k] == null) {
            leftIndex = 0;
            rightIndex = 0;
        } else {
            leftIndex = 2 * k;
        }
        if (2 * k + 1 < size + 1) {
            rightIndex = 2 * k + 1;
        }
        double currPrior = main[k].priority;
        if (leftIndex != 0 && rightIndex != 0) {
            if (main[leftIndex].priority <= main[rightIndex].priority
                    && main[leftIndex].priority < currPrior) {
                secChecker.put(main[k].item, leftIndex);
                secChecker.put(main[leftIndex].item, k);
                swap(leftIndex, k);
                sink(leftIndex);
            } else if (main[leftIndex].priority > main[rightIndex].priority
                    && main[rightIndex].priority < currPrior) {
                secChecker.put(main[k].item, rightIndex);
                secChecker.put(main[rightIndex].item, k);
                swap(k, rightIndex);
                sink(rightIndex);
            }
        } else if (leftIndex != 0) {
            if (main[leftIndex].priority < currPrior) {
                secChecker.put(main[k].item, leftIndex);
                secChecker.put(main[leftIndex].item, k);
                swap(leftIndex, k);
                sink(leftIndex);
            }
        }


    }

    private int parent(int k) {
        if (k >= 2) {
            return k / 2;
        } else {
            return 0;
        }

    }

    private void swap(int k, int p) {
        Priority temp = main[k];
        main[k] = main[p];
        main[p] = temp;
    }

    private class Priority {
        private T item;
        private double priority;

        Priority(T e, double p) {
            this.item = e;
            this.priority = p;
        }

    }

    private int mainLength() {
        return main.length;
    }
}
