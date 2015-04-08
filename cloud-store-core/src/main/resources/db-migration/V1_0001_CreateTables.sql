USE TEST;
DROP DATABASE CLOUDSTORE;
CREATE DATABASE CLOUDSTORE;
USE CLOUDSTORE;

CREATE TABLE image (
    id BIGINT AUTO_INCREMENT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    CONTENT LONGBLOB,
    PRIMARY KEY(id)
) ENGINE=INNODB;

CREATE TABLE product (
	id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(255),
	description VARCHAR(512),
	full_description VARCHAR(1024),
	cost NUMERIC(10,2),
	main_image_id BIGINT,
	PRIMARY KEY (id),
	CONSTRAINT FK_PRODUCT_MAIN_IMAGE FOREIGN KEY (main_image_id) REFERENCES image (id)
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

INSERT INTO product (id, title, description, full_description, cost) VALUES (1, 'Latex balloon green', 'Description Latex balloon green', 'Full description Latex balloon green', '1000.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (2, 'Metalic balloon white', 'Description Metalic balloon white', 'Full description Metalic balloon white','2500.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (3, 'Ligtn balloon red', 'Light red ballon Description', 'Full description Light red baloon', '1250.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (4, 'Hear with Heart', 'wool hear with heart', 'wool hear with heart', '80000.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (5, 'Turtle', 'Wool turtle', 'Wool turtle', '50000.00');


INSERT INTO category (id, title, parent_id) VALUES (1, 'Balloons', NULL);
INSERT INTO category (id, title, parent_id) VALUES (2, 'Ligth Balloons', 1);
INSERT INTO category (id, title, parent_id) VALUES (3, 'Latex Balloons', 1);
INSERT INTO category (id, title, parent_id) VALUES (4, 'Metalic Balloons', 1);
INSERT INTO category (id, title, parent_id) VALUES (5, 'Wool toys', NULL);
INSERT INTO category (id, title, parent_id) VALUES (6, 'Metallic Balloons Round', 4);
INSERT INTO category (id, title, parent_id) VALUES (7, 'Metallic Balloons Figured', 4);

INSERT INTO category_product (category_id, product_id) VALUES (1, 3);
INSERT INTO category_product (category_id, product_id) VALUES (2, 3);
INSERT INTO category_product (category_id, product_id) VALUES (1, 2);
INSERT INTO category_product (category_id, product_id) VALUES (4, 2);
INSERT INTO category_product (category_id, product_id) VALUES (6, 2);
INSERT INTO category_product (category_id, product_id) VALUES (1, 1);
INSERT INTO category_product (category_id, product_id) VALUES (3, 1);
INSERT INTO category_product (category_id, product_id) VALUES (5, 4);
INSERT INTO category_product (category_id, product_id) VALUES (5, 5);


