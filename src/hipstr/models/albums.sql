-- name: get-recently-added
-- Gets the 10 most recently added albums in the db.
SELECT
  art.name AS artist,
  alb.album_id,
  alb.name AS album_name,
  alb.release_date,
  alb.created_at
FROM artists art
  INNER JOIN albums alb ON art.artist_id = alb.artist_id
ORDER BY alb.created_at DESC
LIMIT 10;

-- name: get-discographies-by-artist-name
-- Gets the artist discography by the artist-name
SELECT
  alb.name AS album_name,
  alb.release_date
FROM artists art
  INNER JOIN albums alb ON art.artist_id = alb.artist_id
WHERE
  art.name = :artist_name
ORDER BY
  alb.release_date DESC