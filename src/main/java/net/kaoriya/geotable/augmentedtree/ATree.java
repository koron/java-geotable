package net.kaoriya.geotable.augmentedtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interval tree by augmented tree.
 */
public final class ATree<T> {

    public final static class Builder<T> {
        public ArrayList<Node<T>> list = new ArrayList<>();

        /**
         * Add an interval and its value.
         */
        public void add(long start, long end, T value) {
            list.add(new Node<T>(start, end, value));
        }

        /**
         * Build a tree.
         */
        public ATree<T> build() {
            return new ATree<T>(list);
        }
    }

    private int len;
    private long[] startPoints;
    private long[] endPoints;
    private long[] maxes;
    private ArrayList<T> value;

    private ATree(ArrayList<Node<T>> src) {
        len = src.size();
        startPoints = new long[len];
        endPoints = new long[len];
        maxes = new long[len];
        value = new ArrayList<T>(len);

        Collections.sort(src);
        fillMax(src, 0, len);
        for (int i = 0; i < len; ++i) {
            Node<T> n = src.get(i);
            startPoints[i] = n.start;
            endPoints[i] = n.end;
            maxes[i] = n.max;
            value.add(n.value);
        }
    }

    private long fillMax(ArrayList<Node<T>> src, int start, int end) {
        int mid = (start + end) / 2;
        Node n = src.get(mid);
        n.max = n.end;
        if (mid > start) {
            n.max = Math.max(n.max, fillMax(src, start, mid));
        }
        if (mid < end - 1) {
            n.max = Math.max(n.max, fillMax(src, mid + 1, end));
        }
        return n.max;
    }

    /**
     * query value of intervals which include the point.
     */
    public List<T> query(long pt) {
        ArrayList<T> list = new ArrayList<>();
        find(pt, 0, len, (n) -> list.add(value.get(n)));
        return list;
    }

    void find(long pt, int start, int end, Consumer<Integer> found) {
        int mid = (start + end) / 2;
        if (pt > maxes[mid]) {
            return;
        }
        if (pt < startPoints[mid]) {
            if (mid > start) {
                find(pt, start, mid, found);
            }
            return;
        }
        if (pt <= endPoints[mid]) {
            found.accept(mid);
        }
        if (mid > start) {
            find(pt, start, mid, found);
        }
        if (mid < end - 1) {
            find(pt, mid + 1, end, found);
        }
    }
}
