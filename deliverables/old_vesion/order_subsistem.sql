DROP DATABASE IF EXISTS MANGA_UP;
CREATE DATABASE MANGA_UP;
USE MANGA_UP;

CREATE TABLE users
(
    user_name VARCHAR(20) NOT NULL PRIMARY KEY,
    password VARCHAR(32) NOT NULL
);

CREATE TABLE roles
(
    role_name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE user_roles (
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

INSERT INTO tomcat_users (user_name, password) VALUES ('tommaso', '1a1dc91c907325c69271ddf0c944bc72');
INSERT INTO tomcat_users (user_name, password) VALUES ('alessandro', '1a1dc91c907325c69271ddf0c944bc72');
INSERT INTO tomcat_users (user_name, password) VALUES ('francesco', '1a1dc91c907325c69271ddf0c944bc72');

INSERT INTO tomcat_roles (role_name) VALUES ('USER_MANAGER');
INSERT INTO tomcat_roles (role_name) VALUES ('CATALOG_MANAGER');
INSERT INTO tomcat_roles (role_name) VALUES ('ORDER_MANAGER');

INSERT INTO tomcat_users_roles (user_name, role_name) VALUES ('tommaso', 'USER_MANAGER');
INSERT INTO tomcat_users_roles (user_name, role_name) VALUES ('tommaso', 'CATALOG_MANAGER');
INSERT INTO tomcat_users_roles (user_name, role_name) VALUES ('tommaso', 'ORDER_MANAGER');

INSERT INTO tomcat_users_roles (user_name, role_name) VALUES ('alessandro', 'USER_MANAGER');
INSERT INTO tomcat_users_roles (user_name, role_name) VALUES ('francesco', 'CATALOG_MANAGER');

/* password: password1*/
INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date) VALUES ('tommyrock99@hotmail.it', 'Tommaso', 'Sorrentino', '7c6a180b36896a0a8c02787eeafb0e4c', '292-376-0441', '2000-04-18');
/* password: password2 */
INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date) VALUES ('fra@hotmail.it', 'Francesco', 'Marra', '6cb75f652a9b52798eb6cf2201057c73', '954-856-9215','2000-11-15' );
/* password: password3 */
INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date) VALUES ('ale@hotmail.it', 'Alessandro', 'coppola', '819b0643d6b89dc9b579fdfc9094f28e', '900-289-9060', '2001-11-25');
/* password: password4 */
INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date) VALUES ('kekw@hotmail.it', 'Vittorio', 'Scarano', '34cc93ece0ba9e3f6f235d4af979b16c', '100-923-0337', '2000-05-07');


INSERT INTO orders (ord_date, ord_state, ord_total_price, ord_user_name, ord_end_user_id, ord_courier) VALUES ('2018-10-09', 'TO_SENT', 58.00, 'alessandro', 3, 'BRT');
INSERT INTO orders (ord_date, ord_state, ord_total_price, ord_user_name, ord_end_user_id, ord_courier) VALUES ('2018-10-09', 'TO_SENT', 155.00, 'francesco', 2, 'BRT');
INSERT INTO orders (ord_date, ord_state, ord_total_price, ord_user_name, ord_end_user_id, ord_courier) VALUES ('2018-07-02', 'TO_SENT', 60.00, 'alessandro', 2, 'DHL');
INSERT INTO orders (ord_date, ord_state, ord_total_price, ord_user_name, ord_end_user_id, ord_courier) VALUES ('2018-10-09', 'TO_SENT', 58.00, 'alessandro', 3, 'BRT');
INSERT INTO orders (ord_date, ord_state, ord_total_price, ord_user_name, ord_end_user_id, ord_courier) VALUES ('2018-01-09', 'TO_SENT', 47.00, 'tommaso', 3, 'DHL');
INSERT INTO orders (ord_date, ord_state, ord_total_price, ord_user_name, ord_end_user_id, ord_courier) VALUES ('2018-06-09', 'TO_SENT', 33.00, 'alessandro', 3, 'BRT');

INSERT INTO manages (man_order_id, man_user_name, man_delivery_date, man_tracking_number, man_shipment_date) VALUES (1, 'alessandro', '2018-10-09', 'track_num1', '2018-10-10');
INSERT INTO manages (man_order_id, man_user_name, man_delivery_date, man_tracking_number, man_shipment_date) VALUES (5, 'tommaso', '2018-01-10', 'track_num2', '2018-01-12');
INSERT INTO manages (man_order_id, man_user_name, man_delivery_date, man_tracking_number, man_shipment_date) VALUES (3, 'alessandro', '2018-07-02', 'track_num3', '2018-07-05');
INSERT INTO manages (man_order_id, man_user_name, man_delivery_date, man_tracking_number, man_shipment_date) VALUES (4, 'alessandro', '2018-10-09', 'track_num4', '2018-10-11');
INSERT INTO manages (man_order_id, man_user_name, man_delivery_date, man_tracking_number, man_shipment_date) VALUES (2, 'francesco', '2018-10-10', 'track_num5', '2018-10-10');
INSERT INTO manages (man_order_id, man_user_name, man_delivery_date, man_tracking_number, man_shipment_date) VALUES (6, 'alessandro', '2018-06-10', 'track_num6', '2018-06-11');