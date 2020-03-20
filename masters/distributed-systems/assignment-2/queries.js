use assignment
db.createCollection('items')

// 1. add data
db.items.insertMany([
   { category: "Phone", model: "iPhone 6", display: 6, producer: "Apple", price: 600 },
   { category: "Phone", model: "iPhone 6s", display: 6, producer: "Apple", price: 650 },
   { category: "TV", model: "Super res TV", producer: "Samsung", price: 1200 },
   { category: "Smart Watch", model: "Smart watch v4", producer: "LG", price: 300 },
   { category: "Tablets", model: "iPad 10", producer: "Apple", price: 800 },
   { category: "Laptops", model: "MacBook pro", producer: "Apple", price: 4000 },
   { category: "Laptops", model: "Chromebook", producer: "Lenovo", price: 450 },
   { category: "Laptops", model: "Cheapbook", producer: "Samsung", price: 200 },
]);

// 2. show all records
db.items.find({});

// 3. count laptops
db.items.count({category: "Laptops"});

// 4. count categories
db.items.distinct("category");

// 5.
// a. laptops in range 300 - 600
db.items.find({
    category: "Laptops",
    price: {$gt: 300, $lt: 600}
});

// b. 6 inch display
db.items.find({
    category: "Phone",
    display: 6
})

// c. items in Phones and Tablets
db.items.find({
    category: {$in: ["Phone", "Tablets"]}
})

// 6. all producer without repetition
db.items.distinct("producer")

// 7. sale for cheap laptops
db.items.updateMany(
    {category: "Laptops", price: {$lt: 500}},
    {
        $set: { sale: true },
        $inc: {"price": -100}
    }
)

// 8. all items on sale
db.items.find({
    sale: {$exists: true}
})

// 9. increase price for sale items
db.items.updateMany(
    { sale: {$exists: true} },
    {
        $inc: {"price": 10}
    }
)

//  ---------------------- Part 2 ----------------------
db.createCollection("orders")
db.createCollection("customers")

db.customers.insertMany([
    {name: "Alexander", surname: "Onbysh", phones: [12345, 54321], address: "NY, some cool st. 42"},
    {name: "John", surname: "Brown", phones: [67890, 98786], address: "LA, some another cool st. 24"},
]);

// 1. create orders
db.orders.insertMany([
    {
        order_number : 201513,
        date : new Date("2016-05-18T16:00:00Z"),
        total_sum : 1250,
        customer : {name: "Alexander", surname: "Onbysh", phones: [12345, 54321], address: "NY, some cool st. 42"},
        payment : { card_owner : "Alexander Onbysh", cardId : 23456 },
        order_items_id : [
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e62")
            },
            {
                $ref : "items",
                $id : ObjectId("5e753b31e1df112315ed8e63")
            }
        ]
    },
    {
        order_number : 12345,
        date : new Date("2018-05-18T16:00:00Z"),
        total_sum : 1850,
        customer : {name: "John", surname: "Brown", phones: [67890, 98786], address: "LA, some another cool st. 24"},
        payment : { card_owner : "John Brown", cardId : 7654321 },
        order_items_id : [
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e63")
            },
            {
                $ref : "items",
                $id : ObjectId("5e753b31e1df112315ed8e64")
            }
        ]
    }
]);

// 2. all orders
db.orders.find({})

// 3. orders with total_sum greater than 1500
db.orders.find({ total_sum: {$gt: 1500} })

// 4. orders by Alexander Onbysh
db.orders.find({"customer.name": "Alexander", "customer.surname": "Onbysh"})

// 5. all orders with iPhone 6s
db.orders.find({"order_items_id.$id": ObjectId("5e753b31e1df112315ed8e63")})

// 6. add chromebook to orders with Super res TV
db.orders.update(
    {"order_items_id.$id": ObjectId("5e753b31e1df112315ed8e63")},
    {$push: {order_items_id: {$ref: "items", $id: ObjectId("5e752d768e5d040ba4df9335")}}, $inc: {"total_sum": 110}}
)

// 7. Count number of items in second order
db.orders.aggregate(
    [
        {$match: { _id: ObjectId("5e753bd8e15472a01fc4e21c")}},
        {$project: { count: { $size: "$order_items_id" }}}
    ]
)

// 8. orders with card owner John Brown and total sum greater than 1500
db.orders.find(
    { total_sum: { $gt: 1500 } },
    { "payment.card_owner": "John Brown", "payment.cardId": 7654321 }
)

// 9. remove item 5e753b31e1df112315ed8e63 from orders after 2018-01
db.orders.update(
    { date : { $gt: new Date("2018-01-18T16:00:00Z") } },
    { $pull: { "order_items_id.$id": ObjectId("5e753b31e1df112315ed8e63") }},
    {}
)

// 10. update all names
db.orders.updateMany({}, {$set: {"customer.name": "Name"}})

