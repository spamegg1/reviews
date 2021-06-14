import React, { useState } from 'react'
import ReactDOM from 'react-dom'

const Anecdote = ({text, votes}) => {
  return (
    <>
      <p>{text}</p>
      <p>has {votes} votes</p>
    </>
  )
}

const App = (props) => {
  const size = props.anecdotes.length
  const [selected, setSelected] = useState(0)
  const [votes, setVotes] = useState(Array(size).fill(0))

  const anecdote = props.anecdotes[selected]
  const mostVotes = Math.max(...votes)
  const maxIndex = votes.indexOf(mostVotes)
  const mostVoted = props.anecdotes[maxIndex]

  const clickAnecdote = () => {
    let random = Math.floor(Math.random() * size)
    while (random === selected) {
      random = Math.floor(Math.random() * size)
    }
    setSelected(random)
  }

  const clickVote = () => {
    const copy = [...votes]
    copy[selected] += 1
    setVotes(copy)
  }

  return (
    <div>
      <h2>Anecdote of the day</h2>
      <Anecdote text={anecdote} votes={votes[selected]}/>
      <button onClick={clickVote}>vote</button>
      <button onClick={clickAnecdote}>next anecdote</button>
      <h2>Anecdote with most votes</h2>
      <Anecdote text={mostVoted} votes={mostVotes}/>
    </div>
  )
}

const anecdotes = [
  'If it hurts, do it more often',
  'Adding manpower to a late software project makes it later!',
  'The first 90 percent of the code accounts for the first 90 percent of the development time...The remaining 10 percent of the code accounts for the other 90 percent of the development time.',
  'Any fool can write code that a computer can understand. Good programmers write code that humans can understand.',
  'Premature optimization is the root of all evil.',
  'Debugging is twice as hard as writing the code in the first place. Therefore, if you write the code as cleverly as possible, you are, by definition, not smart enough to debug it.'
]

ReactDOM.render(
  <App anecdotes={anecdotes} />,
  document.getElementById('root')
)