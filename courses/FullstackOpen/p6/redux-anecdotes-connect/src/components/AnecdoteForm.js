// Exercise 6.7
import React from 'react'
import { connect } from 'react-redux'  // Exercise 6.20
import { createAction } from '../reducers/anecdoteReducer'
import { setNotification } from '../reducers/notificationReducer'

const AnecdoteForm = (props) => {
  // Exercise 6.4
  const createAnecdote = async event => {  // Exercise 6.14: async
    event.preventDefault()
    const content = event.target.anecdote.value
    event.target.anecdote.value = ''

    // Exercise 6.14, removed in 6.16 (moved to action creator in reducer)
    // const newAnecdote = await anecdoteService.createNew(content)
    props.createAction(content)  // Exercise 6.20

    // Exercise 6.11, 6.18
    props.setNotification(`new anecdote "${content}" created`, 5)  // Exercise 6.20
  }

  // Exercise 6.7: moved it here from App.js
  return (
    <div>
      <h2>create new</h2>
      <form onSubmit={createAnecdote}>
        <div><input name="anecdote"/></div>   {/* 6.4 */}
        <button type="submit">create</button> {/* 6.4 */}
      </form>
    </div>
  )
}

// Exercise 6.20
export default connect(
  null,
  {
    createAction,
    setNotification
  }
)(AnecdoteForm)
