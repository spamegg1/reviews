SELECT t1
  FROM (
    SELECT movies.title as t1, movies.id AS id1
      FROM movies
      JOIN stars
        ON movies.id = stars.movie_id
      JOIN people
        ON stars.person_id = people.id
     WHERE people.name = "Helena Bonham Carter"
  )
  JOIN (
    SELECT movies.title as t2, movies.id AS id2
      FROM movies
      JOIN stars
        ON movies.id = stars.movie_id
      JOIN people
        ON stars.person_id = people.id
     WHERE people.name = "Johnny Depp"
  )
    ON id1 = id2;
