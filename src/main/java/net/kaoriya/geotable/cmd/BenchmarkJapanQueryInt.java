package net.kaoriya.geotable.cmd;

import java.io.File;
import java.io.IOException;

import net.kaoriya.geotable.GeoIntTable;
import net.kaoriya.geotable.Loader;

public class BenchmarkJapanQueryInt {

    public static GeoIntTable load(String name) throws IOException {
        try (Lap lap = new Lap("load")) {
            return Loader.loadAsIntTable(new File(name));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("require one arg: geostring tsv file");
            System.exit(1);
        }
        GeoIntTable t = load(args[0]);
        BenchmarkCore.benchmarkAll((p) -> t.find(p.lat, p.lng).size() > 0);
    }

}
