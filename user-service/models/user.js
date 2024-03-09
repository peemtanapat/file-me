const myMongoose = require('../database/myMongoose')
const uniqueValidator = require('mongoose-unique-validator')

const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/

const userSchema = new myMongoose.Schema({
  username: {
    type: String,
    validate: emailRegex,
    minLength: 5,
    unique: true,
    required: true,
  },
  password: {
    type: String,
    required: true,
  },
})

userSchema.plugin(uniqueValidator)

userSchema.set('toJSON', {
  transform: (_, returnedObject) => {
    returnedObject.id = returnedObject._id.toString()
    delete returnedObject._id
    delete returnedObject.__v
    delete returnedObject.password
  },
})

module.exports = myMongoose.model('User', userSchema)
