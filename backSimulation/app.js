const express = require('express')

const app = express()
const PORT = process.env.PORT || 8084
const server = app.listen(PORT, () => console.log(`Hello j'ai démarré sur le port ${PORT}`))