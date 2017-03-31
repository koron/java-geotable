package net.kaoriya.geotable.augmentedtree;

import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class TreeTest  {
    @Test
    public void simple() {
        Builder<String> b = new Builder<>();
        b.add(16, 21, "(16, 21)");
        b.add(8, 9, "(8, 9)");
        b.add(25, 30, "(25, 30)");
        b.add(5, 8, "(5, 8)");
        b.add(15, 23, "(15, 23)");
        b.add(17, 19, "(17, 19)");
        b.add(26, 26, "(26, 26)");
        b.add(0, 3, "(0, 3)");
        b.add(6, 10, "(6, 10)");
        b.add(19, 20, "(19, 20)");
        Tree<String> t = b.buildTree();

        List<String> list = t.searchAll(20);
        assertEquals(list.size(), 3);
        assertEquals(list.get(0), "(16, 21)");
        assertEquals(list.get(1), "(15, 23)");
        assertEquals(list.get(2), "(19, 20)");
    }
}
