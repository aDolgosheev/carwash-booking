-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       phone VARCHAR(20),
                       email VARCHAR(100),
                       password VARCHAR(255),
                       role VARCHAR(20)
);

-- Таблица услуг (автомойка: например, "мойка кузова", "химчистка" и т.д.)
CREATE TABLE service_type (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              duration INTEGER NOT NULL,  -- продолжительность в минутах
                              price NUMERIC(10,2) NOT NULL
);

-- Таблица бронирований
CREATE TABLE booking (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER REFERENCES users(id),
                         service_id INTEGER REFERENCES service_type(id),
                         booking_time TIMESTAMP NOT NULL,
                         prepayment BOOLEAN NOT NULL,
                         status VARCHAR(20) NOT NULL
);
