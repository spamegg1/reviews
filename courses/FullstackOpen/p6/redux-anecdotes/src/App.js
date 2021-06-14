import React, { useEffect } from 'react'  // Exercise 6.13
import AnecdoteForm from './components/AnecdoteForm'  // Exercise 6.7
import AnecdoteList from './components/AnecdoteList'  // Exercise 6.8
import Notification from './components/Notification'  // Exercise 6.10, 6.11
import Filter from './components/Filter'  // Exercise 6.12
// import anecdoteService from './services/anecdotes'  // Ex 6.13, removed 6.15
import { useDispatch } from 'react-redux'  // Exercise 6.13
import { initializeAnecdotes } from './reducers/anecdoteReducer' // Exercise 6.13

const App = () => {
  // Exercise 6.13
  const dispatch = useDispatch()

  // modified in Exercise 6.15 (anecdoteService moved to action creator in reducer)
  useEffect(() => {
    dispatch(initializeAnecdotes())
  }, [dispatch])

  return (
    <div>
      <h2>Anecdotes</h2>
      <Notification />  {/* Exercise 6.10, 11 */}
      <Filter />    {/* Exercise 6.12 */}
      <AnecdoteList />  {/* Exercise 6.8 */}
      <AnecdoteForm />  {/* Exercise 6.7 */}
    </div>
  )
}

export default App