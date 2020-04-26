# Assignment 4

```sql
CREATE KEYSPACE shop with replication = {'class' : 'SimpleStrategy', 'replication_factor':1};
use shop;

CREATE TABLE items
(
    category text,
    name text,
    price bigint,
    producer text,
    properties map<text, text>,
    PRIMARY KEY ((category), price, name)
);

CREATE INDEX producer on items (producer);
CREATE INDEX name on items (name);

INSERT INTO items (category, name, price, producer, properties)
VALUES ('Phone', 'iPhone 6s', 650, 'Apple', {'display': '6'});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('Phone', 'iPhone 6', 650, 'Apple', {'display': '6,5'});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('TV', 'Super res TV', 1200, 'Samsung', {});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('Smart Watch', 'Smart watch v4', 300, 'LG', {});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('Tablets', 'iPad 10', 800, 'Apple', {});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('Laptops', 'MacBook pro', 4000, 'Apple', {});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('Laptops', 'Chromebook', 450, 'Lenovo', {});
INSERT INTO items (category, name, price, producer, properties)
VALUES ('Laptops', 'Cheapbook', 200, 'Samsung', {});

-- PART 1
-- 1.
DESCRIBE shop;

-- 2.
SELECT *
FROM shop.items
WHERE category = 'Laptops'
ORDER BY price DESC;

-- 3.
SELECT *
FROM shop.items
WHERE category = 'Laptops'
  AND name = 'Chromebook';
SELECT *
FROM shop.items
WHERE category = 'Laptops'
  AND price >= 300
  and price <= 3000;
SELECT *
FROM shop.items
WHERE category = 'Laptops'
  AND producer = 'Samsung';

-- 4.
SELECT *
FROM shop.items
WHERE category = 'Phone'
  AND properties CONTAINS KEY 'display' ALLOW FILTERING;
SELECT *
FROM shop.items
WHERE category = 'Phone'
  AND properties CONTAINS KEY 'display'
  AND properties['display'] = '6' ALLOW FILTERING;

-- 5.
UPDATE shop.items
SET properties['display'] = '8'
WHERE category = 'Phone'
  AND price = 650
  AND name = 'iPhone 7';

UPDATE shop.items
SET properties = properties + {'display' : '10'}
WHERE category = 'Tablets'
  AND price = 800
  AND name = 'iPad 10';

update shop.items
set properties = properties - {'display'}
WHERE category = 'Phone'
  AND price = 650
  AND name = 'iPhone 7';

-- PART 2

CREATE TABLE orders
(
    customer_name text,
    order_time    timestamp,
    total         double,
    goods         set<text>,
    PRIMARY KEY ((customer_name), order_time)
);


CREATE INDEX goods on orders (goods);

INSERT INTO orders (customer_name, order_time, total, goods)
VALUES ('Alexander', '2020-04-24', 500, {'book', 'pen', 'rope'});
INSERT INTO orders (customer_name, order_time, total, goods)
VALUES ('Dmitry', '2020-04-25', 600, {'phone'});
INSERT INTO orders (customer_name, order_time, total, goods)
VALUES ('Dmitry', '2020-03-10', 1000, {'laptop'});

-- 1.
DESCRIBE orders;

-- 2.
SELECT *
FROM shop.orders
WHERE customer_name = 'Alexander'
ORDER BY order_time DESC;

-- 3.
SELECT *
FROM shop.orders
WHERE customer_name = 'Dmitry'
  AND goods CONTAINS 'laptop';

-- 4.
SELECT customer_name, count(order_time)
FROM shop.orders
WHERE customer_name = 'Dmitry'
  and order_time < '2020-04-01'
GROUP BY customer_name;

-- 5.
SELECT customer_name, avg(total)
FROM shop.orders
GROUP BY customer_name;

-- 6.
SELECT customer_name, sum(total)
FROM shop.orders
GROUP BY customer_name;

-- 7.
SELECT customer_name, max(total)
FROM shop.orders
GROUP BY customer_name;

-- 8.
UPDATE shop.orders
SET goods = goods + {'soap'},
    total = 550
WHERE customer_name = 'Alexander'
  AND order_time = '2020-04-24';

SELECT *
FROM shop.orders
WHERE customer_name = 'Alexander'
  AND order_time = '2020-04-24';

-- 9.
SELECT customer_name, goods, writetime(total)
FROM shop.orders
GROUP BY customer_name, order_time;

-- 10.
INSERT INTO orders (customer_name, order_time, total, goods)
VALUES ('Alexander', '2020-03-18', 10, {'cup'}) USING TTL 100;

-- 11.
SELECT JSON *
FROM shop.orders;

-- 12.
INSERT INTO orders JSON
    '{
      "customer_name": "Ivan",
      "order_time": "2010-04-01 00:05:10.000Z",
      "total": 1005,
      "goods": [
        "tesla",
        "pen"
      ]
    }';

SELECT *
FROM shop.orders
WHERE customer_name = 'Ivan';




```
