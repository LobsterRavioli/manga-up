drop schema MANGA_UP ;
create schema MANGA_UP;
use MANGA_UP;

CREATE TABLE Genre
(
    nome VARCHAR(15) PRIMARY KEY NOT NULL
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
    nome VARCHAR(15) NOT NULL,
    cognome VARCHAR(15) NOT NULL,
    data_scadenza date NOT NULL,
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
);

CREATE TABLE Address
(
    id  INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id INT NOT NULL ,
    country VARCHAR(64) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(100) NOT NULL,
    street_number Int NOT NULL,
    postal_code VARCHAR(5) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES END_USER(id)
);

CREATE TABLE Orders
(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_date date NOT NULL,
    state varchar(50) NOT NULL,
    total_price float NOT NULL,
    courier_id int NOT NULL,
    user_id INT(128) NOT NULL,
    courier VARCHAR(30) NOT NULL
);

CREATE TABLE User
(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username VARCHAR(15) NOT NULL UNIQUE ,
    password varchar(15) NOT NULL
);

CREATE TABLE manages
(
    user_id int NOT NULL,
    order_id int NOT NULL,
    PRIMARY KEY(user_id,order_id),
    delivery_date date NOT NULL,
    tracking_number int NOT NULL,
    shipment_date date NOT NULL,

    FOREIGN KEY (user_id) REFERENCES User(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(order_id) REFERENCES Orders(id)
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
    ISBN VARCHAR(13) NOT NULL,
    book_binding VARCHAR(30) NOT NULL,
    volume VARCHAR(20) NOT NULL,
    release_date date NOT NULL,
    page_number int NOT NULL,
    interior VARCHAR(20) NOT NULL,
    lang VARCHAR(20) NOT NULL,
    image VARCHAR(300) NOT NULL
);


CREATE TABLE Author
(
    id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    role VARCHAR(20) NOT NULL,
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


CREATE TABLE HasProductP
(
    product_id int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY(product_id,user_id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (user_id) REFERENCES END_USER(user_id)
        ON UPDATE cascade ON DELETE cascade
);


CREATE TABLE HasProductM
(
    manga_id int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY(manga_id,user_id),
    FOREIGN KEY (manga_id) REFERENCES Manga(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (user_id) REFERENCES END_USER(user_id)
        ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE TO_MANAGE
(
    user_id int NOT NULL,
    order_id int NOT NULL,

    PRIMARY KEY (user_id,order_id),

    FOREIGN KEY (user_id) REFERENCES User(id)
        ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(order_id) REFERENCES Orders(id)
        ON UPDATE cascade ON DELETE cascade
);



INSERT INTO MANGAUP.END_USER (id, email, name, surname, password, phone_number, birth_date)
VALUES (1, 'toms@hotmail.it', 'tom', 'sirr', 'napoli123', '3662968496', '1970-01-15');




