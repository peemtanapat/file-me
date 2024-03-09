import { createSlice } from '@reduxjs/toolkit'
import fileService from '../services/fileService'
import userService from '../services/userService'
import { LOGGED_FILE_ME_APP_USER } from '../constants'

const userSlice = createSlice({
  name: 'user',
  initialState: null,
  reducers: {
    setLoggedUser(state, action) {
      return action.payload
    },
  },
})

export const { setLoggedUser } = userSlice.actions

export const loginUser = ({ username, password }) => {
  return async (dispatch) => {
    try {
      const loggedUser = await userService.loginUser({ username, password })
      window.localStorage.setItem(
        LOGGED_FILE_ME_APP_USER,
        JSON.stringify(loggedUser),
      )
      fileService.setToken(loggedUser.token)
      dispatch(setLoggedUser(loggedUser))
    } catch (error) {
      console.log(`Error Login: ${error}`)
    }
  }
}

export default userSlice.reducer
