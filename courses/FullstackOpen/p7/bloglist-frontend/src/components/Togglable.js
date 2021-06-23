import React, { useState, useImperativeHandle } from 'react'
import PropTypes from 'prop-types'                              // Exercise 5.11

// Exercise 5.5
const Togglable = React.forwardRef((props, ref) => {
  const [visible, setVisible] = useState(false)

  const hideWhenVisible = { display: visible ? 'none' : '' }
  const showWhenVisible = { display: visible ? '' : 'none' }

  const toggleVisibility = () => {
    setVisible(!visible)
  }

  useImperativeHandle(ref, () => {
    return {
      toggleVisibility
    }
  })

  return (
    <div>
      <div style={hideWhenVisible}>
        <button onClick={toggleVisibility}>{props.buttonLabel}</button>
      </div>
      <div style={showWhenVisible} className="togglableContent"> {/* Ex 5.14 */}
        {props.children}
        <button onClick={toggleVisibility}>cancel</button>
      </div>
    </div>
  )
})

// Exercise 5.11
Togglable.propTypes = {
  buttonLabel: PropTypes.string.isRequired
}

// Exercise 5.12
Togglable.displayName = 'Togglable'

export default Togglable
