CREATE TABLE people
(
    id     SERIAL PRIMARY KEY,
    name   TEXT,
    age    INTEGER CHECK (age > 0),
    rights BOOLEAN
);

CREATE TABLE cars
(
    id        SERIAL PRIMARY KEY,
    car_brand TEXT UNIQUE,
    car_model TEXT,
    price     MONEY,
    user_id   SERIAL REFERENCES people (id)
);