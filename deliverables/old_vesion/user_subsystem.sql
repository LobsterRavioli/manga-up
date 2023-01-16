DROP DATABASE IF EXISTS MANGA_UP;
CREATE DATABASE MANGA_UP;
USE MANGA_UP;

CREATE TABLE end_user
(
    usr_id  INT AUTO_INCREMENT NOT NULL,
    usr_email Varchar(128) NOT NULL UNIQUE ,
    usr_name VARCHAR(32) NOT NULL,
    usr_surname VARCHAR(32) NOT NULL,
    usr_password VARCHAR(32) NOT NULL,
    usr_phone_number VARCHAR(20) NOT NULL,
    usr_birth_date date NOT NULL,
    PRIMARY KEY (usr_id)
);


CREATE TABLE address
(
    addr_id  INT AUTO_INCREMENT,
    usr_id INT NOT NULL ,
    addr_country VARCHAR(64) NOT NULL,
    addr_city VARCHAR(30) NOT NULL,
    addr_street VARCHAR(100) NOT NULL,
    addr_phone_number VARCHAR(15) NOT NULL,
    addr_region VARCHAR(30) NOT NULL ,
    addr_postal_code VARCHAR(5) NOT NULL,
    PRIMARY KEY (addr_id),
    FOREIGN KEY (usr_id) REFERENCES end_user(usr_id)
);

CREATE TABLE credit_card
(
    crd_id INT AUTO_INCREMENT NOT NULL,
    crd_number VARCHAR(20) NOT NULL ,
    usr_id INT NOT NULL,
    crd_cvc VARCHAR(32) NOT NULL,
    crd_holder VARCHAR(15) NOT NULL,
    crd_expirement_date date NOT NULL,
    PRIMARY KEY (crd_id),
    FOREIGN KEY (usr_id) REFERENCES end_user(usr_id)
);

/*
CREATE TABLE tomcat_users
(
    user_name varchar(20) NOT NULL PRIMARY KEY,
    password varchar(32) NOT NULL
);
CREATE TABLE tomcat_roles
(
    role_name varchar(20) NOT NULL PRIMARY KEY
);
CREATE TABLE tomcat_users_roles (
                                    user_name varchar(20) NOT NULL,
                                    role_name varchar(20) NOT NULL,
                                    PRIMARY KEY (user_name, role_name),
                                    CONSTRAINT tomcat_users_roles_foreign_key_1 FOREIGN KEY (user_name) REFERENCES tomcat_users (user_name),
                                    CONSTRAINT tomcat_users_roles_foreign_key_2 FOREIGN KEY (role_name) REFERENCES tomcat_roles (role_name)
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


INSERT INTO credit_card(crd_number, usr_id, crd_cvc, crd_name_holder, crd_surname_holder, crd_expirement_date) VALUES ('374622187618862',1 , 277, 'Tommaso', 'Sorrentino', '2030-02-02');
INSERT INTO credit_card(crd_number, usr_id, crd_cvc, crd_name_holder, crd_surname_holder, crd_expirement_date) VALUES ('3578522457571502',1 , 122, 'Tommaso', 'Sorrentino', '2040-02-02');
INSERT INTO credit_card(crd_number, usr_id, crd_cvc, crd_name_holder, crd_surname_holder, crd_expirement_date) VALUES ('3579629919206814',1 , 900, 'Tommaso', 'Sorrentino', '2030-02-02');
INSERT INTO credit_card(crd_number, usr_id, crd_cvc, crd_name_holder, crd_surname_holder, crd_expirement_date) VALUES ('5602230008375036',1 , 577, 'Tommaso', 'Sorrentino', '2040-02-02');
INSERT INTO credit_card(crd_number, usr_id, crd_cvc, crd_name_holder, crd_surname_holder, crd_expirement_date) VALUES ('589399632112560496',1 , 899 , 'Tommaso', 'Sorrentino', '2050-02-02');
INSERT INTO credit_card(crd_number, usr_id, crd_cvc, crd_name_holder, crd_surname_holder, crd_expirement_date) VALUES ('589399632112560496',1 , 899 , 'Tommaso', 'Sorrentino', '2050-02-02');

INSERT INTO address(usr_id, addr_country, addr_city, addr_street, addr_street_number, addr_postal_code, addr_phone_number, address.addr_region) VALUES (1, 'ITA', 'Napoli', 'Via boccia', '35', '80040', '3662968496', 'Campania');

DROP TABLE MANGA_UP.credit_card;
CREATE TABLE credit_card
(
    crd_id INT AUTO_INCREMENT NOT NULL,
    crd_number VARCHAR(20) NOT NULL ,
    usr_id INT NOT NULL,
    crd_cvc VARCHAR(3) NOT NULL,
    crd_holder_name VARCHAR(15) NOT NULL,
    crd_holder_surname VARCHAR(15) NOT NULL,
    crd_expirement_date date NOT NULL,
    PRIMARY KEY (crd_id),
    FOREIGN KEY (usr_id) REFERENCES end_user(usr_id)
);
 */

