drop schema MangaUp;
create schema MangaUp;
use MangaUp;

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
    state VARCHAR(5) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type_of_product VARCHAR(30) NOT NULL
);



CREATE TABLE Collection
(
	title VARCHAR(15) PRIMARY KEY NOT NULL,
    description VARCHAR(150)
);

CREATE TABLE Credit_Card
(
	number_ VARCHAR(20) PRIMARY KEY NOT NULL,
    cvc VARCHAR(3) NOT NULL,
    nome VARCHAR(15) NOT NULL,
    cognome VARCHAR(15) NOT NULL,
    data_scadenza date NOT NULL
);

CREATE TABLE Address
(
    id  INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    country VARCHAR(64) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(100) NOT NULL,
    street_number Int NOT NULL,
    postal_code VARCHAR(5) NOT NULL
);

CREATE TABLE EndUser
(
	email Varchar(128) PRIMARY KEY NOT NULL,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(128) NOT NULL,
    password VARCHAR(15) NOT NULL,
    number VARCHAR(10) NOT NULL,
    birth_date date NOT NULL,
    add_id int,
    card_number varchar(20),
	FOREIGN KEY (add_id) REFERENCES Address(id)
    ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (card_number) REFERENCES Credit_Card(number_) 
    ON UPDATE cascade ON DELETE cascade

);

CREATE TABLE Courier
(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(20) NOT NULL
);

CREATE TABLE Orders
(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	order_date date NOT NULL,
    state varchar(50) NOT NULL,
    total_price float NOT NULL,
    courier_id int NOT NULL,
    email VARCHAR(128) NOT NULL,
    FOREIGN KEY(courier_id) REFERENCES COURIER(id),
    FOREIGN KEY(email) REFERENCES EndUser(email)
);


CREATE TABLE User
(
	username VARCHAR(15) PRIMARY KEY NOT NULL,
    password varchar(15) NOT NULL
);

CREATE TABLE Catalog_Manager
(
	username VARCHAR(15) PRIMARY KEY NOT NULL,
	FOREIGN KEY (username) REFERENCES USER(username)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Order_Manager
(
	username VARCHAR(15) PRIMARY KEY NOT NULL,
	FOREIGN KEY (username) REFERENCES USER(username)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE User_Manager
(
	username VARCHAR(15) PRIMARY KEY NOT NULL,
	FOREIGN KEY (username) REFERENCES USER(username)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE manages
(
	order_manager_username VARCHAR(15) NOT NULL,
    order_id int NOT NULL,
    PRIMARY KEY(order_manager_username,order_id),
    delivery_date date NOT NULL,
    tracking_number int NOT NULL,
    shipment_date date NOT NULL,
    
    FOREIGN KEY (order_manager_username) REFERENCES Order_manager(username)
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
    ISBN VARCHAR(64) NOT NULL,
    book_binding VARCHAR(30) NOT NULL,
    volume VARCHAR(50) NOT NULL,
    release_date date NOT NULL,
    page_number int NOT NULL,
    interior VARCHAR(20) NOT NULL,
    lang VARCHAR(20) NOT NULL
);


CREATE TABLE Author
(
	id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    role VARCHAR(20) NOT NULL
);


CREATE TABLE Cart_Element
(
	email VARCHAR(128) NOT NULL,
    product_id int NOT NULL,
    PRIMARY KEY(email,product_id),
    FOREIGN KEY (email) REFERENCES EndUser(email)
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

CREATE TABLE WareHouse
(
	name VARCHAR(20) PRIMARY KEY NOT NULL,
    description VARCHAR(128),
    add_id int,
    FOREIGN KEY (add_id) REFERENCES Address(id)
    ON UPDATE cascade ON DELETE cascade
);


CREATE TABLE Situated
(
	product_id int NOT NULL,
    warehouse_id VARCHAR(20) NOT NULL,
    PRIMARY KEY(product_id,warehouse_id),
    quantity int NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Product(id)
    ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (warehouse_id) REFERENCES WareHouse(name)
    ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE HasCollection
(
	product_id int NOT NULL,
    collection_id VARCHAR(15) NOT NULL,
    PRIMARY KEY(product_id,collection_id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
    ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY (collection_id) REFERENCES Collection(title)
    ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE HasProducts
(
	order_id int PRIMARY KEY NOT NULL,
	product_id int NOT NULL,
    quantity int NOT NULL,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    state VARCHAR(5) NOT NULL,
    typeOfProduct VARCHAR(20) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id)
    ON UPDATE cascade ON DELETE cascade

);

CREATE TABLE IF NOT EXISTS ROLES
(
    username VARCHAR(15) NOT NULL,
    role VARCHAR(2)	NOT NULL,
    PRIMARY KEY(username, role),
    FOREIGN KEY(username) REFERENCES USER(username)
);




