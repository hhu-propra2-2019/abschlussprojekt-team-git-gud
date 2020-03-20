-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    database: materialsammlung
-- ------------------------------------------------------
-- Server version	5.7.29

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
-- Table structure for table `Datei`
--

DROP TABLE IF EXISTS `Datei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Datei` (
  `dateiID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `uploaderID` bigint(20) NOT NULL,
  `upload_datum` date NOT NULL,
  `veroeffentlichungs_datum` date NOT NULL,
  `datei_groesse` bigint(20) NOT NULL,
  `datei_typ` text NOT NULL,
  `gruppeID` bigint(20) NOT NULL,
  `kategorie` varchar(100) NOT NULL,
  PRIMARY KEY (`dateiID`),
  KEY `uploaderID` (`uploaderID`),
  KEY `gruppeID` (`gruppeID`),
  CONSTRAINT `Datei_ibfk_1` FOREIGN KEY (`uploaderID`) REFERENCES `User` (`userID`),
  CONSTRAINT `Datei_ibfk_2` FOREIGN KEY (`gruppeID`) REFERENCES `Gruppe` (`gruppeID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Datei`
--

LOCK TABLES `Datei` WRITE;
/*!40000 ALTER TABLE `Datei` DISABLE KEYS */;
INSERT INTO `Datei` VALUES (1,'Protokoll: Material',916883599,'2019-02-20','2012-03-20',997720,'pdf',804187799,'Skript'),(2,'Protokoll: Wachstum',916883599,'2006-09-21','2020-12-19',529301,'pdf',804187799,'Uebung'),(3,'Protokoll: Fehler',916883599,'2006-11-21','2004-06-19',180428,'pdf',804187799,'Uebung'),(4,'Protokoll: Ergebnis',916883599,'2019-02-21','2008-05-19',551007,'pdf',804187799,'Uebung'),(5,'Ana: Uebung 4 Bsprch.',1300980699,'2003-10-20','2021-05-19',287048,'pdf',2634173699,'Uebung'),(6,'Ana: Uebung 5 Bsprch.',1300980699,'2030-08-21','2013-03-20',864748,'pdf',2634173699,'Uebung'),(7,'Ana: Uebung 6 Bsprch.',1300980699,'2015-06-19','2019-01-21',895973,'pdf',2634173699,'Uebung'),(8,'Ana: Uebung 8 Bsprch.',1300980699,'2026-08-20','2026-06-19',486612,'pdf',2634173699,'Uebung'),(9,'Ana: Uebung 9 Bsprch.',1300980699,'2023-12-19','2009-06-20',663279,'pdf',2634173699,'Uebung'),(10,'Ana: Uebung 10',1323396599,'2018-10-20','2030-12-19',289690,'pdf',2634173699,'Uebung'),(11,'Ana: Uebung 11',1323396599,'2015-06-19','2020-01-21',710215,'pdf',2634173699,'Uebung'),(12,'Ana: Uebung 12',1323396599,'2007-05-19','2007-06-19',884517,'pdf',2634173699,'Uebung'),(13,'Ana: Uebung 13',1323396599,'2015-11-19','2003-05-19',492077,'pdf',2634173699,'Uebung'),(14,'Ana: Uebung 14',10255396699,'2029-10-19','2025-05-21',640157,'pdf',10556995299,'Uebung'),(15,'Ana: Uebung 15',10255396699,'2018-08-19','2014-02-21',752439,'pdf',10556995299,'Uebung'),(16,'Ana: Uebung 16',10255396699,'2020-03-21','2028-11-20',810638,'pdf',10556995299,'Uebung'),(17,'Ana: Uebung 17',10255396699,'2003-09-20','2009-05-20',195212,'pdf',10556995299,'Uebung'),(18,'Ana: Uebung 18',10255396699,'2022-08-19','2008-02-20',590964,'pdf',10556995299,'Uebung'),(19,'Analysis 1.1',10255396699,'2006-09-19','2003-08-20',61047,'pdf',10556995299,'Skript'),(20,'Analysis 1.2',10255396699,'2022-05-19','2029-10-19',446856,'pdf',10556995299,'Skript'),(21,'Analysis 1.3',10255396699,'2018-09-20','2001-06-20',14284,'pdf',10556995299,'Skript'),(22,'Botanik: Einführung',99333469499,'2027-08-19','2016-12-20',47375,'pdf',17805362299,'Skript'),(23,'Bio100 Skript 1.1',99333469499,'2025-02-21','2001-09-19',665354,'pdf',17805362299,'Skript'),(24,'Bio100 Skript 1.2',99333469499,'2024-10-19','2027-11-21',199985,'pdf',17805362299,'Skript'),(25,'Bio100 Skript 1.3',99333469499,'2001-01-21','2025-01-20',556296,'pdf',17805362299,'Skript'),(26,'Bio100 Skript 1.4',99333469499,'2010-12-21','2021-04-21',395555,'pdf',17805362299,'Skript'),(27,'Bio100 Skript 1.5',98680336499,'2009-10-19','2025-09-21',370414,'pdf',17805362299,'Skript'),(28,'Bio100 Skript 1.6',98680336499,'2016-10-20','2022-03-19',655854,'pdf',17805362299,'Skript'),(29,'Bio100 Skript 1.7',98680336499,'2010-12-21','2008-08-21',441242,'pdf',17805362299,'Skript'),(30,'Bio100 Skript 1.8',98680336499,'2017-12-20','2007-01-20',342249,'pdf',17805362299,'Skript'),(31,'Bio100 Skript 1.9',98680336499,'2012-09-21','2005-06-19',1043190,'pdf',17805362299,'Skript'),(32,'Evo. Skript 1',87991920199,'2016-08-20','2012-11-21',361102,'pdf',17805362299,'Skript'),(33,'Evo. Skript 2',87991920199,'2022-08-19','2010-06-20',325620,'pdf',87951066299,'Skript'),(34,'Evo. Skript 3',87991920199,'2030-08-21','2026-05-20',66102,'pdf',87951066299,'Skript'),(35,'Evo. Skript 4',87991920199,'2025-11-19','2029-02-20',587651,'pdf',87951066299,'Skript'),(36,'Evo. Skript 5',87991920199,'2022-04-19','2010-10-19',491484,'pdf',87951066299,'Skript'),(37,'Evo. Skript 6',87991920199,'2012-07-21','2018-03-19',376856,'pdf',87951066299,'Skript'),(38,'Evo. Skript 7',87991920199,'2012-02-21','2004-12-21',218114,'pdf',87951066299,'Skript'),(39,'Evo. Skript 8',87991920199,'2012-08-20','2018-07-20',189519,'pdf',87951066299,'Skript'),(40,'Evo. Skript 9',87991920199,'2004-03-20','2004-01-21',823545,'pdf',87951066299,'Skript'),(41,'Evo. Skript 10',87991920199,'2023-11-19','2019-10-20',1019585,'pdf',87951066299,'Skript'),(42,'Evo. Protokoll 11',87991920199,'2017-05-21','2028-01-20',848287,'pdf',87951066299,'Uebung'),(43,'Evo. Protokoll 12',87991920199,'2021-08-20','2020-08-19',990459,'pdf',87951066299,'Uebung'),(44,'Evo. Protokoll 13',87991920199,'2022-01-20','2018-03-21',254471,'pdf',87951066299,'Uebung'),(45,'Evo. Protokoll 14',87991920199,'2010-05-19','2019-08-19',148540,'pdf',87951066299,'Uebung'),(46,'Evo. Protokoll 15',87991920199,'2005-11-19','2016-02-20',674421,'pdf',87951066299,'Uebung'),(47,'Evo. Protokoll 16',87991920199,'2026-10-20','2026-07-20',365529,'pdf',87951066299,'Uebung'),(48,'Evo. Protokoll 17',87991920199,'2005-02-21','2014-07-20',877355,'pdf',87951066299,'Uebung'),(49,'Evo. Protokoll 18',87991920199,'2003-07-21','2018-04-20',756521,'pdf',87951066299,'Uebung'),(50,'Evo. Protokoll 19',87991920199,'2013-11-19','2004-04-20',454132,'pdf',87951066299,'Uebung'),(51,'Evo. Protokoll 20',87991920199,'2011-04-20','2025-06-20',654191,'pdf',87951066299,'Uebung'),(52,'Evo. Protokoll 21',87991920199,'2015-06-20','2019-03-20',13377,'pdf',87951066299,'Uebung'),(53,'MaMe Blatt 1',82651084299,'2017-12-21','2004-02-21',443310,'pdf',77657118799,'Uebung'),(54,'MaMe Blatt 2',82651084299,'2022-07-21','2020-11-20',896612,'pdf',77657118799,'Uebung'),(55,'MaMe Blatt 3',82651084299,'2029-03-21','2011-05-19',81562,'pdf',77657118799,'Uebung'),(56,'MaMe Blatt 4',82651084299,'2016-03-19','2012-04-20',841080,'pdf',77657118799,'Uebung'),(57,'MaMe Blatt 5',82651084299,'2024-12-19','2024-07-21',824703,'pdf',77657118799,'Uebung'),(58,'MaMe Blatt 6',82651084299,'2024-10-21','2023-08-19',643861,'pdf',77657118799,'Uebung'),(59,'MaMe Blatt 7',82651084299,'2019-12-21','2024-07-21',429285,'pdf',77657118799,'Uebung'),(60,'MaMe Blatt 8',82651084299,'2014-05-21','2013-11-20',633258,'pdf',77657118799,'Uebung'),(61,'MaMe Blatt 9',82651084299,'2014-10-19','2018-08-21',666658,'pdf',77657118799,'Uebung'),(62,'MaMe Blatt 10',82651084299,'2002-08-19','2024-10-19',207103,'pdf',77657118799,'Uebung'),(63,'MaMe Blatt 11',82651084299,'2013-09-20','2022-05-20',490953,'pdf',77657118799,'Uebung'),(64,'MaMe Blatt 12',82651084299,'2008-09-19','2021-11-21',1012419,'pdf',77657118799,'Uebung'),(65,'MaMe Blatt 13',82651084299,'2011-08-19','2027-08-20',824891,'pdf',77657118799,'Uebung'),(66,'Math. Meth. Skript 1',82651084299,'2016-08-20','2018-04-21',265001,'txt',77657118799,'Skript'),(67,'Math. Meth. Skript 2',82651084299,'2002-12-19','2009-10-20',756852,'txt',77657118799,'Skript'),(68,'Math. Meth. Skript 3',82651084299,'2021-08-20','2003-08-21',409782,'txt',77657118799,'Skript'),(69,'Math. Meth. Skript 4',82651084299,'2030-11-19','2026-05-20',52288,'txt',77657118799,'Skript'),(70,'Math. Meth. Skript 5',82651084299,'2016-08-21','2006-01-20',759101,'txt',77657118799,'Skript'),(71,'Math. Meth. Skript 6',82651084299,'2029-08-19','2019-10-20',383107,'txt',77657118799,'Skript'),(72,'Math. Meth. Skript 7',82651084299,'2021-04-19','2016-12-19',394948,'txt',77657118799,'Skript'),(73,'Math. Meth. Skript 8',82651084299,'2029-07-20','2013-07-20',443965,'txt',77657118799,'Skript'),(74,'Math. Meth. Skript 9',82651084299,'2019-12-21','2019-01-20',237947,'txt',77657118799,'Skript'),(75,'Math. Meth. Skript 10',82651084299,'2009-11-20','2003-02-21',90952,'txt',77657118799,'Skript'),(76,'Math. Meth. Skript 11',82651084299,'2024-10-20','2028-03-21',107505,'txt',77657118799,'Skript'),(77,'Math. Meth. Skript 12',82651084299,'2023-05-19','2014-02-21',184787,'txt',77657118799,'Skript'),(78,'Math. Meth. Skript 13',82651084299,'2015-05-21','2004-06-21',1048240,'txt',77657118799,'Skript'),(79,'Math. Meth. Skript 14',82651084299,'2021-02-20','2007-11-21',237631,'txt',77657118799,'Skript'),(80,'Math. Meth. Skript 15',82651084299,'2004-04-19','2007-09-20',424248,'txt',77657118799,'Skript'),(81,'Math. Meth. Skript 16',82651084299,'2001-04-21','2006-06-19',172100,'txt',77657118799,'Skript'),(82,'Math. Meth. Skript 17',82651084299,'2017-10-21','2028-07-20',992984,'txt',77657118799,'Skript'),(83,'Math. Meth. Skript 18',82651084299,'2021-10-21','2006-07-21',948370,'txt',77657118799,'Skript'),(84,'Math. Meth. Skript 19',82651084299,'2009-09-19','2025-04-20',12574,'txt',77657118799,'Skript'),(85,'Math. Meth. Skript 20',82651084299,'2004-05-20','2021-01-20',96254,'txt',77657118799,'Skript'),(86,'Math. Meth. Skript 21',82651084299,'2002-05-21','2005-10-20',717418,'txt',77657118799,'Skript'),(87,'Math. Meth. Skript 22',82651084299,'2021-01-21','2026-10-20',932267,'txt',77657118799,'Skript'),(88,'Math. Meth. Skript 23',82651084299,'2024-09-19','2030-01-20',113606,'txt',77657118799,'Skript'),(89,'Math. Meth. Skript 24',82651084299,'2028-04-21','2017-03-20',651730,'txt',77657118799,'Skript'),(90,'Math. Meth. Skript 25',82651084299,'2014-03-20','2005-10-21',1020246,'png',77657118799,'Skript'),(91,'Math. Meth. Skript 26',82651084299,'2004-03-20','2020-03-21',3228,'png',77657118799,'Skript'),(92,'Math. Meth. Skript 27',82651084299,'2015-02-21','2027-05-20',922115,'png',77657118799,'Skript'),(93,'Math. Meth. Skript 28',82651084299,'2025-10-21','2008-04-19',63065,'png',77657118799,'Skript'),(94,'Math. Meth. Skript 29',82651084299,'2015-05-19','2027-03-20',181088,'png',77657118799,'Skript'),(95,'Math. Meth. Skript 30',82651084299,'2027-09-20','2013-07-21',722085,'png',77657118799,'Skript'),(96,'Math. Meth. Skript 31',82651084299,'2025-09-21','2027-03-21',863578,'png',77657118799,'Skript'),(97,'Math. Meth. Skript 32',82651084299,'2031-03-20','2014-11-19',60678,'png',77657118799,'Skript'),(98,'Math. Meth. Skript 33',82651084299,'2025-10-21','2015-04-19',526386,'png',77657118799,'Skript'),(99,'Math. Meth. Skript 34',82651084299,'2004-09-20','2002-09-21',911170,'png',77657118799,'Skript'),(100,'Math. Meth. Skript 35',82651084299,'2019-10-21','2014-04-20',29812,'png',77657118799,'Skript');
/*!40000 ALTER TABLE `Datei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gruppe`
--

DROP TABLE IF EXISTS `Gruppe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Gruppe` (
  `gruppeID` bigint(20) NOT NULL,
  `titel` text NOT NULL,
  `beschreibung` text,
  PRIMARY KEY (`gruppeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gruppe`
--

LOCK TABLES `Gruppe` WRITE;
/*!40000 ALTER TABLE `Gruppe` DISABLE KEYS */;
INSERT INTO `Gruppe` VALUES (804187799,'Lerngruppe Bio110 A','eleifend nec, malesuada ut, sem. Nulla interdum. Curabitur dictum. Phasellus'),(992896999,'Lerngruppe Bio110 B','a, dui. Cras pellentesque. Sed dictum. Proin eget odio. Aliquam'),(1246425999,'Lerngruppe Bio110 C','nibh dolor, nonummy ac, feugiat non, lobortis quis, pede. Suspendisse'),(2634173699,'Lerngruppe Ana1','Donec fringilla. Donec feugiat metus sit amet ante. Vivamus non'),(2896368899,'Programmierpraktikum 1','consectetuer rhoncus. Nullam velit dui, semper et, lacinia vitae, sodales'),(3277082699,'Programmierpraktikum 2','eu erat semper rutrum. Fusce dolor quam, elementum at, egestas'),(3704138299,'Progrmmierung 1','nibh lacinia orci, consectetuer euismod est arcu ac orci. Ut'),(4399075899,'Übung: Prog1 A','lectus ante dictum mi, ac mattis velit justo nec ante.'),(6991330199,'Übung: Prog1B','neque sed sem egestas blandit. Nam nulla magna, malesuada vel,'),(8514326799,'Übung: Prog1 C','sem, vitae aliquam eros turpis non enim. Mauris quis turpis'),(10556995299,'Analysis Uebung 1','euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget'),(10890810699,'Analysis Uebung 2','nulla at sem molestie sodales. Mauris blandit enim consequat purus.'),(12998649399,'Analysis Uebung 3','mattis ornare, lectus ante dictum mi, ac mattis velit justo'),(13400316599,'Analysis Uebung 4','Donec felis orci, adipiscing non, luctus sit amet, faucibus ut,'),(15447023999,'Analysis Uebung 5','dolor. Donec fringilla. Donec feugiat metus sit amet ante. Vivamus'),(17339313099,'Grundlagen der Graphentheorie','Sed molestie. Sed id risus quis diam luctus lobortis. Class'),(17346735399,'Praktikumsvorbereitung','dolor sit amet, consectetuer adipiscing elit. Aliquam auctor, velit eget'),(17802683799,'Zoologie','urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus'),(17805362299,'Botanik','sem molestie sodales. Mauris blandit enim consequat purus. Maecenas libero'),(19369317099,'Anthropologie','hendrerit. Donec porttitor tellus non magna. Nam ligula elit, pretium'),(19496214999,'Moderne Kunstgeschichte','Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam auctor,'),(20875408799,'Lineare Algebra 1','consequat enim diam vel arcu. Curabitur ut odio vel est'),(21948919399,'Übung zur linearen Algebra 1 A','Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus. Morbi'),(23790016399,'Übung zur linearen Algebra 1 B','Cras pellentesque. Sed dictum. Proin eget odio. Aliquam vulputate ullamcorper'),(24459951899,'Übung zur linearen Algebra 1 C','hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer,'),(24709997599,'Übung zur linearen Algebra 1 D','est ac mattis semper, dui lectus rutrum urna, nec luctus'),(25776199499,'Übung zur linearen Algebra 1 D','aliquet molestie tellus. Aenean egestas hendrerit neque. In ornare sagittis'),(28357539699,'Algorithmische Bioinformatik','suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis'),(28453949599,'Spieltheorie','Aliquam fringilla cursus purus. Nullam scelerisque neque sed sem egestas'),(30817992899,'Stochastik','nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam'),(33443797299,'Numerik','fringilla. Donec feugiat metus sit amet ante. Vivamus non lorem'),(33694344299,'Funktionentheorie','non, bibendum sed, est. Nunc laoreet lectus quis massa. Mauris'),(33873451299,'Git-Gud','eu nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula.'),(34773997999,'it-bois','velit. Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus.'),(35354568799,'pamenang','risus. Morbi metus. Vivamus euismod urna. Nullam lobortis quam a'),(37505013999,'gitistfuerunsalleneuland','et malesuada fames ac turpis egestas. Aliquam fringilla cursus purus.'),(39696595699,'trapin in auas','tellus eu augue porttitor interdum. Sed auctor odio a purus.'),(41897039899,'big f for auas','ornare sagittis felis. Donec tempor, est ac mattis semper, dui'),(42667213699,'teamymcteamface','sapien. Cras dolor dolor, tempus non, lacinia at, iaculis quis,'),(43536180299,'mopse','volutpat ornare, facilisis eget, ipsum. Donec sollicitudin adipiscing ligula. Aenean'),(43556770699,'die senioren','orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu,'),(43857657599,'make auas great again','netus et malesuada fames ac turpis egestas. Aliquam fringilla cursus'),(46451326099,'bits please','Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus. Morbi'),(47712749799,'fixlater','vel, convallis in, cursus et, eros. Proin ultrices. Duis volutpat'),(48632252899,'proprastination','congue. In scelerisque scelerisque dui. Suspendisse ac metus vitae velit'),(48972040799,'grossbrandmeister','Mauris quis turpis vitae purus gravida sagittis. Duis gravida. Praesent'),(49850930499,'zikade3301','placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl.'),(52855963599,'team-the-institute','In tincidunt congue turpis. In condimentum. Donec at arcu. Vestibulum'),(53217375099,'Tutoren PP2','Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie'),(53394283699,'Graphenalgorithmen','Proin ultrices. Duis volutpat nunc sit amet metus. Aliquam erat'),(53419252499,'Datenbanken SS21','mauris erat eget ipsum. Suspendisse sagittis. Nullam vitae diam. Proin'),(54826308699,'Rechnernetze','elit, pharetra ut, pharetra sed, hendrerit a, arcu. Sed et'),(55398869099,'Betriebssysteme','nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam'),(57619996899,'Compilerbau','tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque'),(57993307099,'Sprachendesign','auctor non, feugiat nec, diam. Duis mi enim, condimentum eget,'),(59278567399,'Mikrobiologie','sollicitudin commodo ipsum. Suspendisse non leo. Vivamus nibh dolor, nonummy'),(59372442399,'Mikrobiologie: Praktikum','Fusce aliquam, enim nec tempus scelerisque, lorem ipsum sodales purus,'),(60600204399,'Mikrobiologie: Gruppe A','lorem ipsum sodales purus, in molestie tortor nibh sit amet'),(61280206199,'Mikrobiologie: Gruppe B','lorem, sit amet ultricies sem magna nec quam. Curabitur vel'),(61330989599,'Mikrobiologie: Gruppe C','hendrerit neque. In ornare sagittis felis. Donec tempor, est ac'),(61468433099,'Mikrobiologie: Gruppe D','egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est,'),(61836068699,'Mikrobiologie: Gruppe E','quam. Pellentesque habitant morbi tristique senectus et netus et malesuada'),(66454463799,'Mikrobiologie: Gruppe F','convallis est, vitae sodales nisi magna sed dui. Fusce aliquam,'),(66523032499,'Grundpraktikum I','Curabitur consequat, lectus sit amet luctus vulputate, nisi sem semper'),(68899830499,'Grundpraktikum II','fringilla mi lacinia mattis. Integer eu lacus. Quisque imperdiet, erat'),(69808697299,'Theoretische Chemie I','eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus'),(70244620999,'Theoretische Chemie II','erat nonummy ultricies ornare, elit elit fermentum risus, at fringilla'),(70366228299,'Theoretische Chemie III','Duis mi enim, condimentum eget, volutpat ornare, facilisis eget, ipsum.'),(70439586499,'Laborprraktikum organische Chemie','habitant morbi tristique senectus et netus et malesuada fames ac'),(70711335899,'Laborpraktikum oChem: Gruppe 1','sit amet, consectetuer adipiscing elit. Etiam laoreet, libero et tristique'),(71425358399,'Laborpraktikum oChem: Gruppe 2','eleifend. Cras sed leo. Cras vehicula aliquet libero. Integer in'),(72564172199,'Laborpraktikum oChem: Gruppe 3','vitae risus. Duis a mi fringilla mi lacinia mattis. Integer'),(72792956199,'Laborpraktikum oChem: Gruppe 4','ac libero nec ligula consectetuer rhoncus. Nullam velit dui, semper'),(72795873299,'Laborpraktikum oChem: Gruppe 5','sapien, cursus in, hendrerit consectetuer, cursus et, magna. Praesent interdum'),(72831548899,'Laborpraktikum oChem: Gruppe 6','id magna et ipsum cursus vestibulum. Mauris magna. Duis dignissim'),(73879313499,'Laborpraktikum oChem: Gruppe 7','ut nisi a odio semper cursus. Integer mollis. Integer tincidunt'),(75248776499,'Laborpraktikum oChem: Gruppe 8','eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus'),(75297960399,'Laborpraktikum oChem: Gruppe 9','odio. Nam interdum enim non nisi. Aenean eget metus. In'),(75363639299,'Laborpraktikum oChem: Gruppe 10','sem eget massa. Suspendisse eleifend. Cras sed leo. Cras vehicula'),(75554442499,'Lehrstuhl-Projekt: Shorty','rutrum, justo. Praesent luctus. Curabitur egestas nunc sed libero. Proin'),(76827496599,'Numerik II','nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam'),(77657118799,'Mathematische Methoden der Physik','at arcu. Vestibulum ante ipsum primis in faucibus orci luctus'),(80672407299,'Genetik','malesuada fames ac turpis egestas. Fusce aliquet magna a neque.'),(87798956199,'Ökologie','ullamcorper eu, euismod ac, fermentum vel, mauris. Integer sem elit,'),(87951066299,'Evolutionstheorie','Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin'),(90330253299,'Machine Learning','Suspendisse eleifend. Cras sed leo. Cras vehicula aliquet libero. Integer'),(90359361099,'Zusatz: Deep Learning','ac urna. Ut tincidunt vehicula risus. Nulla eget metus eu'),(90574075499,'Angewandte Gruppentheorie','ridiculus mus. Aenean eget magna. Suspendisse tristique neque venenatis lacus.'),(91420503899,'An. Gruppentheorie: A','eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus'),(91856184699,'An. Gruppentheorie: B','ligula. Aenean gravida nunc sed pede. Cum sociis natoque penatibus'),(94246917599,'An. Gruppentheorie: C','volutpat nunc sit amet metus. Aliquam erat volutpat. Nulla facilisis.'),(94841113799,'An. Gruppentheorie: D','adipiscing elit. Etiam laoreet, libero et tristique pellentesque, tellus sem'),(96686149099,'An. Gruppentheorie: E','velit in aliquet lobortis, nisi nibh lacinia orci, consectetuer euismod'),(98547467399,'An. Gruppentheorie: F','sapien imperdiet ornare. In faucibus. Morbi vehicula. Pellentesque tincidunt tempus'),(98669749599,'An. Gruppentheorie: G','faucibus leo, in lobortis tellus justo sit amet nulla. Donec'),(99455555199,'An. Gruppentheorie: H','eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce');
/*!40000 ALTER TABLE `Gruppe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gruppenbelegung`
--

DROP TABLE IF EXISTS `Gruppenbelegung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Gruppenbelegung` (
  `upload_berechtigung` tinyint(1) NOT NULL,
  `gruppeID` bigint(20) NOT NULL,
  `userID` bigint(20) NOT NULL,
  KEY `gruppeID` (`gruppeID`),
  KEY `userID` (`userID`),
  CONSTRAINT `Gruppenbelegung_ibfk_1` FOREIGN KEY (`gruppeID`) REFERENCES `Gruppe` (`gruppeID`),
  CONSTRAINT `Gruppenbelegung_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gruppenbelegung`
--

LOCK TABLES `Gruppenbelegung` WRITE;
/*!40000 ALTER TABLE `Gruppenbelegung` DISABLE KEYS */;
INSERT INTO `Gruppenbelegung` VALUES (1,804187799,916883599),(1,2634173699,1300980699),(1,2634173699,1323396599),(0,2634173699,3422340399),(0,2896368899,1300980699),(0,3277082699,5507758999),(0,3704138299,6325366199),(0,4399075899,9291092799),(1,10556995299,10255396699),(0,8514326799,10484292999),(0,10556995299,10987488399),(0,10890810699,12364770199),(0,12998649399,12492335599),(0,13400316599,14374598099),(0,15447023999,14846488899),(0,17339313099,15247373999),(0,17346735399,15866719599),(0,17802683799,16324374099),(0,17805362299,17213408999),(0,19369317099,17335817199),(0,19496214999,17971635499),(0,20875408799,18383360199),(0,21948919399,20695825699),(0,23790016399,23604947799),(0,24459951899,23784357999),(0,24709997599,26252955099),(0,25776199499,26712762599),(0,28357539699,26863355599),(0,28453949599,28620950099),(0,30817992899,30743703099),(0,33443797299,30987478399),(0,33694344299,32603303699),(0,33873451299,33222646099),(0,34773997999,34648740399),(0,35354568799,35337943799),(0,37505013999,35959473899),(0,39696595699,36668265499),(0,41897039899,37436421399),(0,42667213699,38423182099),(0,43536180299,38630795499),(0,43556770699,38877751599),(0,43857657599,40200309299),(0,46451326099,41816890399),(0,47712749799,42490493299),(0,48632252899,43790352999),(0,48972040799,46212688999),(0,49850930499,46218621599),(0,52855963599,46369316399),(0,53217375099,46760482799),(0,53394283699,47628312799),(0,53419252499,48244828599),(0,54826308699,48358249299),(0,55398869099,48524101699),(0,57619996899,50725413199),(0,57993307099,52634082799),(0,59278567399,53443124399),(0,59372442399,53561483999),(0,60600204399,53848069199),(0,61280206199,54585029399),(0,61330989599,56359562699),(0,61468433099,56640079599),(0,61836068699,56937121099),(0,66454463799,57392217699),(0,66523032499,57415306099),(0,68899830499,61550881499),(0,69808697299,62262940099),(0,70244620999,62296762699),(0,70366228299,62801595899),(0,70439586499,63428953999),(0,70711335899,65831126799),(0,71425358399,66245879399),(0,72564172199,66569154599),(0,72792956199,66810642899),(0,72795873299,69249764499),(0,72831548899,69485280399),(0,73879313499,74426603899),(0,75248776499,74522182799),(0,75297960399,74530133799),(0,75363639299,74657246199),(0,75554442499,75637829999),(0,76827496599,76591062099),(0,77657118799,76978907699),(0,80672407299,77824119099),(0,87798956199,80473737499),(0,87951066299,80531145199),(0,90330253299,82227957699),(0,77657118799,82651084299),(0,90574075499,84750482099),(0,91420503899,86853890899),(1,17805362299,87991920199),(1,87951066299,87991920199),(0,94841113799,89396827199),(0,96686149099,89932149599),(0,98547467399,90877149899),(0,98669749599,93811227199),(0,99455555199,95861625599),(0,99455555199,96307923299),(0,99455555199,96571018299),(1,17805362299,98680336499),(1,17805362299,99333469499);
/*!40000 ALTER TABLE `Gruppenbelegung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tagnutzung`
--

DROP TABLE IF EXISTS `Tagnutzung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tagnutzung` (
  `dateiID` bigint(20) NOT NULL,
  `tagID` bigint(20) NOT NULL,
  KEY `dateiID` (`dateiID`),
  KEY `tagID` (`tagID`),
  CONSTRAINT `Tagnutzung_ibfk_1` FOREIGN KEY (`dateiID`) REFERENCES `Datei` (`dateiID`),
  CONSTRAINT `Tagnutzung_ibfk_2` FOREIGN KEY (`tagID`) REFERENCES `Tags` (`tagID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tagnutzung`
--

LOCK TABLES `Tagnutzung` WRITE;
/*!40000 ALTER TABLE `Tagnutzung` DISABLE KEYS */;
INSERT INTO `Tagnutzung` VALUES (56,15),(87,46),(59,19),(51,46),(61,53),(6,41),(14,63),(66,47),(64,51),(18,97),(49,57),(25,35),(88,88),(39,86),(32,2),(7,48),(2,91),(15,19),(48,32),(99,32),(55,58),(8,84),(66,75),(71,16),(34,60),(24,7),(97,64),(35,90),(27,24),(39,49),(84,93),(57,42),(95,55),(42,1),(28,97),(5,22),(97,27),(93,86),(63,83),(47,52),(1,89),(95,71),(95,78),(48,96),(17,66),(71,80),(75,76),(13,50),(15,100),(60,88),(70,40),(19,35),(1,85),(36,88),(16,52),(62,14),(22,2),(74,81),(37,57),(10,89),(60,41),(58,52),(55,93),(62,76),(90,94),(27,70),(61,43),(83,98),(81,91),(56,24),(10,91),(99,36),(8,33),(39,100),(67,48),(78,33),(26,9),(65,96),(97,65),(39,77),(25,66),(39,97),(98,41),(69,10),(60,97),(61,31),(44,91),(98,4),(16,100),(83,97),(21,94),(87,72),(81,57),(54,43),(15,59),(51,22),(84,49),(89,88),(76,36),(43,3);
/*!40000 ALTER TABLE `Tagnutzung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tags`
--

DROP TABLE IF EXISTS `Tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tags` (
  `tagID` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL,
  PRIMARY KEY (`tagID`),
  UNIQUE KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tags`
--

LOCK TABLES `Tags` WRITE;
/*!40000 ALTER TABLE `Tags` DISABLE KEYS */;
INSERT INTO `Tags` VALUES (84,'Altklausur'),(8,'Anfrage'),(4,'Ankündigung'),(95,'Anleitung'),(82,'Antwort'),(57,'Anweisung'),(58,'Arbeit'),(5,'Asciidoc'),(75,'Asta'),(36,'Audio'),(53,'Auftrag'),(63,'Beachlor'),(54,'Beobachtung'),(29,'Berechnungen'),(88,'Betriebspraktikum'),(21,'Bewerbung'),(27,'Bild'),(39,'Computer'),(92,'Darstellung'),(66,'Daten'),(98,'Docker'),(96,'Doktoranten'),(94,'Dokumentation'),(44,'Draft'),(9,'Dringend'),(37,'Duplikat'),(87,'Einleitung'),(19,'Erklärung'),(25,'Ethymologie'),(23,'Excel'),(26,'Exkursion'),(34,'Fachschaft'),(30,'Fertig'),(7,'Firmen'),(28,'Folien'),(90,'Formel'),(13,'Formelsammlung'),(43,'Foto'),(10,'Frage'),(72,'Git'),(33,'Grundlagen'),(1,'Hilfe'),(45,'Hinweis'),(15,'Image'),(12,'Inphyma'),(64,'Interessant'),(41,'Klausur'),(70,'Klausurrelevant'),(76,'Korrektorenverteilung'),(56,'Korrektur'),(49,'Kurzfristig'),(85,'Langrfristig'),(99,'Liste'),(40,'Markdown'),(24,'Master'),(93,'Material'),(35,'Materialbeschaffung'),(80,'MinIO'),(51,'Movie'),(65,'Muster'),(78,'Nachreichung'),(68,'Nachricht'),(91,'Nebeninformation'),(59,'Paper'),(61,'Personal'),(55,'Physiologie'),(18,'Plan'),(71,'Proseminar'),(20,'Protokoll'),(86,'Purus Gravida Sagittis Associates'),(14,'Quellcode'),(79,'Quelle'),(32,'Raumaufteilung'),(16,'Refractoring'),(69,'Schauen Sie sich an'),(74,'Skizze'),(81,'Skriptergänzung'),(48,'Soundfile'),(77,'Stellenausschreiben'),(42,'Struktur'),(67,'Studie'),(52,'Text'),(50,'Umfrage'),(2,'Vergleich'),(62,'Vernachlässigt'),(3,'Verwaltung'),(83,'Verweis'),(97,'Video'),(46,'Vorbereitung'),(89,'Wichtig'),(73,'Überarbeitet'),(6,'Übersetzung'),(47,'Übrig'),(17,'Übungsaufgabe'),(11,'Zeichnung'),(31,'Zu Bearbeiten'),(100,'zu Kontrollieren'),(60,'Zusatz'),(22,'Zusatzaufgabe'),(38,'Änderung');
/*!40000 ALTER TABLE `Tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `userID` bigint(20) NOT NULL,
  `vorname` text NOT NULL,
  `nachname` text NOT NULL,
  `key_cloak_name` varchar(255) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `key_cloak_name` (`key_cloak_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (-1,'User','deleted','-'),(916883599,'Ali','Hughes','studentin1'),(1300980699,'Bruce','Morin','studentin'),(1323396599,'Bevis','Garrison','bevga102'),(3422340399,'Hunter','Harrison','hunha103'),(4575695099,'Fitzgerald','Murray','fitmu104'),(5507758999,'Jeanette','Mathews','jeama105'),(6325366199,'Diana','Thornton','diath106'),(9291092799,'Isaiah','Mcleod','isamc107'),(10255396699,'Samson','Alford','samal108'),(10484292999,'Acton','Rivas','actri109'),(10987488399,'Danielle','Cochran','danco110'),(12364770199,'Nora','Shepherd','norsh111'),(12492335599,'Grant','Sosa','graso112'),(14374598099,'Raven','Graves','ravgr113'),(14846488899,'Regan','Frederick','regfr114'),(15247373999,'Oren','Frye','orefr115'),(15866719599,'Skyler','Schroeder','skysc116'),(16324374099,'Shelley','Bartlett','sheba117'),(17213408999,'Britanney','Allen','brial118'),(17335817199,'Gloria','Rojas','gloro119'),(17971635499,'Tyrone','Sparks','tyrsp120'),(18383360199,'Samantha','Camacho','samca121'),(20695825699,'Tanek','Kemp','tanke122'),(23604947799,'Roanna','Wong','roawo123'),(23784357999,'Briar','Kent','brike124'),(26252955099,'Myles','Boyd','mylbo125'),(26712762599,'Kenyon','Carrillo','kenca126'),(26863355599,'Herrod','Austin','herau127'),(28620950099,'Edan','Cunningham','edacu128'),(30743703099,'Maia','Mclean','maimc129'),(30987478399,'Wayne','Mckay','waymc130'),(32603303699,'Cameron','Clemons','camcl131'),(33222646099,'Zephania','Carson','zepca132'),(34648740399,'Madeline','Walter','madwa133'),(35337943799,'Lane','Padilla','lanpa134'),(35959473899,'Hamilton','Spencer','hamsp135'),(36668265499,'Lareina','Turner','lartu136'),(37436421399,'Kiona','Moran','kiomo137'),(38423182099,'Austin','Ray','ausra138'),(38630795499,'Cherokee','Francis','chefr139'),(38877751599,'Gary','Wall','garwa140'),(40200309299,'Roary','Chavez','roach141'),(41816890399,'Yoko','Davidson','yokda142'),(42490493299,'Kameko','Lambert','kamla143'),(43790352999,'Lesley','Dejesus','lesde144'),(46212688999,'Taylor','Cook','tayco145'),(46218621599,'Brent','Phelps','breph146'),(46369316399,'Shannon','Mcintosh','shamc147'),(46760482799,'Nathaniel','Shepard','natsh148'),(47628312799,'Maile','Singleton','maisi149'),(48244828599,'Montana','Mcdowell','monmc150'),(48358249299,'Libby','Evans','libev151'),(48524101699,'Cally','Roberts','calro152'),(50725413199,'Malachi','Paul','malpa153'),(52634082799,'Lacey','Romero','lacro154'),(53443124399,'Blaze','Alvarez','blaal155'),(53561483999,'Hilel','Henry','hilhe156'),(53848069199,'Elmo','Hewitt','student157'),(54585029399,'Rashad','Michael','student158'),(56359562699,'Glenna','Brock','student159'),(56640079599,'Cheyenne','Dixon','student160'),(56937121099,'Zia','Nguyen','student161'),(57392217699,'Basil','Harding','student162'),(57415306099,'Vladimir','Suarez','student163'),(61550881499,'Emerson','Palmer','student164'),(62262940099,'Thaddeus','Dillard','student165'),(62296762699,'Sheila','Fitzgerald','student166'),(62801595899,'Adena','Ryan','student167'),(63428953999,'Driscoll','Allen','student168'),(65831126799,'Robin','Haynes','student169'),(66245879399,'Anika','Bradford','student170'),(66569154599,'Melinda','Thornton','student171'),(66810642899,'Brooke','Buckner','student172'),(69249764499,'Lee','Mercado','student173'),(69485280399,'Edan','Flowers','student174'),(74426603899,'Quamar','Logan','student175'),(74522182799,'Penelope','Cardenas','student176'),(74530133799,'Kellie','Savage','student177'),(74657246199,'Deacon','Castillo','student178'),(75637829999,'Hadassah','Flowers','student179'),(76591062099,'Rose','Schmidt','student180'),(76978907699,'Shafira','Gray','student181'),(77824119099,'Callie','Mejia','student182'),(80473737499,'Kamal','Knight','student183'),(80531145199,'Vivian','Munoz','student184'),(82227957699,'Ocean','Bell','student185'),(82651084299,'Jeremy','Hill','student186'),(84750482099,'Colleen','Cabrera','student187'),(86853890899,'Amal','Love','student188'),(87991920199,'Rhea','Marshall','student189'),(88795205899,'Fay','Kramer','student190'),(89396827199,'Ramona','Williamson','student191'),(89932149599,'Stacey','Livingston','student192'),(90877149899,'Amethyst','Trujillo','student193'),(93811227199,'Kennan','Madden','student194'),(95861625599,'Donovan','Mueller','student195'),(96307923299,'Maile','Kemp','student196'),(96571018299,'Brent','Chapman','student197'),(98680336499,'Gloria','Blake','student198'),(99333469499,'Philip','Hendrix','student199');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-18 10:41:46
