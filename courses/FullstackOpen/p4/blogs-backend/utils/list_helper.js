const lodash = require('lodash')

const listWithOneBlog = [
  {
    _id: '5a422aa71b54a676234d17f8',
    title: 'Go To Statement Considered Harmful',
    author: 'Edsger W. Dijkstra',
    url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
    likes: 5,
    __v: 0
  }
]

const listWithManyBlogs = [
  {
    _id: "5a422a851b54a676234d17f7",
    title: "React patterns",
    author: "Michael Chan",
    url: "https://reactpatterns.com/",
    likes: 7,
    __v: 0
  },
  {
    _id: "5a422aa71b54a676234d17f8",
    title: "Go To Statement Considered Harmful",
    author: "Edsger W. Dijkstra",
    url: "http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html",
    likes: 5,
    __v: 0
  },
  {
    _id: "5a422b3a1b54a676234d17f9",
    title: "Canonical string reduction",
    author: "Edsger W. Dijkstra",
    url: "http://www.cs.utexas.edu/~EWD/transcriptions/EWD08xx/EWD808.html",
    likes: 12,
    __v: 0
  },
  {
    _id: "5a422b891b54a676234d17fa",
    title: "First class tests",
    author: "Robert C. Martin",
    url: "http://blog.cleancoder.com/uncle-bob/2017/05/05/TestDefinitions.htmll",
    likes: 10,
    __v: 0
  },
  {
    _id: "5a422ba71b54a676234d17fb",
    title: "TDD harms architecture",
    author: "Robert C. Martin",
    url: "http://blog.cleancoder.com/uncle-bob/2017/03/03/TDD-Harms-Architecture.html",
    likes: 0,
    __v: 0
  },
  {
    _id: "5a422bc61b54a676234d17fc",
    title: "Type wars",
    author: "Robert C. Martin",
    url: "http://blog.cleancoder.com/uncle-bob/2016/05/01/TypeWars.html",
    likes: 2,
    __v: 0
  }
]

const dummy = (blogs) => 1

const totalLikes = (blogs) => {
  if (blogs.length === 0) {
    return 0
  }
  return blogs.reduce((x, y) => x + y.likes, 0)
}

const favoriteBlog = (blogs) => {
  if (blogs.length === 0) {
    return null
  }
  const result = blogs.reduce((x, y) => x.likes > y.likes ? x : y, blogs[0])
  return {
    title: result.title,
    author: result.author,
    likes: result.likes
  }
}

const mostBlogs = (blogs) => {
  if (blogs.length === 0) {
    return null
  }

  const blogsByAuthor = lodash.groupBy(blogs, blog => blog.author)
  const authorBloglistPairs = lodash.toPairs(blogsByAuthor)
  const authorBlogCounts = authorBloglistPairs.map(
    ([author, blogs]) => ([author, blogs.length])
  )
  const result = authorBlogCounts.reduce(
    (x, y) => x[1] > y[1] ? x : y, authorBlogCounts[0]
  )
  return {
    author: result[0],
    blogs: result[1]
  }
}

const mostLikes = (blogs) => {
  if (blogs.length === 0) {
    return null
  }

  const blogsByAuthor = lodash.groupBy(blogs, blog => blog.author)
  const authorBloglistPairs = lodash.toPairs(blogsByAuthor)
  const authorLikeCounts = authorBloglistPairs.map(
    ([author, blogs]) => ([author, totalLikes(blogs)])
  )
  const result = authorLikeCounts.reduce(
    (x, y) => x[1] > y[1] ? x : y, authorLikeCounts[0]
  )
  return {
    author: result[0],
    likes: result[1]
  }
}

module.exports = {
  dummy, totalLikes, listWithOneBlog, mostLikes,
  listWithManyBlogs, favoriteBlog, mostBlogs
}
