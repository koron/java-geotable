package net.kaoriya.geotable;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import com.google.common.geometry.S2CellUnion;

public class GeoStringReader implements Closeable {

    public static class Item {
        public S2CellUnion region;
        public String value;
        public Item(S2CellUnion region, String value) {
            this.region = region;
            this.value = value;
        }
    }

    final BufferedReader br;

    public GeoStringReader(File file) throws IOException {
        br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
    }

    public Item read() throws IOException {
        String s = br.readLine();
        if (s == null) {
            return null;
        }
        String[] items = s.split("\t");
        if (items.length < 2) {
            throw new IOException("two or more columns required");
        }
        int last = items.length - 1;
        ArrayList<Long> list = new ArrayList<>(last);
        for (int i = 0; i < last; ++i) {
            try {
                list.add(Long.parseLong(items[i]));
            } catch (NumberFormatException e) {
                throw new IOException("not long number", e);
            }
        }
        S2CellUnion region = new S2CellUnion();
        region.initFromIds(list);
        return new Item(region, items[last]);
    }

    public void close() throws IOException {
        br.close();
    }

    public static GeoTable<String> loadAsTable(File file) throws IOException {
        GeoTable<String> tree = new GeoTable<>();
        try (GeoStringReader r = new GeoStringReader(file)) {
            while (true) {
                Item item = r.read();
                if (item == null) {
                    break;
                }
                tree.add(item.region, item.value);
            }
        }
        tree.build();
        return tree;
    }
}
