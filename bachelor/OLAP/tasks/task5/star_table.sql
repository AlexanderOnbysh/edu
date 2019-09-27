DROP TABLE INVOICE CASCADE CONSTRAINTS;
DROP TABLE STUFF;
DROP TABLE CLIENT;
DROP TABLE SUPPLIER;
DROP TABLE PRODUCT;
DROP TABLE STORE;
DROP TABLE PAYMENT_TYPE;
DROP TABLE STOCK;
DROP TABLE OPERATION_TYPE;
DROP TABLE FACTS;

CREATE TABLE "STUFF" (
  "id_stuff"      INT PRIMARY KEY,
  "name"          varchar(255),
  "surname"       varchar(255),
  "phone"         varchar(255),
  "address"       varchar(255),
  "position_name" varchar(255),
  "salary"        INT,
  "description"   varchar(255)
);

CREATE TABLE "CLIENT" (
  "id_client" INT PRIMARY KEY,
  "name"      varchar(255),
  "surname"   varchar(255),
  "phone"     varchar(255),
  "address"   varchar(255),
  "email"     varchar(255)
);

CREATE TABLE "SUPPLIER" (
  "id_supplier"   INT PRIMARY KEY,
  "supplier_name" varchar(255),
  "supplier_info" varchar(255)
);

CREATE TABLE "PRODUCT" (
  "id_product"        INT PRIMARY KEY,
  "product_name"      varchar(255),
  "product_type_name" varchar(255),
  "description"       varchar(255),
  "price"             INT
);

CREATE TABLE "STORE" (
  "id_store"    INT PRIMARY KEY,
  "shelf"       varchar(255),
  "date_oper"   DATE,
  "quantity"    INT,
  "name_oper"   varchar(255),
  "description" varchar(255)
);

CREATE TABLE "PAYMENT_TYPE" (
  "id_type"           INT PRIMARY KEY,
  "payment_type_name" varchar(255),
  "description"       varchar(255)
);

-- task

CREATE TABLE "STOCK" (
  "id_stock"            INT PRIMARY KEY,
  "description"         VARCHAR(500),
  "region"              VARCHAR(500),
  "last_delivered_item" DATE,
  "capacity"            FLOAT
);

CREATE TABLE "OPERATION_TYPE" (
  "id_oper_type" INT PRIMARY KEY,
  "type" VARCHAR2(100)
);

CREATE TABLE "FACTS" (
  "id_fact" INT PRIMARY KEY,
  "fact" VARCHAR2(20)
);

-- task

CREATE TABLE "INVOICE" (
  "id_invoice"      INT PRIMARY KEY,
  "id_supplier"     INT REFERENCES SUPPLIER("id_supplier"),
  "id_payment_type" INT REFERENCES PAYMENT_TYPE("id_type"),
  "id_product"      INT REFERENCES PRODUCT("id_product"),
  "id_store"        INT REFERENCES STORE("id_store"),
  "id_stuff"        INT REFERENCES STUFF("id_stuff"),
  "id_client"       INT REFERENCES CLIENT("id_client"),
  "id_stock"        INT REFERENCES STOCK("id_stock"),
  "id_oper_type"    INT REFERENCES OPERATION_TYPE("id_oper_type"),
  "id_fact_type"    INT REFERENCES FACTS("id_fact"),
  "purchase_time"   DATE,
  "quantity"        INT,
  "total_cost_sale" FLOAT,
  "profit"          VARCHAR2(128),
  "description"     varchar(255)
);

insert into OPERATION_TYPE ("id_oper_type", "type")
values (1, 'IN');
insert into OPERATION_TYPE ("id_oper_type", "type")
values (2, 'OUT');
insert into OPERATION_TYPE ("id_oper_type", "type")
values (3, 'PENDING');

insert into PAYMENT_TYPE ("id_type", "payment_type_name", "description")
values (1, 'card', 'by card');
insert into PAYMENT_TYPE ("id_type", "payment_type_name", "description")
values (2, 'cash', 'cash');

insert into FACTS ("id_fact", "fact") values (1, 'ONLINE_SALES');
insert into FACTS ("id_fact", "fact") values (2, 'STORE_SALES');
insert into FACTS ("id_fact", "fact") values (3, 'STOCK');

insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (1, 'aa', 'LoungeBar ROSHEN', 'sweet', 5);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (2, 'bb', 'ROSHEN', null, 10);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (3, 'cc', '������������ ��������� �� ���������� ���������� �������', null, 10);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (5, 'dd', '"������������ ������" � ��������� �������� �� ����� ��������', null, 65);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (6, 'ee', '������� ��������', null, 55);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (7, 'ff', '������� EsfeRo Crema', null, 78);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (8, 'gg', 'Nescafe Gold', null, 60);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (9, 'hh', '������', null, 12);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (10, 'jj', '��� ����������', null, 65);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (11, 'kk', '�����', null, 12);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (12, 'll', '��� ��������', null, 15);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (13, 'mm', '�������', null, 15);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (14, 'nn', '������ �������', null, 25);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (15, 'oo', '������ ����', null, 30);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (16, 'pp', '������', null, 15);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (17, 'qq', '������� ����������', null, 100);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (18, 'rr', '������� �������', null, 120);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (19, 'ss', '������ ����������', null, 170);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (20, 'tt', '�����', null, 98);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (21, 'uu', '��������', null, 95);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (22, 'vv', '���� �����������', null, 4);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (23, 'ww', '���� �����������', null, 7);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (24, 'zz', '���� �����', null, 3);
insert into PRODUCT ("id_product", "product_name", "product_type_name", "description", "price")
values (25, 'qw', '��������', null, 3);

insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (1, '7', to_date('22-05-2012 21:14:02', 'dd-mm-yyyy hh24:mi:ss'), 1, 9, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (2, '15', to_date('16-11-2013 09:54:21', 'dd-mm-yyyy hh24:mi:ss'), 1, 5, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (3, '13', to_date('20-05-2012 01:12:18', 'dd-mm-yyyy hh24:mi:ss'), 2, 1, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (4, '14', to_date('08-08-2013 17:16:10', 'dd-mm-yyyy hh24:mi:ss'), 1, 7, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (5, '1', to_date('14-06-2012 18:34:32', 'dd-mm-yyyy hh24:mi:ss'), 1, 7, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (6, '15', to_date('01-09-2011 16:16:32', 'dd-mm-yyyy hh24:mi:ss'), 2, 8, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (7, '17', to_date('26-11-2013 22:10:58', 'dd-mm-yyyy hh24:mi:ss'), 2, 8, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (8, '9', to_date('20-04-2013 13:11:13', 'dd-mm-yyyy hh24:mi:ss'), 2, 9, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (9, '11', to_date('01-01-2014 14:11:30', 'dd-mm-yyyy hh24:mi:ss'), 2, 4, null);
insert into STORE ("id_store", "shelf", "date_oper", "quantity", "name_oper", "description")
values (10, '12', to_date('01-08-2013 19:20:29', 'dd-mm-yyyy hh24:mi:ss'), 2, 8, null);


insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (1, 'ROSHEN', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (2, 'Nuts', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (3, 'Kinder', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (4, 'Nestle', null);
insert into SUPPLIER ("id_supplier", "supplier_name", "supplier_info")
values (5, 'Ferrero', null);

-- query 1
SELECT "name", "surname", "purchase_time", "quantity" FROM INVOICE
JOIN STUFF S on INVOICE."id_stuff" = S."id_stuff";

-- query 2
SELECT c2."name", c2."surname", i."quantity", p."product_name", TYPE2."payment_type_name", I."purchase_time", C2."phone", C2."address", P."price", I."total_cost_" FROM INVOICE I
JOIN CLIENT C2 on I."id_client" = C2."id_client"
JOIN PAYMENT_TYPE TYPE2 on I."id_payment_type" = TYPE2."id_type"
JOIN PRODUCT P on I."id_product" = P."id_product"
JOIN STOCK SS on I."id_stock" = SS."id_stock"
JOIN STORE ST on ST."id_store" = I."id_store"
JOIN STUFF on STUFF."id_stuff" = I."id_stuff"
JOIN SUPPLIER S on S."id_supplier" = I."id_supplier";

select * from INVOICE;
select COUNT(*) from CLIENT;
select COUNT(*) from STOCK;
select * from PRODUCT;
select COUNT(*) from SUPPLIER;
select COUNT(*) from OPERATION_TYPE;
select COUNT(*) from FACTS;

select COUNT(*) from PAYMENT_TYPE;
select * from INVOICE;

