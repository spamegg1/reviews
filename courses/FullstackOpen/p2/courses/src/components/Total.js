import React from 'react'

const Total = ({ parts }) => {
  const sum = parts.reduce((a, b) => a + b.exercises, 0)

  return(
    <p><strong>total of {sum} exercises</strong></p>
  )
}

export default Total
