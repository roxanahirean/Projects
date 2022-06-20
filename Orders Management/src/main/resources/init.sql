DROP DATABASE `somedb`;
CREATE SCHEMA `somedb`;
CREATE TABLE `somedb`.`client` (
    `id` INT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(64) NOT NULL,
     `address` VARCHAR(64) NOT NULL,
      PRIMARY KEY(`id`)
);