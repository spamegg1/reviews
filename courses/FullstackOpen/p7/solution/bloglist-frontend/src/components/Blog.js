import React from 'react'

import { useSelector, useDispatch } from 'react-redux'
import { useParams, useHistory } from 'react-router-dom'
import { likeBlog, removeBlog, commentBlog } from '../reducers/blogs'

const Comments = ({ comments, handleComment }) => {
  if ( comments.length === 0) {
    return null
  }

  const addComment = (event) => {
    event.preventDefault()
    const content = event.target.comment.value
    event.target.comment.value = ''
    handleComment(content)
  }

  return (
    <div>
      <h3>comments</h3>
      <form onSubmit={addComment}>
        <input name="comment" />
        <button type="submit">add comment</button>
      </form>
      {comments.map((c, i) =>
        <p key={i}>{c}</p>
      )}
    </div>
  )
}

const Blog = () => {
  const id = useParams().id
  const blog = useSelector(state => state.blogs.find(b => b.id === id))
  const user = useSelector(state => state.user)
  const dispatch = useDispatch()
  const history = useHistory()

  if (!blog) {
    return null
  }

  const own = user && user.username === blog.user.username

  const handleLike = () => {
    dispatch(likeBlog(blog))
  }

  const handleRemove = () => {
    const ok = window.confirm(`Remove blog ${blog.title} by ${blog.author}`)
    if (ok) {
      dispatch(removeBlog(id))
      history.push('/')
    }
  }

  const handleComment = (comment) => {
    dispatch(commentBlog(id, comment))
  }

  return (
    <div className='blog'>
      <h3>{blog.title} by {blog.author}</h3>
      <div>
        <div><a href={blog.url}>{blog.url}</a></div>
        <div>likes {blog.likes}
          <button onClick={handleLike}>like</button>
        </div>
        {own&&<button onClick={handleRemove}>remove</button>}
        <Comments
          comments={blog.comments}
          handleComment={handleComment}
        />
      </div>
    </div>
  )
}

export default Blog