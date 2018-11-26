require('dotenv').config();

const express = require('express');
const bodyParser = require('body-parser');
const Sequelize = require('sequelize');
const models = require('./models');

const app = express();
const port = 3000;

const sequelize = new Sequelize(process.env.DB_NAME, process.env.DB_USER, process.env.DB_PASS, {
  host: process.env.DB_HOST,
  dialect: 'mysql'
});

app.use(bodyParser.json());

// APIs Goes Here
app.get('/', (req, res) => {
  res.send('This is Pro-Book Bank-WebService');
});

app.get('/validate-card', (req, res) => {
  models.Account.count({
    where: {
      card_number: req.query.card_number
    }
  })
  .then((result) => {
    result = {'isExist': result};
    res.send(result);
  });
});

app.get('/transfer', (req, res) => {
  models.Account.findAll({
    where: {
      card_number: 10001001
    }
  })
  .then((account) => {
    console.log(account);
  });
});

app.listen(port);

console.log('Listening on port', port);
