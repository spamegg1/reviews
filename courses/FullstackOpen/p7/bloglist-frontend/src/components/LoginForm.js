import React, { useState } from 'react'                         // Exercise 7.12
import { useDispatch } from 'react-redux'                       // Exercise 7.12
import loginService from '../services/login'                    // Exercise 7.12
import { setNotifyAction } from '../reducers/notificationReducer'//Exercise 7.12
import { loginAction } from '../reducers/userReducer'           // Exercise 7.12
import storage from '../utils/storage'

const LoginForm = () => {                                        // Exercise 5.5
  const [username, setUsername] = useState('')                  // Exercise 7.12
  const [password, setPassword] = useState('')                  // Exercise 7.12
  const dispatch = useDispatch()                                // Exercise 7.12

  const handleSubmit = async (event) => {  // Exercise 7.12: moved here from App
    event.preventDefault()
    try {
      const credentials = { username, password }                // Exercise 7.12
      const user = await loginService.login(credentials)        // Exercise 7.12

      storage.saveUser(user)                                    // Exercise 7.12
      dispatch(loginAction(user))                               // Exercise 7.12
      dispatch(setNotifyAction(`${user.name} welcome back!`))   // Exercise 7.12

      setUsername('')
      setPassword('')
    }
    catch (exception) {
      console.log('error logging in')
      dispatch(setNotifyAction('wrong username or password', 'error')) // Ex 7.9
    }
  }

  return (
    <div>
      <h2>Log in to application</h2>
      <form onSubmit={handleSubmit}>
        <div>
          username
          <input
            id='username' type="text"                     // Exercises 5.17-5.22
            value={username} name="Username"
            onChange={({ target }) => setUsername(target.value)}// Exercise 7.12
          />
        </div>
        <div>
          password
          <input
            id='password' type="text"                     // Exercises 5.17-5.22
            value={password} name="Password"
            onChange={({ target }) => setPassword(target.value)}// Exercise 7.12
          />
        </div>
        <button id='login-button' type="submit">login</button> {/* 5.17-5.22 */}
      </form>
    </div>
  )
}

export default LoginForm
