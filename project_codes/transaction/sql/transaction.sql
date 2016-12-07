-- MySQL dump 10.10
--
-- Host: localhost    Database: transaction
-- ------------------------------------------------------
-- Server version	5.0.22-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_comment`
--

DROP DATABASE IF EXISTS `TRANSACTION`;
CREATE DATABASE `TRANSACTION`;
USE `TRANSACTION`;

DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `ID` int(10) NOT NULL auto_increment,
  `CM_TITLE` varchar(255) NOT NULL,
  `CM_CONTENT` text,
  `CM_DATE` varchar(255) default NULL,
  `USER_ID` int(10) NOT NULL,
  `TRANSACTION_ID` int(10) NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `USER_ID` (`USER_ID`),
  KEY `TRANSACTION_ID` (`TRANSACTION_ID`),
  CONSTRAINT `t_comment_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`),
  CONSTRAINT `t_comment_ibfk_2` FOREIGN KEY (`TRANSACTION_ID`) REFERENCES `t_transaction` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_comment`
--


/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
LOCK TABLES `t_comment` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;

--
-- Table structure for table `t_log`
--

DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `ID` int(10) NOT NULL auto_increment,
  `LOG_DATE` varchar(255) NOT NULL,
  `HANDLER_ID` int(10) NOT NULL,
  `COMMENT_ID` int(10) NOT NULL,
  `TS_ID` int(10) NOT NULL,
  `TS_DESC` varchar(255) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_log`
--


/*!40000 ALTER TABLE `t_log` DISABLE KEYS */;
LOCK TABLES `t_log` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_log` ENABLE KEYS */;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ROLE_NAME` varchar(255) NOT NULL,
  `ID` int(10) NOT NULL auto_increment,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_role`
--


/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
LOCK TABLES `t_role` WRITE;
INSERT INTO `t_role` VALUES ('admin',1),('manager',2),('employee',3);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;

--
-- Table structure for table `t_transaction`
--

DROP TABLE IF EXISTS `t_transaction`;
CREATE TABLE `t_transaction` (
  `ID` int(10) NOT NULL auto_increment,
  `TS_TITLE` varchar(255) NOT NULL,
  `TS_CONTENT` text,
  `TS_TARGETDATE` varchar(255) default NULL,
  `TS_FACTDATE` varchar(255) default NULL,
  `TS_CREATEDATE` varchar(255) default NULL,
  `INITIATOR_ID` int(10) default NULL,
  `HANDLER_ID` int(10) default NULL,
  `PRE_HANDLER_ID` int(10) default NULL,
  `TS_STATE` varchar(255) NOT NULL,
  `IS_HURRY` varchar(255) default '0',
  PRIMARY KEY  (`ID`),
  KEY `INITIATOR_ID` (`INITIATOR_ID`),
  KEY `HANDLER_ID` (`HANDLER_ID`),
  KEY `PRE_HANDLER_ID` (`PRE_HANDLER_ID`),
  CONSTRAINT `t_transaction_ibfk_1` FOREIGN KEY (`INITIATOR_ID`) REFERENCES `t_user` (`ID`),
  CONSTRAINT `t_transaction_ibfk_2` FOREIGN KEY (`HANDLER_ID`) REFERENCES `t_user` (`ID`),
  CONSTRAINT `t_transaction_ibfk_3` FOREIGN KEY (`PRE_HANDLER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_transaction`
--


/*!40000 ALTER TABLE `t_transaction` DISABLE KEYS */;
LOCK TABLES `t_transaction` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_transaction` ENABLE KEYS */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` int(10) NOT NULL auto_increment,
  `USER_NAME` varchar(255) NOT NULL,
  `ROLE_ID` int(10) NOT NULL,
  `REAL_NAME` varchar(255) default NULL,
  `IS_DELETE` varchar(255) NOT NULL default '0',
  `PASS_WD` varchar(255) NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `t_user_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_role` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_user`
--


/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
LOCK TABLES `t_user` WRITE;
INSERT INTO `t_user` VALUES (1,'crazyit',1,'crazyit','0','crazyit');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;

--
-- Table structure for table `user_transfer`
--

DROP TABLE IF EXISTS `user_transfer`;
CREATE TABLE `user_transfer` (
  `ID` int(10) NOT NULL auto_increment,
  `TS_ID` int(10) NOT NULL COMMENT 'Transaction foreign key',
  `USER_ID` int(10) NOT NULL COMMENT 'User foreign key',
  `TARGET_USER_ID` int(10) NOT NULL COMMENT 'target user foreign key',
  `OPERATE_DATE` varchar(255) NOT NULL COMMENT 'operate date',
  PRIMARY KEY  (`ID`),
  KEY `TS_ID` (`TS_ID`),
  KEY `USER_ID` (`USER_ID`),
  KEY `TARGET_USER_ID` (`TARGET_USER_ID`),
  CONSTRAINT `user_transfer_ibfk_3` FOREIGN KEY (`TARGET_USER_ID`) REFERENCES `t_user` (`ID`),
  CONSTRAINT `user_transfer_ibfk_1` FOREIGN KEY (`TS_ID`) REFERENCES `t_transaction` (`ID`),
  CONSTRAINT `user_transfer_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_transfer`
--


/*!40000 ALTER TABLE `user_transfer` DISABLE KEYS */;
LOCK TABLES `user_transfer` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `user_transfer` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

