// Exercise 6.12
const filterReducer = (state = '', action) => {
  switch (action.type) {
    case 'UPDATE':
      return action.query
    default:
      return state
  }
}

// Action creators
export const updateAction = query => {
  return {
    type: 'UPDATE',
    query
  }
}

export default filterReducer
