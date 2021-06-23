const Blog = require("../models/blog")
const User = require("../models/user")

const initialBlogs = [
  {
    title: 'React patterns',
    author: 'Michael Chan',
    url: 'https://reactpatterns.com/',
    likes: 7,
  },
  {
    title: 'Go To Statement Considered Harmful',
    author: 'Edsger W. Dijkstra',
    url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
    likes: 5,
  },
  {
    title: 'Canonical string reduction',
    author: 'Edsger W. Dijkstra',
    url: 'http://www.cs.utexas.edu/~EWD/transcriptions/EWD08xx/EWD808.html',
    likes: 12,
  },
]

const newBlog = {
  title: 'async/await simplifies making async calls',
  author: 'Matti Luukkainen',
  url: 'https://fullstackopen.com/en/part4/testing_the_backend',
  likes: 5
}

const blogWithoutLikes = {
  title: 'if likes field is missing, it defaults to zero',
  author: 'Spam Egg',
  url: 'https://spamegg1.github.io',
}

const blogWithoutUrl = {
  title: 'Blazing Fast Delightful Testing',
  author: 'Rick Hanlon',
}

const blogToPostAndDelete = {
  title: 'if a blog is saved to database, it can be deleted',
  author: 'spamegg',
  url: 'https://github.com/spamegg1',
  likes: 3
}

const newUser = {
  username: 'mluukkai',
  name: 'Matti Luukkainen',
  password: 'salainen',
}

const rootUser = {
  username: 'root',
  name: 'Superuser',
  password: 'salainen',
}

const blogsInDb = async () => {
  const blogs = await Blog.find({})
  return blogs.map(blog => blog.toJSON())
}

const usersInDb = async () => {
  const users = await User.find({})
  return users.map(user => user.toJSON())
}

module.exports = {
  initialBlogs, blogsInDb, newBlog, blogWithoutLikes, blogWithoutUrl,
  blogToPostAndDelete, usersInDb, newUser, rootUser
}