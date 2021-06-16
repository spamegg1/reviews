import { createStore, combineReducers, applyMiddleware } from 'redux'
import { composeWithDevTools } from 'redux-devtools-extension'
import thunk from 'redux-thunk'
import blogReducer from './reducers/blogs'
import notificationReducer from './reducers/notification'
import userReducer from './reducers/user'
import usersReducer from './reducers/users'

const reducer = combineReducers({
  blogs: blogReducer,
  notification: notificationReducer,
  user: userReducer,
  users: usersReducer
})

export default createStore(reducer, composeWithDevTools(
  applyMiddleware(thunk)
))