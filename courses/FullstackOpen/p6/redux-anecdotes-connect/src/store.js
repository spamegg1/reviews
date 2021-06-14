// Exercise 6.9
import { combineReducers, createStore, applyMiddleware } from 'redux' // Ex 6.15
import thunk from 'redux-thunk'  // Exercise 6.15
import { composeWithDevTools } from 'redux-devtools-extension'
import anecdoteReducer from './reducers/anecdoteReducer'
import notificationReducer from './reducers/notificationReducer'  // Ex 6.10, 11
import filterReducer from './reducers/filterReducer'  // Exercise 6.12

const reducer = combineReducers({
  anecdotes: anecdoteReducer,
  notification: notificationReducer,  // Exercise 6.10
  filter: filterReducer               // Exercise 6.12
})

const store = createStore(
  reducer,
  composeWithDevTools(
    applyMiddleware(thunk)  // Exercise 6.15
  ))

export default store
