CREATE TABLE IF NOT EXISTS END_USER (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    surname VARCHAR(30) NOT NULL,
    birth_date DATE NOT NULL,
    password CHAR(64) NOT NULL,
    phone_number VARCHAR(30),
    email VARCHAR(30) NOT NULL
);