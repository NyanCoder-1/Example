const express = require('express')
const app = express()
const port = 3000

app.get('/images', (req, res) => res.sendFile(__dirname + "/images/" + req.query.img + ".jpg"))

app.listen(port, () => console.log(`Example app listening on port ${port}!`))