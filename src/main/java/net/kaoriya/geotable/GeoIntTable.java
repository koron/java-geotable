package net.kaoriya.geotable;

import java.util.List;

import com.google.common.geometry.S2CellUnion;
import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;

import net.kaoriya.geotable.augmentedtree.IntTree;
import net.kaoriya.geotable.augmentedtree.IntBuilder;

public class GeoIntTable {

    IntBuilder builder;
    IntTree tree;

    public GeoIntTable() {
        builder = new IntBuilder();
    }

    public void add(S2CellUnion region, int value) {
        for (S2CellId cellId : region) {
            long begin = cellId.rangeMin().id();
            long end = cellId.rangeMax().id();
            builder.add(begin, end, value);
        }
    }

    public List<Integer> find(double lat, double lng) {
        return find(S2LatLng.fromDegrees(lat, lng));
    }

    public List<Integer> find(S2LatLng latLng) {
        return find(S2CellId.fromLatLng(latLng));
    }

    public List<Integer> find(S2CellId cellId) {
        return tree.searchAll(cellId.id());
    }

    /**
     * setup internal index for optimization.
     */
    public GeoIntTable build() {
        if (builder == null) {
            return this;
        }
        tree = builder.buildIntTree();
        builder = null;
        return this;
    }
}
