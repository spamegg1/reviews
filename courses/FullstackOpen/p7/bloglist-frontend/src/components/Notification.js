import React from 'react'
import { useSelector } from 'react-redux'                        // Exercise 7.9

const Notification = () => {
  const notification = useSelector(state => state.notification)  // Exercise 7.9
  if (!notification) {
    return null
  }
  return (
    <div className={notification.type}>          {/* className needed in CSS */}
      {notification.message}
    </div>
  )
}

export default Notification
