import { combineReducers, createStore, applyMiddleware } from 'redux'     // 7.9
import thunk from 'redux-thunk'                                  // Exercise 7.9
import { composeWithDevTools } from 'redux-devtools-extension'   // Exercise 7.9
import notificationReducer from './reducers/notificationReducer' // Exercise 7.9
import blogsReducer from './reducers/blogsReducer'              // Exercise 7.10
import userReducer from './reducers/userReducer'                // Exercise 7.12
import usersReducer from './reducers/usersReducer'              // Exercise 7.13

const reducer = combineReducers({
  notification: notificationReducer,                             // Exercise 7.9
  blogs: blogsReducer,                                          // Exercise 7.10
  user: userReducer,                                            // Exercise 7.12
  users: usersReducer,                                          // Exercise 7.13
})

const store = createStore(
  reducer,
  composeWithDevTools(applyMiddleware(thunk))     // Exercise 7.9: async actions
)

export default store
