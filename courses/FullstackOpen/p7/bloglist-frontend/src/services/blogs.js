import axios from 'axios'
import storage from '../utils/storage'                          // Exercise 7.12
const baseUrl = '/api/blogs'

const getConfig = () => {                       // Exercise 7.12: refactor token
  return {
    headers: { Authorization: `bearer ${storage.loadUser().token}` }
  }
}

const getAll = async () => {
  const response = await axios.get(baseUrl)
  return response.data
}

const create = async newObject => {
  const response = await axios.post(baseUrl, newObject, getConfig())     // 7.12
  return response.data
}

const update = async (newObject) => {                           // Exercise 7.11
  const response = await axios.put(`${baseUrl}/${newObject.id}`, newObject)
  return response.data
}

const remove = async (id) => {                                  // Exercise 5.10
  const response = await axios.delete(`${baseUrl}/${id}`, getConfig())   // 7.12
  return response.data
}

const comment = async (id, comment) => {                        // Exercise 7.17
  const response = await axios.post(
    `${baseUrl}/${id}/comments`, { comment }, getConfig())
  return response.data
}

export default { getAll, create, update, remove, comment }
