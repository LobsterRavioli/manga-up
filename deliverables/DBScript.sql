DROP SCHEMA IF EXISTS MANGA_UP ;
CREATE SCHEMA MANGA_UP;
USE MANGA_UP;

CREATE TABLE User
(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username VARCHAR(15) NOT NULL UNIQUE,
    password varchar(15) NOT NULL
);

CREATE TABLE roles
(
    role_name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_name) REFERENCES User(username),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (role_name) REFERENCES roles(role_name)
);

CREATE TABLE END_USER
(
    id  INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email Varchar(128) NOT NULL UNIQUE ,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(128) NOT NULL,
    password VARCHAR(32) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    birth_date date NOT NULL
);

CREATE TABLE Credit_Card
(
    number VARCHAR(20) PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    cvc VARCHAR(3) NOT NULL,
    nome VARCHAR(128) NOT NULL,
    cognome VARCHAR(15) NOT NULL,
    data_scadenza date NOT NULL,
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
);

CREATE TABLE Address
(
    id  INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    country VARCHAR(64) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(100) NOT NULL,
    street_number Int NOT NULL,
    postal_code VARCHAR(5) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
);

CREATE TABLE Orders
(
    ord_id INT AUTO_INCREMENT NOT NULL,
    ord_date DATE NOT NULL,
    ord_state VARCHAR(50) NOT NULL,
    ord_total_price FLOAT NOT NULL,
    ord_end_user_id INT NOT NULL,

    FOREIGN KEY(ord_end_user_id) REFERENCES END_USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(ord_id)
);

CREATE TABLE TO_MANAGE
(
    user_id int NOT NULL,
    order_id int NOT NULL,

    PRIMARY KEY (user_id,order_id),
    FOREIGN KEY (user_id) REFERENCES User(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(order_id) REFERENCES Orders(ord_id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE manages
(
    man_user_id int NOT NULL,
    man_order_id INT NOT NULL,
    PRIMARY KEY(man_user_id, man_order_id),
    man_delivery_date DATE NOT NULL,
    man_tracking_number VARCHAR(20) NOT NULL,
    man_courier VARCHAR(30) NOT NULL,
    man_shipment_date DATE NOT NULL,

    FOREIGN KEY(man_order_id) REFERENCES Orders(ord_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(man_user_id) REFERENCES User(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Product
(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    weight DOUBLE NOT NULL,
    height DOUBLE NOT NULL,
    lenght DOUBLE NOT NULL,
    state VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type_of_product VARCHAR(30) NOT NULL,
    quantity INT NOT NULL,
    image VARCHAR(300) NOT NULL
);

CREATE TABLE HasProductP
(
    product_id int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY(product_id,user_id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Manga
(
    id int auto_increment KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    weight DOUBLE NOT NULL,
    height DOUBLE NOT NULL,
    lenght DOUBLE NOT NULL,
    state VARCHAR(5) NOT NULL,
    description VARCHAR(255) NOT NULL,
    collections VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    ISBN VARCHAR(64) NOT NULL,
    book_binding VARCHAR(30) NOT NULL,
    volume VARCHAR(50) NOT NULL,
    release_date date NOT NULL,
    page_number int NOT NULL,
    interior VARCHAR(20) NOT NULL,
    lang VARCHAR(20) NOT NULL,
    image VARCHAR(300) NOT NULL
);

CREATE TABLE HasProductM
(
    manga_id int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY(manga_id,user_id),
    FOREIGN KEY (manga_id) REFERENCES Manga(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Genre
(
    nome VARCHAR(15) PRIMARY KEY NOT NULL
);

CREATE TABLE HasGenre
(
    manga_id int NOT NULL,
    genre_id varchar(15) NOT NULL,
    PRIMARY KEY(manga_id,genre_id),
    FOREIGN KEY (manga_id) REFERENCES Manga(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (genre_id) REFERENCES Genre(nome)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Author
(
    id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE HasAuthor
(
    manga_id int NOT NULL,
    author_id int NOT NULL,
    PRIMARY KEY(manga_id,author_id),
    FOREIGN KEY (manga_id) REFERENCES Manga(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (author_id) REFERENCES Author(id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Cart_Element
(
    user_id INT NOT NULL,
    product_id int NOT NULL,
    PRIMARY KEY(user_id,product_id),
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (product_id) REFERENCES Product(id)
        ON UPDATE cascade ON DELETE cascade
);

INSERT INTO User (username, password) VALUES ('Tommaso', 'password1');
INSERT INTO User (username, password) VALUES ('Alessandro', 'password2');
INSERT INTO User (username, password) VALUES ('Giovanni', 'password3');
INSERT INTO User (username, password) VALUES ('Sara', 'password4');
INSERT INTO User (username, password) VALUES ('Francesco', 'password5');


INSERT INTO roles (role_name) VALUES ('GESTORE_CATALOGO');
INSERT INTO roles (role_name) VALUES ('GESTORE_UTENTI');
INSERT INTO roles (role_name) VALUES ('GESTORE_ORDINI');


INSERT INTO user_roles (user_id, user_name, role_name) VALUES (1, 'Tommaso', 'GESTORE_CATALOGO');
INSERT INTO user_roles (user_id, user_name, role_name) VALUES (2, 'Alessandro', 'GESTORE_UTENTI');

INSERT INTO user_roles (user_id, user_name, role_name) VALUES (3, 'Giovanni', 'GESTORE_ORDINI');
INSERT INTO user_roles (user_id, user_name, role_name) VALUES (4, 'Sara', 'GESTORE_ORDINI');
INSERT INTO user_roles (user_id, user_name, role_name) VALUES (5, 'Francesco', 'GESTORE_ORDINI');


INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('toms@hotmail.it', 'Tommaso', 'Sorrentino', 'napoli123', '3662968496', '1970-01-15');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('fra@hotmail.it', 'Francesco', 'Monzillo', 'dontTypeInGoogle', '3409567346', '1976-03-01');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('ale@hotmail.it', 'Alessandro', 'Carnevale', 'passWARUDO', '3201992344', '1975-07-15');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('squidditen@blue.it', 'Squidward Quincy', 'Tentacles', 'golDclarinet', '3774890918', '1999-05-01');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('mrkrabs@blue.it', 'Eugene Harold', 'Krab', 'IngredientXISNotPlanktons', '3446779921', '1942-11-30');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('spongy87@blue.it', 'Spongebob', 'Squarepants', 'spugnaBOBPantaloniQuadrati23', '3449576923', '1986-07-14');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('patrik060@blue.it', 'Patrick', 'Star', 'spongibob-uhKrabbyPattySENSEI', '3994058843', '1986-04-22');

INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)
VALUES ('spike@mars.it', 'Spike', 'Spiegel', 'WhateverHappensHappens', '3998831966', '1944-06-26');


INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('1234990388769210', 8, 297, 'Faye', 'Valentine', '2032-08-03');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('3234338700228810', 2, 887, 'Francesco', 'Monzillo', '2030-01-09');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('6543388700226254', 4, 885, 'Squidward Quincy', 'Tentacles', '2025-11-10');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('4567338711779023', 5, 332, 'Spongebob', 'Squarepants', '2023-09-08');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('3232115522009988', 1, 320, 'Tommaso', 'Sorrentino', '2025-07-10');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('4590120000509023', 3, 775, 'Alessandro', 'Carnevale', '2024-12-12');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('1239888899776534', 6, 266, 'Spongebob', 'Squarepants', '2025-06-10');

INSERT INTO Credit_Card (number, user_id, cvc, nome, cognome, data_scadenza)
VALUES ('2900338661198212', 7, 112, 'Patrick', 'Star', '2025-10-02');



INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (1, 'Italy', 'City1', 'Street1', 36, 84011);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (7, 'Italy', 'City2', 'Street2', 22, 84056);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (5, 'Italy', 'City3', 'Street3', 12, 84098);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (2, 'Italy', 'City4', 'Street4', 9, 84032);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (3, 'Italy', 'City5', 'Street5', 2, 84546);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (8, 'Italy', 'City6', 'Street6', 34, 84213);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (4, 'Italy', 'City7', 'Street7', 28, 84767);

INSERT INTO Address (user_id, country, city, street, street_number, postal_code)
VALUES (6, 'Italy', 'City8', 'Street8', 19, 84323);


INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id)
VALUES ('2018-10-09', 'TO_SENT', 58.00, 5);

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id)
VALUES ('2018-11-02', 'TO_SENT', 100.00, 3);

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id)
VALUES ('2018-11-21', 'TO_SENT', 55.00, 2);

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id)
VALUES ('2018-09-09', 'TO_SENT', 532.00, 1);

INSERT INTO Orders (ord_date, ord_state, ord_total_price, ord_end_user_id)
VALUES ('2018-06-04', 'TO_SENT', 21.50, 8);


INSERT INTO TO_MANAGE (user_id, order_id) VALUES (3, 5);
INSERT INTO TO_MANAGE (user_id, order_id) VALUES (4, 4);
INSERT INTO TO_MANAGE (user_id, order_id) VALUES (4, 1);
INSERT INTO TO_MANAGE (user_id, order_id) VALUES (3, 2);
INSERT INTO TO_MANAGE (user_id, order_id) VALUES (5, 3);



INSERT INTO manages (man_user_id, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES (3, 5, '2007-10-08', 'TRN1', 'BRT', '2007-10-09');

INSERT INTO manages (man_user_id, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES (3, 2, '2007-12-08', 'TRN2', 'BRT', '2007-12-09');

INSERT INTO manages (man_user_id, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES (5, 1, '2007-10-08', 'TRN3', 'DHL', '2007-10-09');

INSERT INTO manages (man_user_id, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES (5, 3, '2007-10-08', 'TRN4', 'DHL', '2007-10-09');

INSERT INTO manages (man_user_id, man_order_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)
VALUES (5, 4, '2007-10-08', 'TRN5', 'BRT', '2007-10-09');