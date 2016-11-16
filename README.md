## Usage

1.  Convert GeoJSON to GeoString TSV.

        $ gradle convert-japan-geojson -Pargs='{INFILE} {OUTFILE}'

    It will take massive memory (4GB) and very long time (10min or so).

2.  Load GeoString TSV then query.

## Test data

*   <https://github.com/niiyz/JapanCityGeoJson/>
*   <https://github.com/dataofjapan/land>
