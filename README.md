## Usage

1.  Convert GeoJSON to GeoString TSV.

        $ gradle convert-japan-geojson -Pargs='{INFILE} {OUTFILE}'

    It might require large memory (about 4GB) and take long time (about 10min).

2.  Load GeoString TSV then query.

## Generate GeoString TSV file for cities in Japan

How to genrate GeoString TSV file of cities in Japan:

```
$ git clone https://github.com/niiyz/JapanCityGeoJson.git
$ git clone https://github.com/koron/java-geotable.git
$ ls -F
JapanCityGeoJson/  java-geotable/

$ cd java-geotable
$ gradle convert-japan-geojson -Pargs="../JapanCityGeoJson/geojson/47都道府県/*.json japan_cities.tsv"
$ ls *.tsv
japan_cities.tsv
```

You'll get a `japan_cities.tsv` file in current directory.

## Benchmark

1.  Prepare data

    Check above *Generate GeoString TSV file for cities in Japan* section.

2.  Run benchmark

    ```
    $ gradle benchmark-japan-query -Pargs=japan_cities.tsv
    :compileJava
    :processResources UP-TO-DATE
    :classes
    :benchmark-japan-query
    Lap load: 15.795181 sec
    Lap const-zero: 1.662323 sec
      hit-rate: 0.000  qps: 601567.787
    Lap const-tokyo: 1.865116 sec
      hit-rate: 1.000  qps: 536159.749
    Lap random-all-japan: 2.225336 sec
      hit-rate: 0.264  qps: 449370.297
    Lap random-kanto: 2.938109 sec
      hit-rate: 0.937  qps: 340354.920
    Lap random-out: 1.243102 sec
      hit-rate: 0.000  qps: 804439.190
    ```

## Minimum jars to query (run benchmark)

To make queries with geotable, requires three jar files.

*   geotable-0.0.1jar
*   s2-geometry-library-java-0.0.1.jar
*   guava-18.0.jar

If you placed those jars in current dir, you could run benchmark like this.

    $ java -cp "geotable-0.0.1.jar;s2-geometry-library-java-0.0.1.jar;guava-18.0.jar" \
      net.kaoriya.geotable.cmd.BenchmarkJapanQuery japan_cities.tsv

You can download those jar files from [release][1]

## Test data

*   <https://github.com/niiyz/JapanCityGeoJson/>
*   <https://github.com/dataofjapan/land>

[1]:https://github.com/koron/java-geotable/releases/v0.0.1
