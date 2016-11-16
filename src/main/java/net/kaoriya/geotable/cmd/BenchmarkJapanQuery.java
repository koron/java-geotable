package net.kaoriya.geotable.cmd;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import net.kaoriya.geotable.GeoStringReader;
import net.kaoriya.geotable.GeoTable;

public class BenchmarkJapanQuery {

    static class Lap implements Closeable {
        String label;
        long start;
        double sec;
        Lap(String label) {
            this.label = label;
            start = System.nanoTime();
        }
        public String lapString() {
            long d = System.nanoTime() - start;
            sec = (double)d / 1000000000;
            return String.format("Lap %s: %.6f sec", label, sec);
        }
        @Override
        public void close() {
            System.out.println(lapString());
        }
    }

    static class LatLng {
        double lat;
        double lng;
        LatLng(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
        final static LatLng CONST_ZERO = new LatLng(0, 0);
        final static LatLng CONST_TOKYO = new LatLng(35.702265, 139.780935);
        static LatLng randomAllJapan(Random r) {
            double lat = 32.0 + r.nextDouble() * 9.0;
            double lng = 130.0 + r.nextDouble() * 12.0;
            return new LatLng(lat, lng);
        }
        static LatLng randomKanto(Random r) {
            double lat = 35.2 + r.nextDouble() * 1.6;
            double lng = 138.6 + r.nextDouble() * 2.0;
            return new LatLng(lat, lng);
        }
        static LatLng randomOut(Random r) {
            double lat = -1.0 + r.nextDouble() * 2.0;
            double lng = -1.0 + r.nextDouble() * 2.0;
            return new LatLng(lat, lng);
        }
    }

    public static void benchmark(
            String label,
            GeoTable<String> t,
            Random r,
            Function<Random, LatLng> gen)
    {
        Lap lap = new Lap(label);
        int total = 1000000;
        int hit = 0;
        for (int i = 0; i < total; ++i) {
            LatLng p = gen.apply(r);
            List<String> list = t.find(p.lat, p.lng);
            if (list.size() > 0) {
                ++hit;
            }
        }
        System.out.println(lap.lapString());
        System.out.println(String.format("  hit-rate: %.3f  qps: %.3f",
                    (double)hit / total, total / lap.sec));
    }

    public static GeoTable<String> load(String name) throws IOException {
        try (Lap lap = new Lap("load")) {
            return GeoStringReader.loadAsTable(new File(name));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("require one arg: geostring tsv file");
            System.exit(1);
        }
        GeoTable<String> t = load(args[0]);
        Random r = new Random();
        benchmark("const-zero", t, r, x -> LatLng.CONST_ZERO);
        benchmark("const-tokyo", t, r, x -> LatLng.CONST_TOKYO);
        benchmark("random-all-japan", t, r, LatLng::randomAllJapan);
        benchmark("random-kanto", t, r, LatLng::randomKanto);
        benchmark("random-out", t, r, LatLng::randomOut);
    }
}
