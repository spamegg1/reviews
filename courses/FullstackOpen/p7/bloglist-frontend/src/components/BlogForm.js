import React, { useState } from 'react'

const BlogForm = ({ createBlog }) => {
  const [title, setTitle] = useState('')
  const [author, setAuthor] = useState('')
  const [url, setUrl] = useState('')

  const handleNewBlog = (event) => {
    event.preventDefault()
    createBlog({ title, author, url })
    setTitle('')
    setAuthor('')
    setUrl('')
  }

  const field = (name, value, handler) => (
    <div>
      {name}:
      <input
        type="text"
        value={value}
        id={name}                      // Exercise 5.16 (also used in 5.17-5.22)
        onChange={({ target }) => handler(target.value)}
      />
    </div>
  )

  return (
    <div className="blogForm">                             {/* Exercise 5.16 */}
      <h2>create new</h2>
      <form onSubmit={handleNewBlog}>
        {field('title', title, setTitle)}
        {field('author', author, setAuthor)}
        {field('url', url, setUrl)}
        <button id="create" type="submit">create</button>     {/* Ex 5.17-22 */}
      </form>
    </div>
  )
}

export default BlogForm
