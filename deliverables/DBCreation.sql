DROP SCHEMA IF EXISTS MANGA_UP;
CREATE SCHEMA MANGA_UP;
USE MANGA_UP;

CREATE TABLE product
(
    prd_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    prd_name VARCHAR(50) NOT NULL,
    prd_brand VARCHAR(50) NOT NULL,
    prd_price DOUBLE NOT NULL,
    prd_weight DOUBLE NOT NULL,
    prd_height DOUBLE NOT NULL,
    prd_lenght DOUBLE NOT NULL,
    prd_state VARCHAR(20) NOT NULL,
    prd_description VARCHAR(255) NOT NULL,
    prd_type_of_product VARCHAR(30) NOT NULL,
    prd_quantity INT NOT NULL,
    prd_image VARCHAR(300) NOT NULL
);

CREATE TABLE manga
(
    man_id INT auto_increment KEY NOT NULL,
    man_name VARCHAR(50) NOT NULL,
    man_brand VARCHAR(50) NOT NULL,
    man_price DOUBLE NOT NULL,
    man_weight DOUBLE NOT NULL,
    man_height DOUBLE NOT NULL,
    man_lenght DOUBLE NOT NULL,
    man_state VARCHAR(5) NOT NULL,
    man_description VARCHAR(255) NOT NULL,
    man_collections VARCHAR(50) NOT NULL,
    man_quantity INT NOT NULL,
    man_isbn VARCHAR(64) NOT NULL,
    man_book_binding VARCHAR(30) NOT NULL,
    man_volume VARCHAR(50) NOT NULL,
    man_release_date DATE NOT NULL,
    man_page_number INT NOT NULL,
    man_interior VARCHAR(20) NOT NULL,
    man_lang VARCHAR(20) NOT NULL,
    man_image VARCHAR(300) NOT NULL
);

CREATE TABLE genre
(
    gen_name VARCHAR(255) PRIMARY KEY NOT NULL
);

CREATE TABLE has_genre
(
    manga_id INT NOT NULL,
    genre_id VARCHAR(255) NOT NULL,
    PRIMARY KEY(manga_id,genre_id),
    FOREIGN KEY (manga_id) REFERENCES manga(man_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre(gen_name)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE author
(
    aut_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    aut_name VARCHAR(30) NOT NULL,
    aut_role VARCHAR(20) NOT NULL
);

CREATE TABLE has_author
(
    manga_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY(manga_id,author_id),
    FOREIGN KEY (manga_id) REFERENCES manga(man_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author(aut_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);
