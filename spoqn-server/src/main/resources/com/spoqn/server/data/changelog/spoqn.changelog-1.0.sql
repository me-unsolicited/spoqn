--liquibase formatted sql

--changeset mvera:1
-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `login_id` VARCHAR(45) NOT NULL,
  `display_name` VARCHAR(100) NULL DEFAULT NULL,
  `create_date` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_id_UNIQUE` (`login_id` ASC),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table user;

--changeset mvera:2
-- -----------------------------------------------------
-- Table `device`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `device` (
  `device_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `device_hash` VARCHAR(64) NOT NULL,
  `device_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`device_id`, `user_id`),
  UNIQUE INDEX `device_id_UNIQUE` (`device_id` ASC),
  INDEX `fk_device_user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_device_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table device;

--changeset mvera:3
-- -----------------------------------------------------
-- Table `room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `room` (
  `room_id` INT(11) NOT NULL AUTO_INCREMENT,
  `room_name` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `is_active` TINYINT(4) NOT NULL,
  `room_topic` VARCHAR(100) NULL,
  `room_uuid` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`room_id`),
  UNIQUE INDEX `room_id_UNIQUE` (`room_id` ASC),
  UNIQUE INDEX `room_uuid_UNIQUE` (`room_uuid` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table room;

--changeset mvera:4
-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `message` (
  `message_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `content` BLOB NOT NULL,
  `create_date` DATETIME NOT NULL,
  `room_id` INT(11) NULL DEFAULT NULL,
  `message_uuid` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`message_id`, `user_id`),
  UNIQUE INDEX `message_id_UNIQUE` (`message_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `room_id_idx` (`room_id` ASC),
  UNIQUE INDEX `message_uuid_UNIQUE` (`message_uuid` ASC),
  CONSTRAINT `fk_mes_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mes_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table message;

--changeset mvera:5
-- -----------------------------------------------------
-- Table `message_recipient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `message_recipient` (
  `mes_rec_id` INT(11) NOT NULL AUTO_INCREMENT,
  `recipient_id` INT(11) NULL DEFAULT NULL,
  `message_id` INT(11) NULL DEFAULT NULL,
  `is_read` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`mes_rec_id`),
  UNIQUE INDEX `mes_rec_id_UNIQUE` (`mes_rec_id` ASC),
  INDEX `fk_recipient_id_idx` (`recipient_id` ASC),
  INDEX `fk_message_id_idx` (`message_id` ASC),
  CONSTRAINT `fk_message_id`
    FOREIGN KEY (`message_id`)
    REFERENCES `message` (`message_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_recipient_id`
    FOREIGN KEY (`recipient_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table message_recipient;

--changeset mvera:6
-- -----------------------------------------------------
-- Table `password`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `password` (
  `user_id` INT(11) NOT NULL,
  `pass_hash` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_pass_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table password;

--changeset mvera:7
-- -----------------------------------------------------
-- Table `token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `token` (
  `user_id` INT(11) NOT NULL,
  `device_id` INT(11) NOT NULL,
  `token_hash` VARCHAR(64) NOT NULL,
  `salt` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`user_id`, `device_id`),
  INDEX `fk_token_device_id_idx` (`device_id` ASC),
  CONSTRAINT `fk_token_device_id`
    FOREIGN KEY (`device_id`)
    REFERENCES `device` (`device_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_token_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table token;

--changeset mvera:8
-- -----------------------------------------------------
-- Table `user_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_room` (
  `user_room_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NULL DEFAULT NULL,
  `room_id` INT(11) NULL DEFAULT NULL,
  `is_active` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`user_room_id`),
  UNIQUE INDEX `user_room_id_UNIQUE` (`user_room_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `room_id_idx` (`room_id` ASC),
  CONSTRAINT `fk_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

--rollback drop table user_room;

--changeset bmannon:9
-- -----------------------------------------------------
-- Alter `password`, drop salt and change pass_hash type
-- -----------------------------------------------------
ALTER TABLE `password` 
DROP COLUMN `salt`,
CHANGE COLUMN `pass_hash` `pass_hash` CHAR(60) NOT NULL ;
--rollback ALTER TABLE `password` ADD COLUMN `salt` VARCHAR(8) NOT NULL, CHANGE COLUMN `pass_hash` `pass_hash` VARCHAR(45) NOT NULL;

--changeset bmannon:10
-- -----------------------------------------------------
-- Alter `token`, drop salt and change token_hash type
-- -----------------------------------------------------
ALTER TABLE `token` 
DROP COLUMN `salt`,
CHANGE COLUMN `token_hash` `token_hash` CHAR(60) NOT NULL ;
--rollback ALTER TABLE `token` ADD COLUMN `salt` VARCHAR(8) NOT NULL, CHANGE COLUMN `token_hash` `token

--changeset bmannon:11
-- -----------------------------------------------------
-- Alter `token`, drop foreign key to user
-- -----------------------------------------------------
ALTER TABLE `token` 
DROP FOREIGN KEY `fk_token_user_id`;
--rollback ALTER TABLE `token` ADD CONSTRAINT `fk_token_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--changeset bmannon:12
-- -----------------------------------------------------
-- Alter `token`, drop column user_id
-- -----------------------------------------------------
ALTER TABLE `token` 
DROP COLUMN `user_id`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`device_id`);
--rollback ALTER TABLE `token` ADD COLUMN `user_id` INT(11) NOT NULL FIRST, DROP PRIMARY KEY, ADD PRIMARY KEY (`user_id`, `device_id`)
