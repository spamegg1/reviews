import React from 'react'
import ReactDOM from 'react-dom'
import App from './App'
import './index.css'
import { Provider } from 'react-redux'                           // Exercise 7.9
import store from './store'                                      // Exercise 7.9

ReactDOM.render(
  <Provider store={store}>                                  {/* Exercise 7.9 */}
    <App />
  </Provider>,
  document.getElementById('root')
)
