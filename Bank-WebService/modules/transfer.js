require('dotenv').config();

const models = require('./../models');
const Sequelize = require('sequelize');

const sequelize = new Sequelize(process.env.DB_NAME, process.env.DB_USER, process.env.DB_PASS, {
  host: process.env.DB_HOST,
  dialect: 'mysql'
});

module.exports = (req, res) => {
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
      // Commit the transaction
      console.log('Transaction has been committed');
      res.send('Transaction successful');
    })
    .catch((err) => {
      // Roll the transaction back
      console.log('Transaction has been rolled back');
      res.send('Transaction unsuccessful');
    })
  })
  .catch((error) => {
    console.log(error);
    res.send(error);
  })
}