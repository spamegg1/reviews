import React, { useState } from 'react'
import PropTypes from 'prop-types'

const Blog = ({ blog, handleLike, handleRemove, owner }) => {
  const [view, setView] = useState(false)  // Exercise 5.7

  // Exercise 5.7
  const blogStyle = {
    paddingTop: 10,
    paddingLeft: 2,
    border: 'solid',
    borderWidth: 1,
    marginBottom: 5
  }

  if (view) {  // Exercise 5.7
    return (
      <div style={blogStyle} className="blog">  {/* Exercise 5.13, 5.14 */}
        <p>
          {blog.title} {blog.author}
          <button id="hide" onClick={() => setView(!view)}>hide</button>
        </p>
        <p>{blog.url}</p>
        <p>
          likes {blog.likes}
          <button
            id="like" // 5.20
            onClick={() => handleLike(blog.id)}>
            like
          </button>  {/* 5.8 */}
        </p>
        <p>{blog.user.name}</p>
        {owner  // Exercise 5.10
          ? <button
            id="remove" // 5.21
            onClick={() => handleRemove(blog.id)}>
                remove
          </button>
          : null
        }
      </div>
    )
  }
  return (
    <div>
      {blog.title} {blog.author}
      <button id="view" onClick={() => setView(!view)}>view</button>{/*5.7,17*/}
    </div>
  )
}

// Exercise 5.11
Blog.propTypes = {
  handleLike: PropTypes.func.isRequired,
  handleRemove: PropTypes.func.isRequired,
  owner: PropTypes.bool.isRequired
}

export default Blog