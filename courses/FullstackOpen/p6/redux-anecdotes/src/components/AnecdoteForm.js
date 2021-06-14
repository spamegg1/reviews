// Exercise 6.7
import React from 'react'
import { useDispatch } from 'react-redux'
import { createAction } from '../reducers/anecdoteReducer'
import { setNotification } from '../reducers/notificationReducer'
// import anecdoteService from '../services/anecdotes'  // Ex 6.14, removed in 6.16

const AnecdoteForm = () => {
  const dispatch = useDispatch()

  // Exercise 6.4
  const createAnecdote = async event => {  // Exercise 6.14: async
    event.preventDefault()
    const content = event.target.anecdote.value
    event.target.anecdote.value = ''

    // Exercise 6.14, removed in 6.16 (moved to action creator in reducer)
    // const newAnecdote = await anecdoteService.createNew(content)
    dispatch(createAction(content))  // Exercise 6.16

    // Exercise 6.11, 6.18
    dispatch(setNotification(`new anecdote "${content}" created`, 5))
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

export default AnecdoteForm
