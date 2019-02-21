-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for rover
CREATE DATABASE IF NOT EXISTS `rover` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rover`;

-- Dumping structure for table rover.sitter_review
CREATE TABLE IF NOT EXISTS `sitter_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dogs` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `sitter_id` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `text` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrhackgl7nghug359te37bd4ec` (`sitter_id`),
  CONSTRAINT `FKrhackgl7nghug359te37bd4ec` FOREIGN KEY (`sitter_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rover.sitter_review_event
CREATE TABLE IF NOT EXISTS `sitter_review_event` (
  `event_type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `handled` bit(1) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `sitter_id` int(11) DEFAULT NULL,
  `sitter_review_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rover.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `average_stay_rating` double DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `sitter_rank` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;



-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for rover
CREATE DATABASE IF NOT EXISTS `rover_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rover_test`;

-- Dumping structure for table rover.sitter_review
CREATE TABLE IF NOT EXISTS `sitter_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dogs` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `sitter_id` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `text` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrhackgl7nghug359te37bd4ec` (`sitter_id`),
  CONSTRAINT `FKrhackgl7nghug359te37bd4ec` FOREIGN KEY (`sitter_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rover.sitter_review_event
CREATE TABLE IF NOT EXISTS `sitter_review_event` (
  `event_type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `handled` bit(1) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `sitter_id` int(11) DEFAULT NULL,
  `sitter_review_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table rover.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `average_stay_rating` double DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `sitter_rank` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
