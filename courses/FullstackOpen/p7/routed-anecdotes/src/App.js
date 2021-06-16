import React, { useState } from 'react'
import {
  Switch,
  Route,
  Link,
  useRouteMatch,
  useHistory,
} from "react-router-dom" // Exercise 7.1, 7.2, 7.3
import useField from './hooks/field'  // Exercise 7.4

const Menu = () => {
  const padding = {
    paddingRight: 5
  }
  // Exercise 7.1: replace <a>s with <Link>s for routing
  return (
    <div>
      <Link style={padding} to="/">anecdotes</Link>
      <Link style={padding} to="/create">create new</Link>
      <Link style={padding} to="/about">about</Link>
    </div>
  )
}

// Exercise 7.2: viewing single anecdote
const Anecdote = ({ anecdote }) => {
  if (!anecdote) { return null }
  return (
    <div>
      <h2>{anecdote.content} by {anecdote.author}</h2>
      <p>has {anecdote.votes} votes</p>
    </div>
  )
}

const AnecdoteList = ({ anecdotes }) => (
  <div>
    <h2>Anecdotes</h2>
    <ul>
      {anecdotes.map(anecdote =>
        <li key={anecdote.id}>
          {/* Exercise 7.2: adding links to each anecdote in the list */}
          <Link to={`/anecdotes/${anecdote.id}`}>
            {anecdote.content}
          </Link>
        </li>
      )}
    </ul>
  </div>
)

const About = () => (
  <div>
    <h2>About anecdote app</h2>
    <p>According to Wikipedia:</p>

    <em>An anecdote is a brief, revealing account of an individual person or an incident.
      Occasionally humorous, anecdotes differ from jokes because their primary purpose is not simply to provoke laughter but to reveal a truth more general than the brief tale itself,
      such as to characterize a person by delineating a specific quirk or trait, to communicate an abstract idea about a person, place, or thing through the concrete details of a short narrative.
      An anecdote is "a story with a point."</em>

    <p>Software engineering is full of excellent anecdotes, at this app you can find the best and add more.</p>
  </div>
)

const Footer = () => (
  <div>
    Anecdote app for <a href='https://courses.helsinki.fi/fi/tkt21009'>Full Stack -websovelluskehitys</a>.

    See <a href='https://github.com/fullstack-hy/routed-anecdotes/blob/master/src/App.js'>https://github.com/fullstack-hy2019/routed-anecdotes/blob/master/src/App.js</a> for the source code.
  </div>
)

const CreateNew = (props) => {
  // Exercise 7.4: replacing useState with useField custom hook
  const content = useField('text')
  const author = useField('text')
  const info = useField('text')

  const history = useHistory()  // Exercise 7.3: redirect to home page


  const handleSubmit = (e) => {
    e.preventDefault()
    props.addNew({
      content: content.form.value,  // Exercise 7.4, 7.6
      author: author.form.value,    // Exercise 7.4, 7.6
      info: info.form.value,        // Exercise 7.4, 7.6
      votes: 0
    })
    history.push('/')  // Exercise 7.3: after creating, display all anecdotes
  }

  // Exercise 7.5
  const handleReset = () => {
    content.reset()
    author.reset()
    info.reset()
  }

  return (
    <div>
      <h2>create a new anecdote</h2>
      <form onSubmit={handleSubmit}>
        <div>
          content
          <input name='content' {...content.form} /> {/* Exercise 7.4, 7.6 */}
        </div>
        <div>
          author
          <input name='author' {...author.form} />   {/* Exercise 7.4, 7.6 */}
        </div>
        <div>
          url for more info
          <input name='info' {...info.form} />       {/* Exercise 7.4, 7.6 */}
        </div>
        <button>create</button>
      </form>
      <button onClick={handleReset}>reset</button> {/* Exercise 7.5 */}
    </div>
  )

}

const App = () => {
  const [anecdotes, setAnecdotes] = useState([
    {
      content: 'If it hurts, do it more often',
      author: 'Jez Humble',
      info: 'https://martinfowler.com/bliki/FrequencyReducesDifficulty.html',
      votes: 0,
      id: '1'
    },
    {
      content: 'Premature optimization is the root of all evil',
      author: 'Donald Knuth',
      info: 'http://wiki.c2.com/?PrematureOptimization',
      votes: 0,
      id: '2'
    }
  ])

  const [notification, setNotification] = useState('')

  const addNew = (anecdote) => {
    anecdote.id = (Math.random() * 10000).toFixed(0)
    setAnecdotes(anecdotes.concat(anecdote))

    // Exercise 7.3: improving create new anecdote functionality
    setNotification(`a new anecdote ${anecdote.content} created!`)
    setTimeout(() => {
      setNotification('')
    }, 10000)

  }

  // const anecdoteById = (id) =>
  //   anecdotes.find(a => a.id === id)

  // const vote = (id) => {
  //   const anecdote = anecdoteById(id)

  //   const voted = {
  //     ...anecdote,
  //     votes: anecdote.votes + 1
  //   }

  //   setAnecdotes(anecdotes.map(a => a.id === id ? voted : a))
  // }

  // Exercise 7.2: finding invididual anecdote
  const match = useRouteMatch('/anecdotes/:id')
  const anecdote = match
    ? anecdotes.find(a => a.id === match.params.id)
    : null

  // Exercise 7.1: add routing and views
  return (
    <div>
      <h1>Software anecdotes</h1>
      <Menu />
      {notification}  {/* Exercise 7.3 */}
      <Switch>
        <Route path="/anecdotes/:id">  {/* Exercise 7.2 */}
          <Anecdote anecdote={anecdote} />
        </Route>
        <Route path="/about">
          <About />
        </Route>
        <Route path="/create">
          <CreateNew addNew={addNew} />
        </Route>
        <Route path="/">
          <AnecdoteList anecdotes={anecdotes} />
        </Route>
      </Switch>
      <Footer />
    </div>
  )
}

export default App;