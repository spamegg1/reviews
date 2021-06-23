// Exercise 7.13: whole file: add view to display all Users
import React from 'react'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'                         // Exercise 7.14

const Users = () => {
  const users = useSelector(state => state.users)

  return (
    <table>
      <thead>
        <tr>
          <th />
          <th>
            blogs added
          </th>
        </tr>
      </thead>
      <tbody>
        {users.map(user =>
          <tr key={user.id}>
            <td>
              <Link to={`/users/${user.id}`}>              {/* Exercise 7.14 */}
                {user.name}
              </Link>
            </td>
            <td>
              {user.blogs.length}
            </td>
          </tr>
        )}
      </tbody>
    </table>
  )
}

export default Users
