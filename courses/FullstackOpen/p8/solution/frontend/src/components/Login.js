import React, { useState, useEffect } from 'react'
import { useMutation } from '@apollo/client'
import { LOGIN } from '../queries'

const Login = ({ show, setToken, setPage }) => {
  const [username, setUsername] = useState('mluukkai')
  const [password, setPassword] = useState('secret')

  const [login, loginRequest] = useMutation(LOGIN)

  useEffect(() => {
    if ( loginRequest.data ) {
      const token = loginRequest.data.login.value
      setToken(token)
      setPage('books')
      localStorage.setItem('libraryToken', token)
    }
  }, [loginRequest.data, setToken, setPage])

  if (!show) {
    return null
  }

  const submit = async (event) => {
    event.preventDefault()
    login({
      variables: { username, password }
    })

    setUsername('')
  }

  return (
    <div>
      <form onSubmit={submit}>
        <div>
          username
          <input
            value={username}
            onChange={({ target }) => setUsername(target.value)}
          />
        </div>
        <div>
          password
          <input
            value={password}
            onChange={({ target }) => setPassword(target.value)}
          />
        </div>
        <button type='submit'>login</button>
      </form>
    </div>
  )
}

export default Login