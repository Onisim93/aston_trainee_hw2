DROP TABLE IF EXISTS book_genres;
DROP TABLE IF EXISTS author_books;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS authors;

-- Создание таблицы authors
CREATE TABLE IF NOT EXISTS authors
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    bio         VARCHAR(1024)
);

-- Создание таблицы genres
CREATE TABLE IF NOT EXISTS genres
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1024)
);

-- Создание таблицы books
CREATE TABLE IF NOT EXISTS books
(
    id             SERIAL PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    description    VARCHAR(1024) NOT NULL,
    published_date VARCHAR(255) NOT NULL,
    isbn           VARCHAR(30) UNIQUE,
    author_id      INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

-- Создание таблицы book_genres для связи ManyToMany между books и genres
CREATE TABLE IF NOT EXISTS book_genres
(
    book_id  INT,
    genre_id INT,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

-- Создание таблицы author_books для связи ManyToMany между authors и books
CREATE TABLE IF NOT EXISTS author_books
(
    author_id INT,
    book_id   INT,
    PRIMARY KEY (author_id, book_id),
    FOREIGN KEY (author_id) REFERENCES authors (id),
    FOREIGN KEY (book_id) REFERENCES books (id)
);