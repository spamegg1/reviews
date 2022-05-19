import React from 'react'

const Filter = (props) => {

  return (
    <input
    value={props.value}
    onChange={props.onChange}
  />
  )
}

export default Filter