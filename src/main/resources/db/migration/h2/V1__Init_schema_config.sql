-- SET MODE MYSQL;
-- CREATE USER IF NOT EXISTS 'paypalm'@'%' IDENTIFIED BY 'paypalm';
-- GRANT ALL PRIVILEGES ON paypalm.* TO 'paypalm'@'%' WITH GRANT OPTION;
-- DROP DATABASE IF EXISTS paypalm;
-- CREATE DATABASE IF NOT EXISTS paypalm DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- USE paypalm;

DROP TABLE IF EXISTS `user_settings`;
CREATE TABLE `user_settings` (
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(128) DEFAULT NULL,
  last_name VARCHAR(128) DEFAULT NULL,
  email VARCHAR(128) DEFAULT NULL,
  paypal_client_id VARCHAR(128) DEFAULT NULL,
  paypal_secret VARCHAR(128) DEFAULT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_EMAIL` (`email`),
  UNIQUE KEY `USER_PAYPAL_CLIENT_ID` (`paypal_client_id`),
  UNIQUE KEY `USER_PAYPAL_SECRET_PHRASE` (`paypal_secret`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_settings` VALUES (1, 'Bender', 'Rodriguez', 'bender-rodriguez@example.com', '<your_paypal_client_id>', '<your_paypal_secret_phrase>');

DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token` (
  id INT NOT NULL AUTO_INCREMENT,
  creation_date DATETIME DEFAULT NOW(),
  expires_in INT DEFAULT 0,
  token_type VARCHAR(128) DEFAULT NULL,
  access_token VARCHAR(256) DEFAULT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_ACCESS_TOKEN` (`access_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  id INT NOT NULL AUTO_INCREMENT,
  settings_id INT DEFAULT NULL,
  access_token_id INT DEFAULT NULL,
  name VARCHAR(30) NOT NULL,
  password VARCHAR(128),

  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_SETTINGS_ID` (`settings_id`),
  UNIQUE KEY `USER_ACCESS_TOKEN_ID` (`access_token_id`),
  UNIQUE KEY `USER_NAME_KEY` (`name`),
  KEY `FK_USER_SETTINGS` (`settings_id`),
  KEY `FK_USER_ACCESS_TOKEN` (`access_token_id`),
  CONSTRAINT `FK_USER_SETTINGS` FOREIGN KEY (`settings_id`) REFERENCES `user_settings` (`id`),
  CONSTRAINT `FK_USER_ACCESS_TOKEN` FOREIGN KEY (`access_token_id`) REFERENCES `access_token` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES (1, 1, NULL, 'demo', '$2a$10$74m5zZPcMtLhNwNs13zrS.xVS80bmnrRQlk5VtqwghBGCvP974shK');

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `ROLE_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `role` VALUES (2, 'ROLE_USER');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  PRIMARY KEY (`user_id`, `role_id`),
  CONSTRAINT `FK_ROLE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 2);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT DEFAULT NULL,
  paypal_id VARCHAR(128) DEFAULT NULL,
  creation_date DATETIME DEFAULT NOW(),
  card_number VARCHAR(100) DEFAULT NULL,
  card_type VARCHAR(50) DEFAULT NULL,
  amount DECIMAL (7,2) DEFAULT 0.00,
  status VARCHAR(20) DEFAULT NULL,
  link VARCHAR(256) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_USER_ID` (`user_id`),
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) DEFAULT NULL,
  description VARCHAR(256) DEFAULT NULL,
  price DECIMAL (7,2) DEFAULT 0.00,

  PRIMARY KEY (`id`),
  UNIQUE KEY `PRODUCT_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `product` VALUES
  (NULL, 'Beer', 'Product description #1', 8.50),
  (NULL, 'Cigar', 'Product description #2', 1.80),
  (NULL, 'Suicide booth services', 'Product description #3', 0.23),
  (NULL, 'One way ticket', 'Product description #4', 66.6),
  (NULL, 'Ice cream', 'Product description #5', 1.00);

DROP TABLE IF EXISTS `order_products`;
CREATE TABLE `order_products` (
  order_id INT NOT NULL,
  product_id INT NOT NULL,

  PRIMARY KEY (`order_id`, `product_id`),
  KEY `FK_ORDER_ID` (`order_id`),
  KEY `FK_PRODUCT_ID` (`product_id`),
  CONSTRAINT `FK_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FK_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
