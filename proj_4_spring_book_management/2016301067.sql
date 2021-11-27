-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: spring5fs
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `Bnum` int NOT NULL AUTO_INCREMENT,
  `Bname` varchar(255) DEFAULT NULL,
  `Publisher` varchar(100) DEFAULT NULL,
  `Author` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Bnum`),
  UNIQUE KEY `Bname` (`Bname`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (14,'1','출판사1','1대출2예약'),(15,'2','출판사2','1대출2대출'),(17,'3','출판사3','1대출2예약1반납1대출'),(18,'4','출판사4','1대출2예약1반납2대출'),(19,'5','출판사5','1대출2예약3예약1반납3대출'),(20,'6','출판사6','1대출 3권이상금지 1예약'),(21,'7','추가하였습니다','추가한책'),(22,'추가하는책','추가한책입니다','추가한책입니다');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowtable`
--

DROP TABLE IF EXISTS `borrowtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrowtable` (
  `BTnum` int NOT NULL AUTO_INCREMENT,
  `Bnum` int DEFAULT NULL,
  `Mnum` int DEFAULT NULL,
  PRIMARY KEY (`BTnum`),
  UNIQUE KEY `Bnum` (`Bnum`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowtable`
--

LOCK TABLES `borrowtable` WRITE;
/*!40000 ALTER TABLE `borrowtable` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrowtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `Mnum` int NOT NULL AUTO_INCREMENT,
  `ID` varchar(255) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL,
  `Ban` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Mnum`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (19,'1','123',NULL),(20,'2','123',NULL),(21,'3','123',NULL),(22,'4','123',NULL),(23,'추가한아이디','123',NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservetable`
--

DROP TABLE IF EXISTS `reservetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservetable` (
  `RTnum` int NOT NULL AUTO_INCREMENT,
  `Bnum` int DEFAULT NULL,
  `Mnum` int DEFAULT NULL,
  PRIMARY KEY (`RTnum`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservetable`
--

LOCK TABLES `reservetable` WRITE;
/*!40000 ALTER TABLE `reservetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'spring5fs'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-07 21:16:37
