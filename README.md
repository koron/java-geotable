## Usage

1.  Convert GeoJSON to GeoString TSV.

        $ gradle convert-japan-geojson -Pargs='{INFILE} {OUTFILE}'

    It might require large memory (about 4GB) and take long time (about 10min).

2.  Load GeoString TSV then query.

## Benchmark

1.  Prepare data

    ```
    $ gradle convert-japan-geojson -Pargs='tmp/japan_cities.geojson tmp/japan_cities.tsv'
    ```

2.  Run benchmark

    ```
    $ gradle benchmark-japan-query -Pargs=./tmp/japan_cities.tsv
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

## Test data

*   <https://github.com/niiyz/JapanCityGeoJson/>
*   <https://github.com/dataofjapan/land>
