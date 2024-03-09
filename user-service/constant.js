require('dotenv').config({ path: __dirname + '/.env' })

const PORT = 3000
const MONGODB_URI = process.env.MONGODB_URI

const SEND_MAIL_URL = 'http://localhost:8081/mail'
const MAIL_NEW_ACCOUNT_SUBJECT = 'Welcome to file-me, you are our new member'
const MAIL_NEW_ACCOUNT_BODY =
  'Here is your account: username: {{username}}  password: {{password}}'

module.exports = {
  PORT,
  MONGODB_URI,
  SEND_MAIL_URL,
  MAIL_NEW_ACCOUNT_SUBJECT,
  MAIL_NEW_ACCOUNT_BODY,
}
