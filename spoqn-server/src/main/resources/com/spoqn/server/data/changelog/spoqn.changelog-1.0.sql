--liquibase formatted sql

--changeset bmannon:1
-- -----------------------------------------------------
-- Table `content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `content` (
  `content_id` INT(11) NOT NULL AUTO_INCREMENT,
  `content_uuid` VARCHAR(36) NOT NULL,
  `mime_type` VARCHAR(255) NOT NULL,
  `body` BLOB NOT NULL,
  PRIMARY KEY (`content_id`),
  UNIQUE INDEX `content_id_UNIQUE` (`content_id` ASC),
  UNIQUE INDEX `content_uuid_UNIQUE` (`content_uuid` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `content`;

--changeset bmannon:2
-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` VARCHAR(36) NULL,
  `login_id` VARCHAR(45) NOT NULL,
  `display_name` VARCHAR(100) NULL DEFAULT NULL,
  `create_date` DATE NOT NULL,
  `profile_content_id` INT(11) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_id_UNIQUE` (`login_id` ASC),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  INDEX `fk_profile_content_id_idx` (`profile_content_id` ASC),
  CONSTRAINT `fk_user_profile_content_id`
    FOREIGN KEY (`profile_content_id`)
    REFERENCES `content` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1
--rollback DROP TABLE `user`;

--changeset bmannon:3
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
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `device`;

--changeset bmannon:4
-- -----------------------------------------------------
-- Table `room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `room` (
  `room_id` INT(11) NOT NULL AUTO_INCREMENT,
  `room_name` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  PRIMARY KEY (`room_id`),
  UNIQUE INDEX `room_id_UNIQUE` (`room_id` ASC),
  UNIQUE INDEX `room_name_UNIQUE` (`room_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `room`;

--changeset bmannon:5
-- -----------------------------------------------------
-- Table `topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `topic` (
  `topic_id` INT(11) NOT NULL AUTO_INCREMENT,
  `topic_url` VARCHAR(2000) NOT NULL,
  PRIMARY KEY (`topic_id`),
  UNIQUE INDEX `topic_id_UNIQUE` (`topic_id` ASC),
  UNIQUE INDEX `topic_url_UNIQUE` (`topic_url` ASC))
ENGINE = InnoDB;
--rollback DROP TABLE `topic`;

--changeset bmannon:6
-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `message` (
  `message_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `room_id` INT(11) NULL DEFAULT NULL,
  `topic_id` INT(11) NULL,
  `message_uuid` VARCHAR(36) NOT NULL,
  `message_text` VARCHAR(2000) NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`message_id`, `user_id`),
  UNIQUE INDEX `message_id_UNIQUE` (`message_id` ASC),
  UNIQUE INDEX `message_uuid_UNIQUE` (`message_uuid` ASC),
  INDEX `fk_user_id_idx` (`user_id` ASC),
  INDEX `fk_room_id_idx` (`room_id` ASC),
  INDEX `fk_topic_id_idx` (`topic_id` ASC),
  CONSTRAINT `fk_message_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_topic_id`
    FOREIGN KEY (`topic_id`)
    REFERENCES `topic` (`topic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = latin1
--rollback DROP TABLE `message`;

--changeset bmannon:7
-- -----------------------------------------------------
-- Table `message_recipient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `message_recipient` (
  `recipient_user_id` INT(11) NOT NULL,
  `message_id` INT(11) NOT NULL,
  `is_read` TINYINT(1) NULL DEFAULT NULL,
  INDEX `fk_recipient_user_id_idx` (`recipient_user_id` ASC),
  INDEX `fk_message_id_idx` (`message_id` ASC),
  PRIMARY KEY (`recipient_user_id`, `message_id`),
  CONSTRAINT `fk_message_recipient_message_id`
    FOREIGN KEY (`message_id`)
    REFERENCES `message` (`message_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_recipient_recipient_user_id`
    FOREIGN KEY (`recipient_user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `message_recipient`;

--changeset bmannon:8
-- -----------------------------------------------------
-- Table `password`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `password` (
  `user_id` INT(11) NOT NULL,
  `pass_hash` CHAR(60) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_password_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `password`;

--changeset bmannon:9
-- -----------------------------------------------------
-- Table `token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `token` (
  `device_id` INT(11) NOT NULL,
  `token_hash` CHAR(60) NOT NULL,
  PRIMARY KEY (`device_id`),
  INDEX `fk_device_id_idx` (`device_id` ASC),
  CONSTRAINT `fk_token_device_id`
    FOREIGN KEY (`device_id`)
    REFERENCES `device` (`device_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `token`;

--changeset bmannon:10
-- -----------------------------------------------------
-- Table `user_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_room` (
  `user_id` INT(11) NOT NULL,
  `room_id` INT(11) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `is_active` TINYINT(1) NOT NULL,
  INDEX `fk_user_id_idx` (`user_id` ASC),
  INDEX `fk_room_id_idx` (`room_id` ASC),
  PRIMARY KEY (`user_id`, `room_id`),
  CONSTRAINT `fk_user_room_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_room_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `user_room`;

--changeset bmannon:11
-- -----------------------------------------------------
-- Table `message_attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `message_attachment` (
  `message_id` INT(11) NOT NULL,
  `content_id` INT(11) NOT NULL,
  PRIMARY KEY (`message_id`, `content_id`),
  INDEX `fk_content_id_idx` (`content_id` ASC),
  INDEX `fk_message_id_idx` (`message_id` ASC),
  CONSTRAINT `fk_message_attachment_message_id`
    FOREIGN KEY (`message_id`)
    REFERENCES `message` (`message_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_attachment_content_id`
    FOREIGN KEY (`content_id`)
    REFERENCES `content` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `message_attachment`;

--changeset bmannon:12
-- -----------------------------------------------------
-- Table `room_topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `room_topic` (
  `room_id` INT(11) NOT NULL,
  `topic_id` INT(11) NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`create_date`, `topic_id`, `room_id`),
  INDEX `fk_topic_id_idx` (`topic_id` ASC),
  INDEX `fk_room_id_idx` (`room_id` ASC),
  CONSTRAINT `fk_room_topic_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_topic_topic_id`
    FOREIGN KEY (`topic_id`)
    REFERENCES `topic` (`topic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--rollback DROP TABLE `room_topic`;

--changeset bmannon:13
-- -----------------------------------------------------
-- Table `tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tag` (
  `tag_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE INDEX `tag_id_UNIQUE` (`tag_id` ASC),
  UNIQUE INDEX `tag_name_UNIQUE` (`tag_name` ASC))
ENGINE = InnoDB;
--rollback DROP TABLE `tag`;

--changeset bmannon:14
-- -----------------------------------------------------
-- Table `topic_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `topic_tag` (
  `topic_id` INT(11) NOT NULL,
  `tag_id` INT(11) NOT NULL,
  PRIMARY KEY (`topic_id`, `tag_id`),
  INDEX `fk_tag_id_idx` (`tag_id` ASC),
  INDEX `fk_topic_id_idx` (`topic_id` ASC),
  CONSTRAINT `fk_topic_tag_topic_id`
    FOREIGN KEY (`topic_id`)
    REFERENCES `topic` (`topic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topic_tag_tag_id`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tag` (`tag_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
--rollback DROP TABLE `topic_tag`;
