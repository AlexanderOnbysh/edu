# Assignment 3 - neo3j

## Code
```
// create users
CREATE (alex:Person {name: 'Alex'})
CREATE (nick:Person {name: 'Nick'})

// create items
CREATE (book:Item {name: 'Book', price: 100})
CREATE (laptop:Item {name: 'Laptop', price: 1000})
CREATE (pen:Item {name: 'Pen', price: 1})
CREATE (paper:Item {name: 'Paper', price: 10})
CREATE (phone:Item {name: 'Phone', price: 800})

// create orders
CREATE (order_1:Order {name: 'Order 1'})
CREATE (order_2:Order {name: 'Order 2'})
CREATE (order_3:Order {name: 'Order 3'})

// assign orders
CREATE (alex)-[:HAVE]->(order_1)
CREATE (alex)-[:HAVE]->(order_2)
CREATE (nick)-[:HAVE]->(order_3)

// add items to orders
CREATE (order_1)-[:CONTAIN]->(book)
CREATE (order_1)-[:CONTAIN]->(pen)
CREATE (order_1)-[:CONTAIN]->(paper)

CREATE (order_2)-[:CONTAIN]->(laptop)
CREATE (order_2)-[:CONTAIN]->(pen)

CREATE (order_3)-[:CONTAIN]->(pen)
CREATE (order_3)-[:CONTAIN]->(paper)

// create views
CREATE (alex)-[:VIEW]->(book)
CREATE (alex)-[:VIEW]->(pen)
CREATE (alex)-[:VIEW]->(phone)

CREATE (nick)-[:VIEW]->(laptop)
CREATE (nick)-[:VIEW]->(pen)

// --------------------------------------------
// 1. All items in order_1
MATCH (o:Order)-[:CONTAIN]-(items)
  WHERE o.name = 'Order 1'
RETURN items

items
{"id":"5","labels":["Item"],"properties":{"name":"Paper","price":10}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"2","labels":["Item"],"properties":{"name":"Book","price":100}}

// --------------------------------------------
// 2. Price of order_1
MATCH (o:Order)-[:CONTAIN]-(items)
  WHERE o.name = 'Order 1'
RETURN sum(items.price)

sum(items.price)
111


// --------------------------------------------
// 3. All orders of Alex
MATCH (p:Person)-[:HAVE]-(orders)
  WHERE p.name = 'Alex'
RETURN orders

orders
{"id":"8","labels":["Order"],"properties":{"name":"Order 2"}}
{"id":"7","labels":["Order"],"properties":{"name":"Order 1"}}

// --------------------------------------------
// 4. All items bought by Alex
MATCH (p:Person)-[:HAVE]-(orders)-[:CONTAIN]-(items)
  WHERE p.name = 'Alex'
RETURN items

items
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"3","labels":["Item"],"properties":{"name":"Laptop","price":1000}}
{"id":"5","labels":["Item"],"properties":{"name":"Paper","price":10}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"2","labels":["Item"],"properties":{"name":"Book","price":100}}

// --------------------------------------------
// 5. Count items bought by Alex
MATCH (p:Person)-[:HAVE]-(orders)-[:CONTAIN]-(items)
  WHERE p.name = 'Alex'
RETURN count(items)

count(items)
5

// --------------------------------------------
// 6. Price for all items bought by Alex
MATCH (p:Person)-[:HAVE]-(orders)-[:CONTAIN]-(items)
  WHERE p.name = 'Alex'
RETURN sum(items.price)

sum(items.price)
1112

// --------------------------------------------
// 7. Count items bought by Alex
MATCH (p:Person)-[:HAVE]-(orders)-[:CONTAIN]-(items)
  WHERE p.name = 'Alex'
RETURN items, count(items)

items	count(items)
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}	2
{"id":"3","labels":["Item"],"properties":{"name":"Laptop","price":1000}}	1
{"id":"5","labels":["Item"],"properties":{"name":"Paper","price":10}}	1
{"id":"2","labels":["Item"],"properties":{"name":"Book","price":100}}	1

// --------------------------------------------
// 8. All items viewed by Alex
MATCH (p:Person)-[:VIEW]-(items)
  WHERE p.name = 'Alex'
RETURN items

items
{"id":"6","labels":["Item"],"properties":{"name":"Phone","price":800}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"2","labels":["Item"],"properties":{"name":"Book","price":100}}

// --------------------------------------------
// 9. All items that were bought with Pen
MATCH (o:Order)-[:CONTAIN]-(items)
  WHERE items.name = 'Pen'
MATCH (o)-[:CONTAIN]-(item)
RETURN item

tem
{"id":"5","labels":["Item"],"properties":{"name":"Paper","price":10}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"2","labels":["Item"],"properties":{"name":"Book","price":100}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"3","labels":["Item"],"properties":{"name":"Laptop","price":1000}}
{"id":"5","labels":["Item"],"properties":{"name":"Paper","price":10}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}

// --------------------------------------------
// 10. All customers who bought Pen
MATCH (c:Person)-[:HAVE]-(:Order)-[:CONTAIN]-(items)
  WHERE items.name = 'Pen'
RETURN DISTINCT c

c
{"id":"0","labels":["Person"],"properties":{"name":"Alex"}}
{"id":"1","labels":["Person"],"properties":{"name":"Nick"}}

// --------------------------------------------
// 11. All bought items by customers who bought pen
MATCH (c:Person)-[:HAVE]-(o)-[:CONTAIN]-(items)
  WHERE items.name = 'Pen'
MATCH (o)-[:CONTAIN]-(item)
RETURN items

items
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}

// --------------------------------------------
// 12. All bought items by customers who viewed pen
MATCH (c:Person)-[:VIEW]-(item)
  WHERE item.name = 'Pen'
MATCH (c)-[:HAVE]-()-[:CONTAIN]-(items)
RETURN DISTINCT items

items
{"id":"4","labels":["Item"],"properties":{"name":"Pen","price":1}}
{"id":"3","labels":["Item"],"properties":{"name":"Laptop","price":1000}}
{"id":"5","labels":["Item"],"properties":{"name":"Paper","price":10}}
{"id":"2","labels":["Item"],"properties":{"name":"Book","price":100}}

// --------------------------------------------
```
