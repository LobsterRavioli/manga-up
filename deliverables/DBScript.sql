drop schema MangaUp;
create schema MangaUp;
use MangaUp;

CREATE TABLE Credit_Card
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id BIGINT UNSIGNED NOT NULL,
    number VARCHAR(20) NOT NULL UNIQUE,
    cvc VARCHAR(3) NOT NULL,
    name VARCHAR(15) NOT NULL,
    surname VARCHAR(15) NOT NULL,
    expired_date date NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES EndUser(id)
);

CREATE TABLE Address
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL ,
    country VARCHAR(64) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(100) NOT NULL,
    street_number Int NOT NULL,
    postal_code VARCHAR(5) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES EndUser(id)
);

CREATE TABLE EndUser
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email Varchar(128) UNIQUE NOT NULL,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(128) NOT NULL,
    password VARCHAR(15) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    birth_date date NOT NULL,
    PRIMARY KEY (id)
);


