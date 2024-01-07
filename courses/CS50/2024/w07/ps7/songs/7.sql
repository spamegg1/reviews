SELECT AVG(energy)
FROM songs
WHERE name IN (
    SELECT name
    FROM songs
    WHERE artist_id = (
        SELECT id
        FROM artists
        WHERE name = "Drake"
    )
);
