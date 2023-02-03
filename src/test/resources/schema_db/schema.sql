

CREATE TABLE IF NOT EXISTS end_user
(
    usr_id  INT AUTO_INCREMENT NOT NULL,
    usr_email Varchar(128) NOT NULL UNIQUE,
    usr_name VARCHAR(32) NOT NULL,
    usr_surname VARCHAR(32) NOT NULL,
    usr_password VARCHAR(64) NOT NULL,
    usr_phone_number VARCHAR(20) NOT NULL,
    usr_birth_date date NOT NULL ,
    PRIMARY KEY (usr_id)
);

CREATE TABLE IF NOT EXISTS address
(
    addr_id           IDENTITY NOT NULL PRIMARY KEY,
    usr_id            INT          NOT NULL,
    addr_country      VARCHAR(64)  NOT NULL,
    addr_city         VARCHAR(30)  NOT NULL,
    addr_street       VARCHAR(100) NOT NULL,
    addr_phone_number VARCHAR(15)  NOT NULL,
    addr_region       VARCHAR(30)  NOT NULL,
    addr_postal_code  VARCHAR(5)   NOT NULL
);

CREATE TABLE IF NOT EXISTS credit_card
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

CREATE TABLE IF NOT EXISTS us_ers
(
    user_name VARCHAR(20) NOT NULL PRIMARY KEY,
    password VARCHAR(32) NOT NULL);


CREATE TABLE IF NOT EXISTS ro_les
(
    role_name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_name VARCHAR(20) NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_name, role_name),
    FOREIGN KEY (user_name) REFERENCES us_ers (user_name) ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES ro_les (role_name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Orders
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

CREATE TABLE IF NOT EXISTS Order_row
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

CREATE TABLE IF NOT EXISTS TO_MANAGE
(
    user_name VARCHAR(20) NOT NULL,
    order_id int NOT NULL,

    PRIMARY KEY (user_name,order_id),
    FOREIGN KEY (user_name) REFERENCES us_ers(user_name)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(order_id) REFERENCES Orders(ord_id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE IF NOT EXISTS manages
(
    man_user_name VARCHAR(20) NOT NULL,
    man_order_id INT NOT NULL,
    PRIMARY KEY(man_user_name, man_order_id),
    man_shipment_date DATE NOT NULL,
    man_tracking_number VARCHAR(20) UNIQUE NOT NULL,
    man_courier VARCHAR(30) NOT NULL,
    man_delivery_date DATE NOT NULL,

    FOREIGN KEY(man_order_id) REFERENCES Orders(ord_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(man_user_name) REFERENCES us_ers(user_name) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Manga
(
    id int auto_increment PRIMARY KEY NOT NULL,
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

CREATE TABLE IF NOT EXISTS CART
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

CREATE TABLE IF NOT EXISTS COLLECTION
(
    nome VARCHAR(25) NOT NULL
);



CREATE TABLE IF NOT EXISTS Genre
(
    nome VARCHAR(15) PRIMARY KEY NOT NULL
);



