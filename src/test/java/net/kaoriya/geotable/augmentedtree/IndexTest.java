package net.kaoriya.geotable.augmentedtree;

import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class IndexTest  {
    @Test
    public void simple() {
        Builder<String> b = new Builder<>();
        b.add(0, 3, "(0, 3)");
        b.add(5, 8, "(5, 8)");
        b.add(6, 10, "(6, 10)");
        b.add(8, 9, "(8, 9)");
        b.add(15, 23, "(15, 23)");
        b.add(16, 21, "(16, 21)");
        b.add(17, 19, "(17, 19)");
        b.add(19, 20, "(19, 20)");
        b.add(25, 30, "(25, 30)");
        b.add(26, 26, "(26, 26)");
        Index idx = b.buildIndex();

        List<Integer> list = idx.searchAll(20);
        assertEquals(list.size(), 3);
        assertEquals(list.get(0).intValue(), 5);
        assertEquals(list.get(1).intValue(), 4);
        assertEquals(list.get(2).intValue(), 7);
    }
}
