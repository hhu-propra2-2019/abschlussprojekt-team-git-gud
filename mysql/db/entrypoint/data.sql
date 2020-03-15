-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: materialsammlung
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

DROP TABLE IF EXISTS `datei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `datei` (
    `dateiid` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` text NOT NULL,
    `pfad` text NOT NULL,
    `uploader_userid` bigint(20) NOT NULL,
    `uploaddatum` date NOT NULL,
    `veroeffentlichungsdatum` date NOT NULL,
    `dateigroesse` bigint(20) NOT NULL,
    `dateityp` text NOT NULL,
    `gruppe_gruppeid` bigint(20) NOT NULL,
    `kategorie` varchar(100) NOT NULL,
  PRIMARY KEY (`dateiid`),
  KEY `uploaderID` (`uploader_userid`),
  KEY `gruppeID` (`gruppe_gruppeid`),
  CONSTRAINT `datei_ibfk_1` FOREIGN KEY (`uploader_userid`) REFERENCES `user` (`userid`),
  CONSTRAINT `datei_ibfk_2` FOREIGN KEY (`gruppe_gruppeid`) REFERENCES `gruppe` (`gruppeid`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Datei`
--

LOCK TABLES `datei` WRITE;
/*!40000 ALTER TABLE `datei` DISABLE KEYS */;
INSERT INTO `datei` VALUES (1,'Trevor','16651003 2276',916883599,'2019-02-20','2012-03-20',997720,'India',804187799,'Skript'),(2,'Lucas','16040224 9262',916883599,'2006-09-21','2020-12-19',529301,'Hanna',804187799,'Uebung'),(3,'Elijah','16730928 7048',916883599,'2006-11-21','2004-06-19',180428,'Alexis',804187799,'Uebung'),(4,'Herrod','16070404 1730',916883599,'2019-02-21','2008-05-19',551007,'Paloma',804187799,'Uebung'),(5,'Jelani','16621201 0729',1300980699,'2003-10-20','2021-05-19',287048,'Marah',2634173699,'Uebung'),(6,'Emery','16520101 1649',1300980699,'2030-08-21','2013-03-20',864748,'Chava',2634173699,'Uebung'),(7,'Kane','16161025 3849',1300980699,'2015-06-19','2019-01-21',895973,'Kimberly',2634173699,'Uebung'),(8,'Joseph','16470110 8351',1300980699,'2026-08-20','2026-06-19',486612,'Patricia',2634173699,'Uebung'),(9,'Nathaniel','16530805 5523',1300980699,'2023-12-19','2009-06-20',663279,'Cathleen',2634173699,'Uebung'),(10,'Clarke','16850621 8463',1323396599,'2018-10-20','2030-12-19',289690,'Oprah',2634173699,'Uebung'),(11,'Zeph','16700330 9502',1323396599,'2015-06-19','2020-01-21',710215,'Carla',2634173699,'Uebung'),(12,'Armando','16370802 7374',1323396599,'2007-05-19','2007-06-19',884517,'Claudia',2634173699,'Uebung'),(13,'Cade','16440907 9763',1323396599,'2015-11-19','2003-05-19',492077,'Rina',2634173699,'Uebung'),(14,'Kelly','16060207 3819',10255396699,'2029-10-19','2025-05-21',640157,'Fleur',10556995299,'Uebung'),(15,'Jacob','16531008 6417',10255396699,'2018-08-19','2014-02-21',752439,'Abigail',10556995299,'Uebung'),(16,'David','16821214 6008',10255396699,'2020-03-21','2028-11-20',810638,'Hiroko',10556995299,'Uebung'),(17,'Lionel','16330506 2360',10255396699,'2003-09-20','2009-05-20',195212,'Sara',10556995299,'Uebung'),(18,'Cyrus','16400222 2521',10255396699,'2022-08-19','2008-02-20',590964,'Samantha',10556995299,'Uebung'),(19,'Quamar','16781112 4572',10255396699,'2006-09-19','2003-08-20',61047,'Emerald',10556995299,'Skript'),(20,'Kuame','16320722 7574',10255396699,'2022-05-19','2029-10-19',446856,'Debra',10556995299,'Skript'),(21,'Joshua','16301022 0865',10255396699,'2018-09-20','2001-06-20',14284,'Lillith',10556995299,'Skript'),(22,'Adrian','16791107 6011',99333469499,'2027-08-19','2016-12-20',47375,'MacKenzie',17805362299,'Skript'),(23,'Roth','16210730 2172',99333469499,'2025-02-21','2001-09-19',665354,'Quin',17805362299,'Skript'),(24,'Devin','16380317 3735',99333469499,'2024-10-19','2027-11-21',199985,'Denise',17805362299,'Skript'),(25,'Brennan','16960710 7225',99333469499,'2001-01-21','2025-01-20',556296,'Maggy',17805362299,'Skript'),(26,'Abraham','16880318 1620',99333469499,'2010-12-21','2021-04-21',395555,'Desirae',17805362299,'Skript'),(27,'Rafael','16290309 3744',98680336499,'2009-10-19','2025-09-21',370414,'Mechelle',17805362299,'Skript'),(28,'Sebastian','16850711 6526',98680336499,'2016-10-20','2022-03-19',655854,'Yuri',17805362299,'Skript'),(29,'Basil','16001127 4198',98680336499,'2010-12-21','2008-08-21',441242,'Ariana',17805362299,'Skript'),(30,'Mark','16820925 0946',98680336499,'2017-12-20','2007-01-20',342249,'Autumn',17805362299,'Skript'),(31,'Lucian','16171103 0088',98680336499,'2012-09-21','2005-06-19',1043190,'Maggy',17805362299,'Skript'),(32,'Oren','16481216 7031',87991920199,'2016-08-20','2012-11-21',361102,'Maya',17805362299,'Skript'),(33,'Hop','16160903 1438',87991920199,'2022-08-19','2010-06-20',325620,'Alea',87951066299,'Skript'),(34,'Norman','16931004 2560',87991920199,'2030-08-21','2026-05-20',66102,'Clementine',87951066299,'Skript'),(35,'Thomas','16731206 7791',87991920199,'2025-11-19','2029-02-20',587651,'Ramona',87951066299,'Skript'),(36,'Orlando','16530221 7756',87991920199,'2022-04-19','2010-10-19',491484,'Winter',87951066299,'Skript'),(37,'Kenyon','16130330 2242',87991920199,'2012-07-21','2018-03-19',376856,'Liberty',87951066299,'Skript'),(38,'Burke','16130804 0854',87991920199,'2012-02-21','2004-12-21',218114,'Whitney',87951066299,'Skript'),(39,'Brett','16930628 2600',87991920199,'2012-08-20','2018-07-20',189519,'Carol',87951066299,'Skript'),(40,'Barry','16790712 6689',87991920199,'2004-03-20','2004-01-21',823545,'Quin',87951066299,'Skript'),(41,'Nero','16541230 0682',87991920199,'2023-11-19','2019-10-20',1019585,'Wynne',87951066299,'Skript'),(42,'Colin','16120117 2036',87991920199,'2017-05-21','2028-01-20',848287,'Wyoming',87951066299,'Uebung'),(43,'Hayes','16510805 2613',87991920199,'2021-08-20','2020-08-19',990459,'Flavia',87951066299,'Uebung'),(44,'Yardley','16691207 5295',87991920199,'2022-01-20','2018-03-21',254471,'Selma',87951066299,'Uebung'),(45,'Robert','16910526 0773',87991920199,'2010-05-19','2019-08-19',148540,'Germane',87951066299,'Uebung'),(46,'Prescott','16051203 7672',87991920199,'2005-11-19','2016-02-20',674421,'Kyla',87951066299,'Uebung'),(47,'Grant','16060722 8400',87991920199,'2026-10-20','2026-07-20',365529,'Unity',87951066299,'Uebung'),(48,'Martin','16490207 4642',87991920199,'2005-02-21','2014-07-20',877355,'Lesley',87951066299,'Uebung'),(49,'Matthew','16400529 9252',87991920199,'2003-07-21','2018-04-20',756521,'Athena',87951066299,'Uebung'),(50,'Brett','16120322 2078',87991920199,'2013-11-19','2004-04-20',454132,'Chelsea',87951066299,'Uebung'),(51,'Steven','16000123 0879',87991920199,'2011-04-20','2025-06-20',654191,'Darrel',87951066299,'Uebung'),(52,'Yoshio','16061202 0743',87991920199,'2015-06-20','2019-03-20',13377,'Hedy',87951066299,'Uebung'),(53,'Jarrod','16210809 8456',82651084299,'2017-12-21','2004-02-21',443310,'Hedy',77657118799,'Uebung'),(54,'Nero','16140625 8705',82651084299,'2022-07-21','2020-11-20',896612,'Chantale',77657118799,'Uebung'),(55,'Daniel','16880725 1031',82651084299,'2029-03-21','2011-05-19',81562,'Jade',77657118799,'Uebung'),(56,'Randall','16540728 6136',82651084299,'2016-03-19','2012-04-20',841080,'Brynne',77657118799,'Uebung'),(57,'Tiger','16420109 7831',82651084299,'2024-12-19','2024-07-21',824703,'Lillith',77657118799,'Uebung'),(58,'Cyrus','16651203 8495',82651084299,'2024-10-21','2023-08-19',643861,'Martina',77657118799,'Uebung'),(59,'Fletcher','16161129 1160',82651084299,'2019-12-21','2024-07-21',429285,'Gay',77657118799,'Uebung'),(60,'Hop','16280826 5512',82651084299,'2014-05-21','2013-11-20',633258,'Alma',77657118799,'Uebung'),(61,'Ashton','16060409 1058',82651084299,'2014-10-19','2018-08-21',666658,'Chastity',77657118799,'Uebung'),(62,'Hilel','16520408 3538',82651084299,'2002-08-19','2024-10-19',207103,'Sonia',77657118799,'Uebung'),(63,'Elmo','16700207 8124',82651084299,'2013-09-20','2022-05-20',490953,'Naomi',77657118799,'Uebung'),(64,'Thaddeus','16101002 9963',82651084299,'2008-09-19','2021-11-21',1012419,'Laurel',77657118799,'Uebung'),(65,'Carson','16671128 7133',82651084299,'2011-08-19','2027-08-20',824891,'Holly',77657118799,'Uebung'),(66,'Edward','16830208 9415',82651084299,'2016-08-20','2018-04-21',265001,'Lareina',77657118799,'Skript'),(67,'Ahmed','16641224 1736',82651084299,'2002-12-19','2009-10-20',756852,'Lareina',77657118799,'Skript'),(68,'Tarik','16860513 8562',82651084299,'2021-08-20','2003-08-21',409782,'Justina',77657118799,'Skript'),(69,'Herman','16330810 1322',82651084299,'2030-11-19','2026-05-20',52288,'Ariana',77657118799,'Skript'),(70,'Zeph','16170221 3123',82651084299,'2016-08-21','2006-01-20',759101,'Jana',77657118799,'Skript'),(71,'Aquila','16731010 4836',82651084299,'2029-08-19','2019-10-20',383107,'Reagan',77657118799,'Skript'),(72,'Hunter','16010223 4275',82651084299,'2021-04-19','2016-12-19',394948,'Stacy',77657118799,'Skript'),(73,'Berk','16510827 6527',82651084299,'2029-07-20','2013-07-20',443965,'Clio',77657118799,'Skript'),(74,'Ryder','16281128 3809',82651084299,'2019-12-21','2019-01-20',237947,'Vivian',77657118799,'Skript'),(75,'Channing','16781209 1580',82651084299,'2009-11-20','2003-02-21',90952,'McKenzie',77657118799,'Skript'),(76,'Wyatt','16771219 2926',82651084299,'2024-10-20','2028-03-21',107505,'Mara',77657118799,'Skript'),(77,'Eagan','16440416 4677',82651084299,'2023-05-19','2014-02-21',184787,'Virginia',77657118799,'Skript'),(78,'Lionel','16900806 1963',82651084299,'2015-05-21','2004-06-21',1048240,'Kameko',77657118799,'Skript'),(79,'Peter','16670218 8654',82651084299,'2021-02-20','2007-11-21',237631,'Fredericka',77657118799,'Skript'),(80,'Malachi','16280211 6570',82651084299,'2004-04-19','2007-09-20',424248,'Xyla',77657118799,'Skript'),(81,'Derek','16670514 7517',82651084299,'2001-04-21','2006-06-19',172100,'Madaline',77657118799,'Skript'),(82,'Norman','16730317 4507',82651084299,'2017-10-21','2028-07-20',992984,'Joelle',77657118799,'Skript'),(83,'Felix','16470420 3662',82651084299,'2021-10-21','2006-07-21',948370,'Risa',77657118799,'Skript'),(84,'Hedley','16210821 1638',82651084299,'2009-09-19','2025-04-20',12574,'Yvonne',77657118799,'Skript'),(85,'Wing','16350316 9132',82651084299,'2004-05-20','2021-01-20',96254,'Maia',77657118799,'Skript'),(86,'Baker','16250224 9309',82651084299,'2002-05-21','2005-10-20',717418,'Summer',77657118799,'Skript'),(87,'Drew','16920817 0093',82651084299,'2021-01-21','2026-10-20',932267,'April',77657118799,'Skript'),(88,'Thomas','16190421 5322',82651084299,'2024-09-19','2030-01-20',113606,'Kelsie',77657118799,'Skript'),(89,'Wyatt','16020605 7689',82651084299,'2028-04-21','2017-03-20',651730,'Riley',77657118799,'Skript'),(90,'Daquan','16080924 5764',82651084299,'2014-03-20','2005-10-21',1020246,'Scarlett',77657118799,'Skript'),(91,'Sawyer','16280623 2282',82651084299,'2004-03-20','2020-03-21',3228,'Jennifer',77657118799,'Skript'),(92,'Christopher','16710713 5308',82651084299,'2015-02-21','2027-05-20',922115,'Cheyenne',77657118799,'Skript'),(93,'Felix','16250510 6399',82651084299,'2025-10-21','2008-04-19',63065,'Gretchen',77657118799,'Skript'),(94,'Lars','16520905 6083',82651084299,'2015-05-19','2027-03-20',181088,'Chiquita',77657118799,'Skript'),(95,'Omar','16900727 0730',82651084299,'2027-09-20','2013-07-21',722085,'Whoopi',77657118799,'Skript'),(96,'Wayne','16930105 9466',82651084299,'2025-09-21','2027-03-21',863578,'Briar',77657118799,'Skript'),(97,'Cain','16750701 1927',82651084299,'2031-03-20','2014-11-19',60678,'Gisela',77657118799,'Skript'),(98,'Patrick','16531108 2589',82651084299,'2025-10-21','2015-04-19',526386,'Velma',77657118799,'Skript'),(99,'Gary','16571103 9429',82651084299,'2004-09-20','2002-09-21',911170,'Cynthia',77657118799,'Skript'),(100,'Graiden','16470602 4264',82651084299,'2019-10-21','2014-04-20',29812,'Audra',77657118799,'Skript');
/*!40000 ALTER TABLE `datei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gruppe`
--

DROP TABLE IF EXISTS `gruppe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppe` (
  `gruppeid` bigint(20) NOT NULL,
  `titel` text NOT NULL,
  `beschreibung` text ,
  PRIMARY KEY (`gruppeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gruppe`
--

LOCK TABLES `gruppe` WRITE;
/*!40000 ALTER TABLE `gruppe` DISABLE KEYS */;
INSERT INTO `gruppe` VALUES (804187799,'El Monte','eleifend nec, malesuada ut, sem. Nulla interdum. Curabitur dictum. Phasellus'),(992896999,'Burlington','a, dui. Cras pellentesque. Sed dictum. Proin eget odio. Aliquam'),(1246425999,'Fayetteville','nibh dolor, nonummy ac, feugiat non, lobortis quis, pede. Suspendisse'),(2634173699,'Leers-et-Fosteau','Donec fringilla. Donec feugiat metus sit amet ante. Vivamus non'),(2896368899,'Dunfermline','consectetuer rhoncus. Nullam velit dui, semper et, lacinia vitae, sodales'),(3277082699,'Dubuisson','eu erat semper rutrum. Fusce dolor quam, elementum at, egestas'),(3704138299,'Frankfort','nibh lacinia orci, consectetuer euismod est arcu ac orci. Ut'),(4399075899,'Fogliano Redipuglia','lectus ante dictum mi, ac mattis velit justo nec ante.'),(6991330199,'Puerto Nariño','neque sed sem egestas blandit. Nam nulla magna, malesuada vel,'),(8514326799,'Shillong','sem, vitae aliquam eros turpis non enim. Mauris quis turpis'),(10556995299,'Seevetal','euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget'),(10890810699,'Torghar','nulla at sem molestie sodales. Mauris blandit enim consequat purus.'),(12998649399,'Antalya','mattis ornare, lectus ante dictum mi, ac mattis velit justo'),(13400316599,'Kotelniki','Donec felis orci, adipiscing non, luctus sit amet, faucibus ut,'),(15447023999,'Shikarpur','dolor. Donec fringilla. Donec feugiat metus sit amet ante. Vivamus'),(17339313099,'Rae Lakes','Sed molestie. Sed id risus quis diam luctus lobortis. Class'),(17346735399,'Jeju','dolor sit amet, consectetuer adipiscing elit. Aliquam auctor, velit eget'),(17802683799,'Zeveneken','urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus'),(17805362299,'Quetta','sem molestie sodales. Mauris blandit enim consequat purus. Maecenas libero'),(19369317099,'Bursa','hendrerit. Donec porttitor tellus non magna. Nam ligula elit, pretium'),(19496214999,'Chile Chico','Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam auctor,'),(20875408799,'Rovereto','consequat enim diam vel arcu. Curabitur ut odio vel est'),(21948919399,'Freising','Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus. Morbi'),(23790016399,'Stratford-upon-Avon','Cras pellentesque. Sed dictum. Proin eget odio. Aliquam vulputate ullamcorper'),(24459951899,'Landshut','hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer,'),(24709997599,'Vichuquén','est ac mattis semper, dui lectus rutrum urna, nec luctus'),(25776199499,'Ansan','aliquet molestie tellus. Aenean egestas hendrerit neque. In ornare sagittis'),(28357539699,'East Jakarta','suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis'),(28453949599,'Enna','Aliquam fringilla cursus purus. Nullam scelerisque neque sed sem egestas'),(30817992899,'Nalinnes','nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam'),(33443797299,'Neusiedl am See','fringilla. Donec feugiat metus sit amet ante. Vivamus non lorem'),(33694344299,'Nieuwkerken-Waas','non, bibendum sed, est. Nunc laoreet lectus quis massa. Mauris'),(33873451299,'Whakatane','eu nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula.'),(34773997999,'Birori','velit. Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus.'),(35354568799,'Nevers','risus. Morbi metus. Vivamus euismod urna. Nullam lobortis quam a'),(37505013999,'Falun','et malesuada fames ac turpis egestas. Aliquam fringilla cursus purus.'),(39696595699,'Manukau','tellus eu augue porttitor interdum. Sed auctor odio a purus.'),(41897039899,'Idaho Falls','ornare sagittis felis. Donec tempor, est ac mattis semper, dui'),(42667213699,'Devon','sapien. Cras dolor dolor, tempus non, lacinia at, iaculis quis,'),(43536180299,'Aartrijke','volutpat ornare, facilisis eget, ipsum. Donec sollicitudin adipiscing ligula. Aenean'),(43556770699,'Veraval','orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu,'),(43857657599,'Omal','netus et malesuada fames ac turpis egestas. Aliquam fringilla cursus'),(46451326099,'Raiganj','Pellentesque ultricies dignissim lacus. Aliquam rutrum lorem ac risus. Morbi'),(47712749799,'Rahimyar Khan','vel, convallis in, cursus et, eros. Proin ultrices. Duis volutpat'),(48632252899,'Greater Sudbury','congue. In scelerisque scelerisque dui. Suspendisse ac metus vitae velit'),(48972040799,'Te Puke','Mauris quis turpis vitae purus gravida sagittis. Duis gravida. Praesent'),(49850930499,'St. Andrews','placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl.'),(52855963599,'Fraser-Fort George','In tincidunt congue turpis. In condimentum. Donec at arcu. Vestibulum'),(53217375099,'Marentino','Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie'),(53394283699,'Langley','Proin ultrices. Duis volutpat nunc sit amet metus. Aliquam erat'),(53419252499,'Schwaz','mauris erat eget ipsum. Suspendisse sagittis. Nullam vitae diam. Proin'),(54826308699,'Cropalati','elit, pharetra ut, pharetra sed, hendrerit a, arcu. Sed et'),(55398869099,'Laren','nibh vulputate mauris sagittis placerat. Cras dictum ultricies ligula. Nullam'),(57619996899,'Richmond','tortor. Nunc commodo auctor velit. Aliquam nisl. Nulla eu neque'),(57993307099,'Kirov','auctor non, feugiat nec, diam. Duis mi enim, condimentum eget,'),(59278567399,'Montenero Val Cocchiara','sollicitudin commodo ipsum. Suspendisse non leo. Vivamus nibh dolor, nonummy'),(59372442399,'Koersel','Fusce aliquam, enim nec tempus scelerisque, lorem ipsum sodales purus,'),(60600204399,'Wimborne Minster','lorem ipsum sodales purus, in molestie tortor nibh sit amet'),(61280206199,'Lienz','lorem, sit amet ultricies sem magna nec quam. Curabitur vel'),(61330989599,'Lublin','hendrerit neque. In ornare sagittis felis. Donec tempor, est ac'),(61468433099,'Labico','egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est,'),(61836068699,'Idaho Falls','quam. Pellentesque habitant morbi tristique senectus et netus et malesuada'),(66454463799,'Frankfort','convallis est, vitae sodales nisi magna sed dui. Fusce aliquam,'),(66523032499,'Bekegem','Curabitur consequat, lectus sit amet luctus vulputate, nisi sem semper'),(68899830499,'St. Johann in Tirol','fringilla mi lacinia mattis. Integer eu lacus. Quisque imperdiet, erat'),(69808697299,'Fort Smith','eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus'),(70244620999,'Graz','erat nonummy ultricies ornare, elit elit fermentum risus, at fringilla'),(70366228299,'Arauca','Duis mi enim, condimentum eget, volutpat ornare, facilisis eget, ipsum.'),(70439586499,'Tomaszów Mazowiecki','habitant morbi tristique senectus et netus et malesuada fames ac'),(70711335899,'Emarèse','sit amet, consectetuer adipiscing elit. Etiam laoreet, libero et tristique'),(71425358399,'Yumbel','eleifend. Cras sed leo. Cras vehicula aliquet libero. Integer in'),(72564172199,'Hasselt','vitae risus. Duis a mi fringilla mi lacinia mattis. Integer'),(72792956199,'Montpelier','ac libero nec ligula consectetuer rhoncus. Nullam velit dui, semper'),(72795873299,'Carson City','sapien, cursus in, hendrerit consectetuer, cursus et, magna. Praesent interdum'),(72831548899,'Amelia','id magna et ipsum cursus vestibulum. Mauris magna. Duis dignissim'),(73879313499,'Évreux','ut nisi a odio semper cursus. Integer mollis. Integer tincidunt'),(75248776499,'Lecce','eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus'),(75297960399,'Turriaco','odio. Nam interdum enim non nisi. Aenean eget metus. In'),(75363639299,'Saint-Laurent','sem eget massa. Suspendisse eleifend. Cras sed leo. Cras vehicula'),(75554442499,'Lehrte','rutrum, justo. Praesent luctus. Curabitur egestas nunc sed libero. Proin'),(76827496599,'Bengkulu','nisi. Mauris nulla. Integer urna. Vivamus molestie dapibus ligula. Aliquam'),(77657118799,'Abohar','at arcu. Vestibulum ante ipsum primis in faucibus orci luctus'),(80672407299,'Torreón','malesuada fames ac turpis egestas. Fusce aliquet magna a neque.'),(87798956199,'Falun','ullamcorper eu, euismod ac, fermentum vel, mauris. Integer sem elit,'),(87951066299,'Ahrensburg','Maecenas malesuada fringilla est. Mauris eu turpis. Nulla aliquet. Proin'),(90330253299,'Montague','Suspendisse eleifend. Cras sed leo. Cras vehicula aliquet libero. Integer'),(90359361099,'IJlst','ac urna. Ut tincidunt vehicula risus. Nulla eget metus eu'),(90574075499,'Ciénaga','ridiculus mus. Aenean eget magna. Suspendisse tristique neque venenatis lacus.'),(91420503899,'Ospedaletto d\'Alpinolo','eu tellus. Phasellus elit pede, malesuada vel, venenatis vel, faucibus'),(91856184699,'Jefferson City','ligula. Aenean gravida nunc sed pede. Cum sociis natoque penatibus'),(94246917599,'Leduc','volutpat nunc sit amet metus. Aliquam erat volutpat. Nulla facilisis.'),(94841113799,'Independence','adipiscing elit. Etiam laoreet, libero et tristique pellentesque, tellus sem'),(96686149099,'Zaanstad','velit in aliquet lobortis, nisi nibh lacinia orci, consectetuer euismod'),(98547467399,'Manado','sapien imperdiet ornare. In faucibus. Morbi vehicula. Pellentesque tincidunt tempus'),(98669749599,'Lahore','faucibus leo, in lobortis tellus justo sit amet nulla. Donec'),(99455555199,'Mataró','eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce');
/*!40000 ALTER TABLE `gruppe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gruppenbelegung`
--

DROP TABLE IF EXISTS `gruppenbelegung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppenbelegung` (
  `uploadberechtigung` tinyint(1) NOT NULL,
  `gruppe_gruppeid` bigint(20) NOT NULL,
  `user_userid` bigint(20) NOT NULL,
  KEY `gruppeID` (`gruppe_gruppeid`),
  KEY `userID` (`user_userid`),
  CONSTRAINT `gruppenbelegung_ibfk_1` FOREIGN KEY (`gruppe_gruppeid`) REFERENCES `gruppe` (`gruppeid`),
  CONSTRAINT `gruppenbelegung_ibfk_2` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gruppenbelegung`
--

LOCK TABLES `gruppenbelegung` WRITE;
/*!40000 ALTER TABLE `gruppenbelegung` DISABLE KEYS */;
INSERT INTO `gruppenbelegung` VALUES (1,804187799,916883599),(1,2634173699,1300980699),(1,2634173699,1323396599),(0,2634173699,3422340399),(0,2896368899,4575695099),(0,3277082699,5507758999),(0,3704138299,6325366199),(0,4399075899,9291092799),(1,10556995299,10255396699),(0,8514326799,10484292999),(0,10556995299,10987488399),(0,10890810699,12364770199),(0,12998649399,12492335599),(0,13400316599,14374598099),(0,15447023999,14846488899),(0,17339313099,15247373999),(0,17346735399,15866719599),(0,17802683799,16324374099),(0,17805362299,17213408999),(0,19369317099,17335817199),(0,19496214999,17971635499),(0,20875408799,18383360199),(0,21948919399,20695825699),(0,23790016399,23604947799),(0,24459951899,23784357999),(0,24709997599,26252955099),(0,25776199499,26712762599),(0,28357539699,26863355599),(0,28453949599,28620950099),(0,30817992899,30743703099),(0,33443797299,30987478399),(0,33694344299,32603303699),(0,33873451299,33222646099),(0,34773997999,34648740399),(0,35354568799,35337943799),(0,37505013999,35959473899),(0,39696595699,36668265499),(0,41897039899,37436421399),(0,42667213699,38423182099),(0,43536180299,38630795499),(0,43556770699,38877751599),(0,43857657599,40200309299),(0,46451326099,41816890399),(0,47712749799,42490493299),(0,48632252899,43790352999),(0,48972040799,46212688999),(0,49850930499,46218621599),(0,52855963599,46369316399),(0,53217375099,46760482799),(0,53394283699,47628312799),(0,53419252499,48244828599),(0,54826308699,48358249299),(0,55398869099,48524101699),(0,57619996899,50725413199),(0,57993307099,52634082799),(0,59278567399,53443124399),(0,59372442399,53561483999),(0,60600204399,53848069199),(0,61280206199,54585029399),(0,61330989599,56359562699),(0,61468433099,56640079599),(0,61836068699,56937121099),(0,66454463799,57392217699),(0,66523032499,57415306099),(0,68899830499,61550881499),(0,69808697299,62262940099),(0,70244620999,62296762699),(0,70366228299,62801595899),(0,70439586499,63428953999),(0,70711335899,65831126799),(0,71425358399,66245879399),(0,72564172199,66569154599),(0,72792956199,66810642899),(0,72795873299,69249764499),(0,72831548899,69485280399),(0,73879313499,74426603899),(0,75248776499,74522182799),(0,75297960399,74530133799),(0,75363639299,74657246199),(0,75554442499,75637829999),(0,76827496599,76591062099),(0,77657118799,76978907699),(0,80672407299,77824119099),(0,87798956199,80473737499),(0,87951066299,80531145199),(0,90330253299,82227957699),(0,77657118799,82651084299),(0,90574075499,84750482099),(0,91420503899,86853890899),(1,17805362299,87991920199),(1,87951066299,87991920199),(0,94841113799,89396827199),(0,96686149099,89932149599),(0,98547467399,90877149899),(0,98669749599,93811227199),(0,99455555199,95861625599),(0,99455555199,96307923299),(0,99455555199,96571018299),(1,17805362299,98680336499),(1,17805362299,99333469499);
/*!40000 ALTER TABLE `gruppenbelegung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tagnutzung`
--

DROP TABLE IF EXISTS `tagnutzung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagnutzung` (
  `dateiid` bigint(20) NOT NULL,
  `tagid` bigint(20) NOT NULL,
  KEY `dateiid` (`dateiid`),
  KEY `tagid` (`tagid`),
  CONSTRAINT `tagnutzung_ibfk_1` FOREIGN KEY (`dateiid`) REFERENCES `datei` (`dateiid`),
  CONSTRAINT `tagnutzung_ibfk_2` FOREIGN KEY (`tagid`) REFERENCES `tags` (`tagid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tagnutzung`
--

LOCK TABLES `tagnutzung` WRITE;
/*!40000 ALTER TABLE `tagnutzung` DISABLE KEYS */;
INSERT INTO `tagnutzung` VALUES (56,15),(87,46),(59,19),(51,46),(61,53),(6,41),(14,63),(66,47),(64,51),(18,97),(49,57),(25,35),(88,88),(39,86),(32,2),(7,48),(2,91),(15,19),(48,32),(99,32),(55,58),(8,84),(66,75),(71,16),(34,60),(24,7),(97,64),(35,90),(27,24),(39,49),(84,93),(57,42),(95,55),(42,1),(28,97),(5,22),(97,27),(93,86),(63,83),(47,52),(1,89),(95,71),(95,78),(48,96),(17,66),(71,80),(75,76),(13,50),(15,100),(60,88),(70,40),(19,35),(1,85),(36,88),(16,52),(62,14),(22,2),(74,81),(37,57),(10,89),(60,41),(58,52),(55,93),(62,76),(90,94),(27,70),(61,43),(83,98),(81,91),(56,24),(10,91),(99,36),(8,33),(39,100),(67,48),(78,33),(26,9),(65,96),(97,65),(39,77),(25,66),(39,97),(98,41),(69,10),(60,97),(61,31),(44,91),(98,4),(16,100),(83,97),(21,94),(87,72),(81,57),(54,43),(15,59),(51,22),(84,49),(89,88),(76,36),(43,3);
/*!40000 ALTER TABLE `tagnutzung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `tagid` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagname` text NOT NULL,
  PRIMARY KEY (`tagid`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Luctus Lobortis Class LLC'),(2,'Ligula Aliquam Corp.'),(3,'Phasellus Libero Mauris Company'),(4,'Arcu Corporation'),(5,'Turpis Egestas PC'),(6,'Elit Pharetra Ut Industries'),(7,'Urna Nunc Quis Company'),(8,'Bibendum Fermentum Consulting'),(9,'Mauris Erat Eget LLC'),(10,'Magna Nec Inc.'),(11,'Feugiat Corporation'),(12,'Vivamus Nibh Dolor PC'),(13,'Sed Consequat Auctor Industries'),(14,'Dui In Ltd'),(15,'Tempor Erat Industries'),(16,'Velit Eu Sem Inc.'),(17,'Morbi Vehicula LLC'),(18,'Duis Corp.'),(19,'Aliquam Corporation'),(20,'Cursus In Industries'),(21,'Tempor Corporation'),(22,'Nibh Incorporated'),(23,'Ultricies Ornare Elit Consulting'),(24,'Vel Sapien LLP'),(25,'Quisque Ornare Tortor LLC'),(26,'Amet Massa Quisque Incorporated'),(27,'Felis Purus Ac PC'),(28,'Cras LLP'),(29,'Urna Associates'),(30,'Eleifend Cras Consulting'),(31,'Egestas Ligula Nullam Corporation'),(32,'Vulputate Corp.'),(33,'Consectetuer LLP'),(34,'Vitae Purus PC'),(35,'Sociis Natoque Penatibus Industries'),(36,'Et Eros Associates'),(37,'Lorem Limited'),(38,'Amet Incorporated'),(39,'Orci PC'),(40,'Turpis PC'),(41,'In Consequat Ltd'),(42,'Risus Donec Industries'),(43,'Est Vitae Incorporated'),(44,'Vestibulum Nec Euismod Ltd'),(45,'Amet Ante Vivamus LLC'),(46,'Id Industries'),(47,'Nullam Enim Company'),(48,'Tempus Risus Donec Consulting'),(49,'Nunc Company'),(50,'Commodo Limited'),(51,'Tempus Lorem LLC'),(52,'Tincidunt Adipiscing Incorporated'),(53,'Fringilla Institute'),(54,'Lorem Consulting'),(55,'Quis Accumsan Company'),(56,'Amet Diam Eu LLP'),(57,'Euismod Inc.'),(58,'Semper Foundation'),(59,'Sem Elit Industries'),(60,'A Purus Corporation'),(61,'Sit Corp.'),(62,'Mauris Sagittis Ltd'),(63,'Ut Erat Sed LLP'),(64,'Nonummy Associates'),(65,'Dictum Incorporated'),(66,'Lacus Quisque Ltd'),(67,'Ipsum Inc.'),(68,'Maecenas Consulting'),(69,'Nulla LLC'),(70,'A Dui Industries'),(71,'Ante Lectus Convallis Ltd'),(72,'Ornare Lectus Ltd'),(73,'Elit LLP'),(74,'Fringilla Donec Feugiat PC'),(75,'Vitae Posuere At Inc.'),(76,'Vulputate Corporation'),(77,'Augue LLP'),(78,'Adipiscing Elit Curabitur Ltd'),(79,'Enim Limited'),(80,'Pharetra Ut Company'),(81,'Mi Foundation'),(82,'Magna Praesent LLP'),(83,'Auctor Mauris Vel Institute'),(84,'Ipsum Curabitur Consequat LLC'),(85,'Nunc Pulvinar Institute'),(86,'Purus Gravida Sagittis Associates'),(87,'Faucibus Orci Luctus Inc.'),(88,'Egestas Inc.'),(89,'Aliquet Institute'),(90,'Risus Quisque Libero Industries'),(91,'Metus In Nec Incorporated'),(92,'Erat Nonummy Ultricies PC'),(93,'Amet Dapibus Institute'),(94,'Lorem Ipsum Associates'),(95,'Auctor Mauris Vel Associates'),(96,'Ut Cursus Limited'),(97,'Erat Volutpat Nulla Company'),(98,'Ornare Facilisis Foundation'),(99,'Eu Dui Cum Incorporated'),(100,'Lorem Incorporated');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` bigint(20) NOT NULL,
  `vorname` text NOT NULL,
  `nachname` text NOT NULL,
  `keycloakname` VARCHAR(255) UNIQUE NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (916883599,'Ali','Hughes','alihu100'),(1300980699,'Bruce','Morin','brumo101'),(1323396599,'Bevis','Garrison','bevga102'),(3422340399,'Hunter','Harrison','hunha103'),(4575695099,'Fitzgerald','Murray','fitmu104'),(5507758999,'Jeanette','Mathews','jeama105'),(6325366199,'Diana','Thornton','diath106'),(9291092799,'Isaiah','Mcleod','isamc107'),(10255396699,'Samson','Alford','samal108'),(10484292999,'Acton','Rivas','actri109'),(10987488399,'Danielle','Cochran','danco110'),(12364770199,'Nora','Shepherd','norsh111'),(12492335599,'Grant','Sosa','graso112'),(14374598099,'Raven','Graves','ravgr113'),(14846488899,'Regan','Frederick','regfr114'),(15247373999,'Oren','Frye','orefr115'),(15866719599,'Skyler','Schroeder','skysc116'),(16324374099,'Shelley','Bartlett','sheba117'),(17213408999,'Britanney','Allen','brial118'),(17335817199,'Gloria','Rojas','gloro119'),(17971635499,'Tyrone','Sparks','tyrsp120'),(18383360199,'Samantha','Camacho','samca121'),(20695825699,'Tanek','Kemp','tanke122'),(23604947799,'Roanna','Wong','roawo123'),(23784357999,'Briar','Kent','brike124'),(26252955099,'Myles','Boyd','mylbo125'),(26712762599,'Kenyon','Carrillo','kenca126'),(26863355599,'Herrod','Austin','herau127'),(28620950099,'Edan','Cunningham','edacu128'),(30743703099,'Maia','Mclean','maimc129'),(30987478399,'Wayne','Mckay','waymc130'),(32603303699,'Cameron','Clemons','camcl131'),(33222646099,'Zephania','Carson','zepca132'),(34648740399,'Madeline','Walter','madwa133'),(35337943799,'Lane','Padilla','lanpa134'),(35959473899,'Hamilton','Spencer','hamsp135'),(36668265499,'Lareina','Turner','lartu136'),(37436421399,'Kiona','Moran','kiomo137'),(38423182099,'Austin','Ray','ausra138'),(38630795499,'Cherokee','Francis','chefr139'),(38877751599,'Gary','Wall','garwa140'),(40200309299,'Roary','Chavez','roach141'),(41816890399,'Yoko','Davidson','yokda142'),(42490493299,'Kameko','Lambert','kamla143'),(43790352999,'Lesley','Dejesus','lesde144'),(46212688999,'Taylor','Cook','tayco145'),(46218621599,'Brent','Phelps','breph146'),(46369316399,'Shannon','Mcintosh','shamc147'),(46760482799,'Nathaniel','Shepard','natsh148'),(47628312799,'Maile','Singleton','maisi149'),(48244828599,'Montana','Mcdowell','monmc150'),(48358249299,'Libby','Evans','libev151'),(48524101699,'Cally','Roberts','calro152'),(50725413199,'Malachi','Paul','malpa153'),(52634082799,'Lacey','Romero','lacro154'),(53443124399,'Blaze','Alvarez','blaal155'),(53561483999,'Hilel','Henry','hilhe156'),(53848069199,'Elmo','Hewitt','student157'),(54585029399,'Rashad','Michael','student158'),(56359562699,'Glenna','Brock','student159'),(56640079599,'Cheyenne','Dixon','student160'),(56937121099,'Zia','Nguyen','student161'),(57392217699,'Basil','Harding','student162'),(57415306099,'Vladimir','Suarez','student163'),(61550881499,'Emerson','Palmer','student164'),(62262940099,'Thaddeus','Dillard','student165'),(62296762699,'Sheila','Fitzgerald','student166'),(62801595899,'Adena','Ryan','student167'),(63428953999,'Driscoll','Allen','student168'),(65831126799,'Robin','Haynes','student169'),(66245879399,'Anika','Bradford','student170'),(66569154599,'Melinda','Thornton','student171'),(66810642899,'Brooke','Buckner','student172'),(69249764499,'Lee','Mercado','student173'),(69485280399,'Edan','Flowers','student174'),(74426603899,'Quamar','Logan','student175'),(74522182799,'Penelope','Cardenas','student176'),(74530133799,'Kellie','Savage','student177'),(74657246199,'Deacon','Castillo','student178'),(75637829999,'Hadassah','Flowers','student179'),(76591062099,'Rose','Schmidt','student180'),(76978907699,'Shafira','Gray','student181'),(77824119099,'Callie','Mejia','student182'),(80473737499,'Kamal','Knight','student183'),(80531145199,'Vivian','Munoz','student184'),(82227957699,'Ocean','Bell','student185'),(82651084299,'Jeremy','Hill','student186'),(84750482099,'Colleen','Cabrera','student187'),(86853890899,'Amal','Love','student188'),(87991920199,'Rhea','Marshall','student189'),(88795205899,'Fay','Kramer','student190'),(89396827199,'Ramona','Williamson','student191'),(89932149599,'Stacey','Livingston','student192'),(90877149899,'Amethyst','Trujillo','student193'),(93811227199,'Kennan','Madden','student194'),(95861625599,'Donovan','Mueller','student195'),(96307923299,'Maile','Kemp','student196'),(96571018299,'Brent','Chapman','student197'),(98680336499,'Gloria','Blake','student198'),(99333469499,'Philip','Hendrix','student199');
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

-- Dump completed on 2020-03-10 16:08:06
