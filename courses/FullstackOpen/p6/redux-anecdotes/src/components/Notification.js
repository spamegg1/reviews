import React from 'react'
import { useSelector } from 'react-redux'  // Exercise 6.10

const Notification = () => {
  const notification = useSelector(state => state.notification) // Exercise 6.10
  if (!notification) { return null }  // Exercise 6.11

  const style = {
    border: 'solid',
    padding: 10,
    borderWidth: 1
  }
  return (
    <div style={style}>
      {notification}
    </div>
  )
}

export default Notification