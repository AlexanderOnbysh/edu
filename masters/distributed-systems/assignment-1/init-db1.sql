CREATE TABLE fly_booking
(
    booking_id     serial PRIMARY KEY,
    client_name    VARCHAR(50) NOT NULL,
    fly_number     VARCHAR(50) NOT NULL,
    departure_from VARCHAR(50) NOT NULL,
    departure_to   VARCHAR(50) NOT NULL,
    date           DATE        NOT NULL DEFAULT CURRENT_DATE
);
