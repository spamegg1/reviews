import React from 'react'
import PropTypes from 'prop-types'  // Exercise 5.11

// Exercise 5.5
const LoginForm = ({
  handleSubmit,
  handleUsernameChange,
  handlePasswordChange,
  username,
  password
}) => (
  <div>
    <h2>Log in to application</h2>
    <form onSubmit={handleSubmit}>
      <div>
        username
        <input
          id='username' // Exercises 5.17-5.22
          type="text"
          value={username}
          name="Username"
          onChange={handleUsernameChange}
        />
      </div>
      <div>
        password
        <input
          id='password' // Exercises 5.17-5.22
          type="text"
          value={password}
          name="Password"
          onChange={handlePasswordChange}
        />
      </div>
      <button
        id='login-button' // Exercises 5.17-5.22
        type="submit">
          login
      </button>
    </form>
  </div>
)

// Exercise 5.11
LoginForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired,
  handleUsernameChange: PropTypes.func.isRequired,
  handlePasswordChange: PropTypes.func.isRequired,
  username: PropTypes.string.isRequired,
  password: PropTypes.string.isRequired
}

export default LoginForm
