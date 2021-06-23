// Exercise 7.13: whole file: implement a view to display all Users
import axios from 'axios'

const baseUrl = '/api/users'

const getAll = async () => {
  const response = await axios.get(baseUrl)
  return response.data
}

export default { getAll }
