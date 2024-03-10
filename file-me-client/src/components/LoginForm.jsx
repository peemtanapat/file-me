import {
  Box,
  Button,
  Grid,
  TextField,
  ThemeProvider,
  Typography,
  createTheme,
} from '@mui/material'
import LoginIcon from '@mui/icons-material/Login'
import React, { Fragment, useState } from 'react'
import { useDispatch } from 'react-redux'
import { loginUser } from '../reducers/userReducer'
import FirstLoginForm from './FirstLoginForm'

const fieldTheme = createTheme({
  typography: {
    allVariants: {
      color: 'white',
    },
  },
})

const LoginForm = () => {
  const dispatch = useDispatch()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [isFirstLogin, setIsFirstLogin] = useState(false)

  const handleLogin = async (event) => {
    event.preventDefault()

    dispatch(loginUser({ username, password }))

    setIsFirstLogin(false)
  }

  const clickVerifyUser = async (event) => {
    setIsFirstLogin(true)
  }

  return (
    <Fragment>
      {isFirstLogin && <FirstLoginForm />}
      {!isFirstLogin && (
        <Fragment>
          <Button onClick={clickVerifyUser}>First Login?</Button>
          <Box component="form" onSubmit={handleLogin} sx={{ width: '100%' }}>
            <ThemeProvider theme={fieldTheme}>
              <Grid
                container
                rowSpacing={2}
                columnSpacing={{ xs: 1, sm: 2, md: 3 }}
              >
                <Grid xs={12} item={true}>
                  <Typography fontSize={20}>Log in to application</Typography>
                </Grid>
                <Grid xs={12} item={true}>
                  <TextField
                    variant="filled"
                    label="Username"
                    type="text"
                    id="username"
                    name="username"
                    onChange={({ target }) => setUsername(target.value)}
                  />
                </Grid>
                <Grid xs={12} item={true}>
                  <TextField
                    type="password"
                    label="Password"
                    id="password"
                    name="password"
                    onChange={({ target }) => setPassword(target.value)}
                  />
                </Grid>
                <Grid xs={12} item={true}>
                  <Button
                    size="small"
                    variant="contained"
                    type="submit"
                    startIcon={<LoginIcon />}
                  >
                    Login
                  </Button>
                </Grid>
              </Grid>
            </ThemeProvider>
          </Box>
        </Fragment>
      )}
    </Fragment>
  )
}

export default LoginForm
