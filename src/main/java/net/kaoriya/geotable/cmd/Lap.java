package net.kaoriya.geotable.cmd;

import java.io.Closeable;

final class Lap implements Closeable {
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
