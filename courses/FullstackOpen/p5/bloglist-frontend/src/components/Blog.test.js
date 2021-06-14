import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import { render, fireEvent } from '@testing-library/react'
import Blog from './Blog'

// Exercise 5.13, 5.14. 5.15
const title = 'Component testing is done with react-testing-library'
const author = 'Matti Luukkainen'
const url = 'https://fullstackopen.com/en/part5'
const user = { name: 'Spam Egg' }
const blog = { title: title, author: author, url: url, likes: 7, user: user }
const rest = {
  owner: true,
  handleLike: () => {},
  handleRemove: () => {}
}

describe('Blog', () => {
  // Exercise 5.13
  test('renders title and author, but not url or likes by default', () => {
    const component = render(<Blog blog={blog} {...rest} />)
    expect(component.container).toHaveTextContent(title)
    expect(component.container).toHaveTextContent(author)
    expect(component.container).not.toHaveTextContent(url)
    expect(component.container).not.toHaveTextContent(`${blog.likes}`)
  })

  // Exercise 5.14
  test('renders url and likes when view button is clicked', () => {
    const component = render(<Blog blog={blog} {...rest} />)

    const button = component.getByText('view')
    fireEvent.click(button)

    expect(component.container).toHaveTextContent(url)
    expect(component.container).toHaveTextContent(`likes ${blog.likes}`)
  })

  // Exercise 5.15
  test('clicking likes twice calls handler twice', () => {
    const mockHandler = jest.fn()
    rest.handleLike = mockHandler

    const component = render(<Blog blog={blog} {...rest} />)

    // to click like, first we have to click view to display like button
    const viewButton = component.getByText('view')
    fireEvent.click(viewButton)

    const likeButton = component.getByText('like')
    fireEvent.click(likeButton)
    fireEvent.click(likeButton)

    expect(mockHandler.mock.calls).toHaveLength(2)
  })
})
