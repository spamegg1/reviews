// Exercise 7.10: whole file: Change the application's blogs to use Redux store
import blogService from '../services/blogs'

const blogsReducer = (state = [], action) => {
  switch (action.type) {

  case 'CREATE':    // new blog is posted to backend by the action creator below
    return [...state, action.data]                              // Exercise 7.10

  case 'INIT_BLOGS':   // blogs are fetched from backend by action creator below
    return action.data                                          // Exercise 7.10

  case 'LIKE': {       // number of likes is updated by the action creator below
    const likedBlog = action.data                               // Exercise 7.11
    return state.map(blog => blog.id === likedBlog.id ? likedBlog : blog)
  }

  case 'DELETE':     // blog is deleted from backend by the action creator below
    return state.filter(blog => blog.id !== action.data)        // Exercise 7.11

  case 'COMMENT': {          // comments are updated by the action creator below
    const commentedBlog = action.data                     // Exercise 7.17, 7.18
    return state.map(blog => blog.id === commentedBlog.id ? commentedBlog : blog)
  }

  default:
    return state
  }
}

// Exercise 7.10, 7.11, 7.17, 7.18: action creators
export const initBlogsAction = () => {
  return async dispatch => {
    const blogs = await blogService.getAll()
    dispatch({
      type: 'INIT_BLOGS',
      data: blogs
    })
  }
}

export const createBlogAction = (blog) => {                     // Exercise 7.11
  return async dispatch => {
    const newBlog = await blogService.create(blog)
    dispatch({
      type: 'CREATE',
      data: newBlog
    })
  }
}

export const likeBlogAction = (blog) => {                       // Exercise 7.11
  return async dispatch => {
    const updatedBlog = { ...blog, likes: blog.likes + 1 }
    const likedBlog = await blogService.update(updatedBlog)
    dispatch({
      type: 'LIKE',
      data: likedBlog
    })
  }
}

export const commentBlogAction = (id, comment) => {       // Exercise 7.17, 7.18
  return async dispatch => {
    const updatedBlog = blogService.comment(id, comment)
    dispatch({
      type: 'COMMENT',
      data: updatedBlog
    })
  }
}

export const deleteBlogAction = (id) => {                       // Exercise 7.11
  return async dispatch => {
    await blogService.remove(id)
    dispatch({
      type: 'DELETE',
      data: id
    })
  }
}

export default blogsReducer
