const app = require('./app')
const { PORT } = require('./constant')

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`)
})
