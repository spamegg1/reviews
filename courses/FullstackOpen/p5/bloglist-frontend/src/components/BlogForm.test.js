import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import { render, fireEvent } from '@testing-library/react'
import BlogForm from './BlogForm'

// Exercise 5.16
const blogData = {
  title: 'Component testing is done with react-testing-library',
  author: 'Matti Luukkainen',
  url: 'https://fullstackopen.com/en/part5'
}

describe('BlogForm', () => {
  // Exercise 5.16
  test('calls event handler it received as props with right details when a new blog is created', () => {
    const createBlog = jest.fn()  // mock event handler function
    const component = render(<BlogForm createBlog={createBlog} />)

    // get the forms to be filled
    const title = component.container.querySelector('#title')
    const author = component.container.querySelector('#author')
    const url = component.container.querySelector('#url')
    const form = component.container.querySelector('form')

    // fill all the forms and submit the data
    fireEvent.change(title, { target: { value: blogData.title } })
    fireEvent.change(author, { target: { value: blogData.author } })
    fireEvent.change(url, { target: { value: blogData.url } })
    fireEvent.submit(form)

    expect(createBlog.mock.calls).toHaveLength(1)
    expect(createBlog.mock.calls[0][0]).toEqual(blogData)
  })
})
