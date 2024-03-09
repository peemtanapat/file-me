import axios from 'axios'
import { LOGIN_USER_URL, VERIFY_USER_URL } from '../constants'

export const verifyUser = async ({ username }) => {
  const res = await axios.post(VERIFY_USER_URL, { username })

  return res.data
}

export const loginUser = async ({ username, password }) => {
  const res = await axios.post(LOGIN_USER_URL, { username, password })

  return res.data
}

export default {
  verifyUser,
  loginUser,
}
