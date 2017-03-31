package net.kaoriya.geotable.cmd;

import java.util.Random;
import java.util.function.Function;

import net.kaoriya.geotable.GeoTable;
import net.kaoriya.geotable.Loader;

public class BenchmarkCore {

    public static void benchmark(
            String label,
            Random r,
            Function<Random, LatLng> gen,
            Function<LatLng, Boolean> check)
    {
        Lap lap = new Lap(label);
        int total = 1000000;
        int hit = 0;
        for (int i = 0; i < total; ++i) {
            LatLng p = gen.apply(r);
            if (check.apply(p)) {
                ++hit;
            }
        }
        System.out.println(lap.lapString());
        System.out.println(String.format("  hit-rate: %.3f  qps: %.3f",
                    (double)hit / total, total / lap.sec));
    }

    public static void benchmarkAll(Function<LatLng, Boolean> check) {
        Random r = new Random();
        benchmark("const-zero", r, x -> LatLng.CONST_ZERO, check);
        benchmark("const-tokyo", r, x -> LatLng.CONST_TOKYO, check);
        benchmark("random-all-japan", r, LatLng::randomAllJapan, check);
        benchmark("random-kanto", r, LatLng::randomKanto, check);
        benchmark("random-out", r, LatLng::randomOut, check);
    }
}
