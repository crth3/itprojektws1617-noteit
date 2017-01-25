-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema noteit-db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema noteit-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `noteit-db` DEFAULT CHARACTER SET utf8 ;
USE `noteit-db` ;

-- -----------------------------------------------------
-- Table `noteit-db`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `noteit-db`.`User` (
  `userId` INT(11) NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NULL DEFAULT NULL,
  `lastName` VARCHAR(45) NULL DEFAULT NULL,
  `emailAddress` VARCHAR(45) NULL DEFAULT NULL,
  `creationDate` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `noteit-db`.`Notebook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `noteit-db`.`Notebook` (
  `notebookId` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `creationDate` TIMESTAMP NULL DEFAULT NULL,
  `modificationDate` TIMESTAMP NULL DEFAULT NULL,
  `User_userId` INT(11) NOT NULL,
  PRIMARY KEY (`notebookId`),
  INDEX `fk_Notebook_User1_idx` (`User_userId` ASC),
  CONSTRAINT `fk_Notebook_User1`
    FOREIGN KEY (`User_userId`)
    REFERENCES `noteit-db`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 72
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `noteit-db`.`Note`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `noteit-db`.`Note` (
  `noteId` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `subtitle` VARCHAR(45) NULL DEFAULT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `maturity` TIMESTAMP NULL DEFAULT NULL,
  `creationDate` TIMESTAMP NULL DEFAULT NULL,
  `modificationDate` TIMESTAMP NULL DEFAULT NULL,
  `Notebook_notebookId` INT(11) NOT NULL,
  `User_userId` INT(11) NOT NULL,
  PRIMARY KEY (`noteId`),
  INDEX `fk_Note_Notebook` (`Notebook_notebookId` ASC),
  INDEX `fk_Note_User1_idx` (`User_userId` ASC),
  CONSTRAINT `fk_Note_Notebook`
    FOREIGN KEY (`Notebook_notebookId`)
    REFERENCES `noteit-db`.`Notebook` (`notebookId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Note_User1`
    FOREIGN KEY (`User_userId`)
    REFERENCES `noteit-db`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 65
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `noteit-db`.`NotePermission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `noteit-db`.`NotePermission` (
  `notePermissionId` INT(11) NOT NULL AUTO_INCREMENT,
  `permission` INT(11) NULL DEFAULT NULL,
  `Note_noteId` INT(11) NOT NULL,
  `User_userId` INT(11) NOT NULL,
  PRIMARY KEY (`notePermissionId`),
  INDEX `fk_NotePermission_Note` (`Note_noteId` ASC),
  INDEX `fk_NotePermission_User` (`User_userId` ASC),
  CONSTRAINT `fk_NotePermission_Note1`
    FOREIGN KEY (`Note_noteId`)
    REFERENCES `noteit-db`.`Note` (`noteId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotePermission_User1`
    FOREIGN KEY (`User_userId`)
    REFERENCES `noteit-db`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 40
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `noteit-db`.`NotebookPermission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `noteit-db`.`NotebookPermission` (
  `notebookPermissionId` INT(11) NOT NULL AUTO_INCREMENT,
  `permission` INT(11) NULL DEFAULT NULL,
  `User_userId` INT(11) NOT NULL,
  `Notebook_notebookId` INT(11) NOT NULL,
  PRIMARY KEY (`notebookPermissionId`),
  UNIQUE INDEX `notebookPermissionId_UNIQUE` (`notebookPermissionId` ASC),
  INDEX `fk_NotebookPermission_User` (`User_userId` ASC),
  INDEX `fk_NotebookPermission_Notebook` (`Notebook_notebookId` ASC),
  CONSTRAINT `fk_NotebookPermission_Notebook1`
    FOREIGN KEY (`Notebook_notebookId`)
    REFERENCES `noteit-db`.`Notebook` (`notebookId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotebookPermission_User1`
    FOREIGN KEY (`User_userId`)
    REFERENCES `noteit-db`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
