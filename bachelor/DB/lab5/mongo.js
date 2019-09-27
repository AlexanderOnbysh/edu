-- denormalize tables, add contracts to clients

db.clients.find().snapshot().forEach(
  function (e) {
    var cont = db.contracts.find({'client_id': e._id}).toArray();
    e.contracts = cont;
    db.clients.save(e);
  }
)

db.contracts.drop();


-- count clients
db.clients.aggregate([
  {
    $group : {
      _id: null,
      count: {$sum: 1}
    }
  },
])


-- group by by company and collect surnames to dict
db.clients.aggregate([
  {
    $group : {
     _id: "$company",
     ids: {$push: "$surname"}
    }
  },
  {
    $sort : {
      _id: -1
    }
  }
])