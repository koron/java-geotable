package net.kaoriya.geotable.cmd;

import java.util.Random;

final class LatLng {
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
