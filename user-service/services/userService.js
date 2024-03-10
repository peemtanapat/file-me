const User = require('../models/User')
const {
  hashPassword,
  generateSecurePassword,
} = require('../utils/passwordUtil')
const emailService = require('./emailService')

const getUser = async ({ username }) => {
  const user = await User.findOne({ username })

  return user
}

const saveNewUser = async ({ username }) => {
  // generate password
  const rawPassword = generateSecurePassword(12)
  const hashedPassword = await hashPassword(rawPassword)
  console.log('%c⧭', 'color: #00a3cc', { username, rawPassword })
  // save user&password
  const newUser = new User({
    username,
    password: hashedPassword,
  })
  const savedUser = await newUser.save()

  // send mail
  await emailService.sendMailUserAccount({ username, password: rawPassword })

  return savedUser
}

module.exports = { getUser, saveNewUser }
