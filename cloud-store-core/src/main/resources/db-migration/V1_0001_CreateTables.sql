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
    description VARCHAR(512),
    full_description VARCHAR(1024),
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

INSERT INTO category (title, code, description, full_description, parent_id) VALUES ('Воздушные шары', 'balloons', 'Студия праздников HappyTime предлагает Вам огромный выбор воздушных шаров.', 'Устройте праздник для любимой, удивите любимого, порадуйте своих детей, подарите улыбку родным, доставьте удовольствие себе с шарами от студии праздников HappyTime. А состав студии поможет Вам в этом.', NULL);
INSERT INTO category (title, code, description, full_description, parent_id) VALUES ('Праздничные аксессуары', 'accessories',  'Аксессуары для праздников', 'У нас  Вы найдете множество отличных и незаменимых аксессуаров для любого праздника на любой вкус и цвет!', NULL);
INSERT INTO category (title, code, description, full_description, parent_id) VALUES ('Оригинальные подарки', 'original-gifts',  'Оригинальные подарки ручной работы', 'Ищите, что подарить оригинального и необыного на день рождения, новый год или другой праздник? В нашей студии Вы можете приобрести эксклюзивные подарки ручной работы и даже заказать его изготовление по вашей идее!', NULL);
INSERT INTO category (title, code, description, full_description, parent_id) VALUES ('Флористика', 'floristics',  'Букеты из цветов, бутоньерки и различные другие цветочные изделия.', 'В студии праздников HappyTime Вы можете заказать практические любое изделие из цветов, будь то простая бутоньерка на пиджак, или оригинальный и красивый букет для невесты.', NULL);

INSERT INTO category (title, code, description, parent_id) VALUES ('Круглые шары', 'round-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Светящиеся шары', 'light-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Шары для моделирования', 'modelling-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Букеты из шаров', 'bouquet-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Шары с надписью', 'inscription-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Свадебные шары', 'wedding-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Шары с гелием', 'helium-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Буквы и слова из шаров', 'letters-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Фольгированные шары', 'foil-balloons', '', 1);
INSERT INTO category (title, code, description, parent_id) VALUES ('Фигурные шары', 'figured-balloons', '', 1);


INSERT INTO category (title, code, description, parent_id) VALUES ('Новогодние аксессуары', 'new-year-accessories', '', 2);
INSERT INTO category (title, code, description, parent_id) VALUES ('Свадебные аксессуары ', 'wedding-accessories', '', 2);
INSERT INTO category (title, code, description, parent_id) VALUES ('Аксессуары для Дня Рождения и юбилея', 'birthday-accessories', '', 2);
INSERT INTO category (title, code, description, parent_id) VALUES ('Аксессуары на 8 марта', 'eight-march-accessories', '', 2);

INSERT INTO category (title, code, description, parent_id) VALUES ('Игрушки из шерсти', 'wool-toys', '', 3);
INSERT INTO category (title, code, description, parent_id) VALUES ('Сувениры ', 'souvenirs', '', 3);
INSERT INTO category (title, code, description, parent_id) VALUES ('Сувенирные упаковки', 'souvenir-packaging', '', 3);

INSERT INTO category (title, code, description, parent_id) VALUES ('Цветочные композиции', 'flower-arrangements', '', 4);
INSERT INTO category (title, code, description, parent_id) VALUES ('Букеты ', 'bouquets', '', 4);
INSERT INTO category (title, code, description, parent_id) VALUES ('Бутоньерки', 'boutonnieres', '', 4);
INSERT INTO category (title, code, description, parent_id) VALUES ('Свадебные букеты', 'wedding-bouquet', '', 4);
INSERT INTO category (title, code, description, parent_id) VALUES ('Венки', 'flower-circlets', '', 4);

SELECT r.role_name, 'Roles' FROM role r, customer_role cr, customer c WHERE
            c.customer_name='admin' AND c.ID=cr.customer_id AND cr.role_id=r.ID