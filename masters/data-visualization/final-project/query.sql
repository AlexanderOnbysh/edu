SELECT
  year,
  b.country,
  b.lat,
  b.lon,
  b.elev,
  AVG(temp) AS avg_temp,
  AVG(slp) AS avg_slp,
  AVG(prcp) AS avg_prcp,
  SUM(CAST(tornado_funnel_cloud AS INT64)) AS tornado_funnel_cloud_number,
  SUM(CAST(thunder AS INT64)) AS thunder_number,
  SUM(CAST(hail AS INT64)) AS hail_number,
  SUM(CAST(snow_ice_pellets AS INT64)) AS snow_ice_pellets_number,
  SUM(CAST(rain_drizzle AS INT64)) AS rain_drizzle_number,
  SUM(CAST(fog AS INT64)) AS fog_number,
  COUNT(*) AS number_of_records
FROM
  `fh-bigquery.weather_gsod.gsod*` a
JOIN
  `fh-bigquery.weather_gsod.stations` b
ON
  a.stn=b.usaf
  AND a.wban=b.wban
GROUP BY year, b.country, b.name, b.lat, b.lon, b.elev
ORDER BY year ASC, b.country
<Paste>
