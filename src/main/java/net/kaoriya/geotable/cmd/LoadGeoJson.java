package net.kaoriya.geotable.cmd;

import java.io.File;

import org.geojson.FeatureCollection;

import net.kaoriya.geotable.GeoJsonReader;

public class LoadGeoJson {
    public static void main(String[] args) throws Exception {
        long st = System.nanoTime();
        File f = new File("./tmp/tokyo2-format.geojson");
        //f = new File("../japan-prefecture-quering/tmp/japan_cities.geojson");
        GeoJsonReader r = new GeoJsonReader();
        //r.getCoverer().setMaxCells(50);
        final int[] size = new int[]{0};
        r.readAll(f, (cu, props) -> {
            ++size[0];
            return true;
        });
        System.out.println("size=" + size[0]);
        long d = System.nanoTime() - st;
        System.out.println(String.format("%.6f", (double)d / 1000000000));
    }
}
