import React from 'react'
import ReactDOM from 'react-dom'
import App from './App'
import store from './reducers/counterReducer'

const renderApp = () => {
  ReactDOM.render(<App />, document.getElementById('root'))
}

renderApp()
store.subscribe(renderApp)
