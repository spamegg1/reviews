// Exercise 7.10, 7.11: refactoring list of blogs out of App, using Redux store
import React from 'react'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'                         // Exercise 7.15

const BlogList = () => {
  const blogs = useSelector(state => state.blogs)               // Exercise 7.10
    .sort((b1, b2) => b2.likes - b1.likes)

  return (
    <div>
      {blogs.map(blog =>
        <div key={blog.id}>
          <Link to={`/blogs/${blog.id}`}>                  {/* Exercise 7.15 */}
            {blog.title} {blog.user.name}
          </Link>
        </div>
      )}
    </div>

  )
}

export default BlogList
