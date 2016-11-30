package net.kaoriya.geotable;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import com.google.common.geometry.S2RegionCoverer;

public class GeoStringConverter {

    public static Function<Map<String, Object>, String> joinMapper(
            final String separator,
            final String... propNames)
    {
        return (props) -> {
            String[] array = new String[propNames.length];
            for (int i = 0; i < propNames.length; ++i) {
                Object v = props.get(propNames[i]);
                array[i] = v != null ? v.toString() : "";
            }
            String s = String.join(separator, array);
            return s.length() > 0 ? s : null;
        };
    }

    public static void convertGeoJson(
            File infile,
            File outfile,
            S2RegionCoverer coverer,
            Function<Map<String, Object>, String> mapper)
        throws IOException
    {
        GeoJsonReader r = new GeoJsonReader(coverer);
        try(final GeoStringWriter w = new GeoStringWriter(outfile)) {
            convertGeoJson(infile, r, w, mapper);
        }
    }

    public static void convertGeoJson(
            File[] infiles,
            File outfile,
            S2RegionCoverer coverer,
            Function<Map<String, Object>, String> mapper)
        throws IOException
    {
        GeoJsonReader r = new GeoJsonReader(coverer);
        try(final GeoStringWriter w = new GeoStringWriter(outfile)) {
            for (File infile : infiles) {
                convertGeoJson(infile, r, w, mapper);
            }
        }
    }

    private static void convertGeoJson(
            File infile,
            GeoJsonReader r,
            GeoStringWriter w,
            Function<Map<String, Object>, String> mapper)
        throws IOException
    {
        final IOException[] ex = new IOException[1];
        r.readAll(infile, (cu, props) -> {
            String s = mapper.apply(props);
            if (s == null) {
                return true;
            }
            try {
                w.write(cu, s);
            } catch (IOException e) {
                ex[0] = e;
                return false;
            }
            return true;
        });
        if (ex[0] != null) {
            throw ex[0];
        }
    }
}
