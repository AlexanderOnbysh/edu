drop table INVOICE_D;
drop table INVOICE;
drop table STAFF_D;

create table INVOICE  (
   ID_INVOICE           int not null PRIMARY KEY,
   STAFF_NAME           varchar2(128),
   E_MAIL               varchar2(128),
   INVOICE              varchar2(128),
   SUPPLIER             varchar2(128),
   PRODUCT              varchar2(128),
   QUANTITY             varchar2(128),
   PRICE                varchar2(128),
   INVOICE_DATE         varchar2(128)
);

insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (1, 'Mark Hughes ', 'Mark .Simon@shop.com', 'INV-15', 'LG', 'TV-10', '1', '3192', '08.05.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (2, 'David Davies', 'Davies.Barry@shop.com', 'INV-75', 'Toshiba', 'TV-20', '1', '8908', '29.06.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (3, 'Adam Thomas', 'Thomas.Adam@shop.com', 'INV-73', 'Sony', 'TV-3', '1', '8887', '02.07.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (4, 'Barry Davies', 'Davies.Barry@shop.com', 'INV-9', 'Toshiba', 'TV-18', '2', '3365', '21.07.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (5, 'Frank Thomas', 'Thomas.Adam@shop.com', 'INV-37', 'Philips', 'TV-12', '1', '6917', '01.08.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (6, 'Chedwig Davies', 'Davies.Chedwig@shop.com', 'INV-85', 'Rolsen', 'TV-11', '1', '5799', '07.08.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (7, 'Louise White', 'White.Basil@shop.com', 'INV-87', 'Philips', 'TV-12', '1', '7567', '07.08.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (8, 'Carlton Edwards', 'Edwards.Carlton@shop.com', 'INV-62', 'Rolsen', 'TV-18', '1', '6890', '23.08.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (9, 'Bob Davies', 'Davies.Bob@shop.com', 'INV-69', 'Sony', 'TV-19', '1', '8349', '09.09.2011');
insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (10, 'Johnny Davies', 'Davies.Johnny@shop.com', 'INV-51', 'LG', 'TV-9', '1', '9334', '11.09.2011');

insert into INVOICE (ID_INVOICE, staff_name, e_mail, invoice, supplier, product, quantity, price, invoice_date)
values (11, 'Alexander Onbysh', 'alexander.onbysh@shop.com', 'INV-51', 'LG', 'TV-9', '1', '9334', '11.09.2011');


select * from INVOICE;

CREATE TABLE STAFF_D (
  ID_STAFF NUMBER NOT NULL PRIMARY KEY,
  ID_POSITION NUMBER NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  SURNAME VARCHAR(255) NOT NULL,
  PHONE VARCHAR(20),
  ADDRESS VARCHAR(255),
  EMAIL VARCHAR(255) NOT NULL
);

insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (3, 1, 'Adam', 'Thomas', '063-564-54-54', '����', 'Davies@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (4, 1, 'Barry', 'Davies', '097-347-44-99', '����', 'White@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (5, 1, 'Frank', 'Thomas', '050-54-21', '����', 'Hughes@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (1, 1, 'Mark', 'Hughes', '365-54-87', '����', 'Smih@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (2, 1, 'David', 'Davies', '584-98-44', '����', 'Thomas@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (6, 1, 'Chedwig', 'Davies', '546-21-64', '����', 'Edwards@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (7, 2, 'Louise', 'White', '878-54-63', '����', 'Green@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (8, 3, 'Carlton', 'Edwards', '050-879-54-16', '����', 'Wood@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (9, 4, 'Bob', 'Davies', '093-879-23-54', '����', 'Harris@market.com');
insert into STAFF_D (id_staff, id_position, name, surname, phone, address, email)
values (10, 5, 'Johnny', 'Davies', '097-213-54-69', '����', 'Martin@market.com');

select * from STAFF_D;

create table INVOICE_D  (
   ID_INVOICE           int not null PRIMARY KEY,
   ID_STAFF             varchar2(128),
   STAFF_NAME           varchar2(128),
   E_MAIL               varchar2(128),
   INVOICE              varchar2(128),
   SUPPLIER             varchar2(128),
   PRODUCT              varchar2(128),
   QUANTITY             varchar2(128),
   PRICE                varchar2(128),
   INVOICE_DATE         varchar2(128)
);


-- task

-- mapping id and name
SELECT * FROM INVOICE I, STAFF_D S WHERE REGEXP_SUBSTR(I.STAFF_NAME, '[^ ]+', 1, 1) = S.NAME;

-- procedure
CREATE OR REPLACE PROCEDURE LOAD_INVOICE IS
 CURSOR C1 IS
    SELECT I.ID_INVOICE, S.ID_STAFF, I.STAFF_NAME, S.EMAIL, I.INVOICE, I.SUPPLIER, I.PRODUCT, I.QUANTITY, I.PRICE, I.INVOICE_DATE
    FROM INVOICE I, STAFF_D S WHERE REGEXP_SUBSTR(I.STAFF_NAME, '[^ ]+', 1, 1) = S.NAME;
BEGIN
  FOR INV_REC IN C1
  LOOP
  INSERT INTO INVOICE_D (ID_INVOICE, ID_STAFF, STAFF_NAME, E_MAIL, INVOICE, SUPPLIER, PRODUCT, QUANTITY, PRICE, INVOICE_DATE)
  VALUES (INV_REC.ID_INVOICE, INV_REC.ID_STAFF, INV_REC.STAFF_NAME,
          INV_REC.EMAIL, INV_REC.INVOICE, INV_REC.SUPPLIER, INV_REC.PRODUCT, INV_REC.QUANTITY, INV_REC.PRICE, INV_REC.INVOICE_DATE);
  END LOOP;
END;

CALL LOAD_INVOICE();

SELECT * FROM INVOICE;
SELECT * FROM STAFF_D;
SELECT * FROM INVOICE_D;

-- now able to join
SELECT * FROM INVOICE_D
JOIN STAFF_D ON STAFF_D.ID_STAFF = INVOICE_D.ID_STAFF;


SELECT COUNT(*) FROM STAFF_D;

CREATE OR REPLACE PROCEDURE LOAD_SECOND IS
 CURSOR C1 IS
    SELECT * FROM INVOICE INV WHERE REGEXP_SUBSTR(INV.STAFF_NAME, '[^ ]+', 1, 1) NOT IN (SELECT NAME FROM STAFF_D);
    ID_INV NUMBER := 11;
BEGIN
  FOR INV_REC IN C1
  LOOP
    INSERT INTO STAFF_D (ID_STAFF, ID_POSITION, NAME, SURNAME, PHONE, ADDRESS, EMAIL)
    VALUES (ID_INV, 1, REGEXP_SUBSTR(INV_REC.STAFF_NAME, '[^ ]+', 1, 1),
        REGEXP_SUBSTR(INV_REC.STAFF_NAME, '[^ ]+', 1, 2), null, null , INV_REC.E_MAIL);
    ID_INV := ID_INV + 1;
  END LOOP;
END;

CALL LOAD_SECOND();

SELECT * FROM INVOICE;
SELECT * FROM INVOICE_D;


SELECT * FROM STAFF_D;
