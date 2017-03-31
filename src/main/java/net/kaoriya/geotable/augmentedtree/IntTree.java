package net.kaoriya.geotable.augmentedtree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class IntTree {

    Index index;
    int[] values;

    IntTree(Index index, List<Node<Integer>> sortedNodes) {
        this.index = index;
        int len = sortedNodes.size();
        values = new int[len];
        for (int i = 0; i < len; ++i) {
            values[i] = sortedNodes.get(i).value.intValue();
        }
    }

    public void search(long pt, Consumer<Integer> found) {
        index.search(pt, (n) -> found.accept(values[n]));
    }

    public List<Integer> searchAll(long pt) {
        ArrayList<Integer> list = new ArrayList<>();
        search(pt, (v) -> list.add(v));
        return list;
    }
}
