const bcrypt = require('bcrypt')
const usersRouter = require('express').Router()
const User = require('../models/user')

usersRouter.get('/', async (request, response) => {
  // Exercise 4.17
  const users = await User
    .find({})
    .populate('blogs', { title: 1, url: 1,  likes: 1, author: 1 })
  response.json(users)
})

usersRouter.post('/', async (request, response) => {
  const { name, username, password } = request.body

  // Exercise 4.16
  if (!password || password.length < 3) {
    return response.status(400).send({
      error: 'password must min length 3'
    })
  }

  const saltRounds = 10
  const passwordHash = await bcrypt.hash(password, saltRounds)

  const user = new User({
    username: username,
    name: name,
    passwordHash
  })

  const savedUser = await user.save()
  response.json(savedUser)
})

module.exports = usersRouter
