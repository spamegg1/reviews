// Exercise 6.8
import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { voteAction } from '../reducers/anecdoteReducer'
import { setNotification } from '../reducers/notificationReducer'

const AnecdoteList = () => {
  const anecdotes = useSelector(state => state.anecdotes)  // Exercise 6.10

  // Exercise 6.12
  const filter = useSelector(state => state.filter.toLowerCase())
  const filteredAnecdotes = anecdotes.filter(anecdote =>
    anecdote.content.toLowerCase().includes(filter)
  )

  const dispatch = useDispatch()

  // Event handler for voting
  const vote = (anecdote) => {
    console.log('vote', anecdote.id)
    dispatch(voteAction(anecdote))  // Exercise 6.3, modified 6.17

    // Exercise 6.11: vote notification (modified in 6.18)
    dispatch(setNotification(`you voted for "${anecdote.content}"`, 5))
  }

  return (
    <div>
      {filteredAnecdotes  // Exercise 6.12
        .sort((a1, a2) => a2.votes - a1.votes)  // Exercise 6.5
        .map(anecdote =>
        <div key={anecdote.id}>
          <div>
            {anecdote.content}
          </div>
          <div>
            has {anecdote.votes}
            <button onClick={() => vote(anecdote)}>vote</button>
          </div>
        </div>
      )}
    </div>
  )
}

export default AnecdoteList
