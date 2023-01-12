DROP DATABASE IF EXISTS MANGA_UP;
CREATE DATABASE MANGA_UP;
USE MANGA_UP;

CREATE TABLE tomcat_users
(
    user_name VARCHAR(20) NOT NULL PRIMARY KEY,
    password VARCHAR(32) NOT NULL
);

CREATE TABLE tomcat_roles
(
    role_name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE tomcat_users_roles (
    user_name VARCHAR(20) NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_name, role_name),
    CONSTRAINT tomcat_users_roles_foreign_key_1 FOREIGN KEY (user_name) REFERENCES tomcat_users (user_name),
    CONSTRAINT tomcat_users_roles_foreign_key_2 FOREIGN KEY (role_name) REFERENCES tomcat_roles (role_name)
);

CREATE TABLE end_user
(
    usr_id  INT AUTO_INCREMENT NOT NULL,
    usr_email VARCHAR(128) NOT NULL UNIQUE ,
    usr_name VARCHAR(32) NOT NULL,
    usr_surname VARCHAR(32) NOT NULL,
    usr_password VARCHAR(32) NOT NULL,
    usr_phone_number VARCHAR(20) NOT NULL,
    usr_birth_date DATE NOT NULL,
    PRIMARY KEY (usr_id)
);

CREATE TABLE orders
(
    ord_id INT AUTO_INCREMENT NOT NULL,
    ord_date DATE NOT NULL,
    ord_state VARCHAR(50) NOT NULL,
    ord_total_price FLOAT NOT NULL,
    ord_user_name VARCHAR(20) NOT NULL,
    ord_end_user_id INT NOT NULL,
    ord_courier VARCHAR(30) NOT NULL,

    FOREIGN KEY(ord_user_name) REFERENCES tomcat_users(user_name) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(ord_end_user_id) REFERENCES end_user(usr_id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(ord_id)
);

CREATE TABLE manages
(
    man_order_id INT NOT NULL,
    man_user_name VARCHAR(20) NOT NULL,
    man_delivery_date DATE NOT NULL,
    man_tracking_number VARCHAR(20) NOT NULL,
    man_shipment_date DATE NOT NULL,
    PRIMARY KEY(man_user_name, man_order_id),

    FOREIGN KEY(man_user_name) REFERENCES tomcat_users(user_name) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(man_order_id) REFERENCES orders(ord_id) ON UPDATE CASCADE ON DELETE CASCADE
);
