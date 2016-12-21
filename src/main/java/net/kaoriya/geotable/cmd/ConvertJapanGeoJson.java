package net.kaoriya.geotable.cmd;

import java.io.File;
import java.util.Map;
import java.util.function.Function;

import com.google.common.geometry.S2RegionCoverer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.kaoriya.geotable.GeoStringConverter;

public class ConvertJapanGeoJson {
    public static void main(String[] args) throws Exception {
        int maxCells = 50;

        // parse argument
        Options options = new Options();
        Option optMaxCells = new Option("mc", "maxcells", true,
                "max cells for coverer (defualt: 50)");
        options.addOption(optMaxCells);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            String v = cmd.getOptionValue("maxcells");
            if (v != null) {
                try {
                    maxCells = Integer.parseInt(v);
                } catch (NumberFormatException e) {
                    throw new ParseException("maxcells accepts only numbers");
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("convert-japan-geojson [OPTIONS] {INFILES} {OUTFILE}", options);
            System.exit(1);
            return;
        }
        args = cmd.getArgs();

        if (args.length < 2) {
            System.out.println("require two args: input and output files name");
            System.exit(1);
        }
        File[] infiles = new File[args.length - 1];
        for (int i = args.length - 2; i >= 0; --i) {
            infiles[i] = new File(args[i]);
        }
        File outfile = new File(args[args.length - 1]);

        S2RegionCoverer coverer = new S2RegionCoverer();
        coverer.setMaxCells(maxCells);
        Function<Map<String, Object>, String> mapper
            = GeoStringConverter.joinMapper(",", "N03_007");
        GeoStringConverter.convertGeoJson(infiles, outfile, coverer, mapper);
    }
}
