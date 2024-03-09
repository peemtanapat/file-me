const { default: axios } = require('axios')
const {
  SEND_MAIL_URL,
  MAIL_NEW_ACCOUNT_SUBJECT,
  MAIL_NEW_ACCOUNT_BODY,
} = require('../constant')

const sendMailUserAccount = async ({ username, password }) => {
  const subject = MAIL_NEW_ACCOUNT_SUBJECT
  const body = MAIL_NEW_ACCOUNT_BODY.replace('{{username}}', username).replace(
    '{{password}}',
    password,
  )

  await sendMail({ receiver: username, subject, body })
}

const sendMail = async ({ receiver, subject, body }) => {
  const mailRequestBody = {
    receiver,
    subject,
    body,
  }

  try {
    await axios.post(SEND_MAIL_URL, mailRequestBody)
  } catch (error) {
    console.log('Error send mail: ' + error)
  }
}

module.exports = { sendMail, sendMailUserAccount }
