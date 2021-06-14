import React from 'react'
import PropTypes from 'prop-types'  // Exercise 5.11

const Notification = ({ notification }) => {
  if (!notification) {
    return null
  }
  return (
    <div className={notification.type}>
      {notification.message}
    </div>
  )
}

// Exercise 5.11
Notification.propTypes = {
  notification: PropTypes.object.isRequired
}

export default Notification
