CREATE TABLE reviews(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(255),
    employee_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);