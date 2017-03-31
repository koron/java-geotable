package net.kaoriya.geotable.augmentedtree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class Tree<T> {

    Index index;
    ArrayList<T> values;

    Tree(Index index, List<Node<T>> sortedNodes) {
        this.index = index;
        int len = sortedNodes.size();
        values = new ArrayList<>(sortedNodes.size());
        for (int i = 0; i < len; ++i) {
            values.add(sortedNodes.get(i).value);
        }
    }

    public void search(long pt, Consumer<T> found) {
        index.search(pt, (n) -> found.accept(values.get(n)));
    }

    public List<T> searchAll(long pt) {
        ArrayList<T> list = new ArrayList<>();
        search(pt, (v) -> list.add(v));
        return list;
    }
}
