package net.kaoriya.geotable.cmd;

import java.io.File;
import java.io.IOException;

import net.kaoriya.geotable.GeoTable;
import net.kaoriya.geotable.Loader;

public class BenchmarkJapanQuery {

    public static GeoTable<String> load(String name) throws IOException {
        try (Lap lap = new Lap("load")) {
            return Loader.loadAsStringTable(new File(name));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("require one arg: geostring tsv file");
            System.exit(1);
        }
        GeoTable<String> t = load(args[0]);
        BenchmarkCore.benchmarkAll((p) -> t.find(p.lat, p.lng).size() > 0);
    }

}
