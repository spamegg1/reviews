import React from 'react'
import { connect } from 'react-redux'  // Exercise 6.20
import { updateAction } from '../reducers/filterReducer'

const Filter = (props) => {
  const handleChange = (event) => {
    // input-field value is in variable event.target.value
    const query = event.target.value
    props.updateAction(query)  // Exercise 6.20
  }

  const style = {
    marginBottom: 10
  }

  return (
    <div style={style}>
      filter <input onChange={handleChange} />
    </div>
  )
}

// Exercise 6.20
export default connect(
  null,
  { updateAction }
)(Filter)
