// Exercise 7.14: whole file: implement view for individual user and their blogs
import React from 'react'
import { Link, useParams } from 'react-router-dom'
import { useSelector } from 'react-redux'

const User = () => {
  const id = useParams().id                                   // get id from URL
  const users = useSelector(state => state.users)
  const user = users.find(u => u.id === id)

  if (!user) {
    return null
  }

  return (
    <div>
      <h3>{user.name}</h3>

      <b>added blogs</b>
      <ul>
        {user.blogs.map(blog =>
          <li key={blog.id}>
            <Link to={`/blogs/${blog.id}`}>
              {blog.title}
            </Link>
          </li>
        )}
      </ul>
    </div>
  )
}

export default User
