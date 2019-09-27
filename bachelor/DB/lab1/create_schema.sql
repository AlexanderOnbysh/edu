ALTER USER alexander WITH SUPERUSER;

CREATE DATABASE database;

GRANT ALL PRIVILEGES ON DATABASE databaes TO alexander;

CREATE TABLE clients (
	id        serial PRIMARY KEY,
	client_id integer NOT NULL,
	name      VARCHAR(50) NOT NULL,
	surname   VARCHAR(50) NOT NULL,
	address   VARCHAR(100) NOT NULL,
	company   VARCHAR(50)
);

CREATE TABLE software (
	id        serial PRIMARY KEY,
	title     VARCHAR(50) NOT NULL,
	version   VARCHAR(50) NOT NULL,
	price     numeric NOT NULL,
	CHECK (price >= 0)
);

CREATE TABLE contracts (
	id          serial PRIMARY KEY,
	date        timestamp,
	client_id   integer REFERENCES clients,
	software_id integer REFERENCES software (id)
);

CREATE TABLE delivery (
	id          serial PRIMARY KEY,
	date        timestamp,
	contract_id integer REFERENCES contracts (id)
);