package net.kaoriya.geotable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.geometry.S2CellUnion;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2Loop;
import com.google.common.geometry.S2Point;
import com.google.common.geometry.S2PolygonBuilder;
import com.google.common.geometry.S2RegionCoverer;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.geojson.Polygon;

public class GeoJsonReader {

    final S2RegionCoverer coverer;

    public GeoJsonReader() {
        this(new S2RegionCoverer());
    }

    public GeoJsonReader(S2RegionCoverer coverer) {
        this.coverer = coverer;
    }

    public S2RegionCoverer getCoverer() {
        return coverer;
    }

    public void readAll(File file, BiPredicate<S2CellUnion, Map<String, Object>> fn) throws IOException {
        FeatureCollection fc = read(file);
        for (Feature f : fc) {
            S2CellUnion cu = toCellUnion(f);
            if (!fn.test(cu, f.getProperties())) {
                break;
            }
        }
    }

    S2CellUnion toCellUnion(Feature f) {
        GeoJsonObject o = f.getGeometry();
        if (o instanceof MultiPolygon) {
            return toCellUnion((MultiPolygon)o);
        } else if (o instanceof Polygon) {
            return toCellUnion((Polygon)o);
        }
        return null;
    }

    S2CellUnion toCellUnion(MultiPolygon p) {
        S2PolygonBuilder b = new S2PolygonBuilder();
        for (List<List<LngLatAlt>> coords : p.getCoordinates()) {
            appendAsPolygon(b, coords);
        }
        return coverer.getCovering(b.assemblePolygon());
    }

    S2CellUnion toCellUnion(Polygon p) {
        S2PolygonBuilder b = new S2PolygonBuilder();
        appendAsPolygon(b, p.getCoordinates());
        return coverer.getCovering(b.assemblePolygon());
    }

    S2Point toPoint(LngLatAlt p) {
        double latitude = p.getLatitude();
        double longitude = p.getLongitude();
        return S2LatLng.fromDegrees(latitude, longitude).toPoint();
    }

    S2Loop toLoop(List<LngLatAlt> list) {
        int size = list.size() - 1;
        ArrayList<S2Point> points = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            points.add(toPoint(list.get(i)));
        }
        return new S2Loop(points);
    }

    void appendAsPolygon(S2PolygonBuilder b, List<List<LngLatAlt>> coords) {
        for (List<LngLatAlt> item : coords) {
            b.addLoop(toLoop(item));
        }
    }

    public static FeatureCollection read(File file) throws IOException {
        return new ObjectMapper().readValue(file, FeatureCollection.class);
    }

}
