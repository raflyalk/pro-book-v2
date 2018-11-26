const models = require('./../models');

module.exports = (req, res) => {
  models.Account.count({
    where: {
      card_number: req.query.card_number
    }
  })
  .then((result) => {
    result = {isExist: result};
    res.send(result);
  });
}