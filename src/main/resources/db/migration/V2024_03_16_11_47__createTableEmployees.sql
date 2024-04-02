CREATE TABLE employees(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(60),
    email VARCHAR(100),
    is_active BOOLEAN
);