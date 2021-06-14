// Exercise 6.10
const notificationReducer = (state = null, action) => {
  console.log('notification state now: ', state)
  console.log('notification action', action)

  switch (action.type) {
    case 'SET':
      return action.content
    case 'REMOVE':
      return null
    default:
      return state
  }
}

// Exercise 6.11: Action creators
export const setNotifyAction = content => {
  return {
    type: 'SET',
    content
  }
}

export const removeNotifyAction = () => {
  return {
    type: 'REMOVE'
  }
}

// Exercise 6.11: making sure timeouts work correctly if
// notification events are fired within 5 secs of each other
// Exercise 6.18: modified to use async
let timeoutId
export const setNotification = (msg, time) => {
  return async dispatch => {
    dispatch(setNotifyAction(msg))

    if (timeoutId) {
      clearTimeout(timeoutId)
    }

    timeoutId = setTimeout(() => {
      dispatch(removeNotifyAction())
    }, time * 1000)
  }
}


export default notificationReducer
