import React, { useState, useEffect } from 'react'
import { useQuery, useLazyQuery } from '@apollo/client'
import _ from 'lodash'
import { ALL_BOOKS } from '../queries'
import BookList from './BookList'

const Books = (props) => {
  const allbooksResult = useQuery(ALL_BOOKS)
  const [genreBooks, genreBooksResult] = useLazyQuery(ALL_BOOKS)

  const [books, setBooks] = useState(null)
  const [genre, setGenre] = useState(null)
  const [genres, setGenres] = useState([])

  useEffect(() => {
    if (allbooksResult.data && allbooksResult.data.allBooks && !genre) {
      const books = allbooksResult.data.allBooks
      setBooks(books)
      const genres = _.uniq(books.flatMap(b => b.genres))
      setGenres(genres)
    }
  }, [allbooksResult.data, genre])

  useEffect(() => {
    if (genreBooksResult.data) {
      setBooks(genreBooksResult.data.allBooks)
    }

  }, [genreBooksResult.data])

  if (!props.show || !books) {
    return null
  }

  const onGenreClick = (newGenre) => {
    setGenre(newGenre)
    genreBooks({ variables: {
      genre: newGenre
    }})
  }

  return (
    <div>
      <h2>books</h2>

      {genre&&
        <div>
          in genre <strong>{genre}</strong>
        </div>
      }

      <BookList books={books} />

      <div>
        {genres.map(g =>
          <button key={g} onClick={() => onGenreClick(g)}>
            {g}
          </button>
        )}
        <button onClick={() => onGenreClick(null)}>
          all genres
        </button>
      </div>
    </div>
  )
}

export default Books