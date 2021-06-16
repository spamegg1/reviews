import React, { useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {
  BrowserRouter as Router,
  Switch, Route, Link
} from 'react-router-dom'

import Notification from './components/Notification'
import Togglable from './components/Togglable'
import NewBlog from './components/NewBlog'
import LoginForm from './components/LoginForm'
import Blogs from './components/Blogs'
import Blog from './components/Blog'
import Users from './components/Users'
import User from './components/User'

import storage from './utils/storage'
import { setNotification } from './reducers/notification'
import { initializeBlogs, createBlog } from './reducers/blogs'
import { login, logout } from './reducers/user'
import { initializeUsers } from './reducers/users'

const App = () => {
  const user = useSelector(state => state.user)
  const blogFormRef = React.createRef()
  const dispatch = useDispatch()

  useEffect(() => {
    dispatch(initializeBlogs())
    dispatch(initializeUsers())
    const user = storage.loadUser()
    if (user) {
      dispatch(login(user))
    }
  }, [dispatch])

  const handleCreateBlog = async (blog) => {
    blogFormRef.current.toggleVisibility()
    dispatch(createBlog(blog))
    dispatch(setNotification(`a new blog '${blog.title}' by ${blog.author} added!`))
  }

  const handleLogout = () => {
    dispatch(logout())
    storage.logoutUser()
  }

  if ( !user ) {
    return (
      <div>
        <h2>Login to application</h2>

        <Notification />
        <LoginForm />
      </div>
    )
  }

  const padding = {
    padding: 5
  }

  const navStyle = {
    margin: 10,
    padding: 10,
    backgroundColor: 'lightgray'
  }

  return (
    <Router>
      <div style={navStyle}>
        <Link style={padding} to="/">blogs</Link>
        <Link style={padding} to="/users">users</Link>
        <span>
          {user.name} logged in <button onClick={handleLogout}>logout</button>
        </span>
      </div>

      <h2>Blogs</h2>

      <Notification />

      <Switch>
        <Route path="/users/:id">
          <User />
        </Route>
        <Route path="/blogs/:id">
          <Blog />
        </Route>
        <Route path="/users">
          <Users />
        </Route>
        <Route path="/">
          <Togglable buttonLabel='create new blog' ref={blogFormRef}>
            <NewBlog createBlog={handleCreateBlog} />
          </Togglable>
          <Blogs />
        </Route>
      </Switch>

    </Router>
  )
}

export default App