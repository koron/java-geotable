package net.kaoriya.geotable;

import java.io.File;
import java.io.IOException;

import net.kaoriya.geotable.GeoStringReader.Item;

public final class Loader {

    public static GeoTable<String> loadAsStringTable(File file)
        throws IOException
    {
        GeoTable<String> t = new GeoTable<>();
        GeoStringReader.load(file, (item) -> {
            t.add(item.region, item.value);
        });
        return t.build();
    }

    public static GeoIntTable loadAsIntTable(File file) throws IOException {
        GeoIntTable t = new GeoIntTable();
        GeoStringReader.load(file, (item) -> {
            t.add(item.region, Integer.parseInt(item.value));
        });
        return t.build();
    }

}
