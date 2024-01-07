SELECT movies.title, ratings.rating
  FROM ratings
  JOIN movies
    ON movies.id = ratings.movie_id
 WHERE year = 2010
 ORDER BY ratings.rating DESC,
          movies.title ASC;
