CREATE TABLE employees(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30),
    last_name VARCHAR(60),
    email VARCHAR(100),
    is_active BOOLEAN
);