import { useMemo } from 'react'
import { useSelector } from 'react-redux'
import { LOGGED_FILE_ME_APP_USER } from '../constants'
import fileService from '../services/fileService'

const useUserState = () => {
  const userState = useSelector((state) => state.user)

  const finalUser = useMemo(() => {
    const loggedUserJSON = window.localStorage.getItem(LOGGED_FILE_ME_APP_USER)
    if (loggedUserJSON) {
      const loggedUser = JSON.parse(loggedUserJSON)
      fileService.setToken(loggedUser.token)
      return loggedUser
    }

    return userState
  })

  return finalUser
}

export default useUserState
