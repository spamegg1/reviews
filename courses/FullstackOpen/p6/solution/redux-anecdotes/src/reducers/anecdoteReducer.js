import anecdoteService from '../services/anecdotes'

const byVotes = (a1, a2) => a2.votes - a1.votes

const reducer = (state = [], action) => {
  switch (action.type) {
    case 'INIT':
      return action.data.sort(byVotes)
    case 'VOTE':
      const voted = action.data
      return state.map(a => a.id===voted.id ? voted : a).sort(byVotes)
    case 'CREATE':
      return [...state, action.data]
    default:
      return state
  }
}

export const createAnecdote = (content) => {
  return async dispatch => {
    const data = await anecdoteService.createNew(content)
    dispatch({
      type: 'CREATE',
      data
    })
  }
}

export const initializeAnecdotes = () => {
  return async dispatch => {
    const data = await anecdoteService.getAll()
    dispatch({
      type: 'INIT',
      data
    })
  }
}

export const voteAnecdote = (anecdote) => {
  return async dispatch => {
    const toVote = {...anecdote, votes: anecdote.votes + 1 }
    const data = await anecdoteService.update(toVote)
    dispatch({
      type: 'VOTE',
      data
    })
  }
}

export default reducer