db.createCollection('items')

db.getName()

db.orders.remove()

// Insert data
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

db.customers.insertMany([
    {name: "Alexander", surname: "Onbysh", phones: [12345, 54321], address: "NY, some cool st. 42"},
    {name: "John", surname: "Brown", phones: [67890, 98786], address: "LA, some another cool st. 24"},
]);

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


// Task 1
db.items.mapReduce(
    function () {
        emit(this.producer, 1);
    },
    function (key, values) {
        return Array.sum(values);
    },
    { out: "output" }
);
db.output.find();

// Task 2
db.items.mapReduce(
    function () {
        emit(this.producer, this.price);
    },
    function (key, values) {
        return Array.sum(values);
    },
    { out: "output" }
);
db.output.find();

// Task 3
db.orders.mapReduce(
    function () {
        emit(this.customer.name + " " + this.customer.surname, this.total_sum);
    },
    function (key, values) {
        return Array.sum(values);
    },
    { out: "output" }
);
db.output.find();

// Task 4
db.orders.mapReduce(
    function () {
        emit(this.customer.name + " " + this.customer.surname, this.total_sum);
    },
    function (key, values) {
        return Array.sum(values);
    },
    {
        out: "output",
        query: {
            date: {
                $gt: new Date("2018-01-01"), $lt: new Date("2020-06-30")
            }
        }
    }
);
db.output.find();

// Task 5
db.orders.mapReduce(
    function () {
        emit("avg", this.total_sum);
    },
    function (key, values) {
        return Array.avg(values);
    },
    { out: "output" }
);
db.output.find();


// Task 6
db.orders.mapReduce(
    function () {
        emit(this.customer.name + " " + this.customer.surname, this.total_sum);
    },
    function (key, values) {
        return Array.avg(values);
    },
    { out: "output" }
);
db.output.find();


// Task 7
db.orders.mapReduce(
    function () {
        for (i = 0; i < this.order_items_id.length; i++) { emit(this.order_items_id[i], 1); }
    },
    function (key, values) {
        return Array.sum(values);
    },
    { out: "output" }
);
db.output.find();



// Task 8
db.orders.mapReduce(
    function () {
        for (i = 0; i < this.order_items_id.length; i++) { emit(this.order_items_id[i], this.customer.name + " " + this.customer.surname); }
    },
    function (key, values) {
        return { "customers": Array.from(new Set(values)) };
    },
    { out: "output" }
);
db.output.find();


// Task 9
db.orders.mapReduce(
    function () {
        for (i = 0; i < this.order_items_id.length; i++) { emit({ item: this.order_items_id[i], customer: this.customer.name + " " + this.customer.surname }, 1); }
    },
    function (key, values) {
        return { "customers": Array.sum(values) };
    },
    { out: "output" }
);
db.output.mapReduce(
    function () {
        emit(this._id, this.value.customers);
    },
    function (key, values) {
        return values;
    },
    { out: "output" }
);
db.output.find();

// Task 10
db.orders.mapReduce(
    function () {
        for (i = 0; i < this.order_items_id.length; i++) { emit(this.order_items_id[i], 1); }
    },
    function (key, values) {
        return Array.sum(values);
    },
    { out: "output" }
);
db.output.mapReduce(
    function () {
        emit(this._value, this._id);
    },
    function (key, values) {
    },
    { out: "output" }
);
db.output.find();

// Task 11


db.orders.insertMany([
    {
        order_number: 201513,
        date: new Date("2019-05-18T16:00:00Z"),
        total_sum: 1250,
        customer: { name: "Alexander", surname: "Onbysh", phones: [12345, 54321], address: "NY, some cool st. 42" },
        payment: { card_owner: "Alexander Onbysh", cardId: 23456 },
        order_items_id: [
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e62")
            },
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e63")
            }
        ]
    },
    {
        order_number: 12345,
        date: new Date("2020-05-18T16:00:00Z"),
        total_sum: 1850,
        customer: { name: "John", surname: "Brown", phones: [67890, 98786], address: "LA, some another cool st. 24" },
        payment: { card_owner: "John Brown", cardId: 7654321 },
        order_items_id: [
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e63")
            },
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e64")
            }
        ]
    },
    {
        order_number: 123456,
        date: new Date("2019-05-18T16:00:00Z"),
        total_sum: 1000,
        customer: { name: "John", surname: "Brown", phones: [67890, 98786], address: "LA, some another cool st. 24" },
        payment: { card_owner: "John Brown", cardId: 7654321 },
        order_items_id: [
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e64")
            }
        ]
    },
    {
        order_number: 2015139,
        date: new Date("2020-05-18T16:00:00Z"),
        total_sum: 300,
        customer: { name: "Alexander", surname: "Onbysh", phones: [12345, 54321], address: "NY, some cool st. 42" },
        payment: { card_owner: "Alexander Onbysh", cardId: 23456 },
        order_items_id: [
            {
                $ref: "items",
                $id: ObjectId("5e753b31e1df112315ed8e62")
            }

        ]
    },
]);


db.orders.mapReduce(
    function () {
        emit({ name: this.customer.name + " " + this.customer.surname, year: Math.floor(this.date.getFullYear()) }, this.total_sum);
    },
    function (key, values) {
        return Array.sum(values);
    },
    { out: "output" }
);
db.output.find();


db.output.mapReduce(
    function () {
        var record = { prev_year_amount: 0, year: 0, diff: 0 };
        if (this._id.year == 2019.0) {
            record.prev_year_amount = this.value;
            emit({ name: this._id.name }, record);
        }
        else if (this._id.year == 2020.0) {
            record.year = this.value;
            emit({ name: this._id.name }, record);
        }
    },
    function (key, values) {
        var record = { prev_year_amount: 0, year: 0, diff: 0 };
        record.prev_year_amount = values.reduce(function(sum, current){return sum + current.prev_year_amount}, 0);
        record.year = values.reduce(function(sum, current){return sum + current.year}, 0);
        record.diff = record.year - record.prev_year_amount;
        return record;
    },
    { out: "output2" }
);
db.output2.find();






db.orders.remove({});
