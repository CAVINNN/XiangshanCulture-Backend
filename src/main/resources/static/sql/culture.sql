-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: culture
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `history_subject` varchar(255) DEFAULT NULL,
  `history_predicate` varchar(255) DEFAULT NULL,
  `history_object` varchar(255) DEFAULT NULL,
  `history_scope` varchar(255) DEFAULT NULL,
  `history_type` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`history_id`),
  KEY `his_user_FK_idx` (`user_name`),
  CONSTRAINT `his_user_FK` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (1,'孙中山',NULL,NULL,'所有类','knowledge','2019-06-02 10:05:59','CAVINNN'),(2,'孙中山',NULL,'容闳',NULL,'relation','2019-06-02 10:07:04','CAVINNN'),(3,'孙中山',NULL,NULL,'朋友关系','attribute','2019-06-02 10:08:47','CAVINNN'),(4,'容闳',NULL,NULL,'香山人物','knowledge','2019-06-02 12:38:48','CAVINNN'),(5,'容闳',NULL,NULL,'同乡关系','attribute','2019-06-02 12:40:19','CAVINNN'),(7,'孙中山',NULL,'孙科',NULL,'relation','2019-06-02 16:07:07','CAVINNN'),(9,'孙中山',NULL,'宋庆龄',NULL,'relation','2019-06-02 16:07:45','CAVINNN'),(11,'孙中山',NULL,NULL,'组织机构','knowledge','2019-06-03 18:02:07','CAVINNN'),(12,'孙中山',NULL,NULL,'职务和称号','knowledge','2019-06-03 18:05:39','CAVINNN'),(15,'马应彪',NULL,'先施公司',NULL,'relation','2019-06-03 18:11:39','CAVINNN'),(16,'孙中山',NULL,NULL,'担任领导','attribute','2019-06-03 18:13:57','CAVINNN'),(17,'孙中山',NULL,NULL,'文化遗存','knowledge','2019-06-04 07:31:06','459546683'),(19,'孙中山',NULL,'孙娫',NULL,'relation','2019-06-04 07:32:49','459546683'),(21,'容闳',NULL,NULL,'相关事件','attribute','2019-06-04 07:33:36','459546683');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `register_date` datetime NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('459546683','f983af081425105771a81e12f4db51666e2c6d77ff62ef5d357ae3d1dbe3e66e','459546683@qq.com','2019-06-04 07:30:17'),('CAVINNN','f983af081425105771a81e12f4db51666e2c6d77ff62ef5d357ae3d1dbe3e66e','hjw@gmail.com','2019-06-02 08:29:34'),('Ethel','4732b5d43f82c0722a73c42f12c4648ec44d0428e82bea9bd20b66b6bf56c60d','hxy@126.com','2019-06-01 14:27:27');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-04 15:47:47
