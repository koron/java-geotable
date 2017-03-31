package net.kaoriya.geotable.augmentedtree;

import java.util.ArrayList;
import java.util.Collections;

public class Builder<T> {

    ArrayList<Node<T>> list = new ArrayList<>();
    boolean sorted;

    /**
     * Add an interval and its value.
     */
    public void add(long start, long end, T value) {
        list.add(new Node<T>(start, end, value));
        sorted = false;
    }

    /**
     * Assures list as sorted.
     */
    void assureToSort() {
        if (!sorted) {
            Collections.sort(list);
            sorted = true;
        }
    }

    /**
     * Build an index.
     */
    public Index buildIndex() {
        assureToSort();
        return new Index(list);
    }

    /**
     * Build a tree.
     */
    public Tree<T> buildTree() {
        assureToSort();
        Index idx = buildIndex();
        return new Tree<T>(idx, list);
    }
}
