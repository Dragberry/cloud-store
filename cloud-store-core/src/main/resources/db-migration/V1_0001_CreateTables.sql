USE TEST;
DROP DATABASE CLOUDSTORE;
CREATE DATABASE CLOUDSTORE;
USE CLOUDSTORE;

CREATE TABLE customer (
	id BIGINT AUTO_INCREMENT NOT NULL,
	customer_name VARCHAR(64),
	password VARCHAR(64),
	PRIMARY KEY (id)
) ENGINE=INNODB;

INSERT INTO customer (id, customer_name, password) VALUES (1, 'admin', 'password');
INSERT INTO customer (id, customer_name, password) VALUES (2, 'user', 'password');

CREATE TABLE role (
	id BIGINT AUTO_INCREMENT NOT NULL,
	role_name VARCHAR(64),
	PRIMARY KEY (id)
) ENGINE=INNODB;

INSERT INTO role (id, role_name) VALUES (1, 'admin');
INSERT INTO role (id, role_name) VALUES (2, 'customer');
INSERT INTO role (id, role_name) VALUES (3, 'guest');

CREATE TABLE customer_role (
	customer_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	CONSTRAINT FK_CUSTOMER_ROLE FOREIGN KEY (customer_id) REFERENCES customer (id),
	CONSTRAINT FK_ROLE_CUSTOMER FOREIGN KEY (role_id) REFERENCES role (id)
) ENGINE=INNODB;

INSERT INTO customer_role (customer_id, role_id) VALUES (1, 1);
INSERT INTO customer_role (customer_id, role_id) VALUES (2, 2);

CREATE TABLE image (
    id BIGINT AUTO_INCREMENT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    alt VARCHAR(255),
    content_type VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
) ENGINE=INNODB;

CREATE TABLE dashboard_item (
	id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(512),
	text VARCHAR(1024),
	image_id BIGINT,
	PRIMARY KEY (id),
	CONSTRAINT FK_IMAGE_DASHBOARD FOREIGN KEY (image_id) REFERENCES image (id)
) ENGINE=INNODB;

CREATE TABLE product (
	id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(255),
	description VARCHAR(512),
	full_description VARCHAR(1024),
	cost NUMERIC(10,2),
	main_image_id BIGINT,
	PRIMARY KEY (id),
	CONSTRAINT FK_MAIN_IMAGE_PRODUCT FOREIGN KEY (main_image_id) REFERENCES image (id)
) ENGINE=INNODB;

CREATE TABLE category (
	id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(255),
    code VARCHAR(32) NOT NULL,
	parent_id BIGINT,
	PRIMARY KEY (id),
	UNIQUE KEY category_code (code)
) ENGINE=INNODB;

CREATE TABLE category_product (
	category_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	CONSTRAINT FK_CATEGORY_PRODUCT FOREIGN KEY (category_id) REFERENCES category (id),
	CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE=INNODB;

INSERT INTO dashboard_item (id, title, text) VALUES (1, 'Ищите оригинальные способы признания в любви?', '{CSapplicationTitleFull} поможет Вам сделать удивительное признание для своей второй половинки!');
INSERT INTO dashboard_item (id, title, text) VALUES (2, 'Хотите весело и необычно отпраздновать важное событие в вашей жизни?', '{CSapplicationTitleFull} наполнит Ваш праздник незабываемыми моментами и неповторимыми эмоциями!');

INSERT INTO product (id, title, description, full_description, cost) VALUES (1, 'Latex balloon green', 'Description Latex balloon green', 'Full description Latex balloon green', '1000.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (2, 'Metalic balloon white', 'Description Metalic balloon white', 'Full description Metalic balloon white','2500.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (3, 'Ligtn balloon red', 'Light red ballon Description', 'Full description Light red baloon', '1250.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (4, 'Hear with Heart', 'wool hear with heart', 'wool hear with heart', '80000.00');
INSERT INTO product (id, title, description, full_description, cost) VALUES (5, 'Turtle', 'Wool turtle', 'Wool turtle', '50000.00');


INSERT INTO category (id, title, code, parent_id) VALUES (1, 'Воздушные шары', 'balloons', NULL);
INSERT INTO category (id, title, code, parent_id) VALUES (2, 'Ligth Balloons', 'lights-balloons', 1);
INSERT INTO category (id, title, code, parent_id) VALUES (3, 'Latex Balloons','latex-balloons', 1);
INSERT INTO category (id, title, code, parent_id) VALUES (4, 'Metalic Balloons','metalic-balloons', 1);
INSERT INTO category (id, title, code, parent_id) VALUES (5, 'Игрушки из шерсти', 'wool-toys', NULL);
INSERT INTO category (id, title, code, parent_id) VALUES (6, 'Metallic Balloons Round', 'round-metallic-balloons', 4);
INSERT INTO category (id, title, code, parent_id) VALUES (7, 'Metallic Balloons Figured', 'figured-metallic-balloons', 4);

INSERT INTO category_product (category_id, product_id) VALUES (1, 3);
INSERT INTO category_product (category_id, product_id) VALUES (2, 3);
INSERT INTO category_product (category_id, product_id) VALUES (1, 2);
INSERT INTO category_product (category_id, product_id) VALUES (4, 2);
INSERT INTO category_product (category_id, product_id) VALUES (6, 2);
INSERT INTO category_product (category_id, product_id) VALUES (1, 1);
INSERT INTO category_product (category_id, product_id) VALUES (3, 1);
INSERT INTO category_product (category_id, product_id) VALUES (5, 4);
INSERT INTO category_product (category_id, product_id) VALUES (5, 5);
INSERT INTO category_product (category_id, product_id) VALUES (7, 5);

SELECT r.role_name, 'Roles' FROM role r, customer_role cr, customer c WHERE
            c.customer_name='admin' AND c.ID=cr.customer_id AND cr.role_id=r.ID