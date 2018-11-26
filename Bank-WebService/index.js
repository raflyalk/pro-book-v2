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
    result = {isExist: result};
    res.send(result);
  });
});

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
      attributes: ['card_number', 'balance'],
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
    // Create transaction to update both account balance and store order details
    return sequelize.transaction((t) => {
      return result[0].update({
        balance: result[0].balance - req.body.transfer_amount
      }, { transaction: t })
      .then(() => {
        return result[1].update({
          balance: result[1].balance + req.body.transfer_amount
        }, { transaction: t })
      })
      .then(() => {
        return models.Transaction.create({
          sender_card: result[0].dataValues.card_number,
          receiver_card: result[1].dataValues.card_number,
          amount: req.body.transfer_amount,
          transaction_time: new Date()
        }, { transaction: t })
      });
    })
    .then(() => {
      console.log('Transaction has been committed');
      res.send('Transaction successful');
    })
    .catch((err) => {
      console.log('Transaction has been rolled back');
      res.send('Transaction unsuccessful');
    })
  })
  .catch((error) => {
    console.log(error);
    res.send(error);
  })
});

app.listen(port);

console.log('Listening on port', port);
