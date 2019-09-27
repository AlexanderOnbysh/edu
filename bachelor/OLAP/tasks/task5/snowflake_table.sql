DROP TABLE TYPE_OPER CASCADE CONSTRAINTS;
DROP TABLE POSITION CASCADE CONSTRAINTS;
DROP TABLE STUFF CASCADE CONSTRAINTS;
DROP TABLE CLIENT CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_TYPE CASCADE CONSTRAINTS;
DROP TABLE SUPPLIER CASCADE CONSTRAINTS;
DROP TABLE PRODUCT CASCADE CONSTRAINTS;
DROP TABLE STORE CASCADE CONSTRAINTS;
DROP TABLE PAYMENT_TYPE CASCADE CONSTRAINTS;
DROP TABLE STOCK CASCADE CONSTRAINTS;
DROP TABLE INVOICE CASCADE CONSTRAINTS;

CREATE TABLE "TYPE_OPER" (
	"id_oper_type" INT PRIMARY KEY,
	"name_oper" varchar(255),
	"description" varchar(255)
);

CREATE TABLE "POSITION" (
	"id_position" INT PRIMARY KEY,
	"position_name" varchar(255),
	"salary" INT,
	"description" varchar(255)
);

CREATE TABLE "STUFF" (
  "id_stuff"    INT PRIMARY KEY,
  "id_position" INT REFERENCES POSITION("id_position"),
  "name"        varchar(255),
  "surname"     varchar(255),
  "phone"       varchar(255),
  "address"     varchar(255)
);

CREATE TABLE "CLIENT" (
	"id_client" INT PRIMARY KEY,
	"name" varchar(255),
	"surname" varchar(255),
	"phone" varchar(255),
	"address" varchar(255),
	"email" varchar(255)
);

CREATE TABLE "PRODUCT_TYPE" (
	"id_product_type" INT PRIMARY KEY,
	"product_type_name" varchar(255)
);

CREATE TABLE "SUPPLIER" (
	"id_supplier" INT PRIMARY KEY,
	"supplier_name" varchar(255),
	"supplier_info" varchar(255)
);

CREATE TABLE "PRODUCT" (
  "id_product"      INT PRIMARY KEY,
  "id_product_type" INT REFERENCES PRODUCT_TYPE("id_product_type"),
  "id_supplier"     INT REFERENCES SUPPLIER("id_supplier"),
  "product_name"    varchar(255),
  "description"     varchar(255),
  "price"           INT
);

CREATE TABLE "STORE" (
  "id_store"     INT PRIMARY KEY,
  "id_oper_type" INT REFERENCES TYPE_OPER("id_oper_type"),
  "shelf"        varchar(255),
  "date_oper"    DATE,
  "quantity"     INT
);

CREATE TABLE "PAYMENT_TYPE" (
	"id_type" INT PRIMARY KEY,
	"payment_type_name" varchar(255),
	"description" varchar(255)
);

-- stock

CREATE TABLE "STOCK" (
  "id_stock"            INT PRIMARY KEY,
  "description"         VARCHAR(500),
  "region"              VARCHAR(500),
  "last_delivered_item" DATE,
  "capacity"            FLOAT
);

-- stock

CREATE TABLE "INVOICE" (
  "id_invoice"      INT PRIMARY KEY,
  "id_payment_type" INT REFERENCES PAYMENT_TYPE("id_type"),
  "id_product"      INT REFERENCES PRODUCT("id_product"),
  "id_stuff"        INT REFERENCES STUFF("id_stuff"),
  "id_client"       INT REFERENCES CLIENT("id_client"),
  "id_store"        INT REFERENCES STORE("id_store"),
  "id_stock"        INT REFERENCES STOCK("id_stock"),
  "purchase_time"   DATE,
  "quantity"        INT,
  "description"     varchar(255)
);

insert into TYPE_OPER ("id_oper_type", "name_oper", "description")
values (1, 'IN', '??????????? ?? ??????.');
insert into TYPE_OPER ("id_oper_type", "name_oper", "description")
values (2, 'OUT', null);

insert into POSITION ("id_position", "position_name", "salary", "description")
values (1, '?????????-?????', 3000, null);
insert into POSITION ("id_position", "position_name", "salary", "description")
values (2, '?????????', 5000, null);
insert into POSITION ("id_position", "position_name", "salary", "description")
values (3, '???????? ??????? ???????', 7000, null);
insert into POSITION ("id_position", "position_name", "salary", "description")
values (4, '?????', 6000, null);
insert into POSITION ("id_position", "position_name", "salary", "description")
values (5, '?????', 6000, null);

insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (1, 1, 'Aaron', 'Smih', '365-54-87', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (2, 1, 'Adam', 'Thomas', '584-98-44', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (3, 1, 'Barry', 'Davies', '063-564-54-54', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (4, 1, 'Basil', 'White', '097-347-44-99', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (5, 1, 'Simon', 'Hughes', '050-54-21', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (6, 1, 'Carlton', 'Edwards', '546-21-64', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (7, 2, 'Sebastian', 'Green', '878-54-63', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (8, 3, 'Scott', 'Wood', '050-879-54-16', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (9, 4, 'Galvin', 'Harris', '093-879-23-54', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (10, 5, 'Bob', 'Martin', '097-213-54-69', '????');
insert into STUFF ("id_stuff", "id_position", "name", "surname", "phone", "address")
values (11, 4, 'Hunter', 'Anderson', '023-878-54-96', '??????????');

insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (1, 'Aaron', 'Smih', '365-54-87', '????', 'Smih@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (2, 'Adam', 'Thomas', '584-98-44', '????', 'Thomas@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (3, 'Barry', 'Davies', '063-564-54-54', '????', 'Davies@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (4, 'Basil', 'White', '097-347-44-99', '????', 'White@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (5, 'Simon', 'Hughes', '050-54-21', '????', 'Hughes@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (6, 'Carlton', 'Edwards', '546-21-64', '????', 'Edwards@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (7, 'Sebastian', 'Green', '878-54-63', '????', 'Green@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (8, 'Scott', 'Wood', '050-879-54-16', '????', 'Wood@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (9, 'Galvin', 'Harris', '093-879-23-54', '????', 'Harris@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (10, 'Bob', 'Martin', '097-213-54-69', '????', 'Martin@market.com');
insert into CLIENT ("id_client", "name", "surname", "phone", "address", "email")
values (11, 'Hunter', 'Anderson', '023-878-54-96', '??????????', 'Anderson@market.com');

insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (1, 'aa');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (2, 'bb');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (3, 'cc');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (4, 'dd');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (5, 'ee');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (6, 'ff');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (7, 'gg');
insert into PRODUCT_TYPE ("id_product_type", "product_type_name")
values (8, 'jj');

insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (1, 'ROSHEN', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (2, '???', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (3, '????-?????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (4, 'Nestle', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (5, 'Ferrero', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (6, 'Kraft Food', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (7, '??????????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (8, '?????????????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (9, 'Danone', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (10, '????????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (11, '????????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (12, '???? ????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (13, '?????? ???????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (14, '????????????? ?????????', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (15, '???', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (16, '????????', null);

insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (1, 1, 1, '?????? ??????? ? ???????? ?LoungeBar ROSHEN? ???????? ???????', ' sweet', 5);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (2, 1, 1, '?ROSHEN? ????? ? ?????? ???????? ????????', null, 10);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (3, 1, 1, '???????????? ????????? ?? ?????????? ?????????? ???????', null, 10);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (5, 1, 2, '"???????????? ??????" ? ????????? ???????? ?? ????? ????????', null, 65);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (6, 1, 2, '??????? ????????', null, 55);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (7, 1, 3, '??????? EsfeRo Crema', null, 78);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (8, 1, 4, 'Nescafe Gold', null, 60);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (9, 3, 7, '??????', null, 12);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (10, 4, 7, '??? ??????????', null, 65);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (11, 3, 7, '?????', null, 12);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (12, 3, 8, '??? ????????', null, 15);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (13, 3, 8, '???????', null, 15);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (14, 5, 12, '?????? ???????', null, 25);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (15, 5, 12, '?????? ????', null, 30);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (16, 3, 10, '??????', null, 15);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (17, 6, 13, '??????? ??????????', null, 100);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (18, 6, 13, '??????? ???????', null, 120);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (19, 6, 13, '?????? ??????????', null, 170);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (20, 5, 14, '?????', null, 98);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (21, 5, 14, '????????', null, 95);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (22, 7, 15, '???? ???????????', null, 4);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (23, 7, 15, '???? ???????????', null, 7);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (24, 7, 16, '???? ?????', null, 3);
insert into PRODUCT ("id_product", "id_product_type", "id_supplier", "product_name", "description", "price")
values (25, 7, 16, '????????', null, 3);

insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (1, 1, '7', to_date('22-05-2012 21:14:02', 'dd-mm-yyyy hh24:mi:ss'), 9);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (2, 2, '15', to_date('16-11-2013 09:54:21', 'dd-mm-yyyy hh24:mi:ss'), 5);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (3, 1, '13', to_date('20-05-2012 01:12:18', 'dd-mm-yyyy hh24:mi:ss'), 1);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (4, 1, '14', to_date('08-08-2013 17:16:10', 'dd-mm-yyyy hh24:mi:ss'), 7);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (5, 2, '1', to_date('14-06-2012 18:34:32', 'dd-mm-yyyy hh24:mi:ss'), 7);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (6, 1, '15', to_date('01-09-2011 16:16:32', 'dd-mm-yyyy hh24:mi:ss'), 8);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (7, 1, '17', to_date('26-11-2013 22:10:58', 'dd-mm-yyyy hh24:mi:ss'), 8);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (8, 2, '9', to_date('20-04-2013 13:11:13', 'dd-mm-yyyy hh24:mi:ss'), 9);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (9, 2, '11', to_date('01-01-2014 14:11:30', 'dd-mm-yyyy hh24:mi:ss'), 4);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (10, 2, '12', to_date('01-08-2013 19:20:29', 'dd-mm-yyyy hh24:mi:ss'), 8);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (11, 1, '7', to_date('28-09-2013 11:20:31', 'dd-mm-yyyy hh24:mi:ss'), 10);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (12, 2, '15', to_date('05-03-2013 10:35:54', 'dd-mm-yyyy hh24:mi:ss'), 5);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (13, 2, '6', to_date('15-05-2013 09:14:03', 'dd-mm-yyyy hh24:mi:ss'), 5);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (14, 1, '4', to_date('18-12-2013 08:15:11', 'dd-mm-yyyy hh24:mi:ss'), 10);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (15, 1, '14', to_date('15-02-2012 08:31:12', 'dd-mm-yyyy hh24:mi:ss'), 4);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (16, 1, '5', to_date('09-08-2013 08:14:07', 'dd-mm-yyyy hh24:mi:ss'), 5);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (17, 1, '12', to_date('16-07-2013 06:52:47', 'dd-mm-yyyy hh24:mi:ss'), 3);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (18, 1, '11', to_date('03-11-2011 16:10:13', 'dd-mm-yyyy hh24:mi:ss'), 2);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (19, 2, '17', to_date('27-07-2012 07:36:18', 'dd-mm-yyyy hh24:mi:ss'), 3);
insert into STORE ("id_store", "id_oper_type", "shelf", "date_oper", "quantity")
values (20, 1, '11', to_date('22-07-2013 14:19:40', 'dd-mm-yyyy hh24:mi:ss'), 6);

insert into PAYMENT_TYPE ("id_type", "payment_type_name", "description")
values (1, '?????????? ??????????', null);
insert into PAYMENT_TYPE ("id_type", "payment_type_name", "description")
values (2, '????????????? ??????????', null);

insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (3, 1, 5, 11, 10, 15, to_date('12-09-2011 13:24:43', 'dd-mm-yyyy hh24:mi:ss'), 15, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (4, 1, 3, 10, 5, 20, to_date('25-09-2011 08:57:03', 'dd-mm-yyyy hh24:mi:ss'), 16, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (5, 1, 5, 9, 2, 4, to_date('01-08-2012 08:51:18', 'dd-mm-yyyy hh24:mi:ss'), 60, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (6, 2, 6, 8, 8, 14, to_date('11-03-2013 15:41:48', 'dd-mm-yyyy hh24:mi:ss'), 70, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (7, 1, 1, 7, 2, 1, to_date('16-02-2012 08:18:04', 'dd-mm-yyyy hh24:mi:ss'), 32, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (8, 1, 3, 6, 1, 5, to_date('17-10-2012 21:40:40', 'dd-mm-yyyy hh24:mi:ss'), 23, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (9, 2, 5, 5, 6, 6, to_date('24-12-2012 02:36:56', 'dd-mm-yyyy hh24:mi:ss'), 14, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (10, 2, 5, 4, 8, 8, to_date('09-06-2013 08:45:01', 'dd-mm-yyyy hh24:mi:ss'), 67, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (11, 1, 3, 3, 6, 12, to_date('27-09-2011 19:18:38', 'dd-mm-yyyy hh24:mi:ss'), 12, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (12, 2, 4, 2, 7, 16, to_date('21-01-2014 14:06:15', 'dd-mm-yyyy hh24:mi:ss'), 63, null);
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (13, 2, 2, 1, 6, 18, to_date('01-10-2012 05:20:36', 'dd-mm-yyyy hh24:mi:ss'), 76, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (14, 2, 3, 1, 4, 13, to_date('20-01-2014 20:42:28', 'dd-mm-yyyy hh24:mi:ss'), 100, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (15, 1, 2, 4, 10, 1, to_date('11-08-2013 05:16:26', 'dd-mm-yyyy hh24:mi:ss'), 12, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (16, 1, 3, 6, 10, 4, to_date('10-02-2012 13:18:18', 'dd-mm-yyyy hh24:mi:ss'), 1, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (17, 2, 5, 7, 10, 3, to_date('07-02-2013 11:27:05', 'dd-mm-yyyy hh24:mi:ss'), 23, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (18, 1, 6, 6, 11, 12, to_date('14-01-2012 20:16:22', 'dd-mm-yyyy hh24:mi:ss'), 32, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (19, 1, 5, 2, 7, 7, to_date('29-10-2012 04:08:29', 'dd-mm-yyyy hh24:mi:ss'), 151, null );
insert into INVOICE ("id_invoice", "id_payment_type", "id_product", "id_stuff", "id_client", "id_store", "purchase_time", "quantity", "description")
values (20, 1, 3, 6, 6, 6, to_date('05-10-2012 21:25:57', 'dd-mm-yyyy hh24:mi:ss'), 0, null );


-- query 1
SELECT "name", "surname", "purchase_time", "quantity" FROM INVOICE
JOIN STUFF S on INVOICE."id_stuff" = S."id_stuff";

-- query 2
SELECT s."name", p."position_name", p."salary", p2."product_name", TYPE2."product_type_name", "quantity" FROM INVOICE
JOIN STUFF S on INVOICE."id_stuff" = S."id_stuff"
JOIN POSITION P on S."id_position" = P."id_position"
JOIN PRODUCT P2 on INVOICE."id_product" = P2."id_product"
JOIN PRODUCT_TYPE TYPE2 on P2."id_product_type" = TYPE2."id_product_type";