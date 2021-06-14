import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import { render, fireEvent } from '@testing-library/react'
import Blog from './Blog'

const other = {
  own: true,
  handleRemove: () => {},
  handleLike: () => {}
}

describe('Blog', () => {
  test('renders only author and title by default', () => {
    const blog = {
      author: 'Ron Jeffries',
      title: 'You’re NOT gonna need it!',
      url: 'https://ronjeffries.com/xprog/articles/practices/pracnotneed/',
      likes: 3,
    }

    const component = render(
      <Blog blog={blog} {...other} />
    )

    expect(component.container).toHaveTextContent(blog.author)
    expect(component.container).toHaveTextContent(blog.title)
    expect(component.container).not.toHaveTextContent(blog.url)
  })

  test('renders url and likes when expanded', () => {
    const blog = {
      author: 'Ron Jeffries',
      title: 'You’re NOT gonna need it!',
      url: 'https://ronjeffries.com/xprog/articles/practices/pracnotneed/',
      likes: 3,
      user: {
        name: 'Arto Hellas'
      }
    }

    const component = render(
      <Blog blog={blog} {...other} />
    )

    const viewButton = component.getByText('view')
    fireEvent.click(viewButton)

    expect(component.container).toHaveTextContent(blog.url)
    expect(component.container).toHaveTextContent(`likes ${blog.likes}`)
  })

  test('when liked twice, the event handler gets called twice', () => {
    const blog = {
      author: 'Ron Jeffries',
      title: 'You’re NOT gonna need it!',
      url: 'https://ronjeffries.com/xprog/articles/practices/pracnotneed/',
      likes: 3,
      id: 1,
      user: {
        name: 'Arto Hellas'
      }
    }

    other.handleLike = jest.fn()

    const component = render(
      <Blog blog={blog} {...other} />
    )

    const viewButton = component.getByText('view')
    fireEvent.click(viewButton)

    const likeButton = component.getByText('like')
    fireEvent.click(likeButton)
    fireEvent.click(likeButton)

    expect(other.handleLike.mock.calls.length).toBe(2)
  })

})