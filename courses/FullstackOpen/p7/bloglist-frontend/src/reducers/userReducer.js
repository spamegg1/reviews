// Exercise 7.12: store info about logged in user in Redux store

const userReducer = (state = null, action) => {
  switch (action.type) {

  case 'LOGIN':
    return action.data

  case 'LOGOUT':
    return null

  default:
    return state
  }
}

// Exercise 7.12: action creators for user
export const loginAction = (user) => {
  return {
    type: 'LOGIN',
    data: user
  }
}

export const logoutAction = () => {
  return {
    type: 'LOGOUT'
  }
}

export default userReducer
