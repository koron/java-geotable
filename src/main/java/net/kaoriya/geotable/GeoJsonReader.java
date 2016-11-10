package net.kaoriya.geotable;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonReader;

public class GeoJsonReader implements Closeable {

    public static class Item {
        public String value;
    }

    final JsonReader jr;
    final ArrayList<String> props = new ArrayList<>();

    public GeoJsonReader(File file, String... props) throws IOException {
        jr = Json.createReader(Files.newBufferedReader(file.toPath(),
                    StandardCharsets.UTF_8));
        this.props.addAll(Arrays.asList(props));
    }

    public Item read() throws IOException {
        // TODO:
        return null;
    }

    public void close() throws IOException {
        jr.close();
    }
}
