import React, { useState } from 'react'
import { useQuery } from '@apollo/client'
import _ from 'lodash'
import { ALL_BOOKS } from '../queries'
import BookList from './BookList'

const Books = (props) => {
  const result = useQuery(ALL_BOOKS)
  const [genre, setGenre] = useState(null)

  if (!props.show || !result.data) {
    return null
  }

  const books = result.data.allBooks
  const genres = _.uniq(books.flatMap(b => b.genres))

  const booksToShow = genre
    ? books.filter(b => b.genres.includes(genre))
    : books

  return (
    <div>
      <h2>books</h2>

      {genre&&
        <div>
          in genre <strong>{genre}</strong>
        </div>
      }

      <BookList books={booksToShow} />

      <div>
        {genres.map(g =>
          <button key={g} onClick={() => setGenre(g)}>
            {g}
          </button>
        )}
        <button onClick={() => setGenre(null)}>
          all genres
        </button>
      </div>
    </div>
  )
}

export default Books