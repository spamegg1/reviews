import React from 'react'
import { useQuery } from '@apollo/client'
import { ALL_BOOKS, ME } from '../queries'
import BookList from './BookList'

const Books = (props) => {
  const booksResult = useQuery(ALL_BOOKS)
  const meResult = useQuery(ME)

  if (!props.show || !booksResult.data || !meResult.data  ) {
    return null
  }


  const genre = meResult.data.me.favoriteGenre
  const recommendedBooks = booksResult.data.allBooks.filter(b => b.genres.includes(genre))

  return (
    <div>
      <h2>recommendations</h2>

      <div>
        books in your favorite genre <strong>{genre}</strong>
      </div>

      <BookList books={recommendedBooks} />
    </div>
  )
}

export default Books