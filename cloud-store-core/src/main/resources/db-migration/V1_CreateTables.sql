USE TEST;
DROP DATABASE CLOUDSTORE;
CREATE DATABASE CLOUDSTORE;
USE CLOUDSTORE;

CREATE TABLE product (
	id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(255),
	description VARCHAR(512),
	full_description VARCHAR(1024),
	PRIMARY KEY (id)
) ENGINE=INNODB;

CREATE TABLE category (
	id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(255),
	parent_id BIGINT,
	PRIMARY KEY (id)
) ENGINE=INNODB;

CREATE TABLE category_product (
	category_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	CONSTRAINT FK_CATEGORY_PRODUCT FOREIGN KEY (category_id) REFERENCES category (id),
	CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE=INNODB;

INSERT INTO product (id, title, description, full_description) VALUES (1, 'productA', 'Description A', 'Full description A');
INSERT INTO product (id, title, description, full_description) VALUES (2, 'BproductB', 'B Description B', 'B Full description B');
INSERT INTO product (id, title, description, full_description) VALUES (3, 'Ligtn balloon red', 'Light red ballon', 'Light red baloon');

INSERT INTO category (id, title, parent_id) VALUES (1, 'Balloons', NULL);
INSERT INTO category (id, title, parent_id) VALUES (2, 'Ligth Balloons', 1);

INSERT INTO category_product (category_id, product_id) VALUES (1, 3);
INSERT INTO category_product (category_id, product_id) VALUES (2, 3);