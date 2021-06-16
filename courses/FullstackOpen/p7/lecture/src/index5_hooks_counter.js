import React from 'react'
import ReactDOM from 'react-dom'
import useCounter from './hooks/counter'

const App = () => {
  const counter = useCounter()

  return (
    <div>
      <div>{counter}</div>
      <button onClick={counter.increase}>
        plus
      </button>
      <button onClick={counter.decrease}>
        minus
      </button>
      <button onClick={counter.zero}>
        zero
      </button>
    </div>
  )
}

ReactDOM.render(<App />, document.getElementById('root'))
