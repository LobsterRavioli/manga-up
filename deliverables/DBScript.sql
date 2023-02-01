DROP SCHEMA IF EXISTS MANGA_UP ;
CREATE SCHEMA MANGA_UP;
USE MANGA_UP;

CREATE TABLE end_user
(
    usr_id  INT AUTO_INCREMENT NOT NULL,
    usr_email Varchar(128) NOT NULL UNIQUE,
    usr_name VARCHAR(32) NOT NULL,
    usr_surname VARCHAR(32) NOT NULL,
    usr_password VARCHAR(32) NOT NULL,
    usr_phone_number VARCHAR(20) NOT NULL,
    usr_birth_date date NOT NULL,
    PRIMARY KEY (usr_id)
);

CREATE TABLE address
(
    addr_id           INT AUTO_INCREMENT,
    usr_id            INT          NOT NULL,
    addr_country      VARCHAR(64)  NOT NULL,
    addr_city         VARCHAR(30)  NOT NULL,
    addr_street       VARCHAR(100) NOT NULL,
    addr_phone_number VARCHAR(15)  NOT NULL,
    addr_region       VARCHAR(30)  NOT NULL,
    addr_postal_code  VARCHAR(5)   NOT NULL,
    PRIMARY KEY (addr_id),
    FOREIGN KEY (usr_id) REFERENCES end_user (usr_id)
);

CREATE TABLE credit_card
(
    crd_id INT AUTO_INCREMENT NOT NULL,
    crd_number VARCHAR(16) UNIQUE NOT NULL,
    usr_id INT NOT NULL,
    crd_cvc VARCHAR(32) NOT NULL,
    crd_holder VARCHAR(50) NOT NULL,
    crd_expiration_date date NOT NULL,
    PRIMARY KEY (crd_id),
    FOREIGN KEY (usr_id) REFERENCES end_user(usr_id)
);

CREATE TABLE users
(
    user_name VARCHAR(20) NOT NULL PRIMARY KEY,
    password VARCHAR(32) NOT NULL);


CREATE TABLE roles
(
    role_name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE user_roles
(
    user_name VARCHAR(20) NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_name, role_name),
    FOREIGN KEY (user_name) REFERENCES users (user_name) ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES roles (role_name) ON DELETE CASCADE
);

CREATE TABLE Orders
(
    ord_id INT AUTO_INCREMENT NOT NULL,
    ord_date DATE NOT NULL,
    ord_state VARCHAR(50) NOT NULL,
    ord_total_price FLOAT NOT NULL,
    ord_end_user_id INT NOT NULL,
    ord_address VARCHAR(100) NOT NULL,
    ord_card VARCHAR(100) NOT NULL,

    FOREIGN KEY(ord_end_user_id) REFERENCES end_user(usr_id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(ord_id)
);

CREATE TABLE Order_row
(
    id INT AUTO_INCREMENT NOT NULL,
    ord_id INT NOT NULL,
    user_id INT NOT NULL,
    manga_name VARCHAR(50) NOT NULL,
    manga_price DOUBLE NOT NULL,
    quantity int NOT NULL,

    PRIMARY KEY (id,ord_id,user_id),

    FOREIGN KEY (ord_id) REFERENCES Orders(ord_id),
    FOREIGN KEY (user_id) REFERENCES end_user(usr_id)
);

CREATE TABLE TO_MANAGE
(
    user_name VARCHAR(20) NOT NULL,
    order_id int NOT NULL,

    PRIMARY KEY (user_name,order_id),
    FOREIGN KEY (user_name) REFERENCES users(user_name)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(order_id) REFERENCES Orders(ord_id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE manages
(
    man_user_name VARCHAR(20) NOT NULL,
    man_order_id INT NOT NULL,
    PRIMARY KEY(man_user_name, man_order_id),
    man_shipment_date DATE NOT NULL,
    man_tracking_number VARCHAR(20) UNIQUE NOT NULL,
    man_courier VARCHAR(30) NOT NULL,
    man_delivery_date DATE NOT NULL,

    FOREIGN KEY(man_order_id) REFERENCES Orders(ord_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(man_user_name) REFERENCES users(user_name) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Manga
(
    id int auto_increment KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    editore VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    weight DOUBLE NOT NULL,
    height DOUBLE NOT NULL,
    lenght DOUBLE NOT NULL,
    state VARCHAR(5) NOT NULL,
    description VARCHAR(255),
    ISBN VARCHAR(13) NOT NULL,
    book_binding VARCHAR(30),
    volume VARCHAR(20),
    release_date date NOT NULL,
    page_number int,
    quantity int NOT NULL,
    interior VARCHAR(20),
    lang VARCHAR(20) NOT NULL,
    image VARCHAR(300) NOT NULL,
    collection_id  VARCHAR(25) NOT NULL,
    genre_id VARCHAR(15) NOT NULL,
    storyMaker VARCHAR(25) NOT NULL
);

CREATE TABLE CART
(
    manga_id int NOT NULL,
    user_id int NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY(manga_id,user_id),

    FOREIGN KEY (manga_id) REFERENCES Manga(id)
        ON UPDATE cascade ON DELETE cascade,

    FOREIGN KEY (user_id) REFERENCES end_user(usr_id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE COLLECTION
(
    nome VARCHAR(25) NOT NULL
);



CREATE TABLE Genre
(
    nome VARCHAR(15) PRIMARY KEY NOT NULL
);


INSERT INTO users (user_name, password) VALUES ('Tommaso', 'password1');
INSERT INTO users (user_name, password) VALUES ('Alessandro', 'password2');
INSERT INTO users (user_name, password) VALUES ('Giovanni', 'password3');
INSERT INTO users (user_name, password) VALUES ('Sara', 'password4');
INSERT INTO users (user_name, password) VALUES ('Francesco', 'password5');
INSERT INTO users (user_name, password) VALUES ('Riccardo', 'password6');


INSERT INTO roles (role_name) VALUES ('CATALOG_MANAGER');
INSERT INTO roles (role_name) VALUES ('USER_MANAGER');
INSERT INTO roles (role_name) VALUES ('ORDER_MANAGER');


INSERT INTO user_roles (user_name, role_name) VALUES ('Tommaso', 'CATALOG_MANAGER');
INSERT INTO user_roles (user_name, role_name) VALUES ('Alessandro', 'USER_MANAGER');

INSERT INTO user_roles (user_name, role_name) VALUES ('Giovanni', 'ORDER_MANAGER');
INSERT INTO user_roles (user_name, role_name) VALUES ('Giovanni', 'USER_MANAGER');
INSERT INTO user_roles (user_name, role_name) VALUES ('Giovanni', 'CATALOG_MANAGER');

INSERT INTO user_roles (user_name, role_name) VALUES ('Sara', 'ORDER_MANAGER');
INSERT INTO user_roles (user_name, role_name) VALUES ('Sara', 'CATALOG_MANAGER');

INSERT INTO user_roles (user_name, role_name) VALUES ('Francesco', 'ORDER_MANAGER');
INSERT INTO user_roles (user_name, role_name) VALUES ('Riccardo', 'ORDER_MANAGER');


INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('toms@hotmail.it', 'Tommaso', 'Sorrentino', 'napoli123', '3662968496', '1970-01-15');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('fra@hotmail.it', 'Francesco', 'Monzillo', 'dontTypeInGoogle', '3409567346', '1976-03-01');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('ale@hotmail.it', 'Alessandro', 'Carnevale', 'passWARUDO', '3201992344', '1975-07-15');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('squidditen@blue.it', 'Squidward Quincy', 'Tentacles', 'golDclarinet', '3774890918', '1999-05-01');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('mrkrabs@blue.it', 'Eugene Harold', 'Krab', 'IngredientXISNotPlanktons', '3446779921', '1942-11-30');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('spongy87@blue.it', 'Spongebob', 'Squarepants', 'spugnaBOBPantaloniQuadrati23', '3449576923', '1986-07-14');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('patrik060@blue.it', 'Patrick', 'Star', 'spongibob-uhKrabbyPattySENSEI', '3994058843', '1986-04-22');

INSERT INTO end_user (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)
VALUES ('spike@mars.it', 'Spike', 'Spiegel', 'WhateverHappensHappens', '3998831966', '1944-06-26');


INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('1234990388769210', 8, 297, 'Faye Valentine', '2032-08-03');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('3234338700228810', 2, 887, 'Francesco Monzillo', '2030-01-09');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('6543388700226254', 4, 885, 'Squidward Quincy Tentacles', '2025-11-10');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('4567338711779023', 5, 332, 'Spongebob Squarepants', '2023-09-08');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('3232115522009988', 1, 320, 'Tommaso Sorrentino', '2025-07-10');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('4590120000509023', 3, 775, 'Alessandro Carnevale', '2024-12-12');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('1239888899776534', 6, 266, 'Spongebob Squarepants', '2025-06-10');

INSERT INTO credit_card (crd_number, usr_id, crd_cvc, crd_holder, crd_expiration_date)
VALUES ('2900338661198212', 7, 112, 'Patrick Star', '2025-10-02');



INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (1, 'Italy', 'City1', 'Street1', 1234567890, 'Region1', 84011);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (7, 'Italy', 'City2', 'Street2', 9274846890, 'Region2', 84056);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (5, 'Italy', 'City3', 'Street3', 6774125997, 'Region3', 84098);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (2, 'Italy', 'City4', 'Street4', 1674890111, 'Region4', 84032);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (3, 'Italy', 'City5', 'Street5', 1552334998, 'Region5', 84546);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (8, 'Italy', 'City6', 'Street6', 4333294477, 'Region6', 84213);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (4, 'Italy', 'City7', 'Street7', 3401267677, 'Region7', 84767);

INSERT INTO address (usr_id, addr_country, addr_city, addr_street, addr_phone_number, addr_region, addr_postal_code)
VALUES (6, 'Italy', 'City8', 'Street8', 1563259833, 'Region8', 84323);


INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id, ord_address, ord_card)
VALUES('2018-10-09', 'TO_SENT', 58.00, 5, 'ADDRESS_INFO1', 'CREDITCARD_INFO1');

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id, ord_address, ord_card)
VALUES ('2018-11-02', 'TO_SENT', 100.00, 3, 'ADDRESS_INFO2', 'CREDITCARD_INFO2');

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id, ord_address, ord_card)
VALUES ('2018-11-21', 'TO_SENT', 55.00, 2, 'ADDRESS_INFO3', 'CREDITCARD_INFO3');

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id, ord_address, ord_card)
VALUES ('2018-09-09', 'TO_SENT', 532.00, 1, 'ADDRESS_INFO4', 'CREDITCARD_INFO4');

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id, ord_address, ord_card)
VALUES ('2018-06-04', 'TO_SENT', 21.50, 8, 'ADDRESS_INFO5', 'CREDITCARD_INFO5');


INSERT INTO TO_MANAGE (user_name, order_id) VALUES ('Giovanni', 5);
INSERT INTO TO_MANAGE (user_name, order_id) VALUES ('Sara', 4);
INSERT INTO TO_MANAGE (user_name, order_id) VALUES ('Sara', 1);
INSERT INTO TO_MANAGE (user_name, order_id) VALUES ('Giovanni', 2);
INSERT INTO TO_MANAGE (user_name, order_id) VALUES ('Francesco', 3);



INSERT INTO Manga (id, name, editore, price, weight, height, lenght, state, description, ISBN, book_binding, volume, release_date, page_number, quantity, interior, lang, image, collection_id, genre_id, storyMaker)
VALUES (1, 'd', 'f', 1, 1, 1, 1, 'd', 'd', 'd', 'd', 'd', '2023-01-17', 1, 32, 'd', 'd', 'd', '1', 'd', 'd');

INSERT INTO CART (manga_id, user_id, quantity) VALUES (1, 3, 32);



/*
INSERT INTO manages (man_user_name, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES ('Giovanni', 5, '2007-10-08', 'TRN1', 'BRT', '2007-10-09');

INSERT INTO manages (man_user_name, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES ('Giovanni', 2, '2007-12-08', 'TRN2', 'BRT', '2007-12-09');

INSERT INTO manages (man_user_name, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES ('Francesco', 1, '2007-10-08', 'TRN3', 'DHL', '2007-10-09');

INSERT INTO manages (man_user_name, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES ('Francesco', 3, '2007-10-08', 'TRN4', 'DHL', '2007-10-09');

INSERT INTO manages (man_user_name, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES ('Francesco', 4, '2007-10-08', 'TRN5', 'BRT', '2007-10-09');
*/