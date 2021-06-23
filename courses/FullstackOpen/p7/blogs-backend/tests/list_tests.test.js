const listHelper = require('../utils/list_helper')

test('dummy returns one', () => {
  const blogs = []

  const result = listHelper.dummy(blogs)
  expect(result).toBe(1)
})

describe('totalLikes', () => {
  test('of empty list is zero', () => {
    expect(listHelper.totalLikes([])).toBe(0)
  })

  test('when list has only one blog, equals the likes of that', () => {
    const blogs = listHelper.listWithOneBlog
    const result = listHelper.totalLikes(blogs)
    expect(result).toBe(blogs[0].likes)
  })

  test('of a bigger list is calculated right', () => {
    const blogs = listHelper.listWithManyBlogs
    const result = listHelper.totalLikes(blogs)
    expect(result).toBe(36)
  })
})

describe('favoriteBlog', () => {
  test('of empty list is null', () => {
    expect(listHelper.favoriteBlog([])).toBe(null)
  })

  test('when list has only one blog, equals that blog', () => {
    const blogs = listHelper.listWithOneBlog
    const result = listHelper.favoriteBlog(blogs)
    const expected = {
      title: 'Go To Statement Considered Harmful',
      author: 'Edsger W. Dijkstra',
      likes: 5
    }
    expect(result).toEqual(expected)
  })

  test('of a bigger list is calculated right', () => {
    const blogs = listHelper.listWithManyBlogs
    const result = listHelper.favoriteBlog(blogs)
    const expected = {
      title: "Canonical string reduction",
      author: "Edsger W. Dijkstra",
      likes: 12
    }
    expect(result).toEqual(expected)
  })
})

describe('mostBlogs', () => {
  test('of empty list is null', () => {
    expect(listHelper.mostBlogs([])).toBe(null)
  })

  test('when list has only one blog, equals that blog', () => {
    const blogs = listHelper.listWithOneBlog
    const result = listHelper.mostBlogs(blogs)
    const expected = {
      author: 'Edsger W. Dijkstra',
      blogs: 1
    }
    expect(result).toEqual(expected)
  })

  test('of a bigger list is calculated right', () => {
    const blogs = listHelper.listWithManyBlogs
    const result = listHelper.mostBlogs(blogs)
    const expected = {
      author: "Robert C. Martin",
      blogs: 3
    }
    expect(result).toEqual(expected)
  })
})

describe('mostLikes', () => {
  test('of empty list is null', () => {
    expect(listHelper.mostLikes([])).toBe(null)
  })

  test('when list has only one blog, equals that blog', () => {
    const blogs = listHelper.listWithOneBlog
    const result = listHelper.mostLikes(blogs)
    const expected = {
      author: 'Edsger W. Dijkstra',
      likes: 5
    }
    expect(result).toEqual(expected)
  })

  test('of a bigger list is calculated right', () => {
    const blogs = listHelper.listWithManyBlogs
    const result = listHelper.mostLikes(blogs)
    const expected = {
      author: "Edsger W. Dijkstra",
      likes: 17
    }
    expect(result).toEqual(expected)
  })
})
