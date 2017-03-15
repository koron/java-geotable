package net.kaoriya.geotable.augmentedtree;

final class Node<T> implements Comparable<Node<T>> {
    long start;
    long end;
    long max;
    T value;

    Node(long start, long end, T value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public int compareTo(Node<T> other) {
        if (this.start < other.start) {
            return -1;
        } else if (this.start > other.start) {
            return 1;
        } else if (this.end < other.end) {
            return -1;
        } else if (this.end > other.end) {
            return 1;
        } else {
            return 0;
        }
    }
}
