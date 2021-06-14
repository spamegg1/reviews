const reducer = (state = '', action) => {
  switch (action.type) {
    case 'SET_FILTER':
      return action.content
    default:
      return state
  }
}

export const setFilter = (content) => (
  {
    type: 'SET_FILTER',
    content
  }
)

export default reducer