const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')

const User = require('../models/User')
const { getUser, saveNewUser } = require('../services/userService')

const loginRouter = require('express').Router()

loginRouter.post('/verify', async (req, res) => {
  const { username } = req.body

  const existUser = await getUser({ username })

  if (!existUser) {
    await saveNewUser({ username })
    return res.status(200).json({ username, isNewUser: true })
  } else {
    return res.status(200).json(existUser)
  }
})

loginRouter.post('/', async (req, res) => {
  const { username, password } = req.body

  const user = await User.findOne({ username })
  const isValidPassword =
    user == null ? false : await bcrypt.compare(password, user.password)

  if (!(user && isValidPassword)) {
    return res.status(401).json({
      error: 'invalid username or password',
    })
  }

  const userForToken = {
    username: user.username,
    id: user._id,
  }

  const token = jwt.sign(userForToken, process.env.JWT_SECRET)

  res.status(200).send({ token, username: user.username, name: user.name })
})

module.exports = loginRouter
