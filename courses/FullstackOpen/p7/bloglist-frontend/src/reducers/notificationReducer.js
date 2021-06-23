// Exercise 7.9: whole file: Change the application's notifications to use Redux
const notificationReducer = (state = null, action) => {
  switch (action.type) {
  case 'SET':
    return {
      message: action.message,
      type: action.style                     // this can be 'success' or 'error'
    }
  case 'CLEAR':
    return null
  default:
    return state            // React complained about this! Cannot be undefined!
  }
}


let timeoutId   // once again dealing with the familiar notification timeout bug

// Exercise 7.9: action creators for notification
export const setNotifyAction = (message, style) => {
  return async dispatch => {           // async action creators need redux-thunk
    dispatch({
      type: 'SET',
      message: message,
      style: style                           // this can be 'success' or 'error'
    })

    if (timeoutId) {
      clearTimeout(timeoutId)
    }

    timeoutId = setTimeout(() => {
      dispatch({
        type: 'CLEAR'
      })
    }, 3000)
  }
}

export const clearNotifyAction = () => ({ type: 'CLEAR' })

export default notificationReducer
