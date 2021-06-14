import React from 'react';
import ReactDOM from 'react-dom'
import { createStore } from 'redux'
import reducer from './reducer'

const store = createStore(reducer)

const App = () => {
  const good = () => {
    store.dispatch({
      type: 'GOOD'
    })
  }

  // Exercise 6.2
  const neutral = () => {
    store.dispatch({
      type: 'OK'
    })
  }

  // Exercise 6.2
  const bad = () => {
    store.dispatch({
      type: 'BAD'
    })
  }

  // Exercise 6.2
  const reset = () => {
    store.dispatch({
      type: 'ZERO'
    })
  }

  return (
    <div>
      <button onClick={good}>good</button>
      <button onClick={neutral}>neutral</button>  {/* Exercise 6.2 */}
      <button onClick={bad}>bad</button>  {/* Exercise 6.2 */}
      <button onClick={reset}>reset stats</button>  {/* Exercise 6.2 */}
      <div>good {store.getState().good}</div>
      <div>neutral {store.getState().ok}</div>  {/* Exercise 6.2 */}
      <div>bad {store.getState().bad}</div>  {/* Exercise 6.2 */}
    </div>
  )
}

const renderApp = () => {
  ReactDOM.render(<App />, document.getElementById('root'))
}

renderApp()
store.subscribe(renderApp)