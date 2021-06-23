// A FEW THINGS LEFT TO DO:
// Comments don't show without a page reload after posting it
// Removing a blog does not update "blog count by user" stat without a reload
// More styling!
// Tests are probably broken! Also add tests for new features.
// Some errors in console: unmounted component, GET req to socket-node etc.
import React, { useEffect, useRef } from 'react'
import Notification from './components/Notification'
import LoginForm from './components/LoginForm'                   // Exercise 5.5
import BlogForm from './components/BlogForm'                     // Exercise 5.5
import Togglable from './components/Togglable'                   // Exercise 5.5

import { setNotifyAction } from './reducers/notificationReducer' // Exercise 7.9
import { useDispatch, useSelector } from 'react-redux'     // Exercise 7.9, 7.10
import { initBlogsAction, createBlogAction } from './reducers/blogsReducer'//710
import BlogList from './components/BlogList'                    // Exercise 7.10
import { loginAction, logoutAction } from './reducers/userReducer'    // Ex 7.12
import storage from './utils/storage'                           // Exercise 7.12
import { initUsersAction } from './reducers/usersReducer'       // Exercise 7.13
import Users from './components/Users'                          // Exercise 7.13
import { BrowserRouter, Switch, Route, Link } from 'react-router-dom' // Ex 7.13
import User from './components/User'                            // Exercise 7.14
import Blog from './components/Blog'                            // Exercise 7.15

const App = () => {
  const blogFormRef = useRef()
  const dispatch = useDispatch()                                 // Exercise 7.9
  const user = useSelector(state => state.user)                 // Exercise 7.12

  useEffect(() => {
    dispatch(initBlogsAction())                                 // Exercise 7.10
    dispatch(initUsersAction())                                 // Exercise 7.13
    const user = storage.loadUser()      // check if a user is already logged in
    if (user) {
      dispatch(loginAction(user))                               // Exercise 7.12
    }
  }, [dispatch])

  const createBlog = async (blog) => {           // Exercise 5.3, refactored 5.6
    try {
      dispatch(createBlogAction(blog))                          // Exercise 7.10
      blogFormRef.current.toggleVisibility()  // form is invisible after posting
      const msg = `a new blog: ${blog.title}, by: ${blog.author}, added`
      dispatch(setNotifyAction(msg, 'success'))                  // Exercise 7.9
    }
    catch (exception) {
      console.log(exception)
      dispatch(setNotifyAction('error creating blog', 'error'))  // Exercise 7.9
    }
  }

  const handleLogout = () => {                                   // Exercise 5.2
    storage.logoutUser()
    dispatch(logoutAction())                                    // Exercise 7.12
  }

  if ( !user ) {
    return (
      <div>
        <Notification />                      {/* Exercise 7.9: removed prop */}
        <LoginForm />                                      {/* Exercise 7.12 */}
      </div>
    )
  }

  return (                         // Exercise 5.6: moving state into components
    <BrowserRouter>                                        {/* Exercise 7.13 */}
      <div className="navBar">                             {/* Exercise 7.16 */}
        <Link className="link" to="/">blogs</Link>
        <Link className="link" to="/users">users</Link>    {/* Exercise 7.13 */}
        {user.name} logged in <button onClick={handleLogout}>logout</button>
      </div>

      <h2>Blogs</h2>
      <Notification />                        {/* Exercise 7.9: removed prop */}

      <Switch>
        <Route path="/users/:id">                          {/* Exercise 7.14 */}
          <User />
        </Route>
        <Route path="/blogs/:id">                          {/* Exercise 7.15 */}
          <Blog />
        </Route>
        <Route path="/users">
          <Users />                                        {/* Exercise 7.13 */}
        </Route>
        <Route path="/">
          <Togglable buttonLabel="new blog" ref={blogFormRef}>    {/* Ex 5.6 */}
            <BlogForm createBlog={createBlog}/>
          </Togglable>
          <BlogList />
        </Route>
      </Switch>
    </BrowserRouter>
  )
}

export default App
