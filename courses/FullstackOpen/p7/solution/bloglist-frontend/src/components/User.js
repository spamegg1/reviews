import React from 'react'
import { Link } from 'react-router-dom'

import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

const User = () => {
  const id = useParams().id
  const user = useSelector(state => state.users.find(u => u.id === id))

  if (!user) {
    return null
  }

  return (
    <div>
      <h3>{user.name}</h3>

      <b>added blogs</b>
      <ul>
        {user.blogs.map(b =>
          <li key={b.id}>
            <Link to={`/blogs/${b.id}`}>
              {b.title}
            </Link>
          </li>
        )}
      </ul>
    </div>
  )
}

export default User