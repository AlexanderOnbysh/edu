CREATE TABLE hotel_booking
(
    booking_id  serial PRIMARY KEY,
    client_name VARCHAR(50) NOT NULL,
    hotel_name  VARCHAR(50) NOT NULL,
    arrival     DATE        NOT NULL DEFAULT CURRENT_DATE,
    departure   DATE        NOT NULL DEFAULT CURRENT_DATE
);
