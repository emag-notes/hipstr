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