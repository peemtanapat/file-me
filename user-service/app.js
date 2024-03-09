const express = require('express')
const cors = require('cors')
const loginRouter = require('./controllers/login')

const app = express()

app.use(express.json())
app.use(cors())

app.get('/', (req, res) => {
  res.status(200).send({ message: 'Hello!' })
})

app.use('/api/login', loginRouter)

module.exports = app
