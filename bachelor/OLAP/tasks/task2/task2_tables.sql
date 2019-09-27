/*==============================================================*/

/* Database name:  SALE                                         */

/* DBMS name:      ORACLE Version 11g                           */

/* Created on:     25.03.2018 21:23:56                          */

/*==============================================================*/


--
ALTER TABLE INVOICE
   DROP CONSTRAINT FK_INVOICE_REFERENCE_PAYMENT_;
--
ALTER TABLE INVOICE
    DROP CONSTRAINT FK_INVOICE_REFERENCE_INVOICE_;
--
ALTER TABLE INVOICE_DETAIL
    DROP CONSTRAINT FK_INVOICE__REFERENCE_PRODUCT;
--
ALTER TABLE PRODUCT
    DROP CONSTRAINT FK_PRODUCT_REFERENCE_PRODUCT_;
--
ALTER TABLE PRODUCT
    DROP CONSTRAINT FK_PRODUCT_REFERENCE_SUPPLIER;
--
ALTER TABLE STORE
    DROP CONSTRAINT FK_STORE_REFERENCE_TYPE_OPE;
--
ALTER TABLE STUFF
    DROP CONSTRAINT FK_STUFF_REFERENCE_POSITION;
--
DROP TABLE INVOICE CASCADE CONSTRAINTS;
--
DROP TABLE INVOICE_DETAIL CASCADE constraints;
--
DROP TABLE PAYMENT_TYPE CASCADE constraints;
--
DROP TABLE POSITION CASCADE constraints;
--
DROP TABLE CLIENT CASCADE constraints;
--
DROP TABLE PRODUCT CASCADE constraints;
--
DROP TABLE PRODUCT_TYPE CASCADE constraints;
--
DROP TABLE STORE CASCADE constraints;
--
DROP TABLE STUFF CASCADE constraints;
--
DROP TABLE SUPPLIER CASCADE constraints;
--
DROP TABLE TYPE_OPER CASCADE constraints;
--
DROP TABLE UNIT CASCADE constraints;
--
DROP INDEX "id_supplier_ind";
--
DROP INDEX "id_product_type_ind";
--
DROP INDEX "id_product_ind";



CREATE TABLE INVOICE (
   "id_invoice"         NUMBER               not null
    CONSTRAINT CKC_ID_INVOICE_INVOICE check ("id_invoice" >= 1),
   "id_paiment_type"    NUMBER               not null,
   "purchase_time"      TIMESTAMP            not null,
   "id_invoice_detail"  NUMBER               not null,
   CONSTRAINT PK_INVOICE primary key ("id_invoice")
);

CREATE TABLE INVOICE_DETAIL (
   "id_invoice"         NUMBER               not null
      constraint CKC_ID_INVOICE_INVOICE_ check ("id_invoice" >= 1),
   "id_product"         NUMBER               not null,
   "quantity"           NUMBER               not null
      constraint CKC_QUANTITY_INVOICE_ check ("quantity" between 1 and 25),
   "description"        VARCHAR2(500),
   constraint PK_INVOICE_DETAIL primary key ("id_invoice")
);

CREATE INDEX "id_product_ind" ON INVOICE_DETAIL (
   "id_product" ASC
);


CREATE TABLE PAYMENT_TYPE (
   "id_type"            NUMBER               not null
      CONSTRAINT CKC_ID_TYPE_PAYMENT_ check ("id_type" >= 1),
   "payment_type_name"  VARCHAR2(128)        not null,
   "description"        VARCHAR2(256),
   CONSTRAINT PK_PAYMENT_TYPE primary key ("id_type")
);

CREATE TABLE CLIENT (
   "id_client"          NUMBER               not null,
   "name"               VARCHAR2(128)        not null,
   "surname"            VARCHAR(128)         not null,
   "phone"              VARCHAR2(13)         not null,
   "address"            VARCHAR2(128)        not null,
   constraint PK_CLIENT primary key ("id_client")
);

ALTER TABLE invoice
ADD "id_client"
NUMBER CONSTRAINT fk_client_in_invoice
REFERENCES CLIENT ("id_client");

CREATE TABLE POSITION (
   "id_position"        NUMBER               not null
      CONSTRAINT CKC_ID_POSITION_POSITION check ("id_position" >= 1),
   "position_name"      varchar2(128)        not null,
   "salary"             NUMBER               not null
      CONSTRAINT CKC_SALARY_POSITION check ("salary" >= 1),
   "description"        VARCHAR2(1024),
   CONSTRAINT PK_POSITION primary key ("id_position")
);


CREATE TABLE PRODUCT (
   "id_product"         NUMBER               not null
      CONSTRAINT CKC_ID_PRODUCT_PRODUCT check ("id_product" >= 1),
   "product_name"       varchar2(128)        not null,
   "description"        varchar2(128),
   "price"              NUMBER               not null,
   "id_product_type"    NUMBER               not null,
   "id_supplier"        NUMBER               not null,
   CONSTRAINT PK_PRODUCT primary key ("id_product")
);

CREATE INDEX id_product_type_ind ON PRODUCT (
   "id_product_type" ASC
);

CREATE INDEX id_supplier_ind ON PRODUCT (
   "id_supplier" ASC
);

CREATE TABLE PRODUCT_TYPE (
   "id_product_type"    NUMBER               not null
      CONSTRAINT CKC_ID_PRODUCT_TYPE_PRODUCT_ CHECK ("id_product_type" >= 1),
   "product_type_name"  VARCHAR2(128)        not null,
   CONSTRAINT PK_PRODUCT_TYPE primary key ("id_product_type")
);

CREATE TABLE STORE (
   "id_store"           NUMBER               not null
      CONSTRAINT CKC_ID_STORE_STORE check ("id_store" >= 1),
   "shelf"              NUMBER               not null,
   "date_oper"          DATE                 not null,
   "quantity"           NUMBER               not null,
   "id_oper_type"       NUMBER               not null,
   "id_product"         NUMBER               not null
);

CREATE TABLE STUFF (
   "id_stuff"           NUMBER               not null
      CONSTRAINT CKC_ID_STUFF_STUFF check ("id_stuff" >= 1),
   "name"               VARCHAR2(64)         not null,
   "surname"            VARCHAR2(64)         not null,
   "phone"              VARCHAR2(64),
   "address"            VARCHAR2(256),
   "id_position"        NUMBER               not null,
   CONSTRAINT PK_STUFF primary key ("id_stuff")
);


ALTER TABLE invoice
ADD "id_stuff"
NUMBER CONSTRAINT fk_stuff_in_invoice
REFERENCES STUFF ("id_stuff");

CREATE TABLE SUPPLIER (
   "id_supplier"        NUMBER               not null
      CONSTRAINT CKC_ID_SUPPLIER_SUPPLIER check ("id_supplier" >= 1),
   "supplier_name"      VARCHAR2(128)        not null,
   "supplier_info"      VARCHAR2(1024),
   CONSTRAINT PK_SUPPLIER primary key ("id_supplier")
);


CREATE TABLE TYPE_OPER (
   "id_oper_type"       INTEGER              not null
      CONSTRAINT CKC_ID_OPER_TYPE_TYPE_OPE check ("id_oper_type" >= 1),
   "name_oper"          VARCHAR2(128)     not null,
   "description"        VARCHAR(100),
   CONSTRAINT PK_TYPE_OPER primary key ("id_oper_type")
);

CREATE TABLE UNIT (
   "id_unit"            NUMBER               not null
      CONSTRAINT CKC_ID_UNIT_UNIT check ("id_unit" >= 1),
   "unit_name"          varchar2(128)        not null,
   "description"        varchar2(512),
   CONSTRAINT PK_UNIT primary key ("id_unit")
);

ALTER TABLE INVOICE
   ADD CONSTRAINT FK_INVOICE_REFERENCE_PAYMENT_ foreign key ("id_paiment_type")
      REFERENCES PAYMENT_TYPE ("id_type");

ALTER TABLE INVOICE
   ADD CONSTRAINT FK_INVOICE_REFERENCE_INVOICE_ foreign key ("id_invoice_detail")
      REFERENCES INVOICE_DETAIL ("id_invoice");

ALTER TABLE INVOICE_DETAIL
   ADD CONSTRAINT FK_INVOICE__REFERENCE_PRODUCT foreign key ("id_product")
      REFERENCES PRODUCT ("id_product");

ALTER TABLE PRODUCT
   ADD CONSTRAINT FK_PRODUCT_REFERENCE_PRODUCT_ foreign key ("id_product_type")
      REFERENCES PRODUCT_TYPE ("id_product_type");

ALTER TABLE PRODUCT
   ADD CONSTRAINT FK_PRODUCT_REFERENCE_SUPPLIER foreign key ("id_supplier")
      REFERENCES SUPPLIER ("id_supplier");

ALTER TABLE STUFF
   ADD CONSTRAINT FK_STUFF_REFERENCE_POSITION foreign key ("id_position")
      REFERENCES POSITION ("id_position");

ALTER TABLE STORE
   ADD CONSTRAINT FK_STORE_REFERENCE_PRODUCT foreign key ("id_product")
      REFERENCES PRODUCT ("id_product");

ALTER TABLE STORE
   ADD CONSTRAINT FK_STORE_REFERENCE_TYPE_OPE foreign key ("id_oper_type")
      REFERENCES TYPE_OPER ("id_oper_type");
