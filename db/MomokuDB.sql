-- MySQL Script generated by MySQL Workbench
-- Sat Jan  8 22:51:38 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema MomokuDB
-- -----------------------------------------------------
-- Momoku - Blind Test for the Weeb - Database

-- -----------------------------------------------------
-- Schema MomokuDB
--
-- Momoku - Blind Test for the Weeb - Database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MomokuDB` ;
USE `MomokuDB` ;

-- -----------------------------------------------------
-- Table `MomokuDB`.`Images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MomokuDB`.`Images` (
  `id` INT NOT NULL,
  `filename` VARCHAR(64) NOT NULL,
  `whoisthis` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Images and their data.';


-- -----------------------------------------------------
-- Table `MomokuDB`.`Rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MomokuDB`.`Rooms` (
  `id` INT NOT NULL,
  `title` VARCHAR(128) NOT NULL,
  `pass` VARCHAR(64) NULL,
  `owner` INT NOT NULL,
  `playing` TINYINT NOT NULL,
  `rounds` INT NOT NULL,
  `creation_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_idx` (`owner` ASC) VISIBLE,
  CONSTRAINT `fk_id`
    FOREIGN KEY (`owner`)
    REFERENCES `MomokuDB`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MomokuDB`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MomokuDB`.`Users` (
  `id` INT NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `current_room` INT NULL,
  `playing` TINYINT NOT NULL,
  `ready` TINYINT NULL,
  `creation_date` DATETIME NOT NULL,
  `games_won` INT NOT NULL,
  `current_score` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_idx` (`current_room` ASC) VISIBLE,
  CONSTRAINT `fk_id`
    FOREIGN KEY (`current_room`)
    REFERENCES `MomokuDB`.`Rooms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
