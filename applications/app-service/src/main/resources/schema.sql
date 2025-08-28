CREATE TABLE IF NOT EXISTS loan_application_states (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS loan_application_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    min_amount BIGINT NOT NULL,
    max_amount BIGINT NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,
    auto_validation BOOLEAN NOT NULL,
    description VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS loan_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_identity_number VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    term_in_months INT NOT NULL,
    state_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    CONSTRAINT fk_state FOREIGN KEY (state_id) REFERENCES loan_application_states(id),
    CONSTRAINT fk_type FOREIGN KEY (type_id) REFERENCES loan_application_types(id)
    );
