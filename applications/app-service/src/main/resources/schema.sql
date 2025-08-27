CREATE TABLE IF NOT EXISTS loan_request_states (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS loan_request_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    min_amount BIGINT NOT NULL,
    max_amount BIGINT NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,
    auto_validation BOOLEAN NOT NULL,
    description VARCHAR(255) NOT NULL
    );