package net.kaoriya.geotable;

import java.util.List;

import com.google.common.geometry.S2CellUnion;
import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;

import net.kaoriya.geotable.intervaltree.IntervalTree;

public class GeoTable<T> {

    final IntervalTree<T> tree = new IntervalTree<>();

    public void add(S2CellUnion region, T value) {
        for (S2CellId cellId : region) {
            long begin = cellId.rangeMin().id();
            long end = cellId.rangeMax().id();
            tree.addInterval(begin, end, value);
        }
    }

    public List<T> find(double lat, double lng) {
        return find(S2LatLng.fromDegrees(lat, lng));
    }

    public List<T> find(S2LatLng latLng) {
        return find(S2CellId.fromLatLng(latLng));
    }

    public List<T> find(S2CellId cellId) {
        return tree.get(cellId.id());
    }
}
