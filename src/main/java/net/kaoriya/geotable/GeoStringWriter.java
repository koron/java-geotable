package net.kaoriya.geotable;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2CellUnion;

public class GeoStringWriter implements Closeable {

    final BufferedWriter bw;

    public GeoStringWriter(File file) throws IOException {
        bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
    }

    public void write(S2CellUnion region, String value) throws IOException {
        if (region.size() <= 0) {
            throw new IOException("region must contain one or more cells");
        }
        for (S2CellId cell : region) {
            bw.write(Long.toString(cell.id()));
            bw.write('\t');
        }
        bw.write(value);
        bw.newLine();
    }

    public void close() throws IOException {
        bw.flush();
        bw.close();
    }
}
