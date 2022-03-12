CREATE DATABASE IEC_CRUD;

CREATE TABLE category (
    id INT PRIMARY KEY auto_increment,
    name VARCHAR(100)
);

CREATE TABLE product (
    id INT PRIMARY KEY auto_increment,
    name VARCHAR(100),
    price DECIMAL(10, 2),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
