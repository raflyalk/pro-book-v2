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
  sequelize.query("SELECT COUNT(1) as isExist FROM Accounts WHERE card_number = " + req.query.card_number + ";").then(
    function(result) {
      res.send(result[0][0]);
    }
  );  
});

/**
 * {
 *  sender_card_number,
 *  receiver_card_number,
 *  transfer_amount
 * }
 */
app.post('/transfer', (req, res) => {
  Promise.all([
    models.Account.findOne({
      attributes: ['card_number', 'balance'],
      where: {
        card_number: req.body.sender_card_number
      }
    })
    .then((result) => {
      return new Promise((resolve, reject) => {
        if (!result) {
          reject("Sender Account Not Found");
        } else if (result.dataValues.balance < req.body.transfer_amount) {
          reject("Insufficient Sender Balance");
        } else {
          resolve(result);
        }
      });
    })
    .catch((error) => {
      res.send(error);
    }),

    models.Account.findOne({
      attributes: ['card_number'],
      where: {
        card_number: req.body.receiver_card_number
      }
    })
    .then((result) => {
      return new Promise((resolve, reject) => {
        if (!result) {
          reject("Receiver Account Not Found");
        } else {
          resolve(result);
        }
      });
    })
    .catch((error) => {
      res.send(error);
    }),
  ])
  .then((result) => {
    // Start decreasing sender balance and increasing receiver balance
    res.send('Transaction successful');
  })
  .catch((error) => {
    console.log(error);
    res.send(error);
  })
});

app.listen(port);

console.log('Listening on port', port);
