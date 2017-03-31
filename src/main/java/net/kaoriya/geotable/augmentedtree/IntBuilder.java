package net.kaoriya.geotable.augmentedtree;

public final class IntBuilder extends Builder<Integer> {
    public IntTree buildIntTree() {
        assureToSort();
        Index idx = buildIndex();
        return new IntTree(idx, list);
    }
}
