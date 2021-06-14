import React from 'react'
import Total from './Total'
import Header from './Header'
import Content from './Content'

const Course = ({course}) => {
  return (
    <div>
      <Header name={course.name} />
      <Content parts={course.parts} />
      <Total parts={course.parts} />
    </div>
  )
}

export default Course
