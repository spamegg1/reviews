import anecdoteService from '../services/anecdotes'  // Exercise 6.15

const byVotes = (a1, a2) => a2.votes - a1.votes  // Exercise 6.17

const anecdoteReducer = (state = [], action) => {
  console.log('anecdote state now: ', state)
  console.log('anecdote action', action)

  // Exercise 6.3, modified 6.17 (increasing vote count inside action creator)
  switch (action.type) {
    case 'VOTE': {
      const id = action.data.id
      return state
        .map(a => a.id !== id ? a : action.data)
        .sort(byVotes)
    }

    // Exercise 6.13
    case 'INIT':
      return action.data.sort(byVotes)

    // Exercise 6.4
    case 'NEW':
      return [...state, action.data]

    default:
      return state
  }
}

// Exercise 6.6: Action Creators
// Exercise 6.3, 6.17
export const voteAction = anecdote => {
  return async dispatch => {
    const object = { ...anecdote, votes: anecdote.votes + 1 }
    const data = await anecdoteService.update(object)
    dispatch({
      type: 'VOTE',
      data
    })
  }
}

// Exercise 6.4, 6.14, 6.16
export const createAction = content => {
  return async dispatch => {
    const anecdote = await anecdoteService.createNew(content)
    dispatch({
      type: 'NEW',
      data: anecdote
    })
  }
}

// modified in Exercise 6.15
export const initializeAnecdotes = () => {
  return async dispatch => {
    const anecdotes = await anecdoteService.getAll()
    dispatch({
      type: 'INIT',
      data: anecdotes
    })
  }
}

export default anecdoteReducer  // Exercise 6.10