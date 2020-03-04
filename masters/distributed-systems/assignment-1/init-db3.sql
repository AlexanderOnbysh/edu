CREATE TABLE account
(
    account_id  serial PRIMARY KEY,
    client_name VARCHAR(50) NOT NULL,
    amount      NUMERIC
        CONSTRAINT positive_amount CHECK (amount >= 0)
);
