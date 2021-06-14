import React from 'react'
import { connect } from 'react-redux'

const Notification = ({ notification }) => {

  if (!notification) {
    return null
  }

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

export default connect(
  (state) => ({ notification: state.notification })
)(Notification)