-- Task 1
SELECT *
FROM STORE
WHERE PRODUCT IN
      (SELECT PRODUCT
       FROM STORE
       GROUP BY PRODUCT
       HAVING COUNT(*) > 1)
ORDER BY PRODUCT;


select * from TASK1.STORE;

-- Task 2
SELECT
  SHELF,
  SUM(QUANTITY) AS ON_SHELF
FROM
  (SELECT *
   FROM STORE
   WHERE REGEXP_LIKE(QUANTITY, '^\d+$'))
GROUP BY SHELF
HAVING SUM(QUANTITY) > 30;

-- Task 3
SELECT *
FROM STORE
WHERE REGEXP_LIKE(
          STORE_DATE,
          '^(3[01]|[12]\d|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})$'
      )
      AND NOT (
  STORE_DATE
  BETWEEN
  TO_DATE('01.01.2011', 'DD.MM.YYYY')
  AND
  TO_DATE('31.05.2014', 'DD.MM.YYYY')
);

-- Task 4
SELECT *
FROM INVOICE
WHERE NOT REGEXP_LIKE(E_MAIL, '^[A-Za-z]+[A-Za-z\d.]+@shop.com$');

-- Task 5
SELECT *
FROM INVOICE
WHERE ID_STUFF
      IN (SELECT ID_STUFF
          FROM INVOICE
          GROUP BY ID_STUFF
          HAVING COUNT(DISTINCT STAFF_NAME) = 1 AND COUNT(DISTINCT E_MAIL) = 1)
ORDER BY ID_STUFF;

-- Task 6
SELECT *
FROM STORE
WHERE UPPER(OPER_TYPE) != 'IN' AND UPPER(OPER_TYPE) != 'OUT';

-- Task 7
SELECT *
FROM INVOICE
WHERE PRICE < 1000;

-- Task 8
SELECT *
FROM INVOICE
WHERE INVOICE IS NULL OR NOT REGEXP_LIKE(INVOICE, '^INV-\d+$');

-- Task 9
SELECT *
FROM INVOICE
WHERE PRODUCT IS NULL OR NOT REGEXP_LIKE(PRODUCT, '^TV-\d+$');


create table CATEGORY (
  id   varchar2(128) not null,
  name varchar2(128),
  subcategory varchar2(128),

  CONSTRAINT type_f
    FOREIGN KEY (subcategory)
    REFERENCES CATEGORY (id)
);

create table DESC_TYPE (
  id varchar2(128),
  name varchar2(128)
);

create table PRODUCT_DESC (
  id varchar2(128) not null,
  product varchar2(128),
  desc_type varchar2(128),
  value varchar2(128),

  CONSTRAINT product_f
    FOREIGN KEY (product)
    REFERENCES TASK1.STORE (product),

  CONSTRAINT desc_type_f
    FOREIGN KEY (desc_type)
    REFERENCES TASK1.DESC_TYPE (id)
);