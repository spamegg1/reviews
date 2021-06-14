const supertest = require('supertest')
const mongoose = require('mongoose')
const helper = require('./test_helper')
const app = require('../app')
const api = supertest(app)
const User = require('../models/user')
const Blog = require('../models/blog')

beforeEach(async () => {
  await Blog.deleteMany({})
  await User.deleteMany({})

  const blogObjects = helper.initialBlogs
    .map(blog => new Blog(blog))

  const promiseArray = blogObjects.map(blog => blog.save())
  await Promise.all(promiseArray)
})

// Exercise 4.23
describe('when some blogs are saved', () => {
  // Exercise 4.8
  test('all blogs are returned', async () => {
    const response = await api.get('/api/blogs')

    expect(response.body.length).toBe(helper.initialBlogs.length)
  })

  // Exercise 4.9
  test('id field is properly named', async () => {
    const response = await api.get('/api/blogs')

    expect(response.body[0].id).toBeDefined()
  })
})

describe('when a blog is posted to api', () => {
  let headers

  beforeEach(async () => {
    const newUser = {
      username: 'janedoez',
      name: 'Jane Z. Doe',
      password: 'password',
    }

    await api
      .post('/api/users')
      .send(newUser)

    const result = await api
      .post('/api/login')
      .send(newUser)

    headers = {
      'Authorization': `bearer ${result.body.token}`
    }
  })

  // Exercise 4.10
  test('it successfully saves a new blog post to db', async () => {
    const blogsAtStart = await helper.blogsInDb()

    await api
      .post('/api/blogs')
      .send(helper.newBlog)
      .set(headers)
      .expect(201)
      .expect('Content-Type', /application\/json/)

    const blogsAtEnd = await helper.blogsInDb()
    expect(blogsAtEnd.length).toBe(blogsAtStart.length + 1)

    const titles = blogsAtEnd.map(blog => blog.title)
    expect(titles).toContain('async/await simplifies making async calls')
  })

  // Exercise 4.11
  test('likes defaults to 0', async () => {
    await api
      .post('/api/blogs')
      .send(helper.blogWithoutLikes)
      .set(headers)
      .expect(201)
      .expect('Content-Type', /application\/json/)

    const blogsAtEnd = await helper.blogsInDb()
    const added = blogsAtEnd.find(b => b.url === helper.blogWithoutLikes.url)
    expect(added.likes).toBe(0)
  })

  // Exercise 4.12
  test('it fails with code 400 if title and/or url are missing', async () => {
    await api
      .post('/api/blogs')
      .send(helper.blogWithoutUrl)
      .set(headers)
      .expect(400)
      .expect('Content-Type', /application\/json/)
  })

  // Exercise 4.19
  test('operation fails with proper error if token is missing', async () => {
    const newBlog = {
      title: 'Blazing Fast Delightful Testing',
      author: 'Rick Hanlon',
    }

    await api
      .post('/api/blogs')
      .send(newBlog)
      .expect(401)
      .expect('Content-Type', /application\/json/)
  })

  // Exercises 4.13-4.14
  describe('after it is saved to database', () => {
    let result

    beforeEach(async () => {
      result = await api
        .post('/api/blogs')
        .send(helper.blogToPostAndDelete)
        .set(headers)
        .expect(201)
        .expect('Content-Type', /application\/json/)
    })

    // Exercise 4.14
    test('it can be updated', async () => {
      const blogToUpdate = result.body
      const editedBlog = {...blogToUpdate, likes: blogToUpdate.likes + 1}

      await api
        .put(`/api/blogs/${blogToUpdate.id}`)
        .send(editedBlog)
        .set(headers)
        .expect(200)
        .expect('Content-Type', /application\/json/)

      const blogsAtEnd = await helper.blogsInDb()
      const edited = blogsAtEnd.find(b => b.url === blogToUpdate.url)
      expect(edited.likes).toBe(blogToUpdate.likes + 1)
    })

    // Exercise 4.13
    test('it can be deleted', async () => {
      const blogToDelete = result.body
      const blogsAtStart = await helper.blogsInDb()

      await api
        .delete(`/api/blogs/${blogToDelete.id}`)
        .set(headers)
        .expect(204)

      const blogsAtEnd = await helper.blogsInDb()
      expect(blogsAtEnd.length).toBe(blogsAtStart.length - 1)

      const titles = blogsAtEnd.map(b => b.title)
      expect(titles).not.toContain(blogToDelete.title)
    })
  })
})

// Exercises 4.15-4.16
describe('creation of a user', () => {
  // Exercise 4.15
  test('succeeds with a fresh username', async () => {
    const usersAtStart = await helper.usersInDb()

    const newUser = {
      username: 'mluukkai',
      name: 'Matti Luukkainen',
      password: 'salainen',
    }

    await api
      .post('/api/users')
      .send(newUser)
      .expect(200)
      .expect('Content-Type', /application\/json/)

    const usersAtEnd = await helper.usersInDb()
    expect(usersAtEnd.length).toBe(usersAtStart.length + 1)

    const usernames = usersAtEnd.map(u => u.username)
    expect(usernames).toContain(newUser.username)
  })

  // Exercise 4.16
  test('creation fails with proper statuscode and message if username already taken', async () => {
    const newUser = {
      username: 'janedoez',
      name: 'Jane Z. Doe',
      password: 'password',
    }

    await api
      .post('/api/users')
      .send(newUser)

    const result = await api
      .post('/api/users')
      .send(newUser)
      .expect(400)
      .expect('Content-Type', /application\/json/)

    expect(result.body.error).toContain('`username` to be unique')
  })

  // Exercise 4.16
  test('creation fails with proper statuscode and message if password is too short', async () => {
    const newUser = {
      username: 'johndoe',
      name: 'John Doe',
      password: 'p',
    }

    const result = await api
      .post('/api/users')
      .send(newUser)
      .expect(400)
      .expect('Content-Type', /application\/json/)

    expect(result.body.error).toContain('password must min length 3')
  })
})

afterAll(() => {
  mongoose.connection.close()
})
