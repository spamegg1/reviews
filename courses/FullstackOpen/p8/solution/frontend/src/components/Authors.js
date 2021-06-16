import React from 'react'
import { useQuery, useMutation } from '@apollo/client'
import { ALL_AUTHORS, EDIT_AUTHOR } from '../queries'

import Select from 'react-select'

const SetBirthyear = ({ authors }) => {
  const [ editAuthor ] = useMutation(EDIT_AUTHOR)

  if (authors.length === 0) {
    return null
  }

  const options = authors.map(a => ({
    value: a.name,
    label: a.name
  }))

  const handleSubmit = (event) => {
    event.preventDefault()

    editAuthor({
      variables: {
        name: event.target.author.value,
        setBornTo: Number(event.target.year.value)
      }
    })

    event.target.year.value = ''
    event.target.author.value = null
  }

  return (
    <div>
      <h3>Set birthyear</h3>
      <form onSubmit={handleSubmit}>
        <Select options={options} name='author' />
        <input name='year'></input>
        <button>update author</button>
      </form>
    </div>
  )
}

const Authors = (props) => {
  const result = useQuery(ALL_AUTHORS)

  if (!props.show || !result.data) {
    return null
  }

  const authors = result.data.allAuthors

  return (
    <div>
      <h2>authors</h2>
      <table>
        <tbody>
          <tr>
            <th></th>
            <th>
              born
            </th>
            <th>
              books
            </th>
          </tr>
          {authors.map(a =>
            <tr key={a.name}>
              <td>{a.name}</td>
              <td>{a.born}</td>
              <td>{a.bookCount}</td>
            </tr>
          )}
        </tbody>
      </table>
      <SetBirthyear authors={authors.filter(a => !a.born)}/>
    </div>
  )
}

export default Authors