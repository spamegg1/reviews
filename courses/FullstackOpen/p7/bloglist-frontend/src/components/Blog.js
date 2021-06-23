import React from 'react'                     // removed useState: Exercise 7.15
import { useDispatch, useSelector } from 'react-redux'          // Exercise 7.10
import { useParams, useHistory } from 'react-router'            // Exercise 7.15
import { likeBlogAction, deleteBlogAction } from '../reducers/blogsReducer'//711
import Comments from './Comments'                               // Exercise 7.18
import { commentBlogAction } from '../reducers/blogsReducer'    // Exercise 7.18

const Blog = () => {                                            // Exercise 7.12
  const user = useSelector(state => state.user)                 // Exercise 7.12
  const dispatch = useDispatch()                                // Exercise 7.10
  const history = useHistory()                                  // Exercise 7.15

  const id = useParams().id                                     // Exercise 7.15
  const blog = useSelector(state => state.blogs.find(b => b.id === id))  // 7.15
  if (!blog) {                                                  // Exercise 7.15
    return null
  }

  const owner = blog.user.username === user.username            // Exercise 7.12

  const handleLike = async (blogToLike) => {                     // Exercise 5.8
    dispatch(likeBlogAction(blogToLike))                        // Exercise 7.11
  }

  const handleRemove = async (blog) => {                        // Exercise 7.11
    const msg = `Remove blog ${blog.title} by ${blog.author}?`
    const confirm = window.confirm(msg)
    if (confirm) {
      dispatch(deleteBlogAction(blog.id))                       // Exercise 7.11
    }
    history.push('/')                                           // Exercise 7.15
  }

  const handleComment = (comment) => {                          // Exercise 7.18
    dispatch(commentBlogAction(id, comment))
  }

  return (
    <div className="blog">                           {/* Exercise 5.13, 5.14 */}
      <h3>{blog.title} by {blog.author}</h3>               {/* Exercise 7.15 */}
      <p>{blog.url}</p>
      <p>
        likes {blog.likes}
        <button
          id="like"                                             // Exercise 5.20
          onClick={() => handleLike(blog)}>                {/* Exercise 7.11 */}
          like
        </button>                                           {/* Exercise 5.8 */}
      </p>
      <p>{blog.user.name}</p>
      {owner                                                    // Exercise 5.10
        ? <button
          id="remove"                                           // Exercise 5.21
          onClick={() => handleRemove(blog)}>              {/* Exercise 7.11 */}
              remove
        </button>
        : null
      }
      <Comments                                                 // Exercise 7.18
        comments={blog.comments}
        handleComment={handleComment}
      />
    </div>
  )
}

export default Blog
