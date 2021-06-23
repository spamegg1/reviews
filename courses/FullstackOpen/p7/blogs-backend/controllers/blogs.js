const blogsRouter = require('express').Router()
const Blog = require('../models/blog')
const userExtractor = require('../utils/middleware').userExtractor // Exercise 4.22

blogsRouter.get('/', async (request, response) => {
  // Exercise 4.17
  const blogs = await Blog
    .find({})
    .populate('user', { username: 1, name: 1 })
  response.json(blogs)
})

// Exercise 4.22
blogsRouter.post('/', userExtractor, async (request, response) => {
  const blog = new Blog(request.body)

  // Exercise 4.11
  if (!blog.likes) {
    blog.likes = 0
  }

  // Exercise 4.12
  if (!blog.url || !blog.title) {
    return response.status(400).send({ error: 'title or url missing ' })
  }

  const user = request.user  // Exercise 4.22
  blog.user = user._id

  const savedBlog = await blog.save()
  user.blogs = user.blogs.concat(savedBlog._id)
  await user.save()

  response.status(201).json(savedBlog)
})

// Exercise 4.13, 4.22
blogsRouter.delete('/:id', userExtractor, async (request, response) => {
  const user = request.user                // userExtractor adds user to request
  const blog = await Blog.findById(request.params.id)

  // Exercise 4.21
  if (blog.user.toString() !== user.id.toString()) {
    return response.status(401).json({
      error: 'only the creator can delete blogs'
     })
  }

  await blog.remove()
  user.blogs = user.blogs.filter(b =>
    b.id.toString() !== request.params.id.toString()
  )

  await user.save()
  response.status(204).end()
})

// Exercise 4.14 (should this use token authentication? I'll leave it for now)
blogsRouter.put('/:id', async (request, response) => {
  const blog = request.body
  blog.user = blog.user.id                                      // Exercise 7.11
  const updatedBlog = await Blog
    .findByIdAndUpdate(request.params.id, blog, { new: true })
    .populate('user')                                           // Exercise 7.11
  response.json(updatedBlog.toJSON())
})

// Exercise 7.17
blogsRouter.post('/:id/comments', async (request, response) => {
  const blog = await Blog.findById(request.params.id)

  blog.comments = blog.comments.concat(request.body.comment)
  await blog.save()

  response.json(blog)
})

module.exports = blogsRouter
