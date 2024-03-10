import { Fragment, useState } from 'react'
import { useDispatch } from 'react-redux'
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
import { verifyUser } from '../reducers/userReducer'
import LoginForm from './LoginForm'

const fieldTheme = createTheme({
  typography: {
    allVariants: {
      color: 'white',
    },
  },
})

const FirstLoginForm = () => {
  const dispatch = useDispatch()
  const [username, setUsername] = useState('')
  const [isFirstLogin, setIsFirstLogin] = useState(true)

  const handleVerifyUser = async (event) => {
    event.preventDefault()

    dispatch(verifyUser({ username }))

    setIsFirstLogin(false)
  }

  return (
    <Fragment>
      {!isFirstLogin && <LoginForm />}
      {isFirstLogin && (
        <Box
          component="form"
          onSubmit={handleVerifyUser}
          sx={{ width: '100%' }}
        >
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
                <Button
                  size="small"
                  variant="contained"
                  type="submit"
                  startIcon={<LoginIcon />}
                >
                  Send Password via email
                </Button>
              </Grid>
            </Grid>
          </ThemeProvider>
        </Box>
      )}
    </Fragment>
  )
}

export default FirstLoginForm
