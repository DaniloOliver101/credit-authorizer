CREATE TABLE Account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    food_balance DOUBLE DEFAULT 500.00,
    meal_balance DOUBLE DEFAULT 500.00,
    cash_balance DOUBLE DEFAULT 1000.00
);

CREATE TABLE merchant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    mcc VARCHAR(4)
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    mcc VARCHAR(4),
    total_amount DOUBLE,
    merchant VARCHAR(255),
    code VARCHAR(10),
    FOREIGN KEY (account_id) REFERENCES Account(id)
);
