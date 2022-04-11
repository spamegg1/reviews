SELECT name
  FROM (
    SELECT movies.id as mid1
      FROM movies
      JOIN stars
        ON movies.id = stars.movie_id
      JOIN people
        ON stars.person_id = people.id
     WHERE people.name = "Kevin Bacon" AND people.birth = 1958
  )
  JOIN (
    SELECT people.name, movies.id AS mid2
      FROM movies
      JOIN stars
        ON movies.id = stars.movie_id
      JOIN people
        ON stars.person_id = people.id
  )
    ON mid1 = mid2
 WHERE NOT name = "Kevin Bacon";
