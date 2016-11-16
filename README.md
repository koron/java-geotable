## Usage

1.  Convert GeoJSON to GeoString TSV.

        $ gradle convert-japan-geojson -Pargs='{INFILE} {OUTFILE}'

    It might require large memory (about 4GB) and take long time (about 10min).

2.  Load GeoString TSV then query.

## Test data

*   <https://github.com/niiyz/JapanCityGeoJson/>
*   <https://github.com/dataofjapan/land>
