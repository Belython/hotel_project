-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: localhost    Database: BOOKING
-- ------------------------------------------------------
-- Server version	5.6.31-log

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
-- Table structure for table `ORDERS`
--

DROP TABLE IF EXISTS `ORDERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDERS` (
  `ORDER_ID` bigint(16) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(16) NOT NULL,
  `HOTEL_ID` bigint(16) NOT NULL,
  `ROOM_TYPE_ID` bigint(16) NOT NULL,
  `TOTAL_PERSONS` int(3) NOT NULL,
  `CHECK_IN_DATE` bigint(16) NOT NULL,
  `CHECK_OUT_DATE` bigint(16) NOT NULL,
  `ORDER_STATUS` varchar(45) NOT NULL,
  PRIMARY KEY (`ORDER_ID`),
  KEY `USER_ID_idx` (`USER_ID`),
  KEY `ORDER_ROOM_TYPE_KEY_idx` (`ROOM_TYPE_ID`),
  KEY `ORDER_HOTEL_KEY_idx` (`HOTEL_ID`),
  CONSTRAINT `ORDER_HOTEL_KEY` FOREIGN KEY (`HOTEL_ID`) REFERENCES `HOTELS` (`HOTEL_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ORDER_ROOM_TYPE_KEY` FOREIGN KEY (`ROOM_TYPE_ID`) REFERENCES `ROOMS_TYPES` (`ROOM_TYPE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ORDER_USER_KEY` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDERS`
--

LOCK TABLES `ORDERS` WRITE;
/*!40000 ALTER TABLE `ORDERS` DISABLE KEYS */;
INSERT INTO `ORDERS` VALUES (1,1,1,1,2,12345678,123456789,'11'),(2,2,1,2,1,12345678,123456789,'11'),(3,3,1,3,3,12345678,123456789,'11'),(4,4,1,4,3,12345678,123456789,'11'),(5,5,1,5,1,12345678,123456789,'11'),(6,1,1,2,1,1461099600000,1461963600000,'checked'),(7,1,1,1,1,1461099600000,1463346000000,'checked'),(8,1,1,2,1,1461099600000,1466370000000,'checked'),(9,1,1,2,1,1461963600000,1464555600000,'checked'),(10,1,1,1,1,1461963600000,1464555600000,'checked');
/*!40000 ALTER TABLE `ORDERS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-30  1:51:38
