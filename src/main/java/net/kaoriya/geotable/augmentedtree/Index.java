package net.kaoriya.geotable.augmentedtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

final class Index {

    int len;
    long[] startPoints;
    long[] endPoints;
    long[] rightMaxes;
    

    <T> Index(ArrayList<Node<T>> src) {
        len = src.size();
        startPoints = new long[len];
        endPoints = new long[len];
        rightMaxes = new long[len];

        Collections.sort(src);
        fillMax(src, 0, len);
        for (int i = 0; i < len; ++i) {
            Node<T> n = src.get(i);
            startPoints[i] = n.start;
            endPoints[i] = n.end;
            rightMaxes[i] = n.max;
        }
    }

    static<T> long fillMax(ArrayList<Node<T>> src, int start, int end) {
        int mid = (start + end) / 2;
        Node<T> n = src.get(mid);
        n.max = n.end;
        if (mid > start) {
            n.max = Math.max(n.max, fillMax(src, start, mid));
        }
        if (mid < end - 1) {
            n.max = Math.max(n.max, fillMax(src, mid + 1, end));
        }
        return n.max;
    }

    void find(long pt, int start, int end, Consumer<Integer> found) {
        int mid = (start + end) / 2;
        if (pt > rightMaxes[mid]) {
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

    public void search(long pt, Consumer<Integer> found) {
        find(pt, 0, len, found);
    }

    public List<Integer> searchAll(long pt) {
        ArrayList<Integer> list = new ArrayList<>();
        search(pt, (n) -> list.add(n));
        return list;
    }
}
