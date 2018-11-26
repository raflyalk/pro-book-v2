'use strict';
module.exports = (sequelize, DataTypes) => {
  var Transaction = sequelize.define('Transaction', {
    sender_card: DataTypes.INTEGER,
    receiver_card: DataTypes.INTEGER,
    amount: DataTypes.FLOAT,
    transaction_time: DataTypes.DATE
  }, {});
  Transaction.associate = function(models) {
    // associations can be defined here
    Transaction.belongsTo(models.Account, {
      foreignKey: 'sender_card',
      targetKey: 'card_number'
    });

    Transaction.belongsTo(models.Account, {
      foreignKey: 'receiver_card',
      targetKey: 'card_number'
    });
  };
  return Transaction;
};