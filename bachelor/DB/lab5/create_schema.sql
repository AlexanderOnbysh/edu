CREATE TABLE clients (
	_id       serial PRIMARY KEY,
	client_id integer NOT NULL,
	name      VARCHAR(50) NOT NULL,
	surname   VARCHAR(50) NOT NULL,
	address   VARCHAR(100) NOT NULL,
	company   VARCHAR(50)
);

CREATE TABLE software (
	_id       serial PRIMARY KEY,
	title     VARCHAR(50) NOT NULL,
	version   VARCHAR(50) NOT NULL,
	price     numeric NOT NULL,
	CHECK (price >= 0)
);

CREATE TABLE contracts (
	_id         serial PRIMARY KEY,
	date        timestamp,
	client_id   integer REFERENCES clients,
	software_id integer REFERENCES software (_id)
);

CREATE TABLE delivery (
	_id         serial PRIMARY KEY,
	date        timestamp,
	contract_id integer REFERENCES contracts (_id)
);