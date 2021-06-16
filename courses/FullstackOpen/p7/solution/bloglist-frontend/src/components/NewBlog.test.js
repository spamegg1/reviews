import React from 'react'
import '@testing-library/jest-dom/extend-expect'
import { render, fireEvent } from '@testing-library/react'
import NewBlog from './NewBlog'

describe('NewBlog', () => {

  test('When created, the callback will be called with data entered to form', () => {
    const createBlog = jest.fn()

    const component = render(
      <NewBlog createBlog={createBlog} />
    )

    const blogData = {
      author: 'Martin Fowler',
      title: 'Continuous Integration',
      url: 'https://martinfowler.com/articles/continuousIntegration.html'
    }

    const author = component.container.querySelector('#author')
    const title = component.container.querySelector('#title')
    const url = component.container.querySelector('#url')
    const form = component.container.querySelector('form')

    fireEvent.change(author, {
      target: { value: blogData.author }
    })
    fireEvent.change(title, {
      target: { value: blogData.title }
    })
    fireEvent.change(url, {
      target: { value: blogData.url }
    })
    fireEvent.submit(form)

    expect(createBlog.mock.calls.length).toBe(1)

    expect(createBlog.mock.calls[0][0]).toEqual(blogData)
  })
})