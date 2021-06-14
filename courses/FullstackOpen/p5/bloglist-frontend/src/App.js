import React, { useState, useEffect, useRef } from 'react'
import Blog from './components/Blog'
import blogService from './services/blogs'
import loginService from './services/login'
import Notification from './components/Notification'
import LoginForm from './components/LoginForm'  // Exercise 5.5
import BlogForm from './components/BlogForm'  // Exercise 5.5
import Togglable from './components/Togglable'  // Exercise 5.5

const App = () => {
  const [blogs, setBlogs] = useState([])
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [user, setUser] = useState(null)
  const [notification, setNotification] = useState(null)
  const blogFormRef = useRef()

  // get all the blogs, put them in state
  useEffect(() => {
    blogService
      .getAll()
      .then(blogs =>
        setBlogs( blogs )
      )
  }, [])  // <- use effect only on first render!

  // check if a user is already logged in
  useEffect(() => {
    const loggedUserJSON = window.localStorage.getItem('loggedBlogappUser')
    if (loggedUserJSON) {
      const user = JSON.parse(loggedUserJSON)
      setUser(user)
      blogService.setToken(user.token)
    }
  }, [])  // <- use effect only on first render!

  // Exercise 5.4
  const handleNotify = (message, type) => {
    setNotification({
      message: message,
      type: type
    })
    setTimeout(() => {
      setNotification(null)
    }, 5000)
  }

  // Exercise 5.1, 5.2
  const handleLogin = async (event) => {
    event.preventDefault()

    try {
      const user = await loginService.login({
        username, password
      })

      window.localStorage.setItem(
        'loggedBlogappUser', JSON.stringify(user)
      )

      blogService.setToken(user.token)
      setUser(user)
      setUsername('')
      setPassword('')
    }
    catch (exception) {
      console.log('error logging in')
      handleNotify('wrong username or password', 'error')  // 5.4
    }
  }

  // Exercise 5.8
  const handleLike = async (id) => {
    const blogToLike = blogs.find(b => b.id === id)
    const likedBlog = { ...blogToLike, likes: blogToLike.likes + 1, user: blogToLike.user.id }
    await blogService.update(blogToLike.id, likedBlog)
    setBlogs(blogs.map(b => b.id === id ?  { ...blogToLike, likes: blogToLike.likes + 1 } : b))
  }

  // Exercise 5.10
  const handleRemove = async (id) => {
    const blogToRemove = blogs.find(b => b.id === id)
    const confirm = window.confirm(
      `Remove blog ${blogToRemove.title} by ${blogToRemove.author}?`
    )
    if (confirm) {
      await blogService.remove(id)
      setBlogs(blogs.filter(b => b.id !== id))
    }
  }

  // Exercise 5.3, refactored in Exercise 5.6
  const createBlog = async (blog) => {
    try {
      const newBlog = await blogService.create(blog)
      setBlogs(blogs.concat(newBlog))
      blogFormRef.current.toggleVisibility() // form is invisible after posting
      // Exercise 5.4
      handleNotify(`a new blog: ${newBlog.title}, by: ${newBlog.author}, added`, 'success')
    }
    catch (exception) {
      console.log(exception)
      handleNotify('error creating blog', 'error')  // 5.4
    }
  }

  // Exercise 5.2
  const logout = async () => {
    window.localStorage.removeItem('loggedBlogappUser')
    setUser(null)
  }

  // Exercise 5.1: login form. Refactored into /components/LoginForm.js in 5.6
  const loginForm = () => (
    <LoginForm
      username={username}
      password={password}
      handleUsernameChange={({ target }) => setUsername(target.value)}
      handlePasswordChange={({ target }) => setPassword(target.value)}
      handleSubmit={handleLogin}
    />
  )

  // Exercise 5.1
  const blogList = () => (
    <div>
      {blogs
        .sort((b1, b2) => b2.likes - b1.likes)                   // Exercise 5.9
        .map(blog =>
          <Blog className='blog'                                // Exercise 5.13
            key={blog.id} blog={blog}
            handleLike={handleLike}                              // Exercise 5.8
            handleRemove={handleRemove}                         // Exercise 5.10
            owner={blog.user.username === user.username}        // Exercise 5.10
          />
        )}
    </div>
  )

  // Exercise 5.3: blog create form. Refactored /components/BlogForm.js in 5.6
  const blogForm = () => (
    <Togglable buttonLabel="new blog" ref={blogFormRef}>  {/* 5.6 */}
      <BlogForm createBlog={createBlog}/>
    </Togglable>
  )

  // Exercise 5.6: moving state into components
  return (
    <div>
      <Notification notification={notification} />
      {user === null
        ? <Togglable buttonLabel="login">
          {loginForm()}
        </Togglable>
        : <>
          <h2>Blogs</h2>
          {user.name} logged in
          <button onClick={logout}>logout</button>
          {blogForm()}
          {blogList()}
        </>
      }
    </div>
  )
}

export default App
