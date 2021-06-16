const reducer = (state = null, action) => {
  switch (action.type) {
  case 'LOGIN':
    return action.payload
  case 'LOGOUT':
    return null
  default:
    return state
  }
}

export const login = (user) => (
  {
    type: 'LOGIN',
    payload: user
  }
)

export const logout = () => (
  { type: 'LOGOUT' }
)

export default reducer