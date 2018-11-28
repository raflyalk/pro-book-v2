require('dotenv').config();

const express = require('express');
const bodyParser = require('body-parser');

const transfer = require('./modules/transfer');
const validate = require('./modules/validate');

const app = express();
const port = 3000;

app.use(bodyParser.json());
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});
// APIs Goes Here
app.get('/', (req, res) => {
  res.send('This is Pro-Book Bank-WebService');
});

app.get('/validate-card', validate);
app.post('/transfer', transfer);

app.listen(port);

console.log('Listening on port', port);
