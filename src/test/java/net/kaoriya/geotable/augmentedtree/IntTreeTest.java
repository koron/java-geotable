package net.kaoriya.geotable.augmentedtree;

import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class IntTreeTest  {
    @Test
    public void simple() {
        IntBuilder b = new IntBuilder();
        b.add(0, 3, 999);
        b.add(5, 8, 888);
        b.add(6, 10, 777);
        b.add(8, 9, 666);
        b.add(15, 23, 555);
        b.add(16, 21, 444);
        b.add(17, 19, 333);
        b.add(19, 20, 222);
        b.add(25, 30, 111);
        b.add(26, 26, 0);
        IntTree t = b.buildIntTree();

        List<Integer> list = t.searchAll(20);
        assertEquals(list.size(), 3);
        assertEquals(list.get(0).intValue(), 444);
        assertEquals(list.get(1).intValue(), 555);
        assertEquals(list.get(2).intValue(), 222);
    }
}
