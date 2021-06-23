// Exercise 7.12: refactoring local storage for user
const storageKey = 'loggedBlogappUser'

const saveUser = (user) =>
  localStorage.setItem(storageKey, JSON.stringify(user))

const loadUser = () =>
  JSON.parse(localStorage.getItem(storageKey))

const logoutUser = () =>
  localStorage.removeItem(storageKey)

export default {
  saveUser,
  loadUser,
  logoutUser
}
