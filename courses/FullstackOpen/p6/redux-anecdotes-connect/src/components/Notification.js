import React from 'react'
import { connect } from 'react-redux'  // Exercise 6.19

const Notification = (props) => {         // Exercise 6.19
  const notification = props.notification // Exercise 6.19
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

// Exercise 6.19
const mapStateToProps = (state) => {
  return {
    notification: state.notification
  }
}

// Exercise 6.19
const ConnectedNotification = connect(
  mapStateToProps
)(Notification)

export default ConnectedNotification