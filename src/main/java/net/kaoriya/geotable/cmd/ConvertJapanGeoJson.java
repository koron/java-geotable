package net.kaoriya.geotable.cmd;

import java.io.File;
import java.util.Map;
import java.util.function.Function;

import com.google.common.geometry.S2RegionCoverer;

import net.kaoriya.geotable.GeoStringConverter;

public class ConvertJapanGeoJson {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("require two args: input and output files name");
            System.exit(1);
        }
        File infile = new File(args[0]);
        File outfile = new File(args[1]);
        S2RegionCoverer coverer = new S2RegionCoverer();
        coverer.setMaxCells(50);
        Function<Map<String, Object>, String> mapper
            = GeoStringConverter.joinMapper(",", "N03_007");
        GeoStringConverter.convertGeoJson(infile, outfile, coverer, mapper);
    }
}
