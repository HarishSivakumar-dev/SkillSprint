-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: userinfo
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_application`
--

CREATE DATABASE IF NOT EXISTS userinfo;
USE userinfo;

DROP TABLE IF EXISTS `admin_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_application` (
  `id` int NOT NULL,
  `achievements` varchar(255) DEFAULT NULL,
  `applied_date` datetime(6) DEFAULT NULL,
  `auto_evaluation` bit(1) DEFAULT NULL,
  `avgrating` float NOT NULL,
  `documents_url` varchar(255) DEFAULT NULL,
  `exp_years` bigint DEFAULT NULL,
  `feedbackcount` int NOT NULL,
  `instructor_email` varchar(255) DEFAULT NULL,
  `is_verified` bit(1) DEFAULT NULL,
  `is_violated` bit(1) DEFAULT NULL,
  `promotion_status` enum('Pending','Promoted','Rejected') DEFAULT NULL,
  `reason_for_application` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `reviewed_on` datetime(6) DEFAULT NULL,
  `stud_trained` int NOT NULL,
  `totcourses` int NOT NULL,
  `type` enum('Auto','Manual') DEFAULT NULL,
  `admin_manager` int DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKee0lstligptq9mnn5e7vqj9mx` (`instructor_id`),
  KEY `FKpojwo40pnbnv5q6i9hjx7xk3k` (`admin_manager`),
  CONSTRAINT `FK73ptw1ukmaxo1m6g64rg3q3vh` FOREIGN KEY (`instructor_id`) REFERENCES `instructor_profile` (`id`),
  CONSTRAINT `FKpojwo40pnbnv5q6i9hjx7xk3k` FOREIGN KEY (`admin_manager`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_application`
--

LOCK TABLES `admin_application` WRITE;
/*!40000 ALTER TABLE `admin_application` DISABLE KEYS */;
INSERT INTO `admin_application` VALUES (1,'Won 1st place in BIT Hackathon 2025; Built SkillSprint learning platform','2026-02-05 16:26:41.323328',_binary '',4,'https://example.com/documents/resume.pdf',0,1,'harishss.ec24@bitsathy.ac.in',_binary '\0',_binary '','Rejected','Applying to enhance my skills in backend development and Spring Boot.',NULL,'2026-02-05 16:26:41.323328',2,2,'Auto',NULL,1);
/*!40000 ALTER TABLE `admin_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_application_seq`
--

DROP TABLE IF EXISTS `admin_application_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_application_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_application_seq`
--

LOCK TABLES `admin_application_seq` WRITE;
/*!40000 ALTER TABLE `admin_application_seq` DISABLE KEYS */;
INSERT INTO `admin_application_seq` VALUES (101);
/*!40000 ALTER TABLE `admin_application_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_logs`
--

DROP TABLE IF EXISTS `admin_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_logs` (
  `id` int NOT NULL,
  `admin_id` int NOT NULL,
  `last_active` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_logs`
--

LOCK TABLES `admin_logs` WRITE;
/*!40000 ALTER TABLE `admin_logs` DISABLE KEYS */;
INSERT INTO `admin_logs` VALUES (1,152,'2026-02-05');
/*!40000 ALTER TABLE `admin_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_logs_seq`
--

DROP TABLE IF EXISTS `admin_logs_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_logs_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_logs_seq`
--

LOCK TABLES `admin_logs_seq` WRITE;
/*!40000 ALTER TABLE `admin_logs_seq` DISABLE KEYS */;
INSERT INTO `admin_logs_seq` VALUES (51);
/*!40000 ALTER TABLE `admin_logs_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attempts_table`
--

DROP TABLE IF EXISTS `attempts_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attempts_table` (
  `id` int NOT NULL,
  `attemptcount` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `courseid` int DEFAULT NULL,
  `quizid` int DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8roj664d5k59t6i9gono1omlv` (`courseid`),
  KEY `FKk2unddjkunjjb89oclm46tcyd` (`quizid`),
  KEY `FK3dtvpdgnmuxyiisnx31swi0xm` (`userid`),
  CONSTRAINT `FK3dtvpdgnmuxyiisnx31swi0xm` FOREIGN KEY (`userid`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `FK8roj664d5k59t6i9gono1omlv` FOREIGN KEY (`courseid`) REFERENCES `course_details` (`id`),
  CONSTRAINT `FKk2unddjkunjjb89oclm46tcyd` FOREIGN KEY (`quizid`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attempts_table`
--

LOCK TABLES `attempts_table` WRITE;
/*!40000 ALTER TABLE `attempts_table` DISABLE KEYS */;
INSERT INTO `attempts_table` VALUES (304,1,'PASSED',1,304,252),(353,1,'PASSED',1,353,252),(402,1,'FAILED',1,402,252),(452,1,'PASSED',2,452,252),(502,3,'PASSED',2,502,252),(552,0,'NOT_COMPLETED',2,552,252);
/*!40000 ALTER TABLE `attempts_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attempts_table_seq`
--

DROP TABLE IF EXISTS `attempts_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attempts_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attempts_table_seq`
--

LOCK TABLES `attempts_table_seq` WRITE;
/*!40000 ALTER TABLE `attempts_table_seq` DISABLE KEYS */;
INSERT INTO `attempts_table_seq` VALUES (651);
/*!40000 ALTER TABLE `attempts_table_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint_audit_table`
--

DROP TABLE IF EXISTS `complaint_audit_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaint_audit_table` (
  `id` int NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `complaint_handled` bit(1) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl3xkwew4o7p61guev5wb8hng9` (`admin_id`),
  KEY `FKggdknv8m9d5nasptgrjkp2wof` (`user_id`),
  CONSTRAINT `FKggdknv8m9d5nasptgrjkp2wof` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `FKl3xkwew4o7p61guev5wb8hng9` FOREIGN KEY (`admin_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint_audit_table`
--

LOCK TABLES `complaint_audit_table` WRITE;
/*!40000 ALTER TABLE `complaint_audit_table` DISABLE KEYS */;
INSERT INTO `complaint_audit_table` VALUES (1,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 11:30:18.744554',152,252),(2,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 11:33:24.449726',152,252),(52,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:09:03.873074',152,252),(53,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:10:24.856729',152,252),(54,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:19:14.892020',152,252),(55,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:21:13.437472',152,252),(102,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:26:51.896863',152,252),(103,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:29:04.967885',152,252),(104,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:30:36.678863',152,252),(105,'The issue has been resolved. Thank you for your patience. We appreciate your understanding.',_binary '','2026-02-05 12:31:45.799437',152,252);
/*!40000 ALTER TABLE `complaint_audit_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint_audit_table_seq`
--

DROP TABLE IF EXISTS `complaint_audit_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaint_audit_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint_audit_table_seq`
--

LOCK TABLES `complaint_audit_table_seq` WRITE;
/*!40000 ALTER TABLE `complaint_audit_table_seq` DISABLE KEYS */;
INSERT INTO `complaint_audit_table_seq` VALUES (201);
/*!40000 ALTER TABLE `complaint_audit_table_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaints_table`
--

DROP TABLE IF EXISTS `complaints_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints_table` (
  `id` int NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` enum('Pending','Rejected','Resolved') DEFAULT NULL,
  `reported_inst` int DEFAULT NULL,
  `reporting_user` int DEFAULT NULL,
  `course_handled` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkxfawpkibg8p5uy1nolrqt4im` (`reported_inst`),
  KEY `FK7hbale8017j0m1ikpmf9vv5r5` (`reporting_user`),
  KEY `FKbji4ytarghhohtoaljy8l4pei` (`course_handled`),
  CONSTRAINT `FK7hbale8017j0m1ikpmf9vv5r5` FOREIGN KEY (`reporting_user`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `FKbji4ytarghhohtoaljy8l4pei` FOREIGN KEY (`course_handled`) REFERENCES `course_details` (`id`),
  CONSTRAINT `FKkxfawpkibg8p5uy1nolrqt4im` FOREIGN KEY (`reported_inst`) REFERENCES `instructor_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints_table`
--

LOCK TABLES `complaints_table` WRITE;
/*!40000 ALTER TABLE `complaints_table` DISABLE KEYS */;
INSERT INTO `complaints_table` VALUES (1,NULL,'2026-02-05','Skipping important topics','Resolved',1,252,NULL),(2,'The instructor was not responsive and did not address our concerns.','2026-02-05','misconduct in live sessions','Rejected',1,252,NULL),(52,'The instructor used unprofessional language during the class.','2026-02-05','inappropriate language','Rejected',1,252,1),(102,'The concepts were not explained clearly and sessions felt rushed.','2026-02-05','poor teaching quality','Resolved',1,252,1),(103,'Multiple sessions were cancelled without prior communication.','2026-02-05','session cancellation without notice','Resolved',1,252,1),(104,'The course content did not match what was promised initially.','2026-02-05','misleading course content','Resolved',1,252,1),(152,'The instructor showed unprofessional behavior during discussions.','2026-02-05','unprofessional behavior','Resolved',1,252,1),(153,'Live sessions were frequently cancelled without prior notice or rescheduling.','2026-02-05','frequent class cancellations','Resolved',1,252,1),(154,'Doubts raised in the course forum were ignored for a long time by the instructor.','2026-02-05','lack of course support','Resolved',1,252,1),(155,'The topics covered during the sessions did not match the syllabus mentioned on the course page.','2026-02-05','course content mismatch','Resolved',1,252,1);
/*!40000 ALTER TABLE `complaints_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaints_table_seq`
--

DROP TABLE IF EXISTS `complaints_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaints_table_seq`
--

LOCK TABLES `complaints_table_seq` WRITE;
/*!40000 ALTER TABLE `complaints_table_seq` DISABLE KEYS */;
INSERT INTO `complaints_table_seq` VALUES (251);
/*!40000 ALTER TABLE `complaints_table_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_completion_status`
--

DROP TABLE IF EXISTS `course_completion_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_completion_status` (
  `id` int NOT NULL,
  `course_completion_status` enum('Completed','CompletedAndCertified') DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK91y0dq0bl6j0ok09flx3qwqkn` (`user_id`,`course_id`),
  KEY `FKh677r2kkfnf3cpkffs2l8ktkm` (`course_id`),
  CONSTRAINT `FKh677r2kkfnf3cpkffs2l8ktkm` FOREIGN KEY (`course_id`) REFERENCES `course_details` (`id`),
  CONSTRAINT `FKqntyhesfgcurf1cr8u1rgvghl` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_completion_status`
--

LOCK TABLES `course_completion_status` WRITE;
/*!40000 ALTER TABLE `course_completion_status` DISABLE KEYS */;
INSERT INTO `course_completion_status` VALUES (1,NULL,NULL,NULL),(2,NULL,NULL,NULL),(52,'Completed',1,252),(102,NULL,NULL,NULL),(152,NULL,NULL,NULL),(202,NULL,NULL,NULL),(252,NULL,NULL,NULL),(302,NULL,NULL,NULL),(303,NULL,NULL,NULL);
/*!40000 ALTER TABLE `course_completion_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_completion_status_seq`
--

DROP TABLE IF EXISTS `course_completion_status_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_completion_status_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_completion_status_seq`
--

LOCK TABLES `course_completion_status_seq` WRITE;
/*!40000 ALTER TABLE `course_completion_status_seq` DISABLE KEYS */;
INSERT INTO `course_completion_status_seq` VALUES (401);
/*!40000 ALTER TABLE `course_completion_status_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_contents`
--

DROP TABLE IF EXISTS `course_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_contents` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `topicid` int NOT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjw23f5kdjwipkxb4bqdod4kj1` (`course_id`),
  CONSTRAINT `FKjw23f5kdjwipkxb4bqdod4kj1` FOREIGN KEY (`course_id`) REFERENCES `course_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_contents`
--

LOCK TABLES `course_contents` WRITE;
/*!40000 ALTER TABLE `course_contents` DISABLE KEYS */;
INSERT INTO `course_contents` VALUES (1,'An Introduction to programming to non computing branches','Introduction to Programming ',1,1),(2,'Programming with java, an introduction to the cup of coffee','Java Programming, an Overview',2,1),(52,'Annotations and their uses','SpringBoot Basics',1,2),(102,'This lecture is about the internals of SpringBoot','SpringBoot Internals',2,2);
/*!40000 ALTER TABLE `course_contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_contents_seq`
--

DROP TABLE IF EXISTS `course_contents_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_contents_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_contents_seq`
--

LOCK TABLES `course_contents_seq` WRITE;
/*!40000 ALTER TABLE `course_contents_seq` DISABLE KEYS */;
INSERT INTO `course_contents_seq` VALUES (201);
/*!40000 ALTER TABLE `course_contents_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_details`
--

DROP TABLE IF EXISTS `course_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_details` (
  `id` int NOT NULL,
  `catagory` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `rating` float NOT NULL,
  `status` enum('Active','InProcess','Inactive') DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41qqatohcwkbmgkjoaf7ysd74` (`instructor_id`),
  CONSTRAINT `FK41qqatohcwkbmgkjoaf7ysd74` FOREIGN KEY (`instructor_id`) REFERENCES `instructor_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_details`
--

LOCK TABLES `course_details` WRITE;
/*!40000 ALTER TABLE `course_details` DISABLE KEYS */;
INSERT INTO `course_details` VALUES (1,'Java','2026-02-03 11:50:06.259754','this course is an introduction of java programming to fuel development in young minds','2 hours','Beginner',4,'Active','Fundamentals of Java Programming ',1),(2,'Java','2026-02-03 11:50:59.071892','Intro course about springboot','5 Hours 30 mins','Beginner',0,'Active','Introduction to SpringBoot',1);
/*!40000 ALTER TABLE `course_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_details_seq`
--

DROP TABLE IF EXISTS `course_details_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_details_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_details_seq`
--

LOCK TABLES `course_details_seq` WRITE;
/*!40000 ALTER TABLE `course_details_seq` DISABLE KEYS */;
INSERT INTO `course_details_seq` VALUES (101);
/*!40000 ALTER TABLE `course_details_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_verification_token`
--

DROP TABLE IF EXISTS `email_verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verification_token` (
  `id` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `generated_time` datetime(6) DEFAULT NULL,
  `is_valid` bit(1) DEFAULT NULL,
  `is_verified` bit(1) DEFAULT NULL,
  `otp` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_verification_token`
--

LOCK TABLES `email_verification_token` WRITE;
/*!40000 ALTER TABLE `email_verification_token` DISABLE KEYS */;
INSERT INTO `email_verification_token` VALUES (1,'harishss.2k07@gmail.com','2026-02-01 22:05:48.855887',_binary '\0',_binary '',909337),(2,'harishsivakumar413@gmail.com','2026-02-02 14:55:21.293053',_binary '',_binary '\0',928674),(3,'harishsivakumar935@gmail.com','2026-02-02 14:56:06.936109',_binary '\0',_binary '',933421),(52,'harishsathiyasivakumar@gmail.com','2026-02-02 15:16:43.249921',_binary '\0',_binary '',538880),(53,'harishss.ec24@bitsathy.ac.in','2026-02-02 15:35:19.600309',_binary '\0',_binary '',165965),(54,NULL,'2026-02-02 15:36:03.759765',_binary '',_binary '\0',862485),(102,'shivashanmugamk2506@gmail.com','2026-02-03 11:33:51.999756',_binary '\0',_binary '',343655);
/*!40000 ALTER TABLE `email_verification_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_verification_token_seq`
--

DROP TABLE IF EXISTS `email_verification_token_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verification_token_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_verification_token_seq`
--

LOCK TABLES `email_verification_token_seq` WRITE;
/*!40000 ALTER TABLE `email_verification_token_seq` DISABLE KEYS */;
INSERT INTO `email_verification_token_seq` VALUES (201);
/*!40000 ALTER TABLE `email_verification_token_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment_data`
--

DROP TABLE IF EXISTS `enrollment_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollment_data` (
  `id` int NOT NULL,
  `enrollment_date` datetime(6) DEFAULT NULL,
  `inst_id` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKow578tugcmos4e5orati0bd8k` (`course_id`),
  KEY `FKsfyj7i2areapvmd2tawcd1h0t` (`user_id`),
  KEY `idx_distinctusers` (`inst_id`,`user_id`),
  CONSTRAINT `FKow578tugcmos4e5orati0bd8k` FOREIGN KEY (`course_id`) REFERENCES `course_details` (`id`),
  CONSTRAINT `FKsfyj7i2areapvmd2tawcd1h0t` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_data`
--

LOCK TABLES `enrollment_data` WRITE;
/*!40000 ALTER TABLE `enrollment_data` DISABLE KEYS */;
INSERT INTO `enrollment_data` VALUES (1,'2026-02-03 14:01:46.666574',1,'ACTIVE',1,252),(2,'2026-02-03 14:35:06.306738',1,'ACTIVE',2,252);
/*!40000 ALTER TABLE `enrollment_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment_data_seq`
--

DROP TABLE IF EXISTS `enrollment_data_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollment_data_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_data_seq`
--

LOCK TABLES `enrollment_data_seq` WRITE;
/*!40000 ALTER TABLE `enrollment_data_seq` DISABLE KEYS */;
INSERT INTO `enrollment_data_seq` VALUES (101);
/*!40000 ALTER TABLE `enrollment_data_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_table`
--

DROP TABLE IF EXISTS `feedback_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_table` (
  `id` int NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `course_id` int NOT NULL,
  `rating` int NOT NULL,
  `instructor_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmk6nvexwj1lf96janqpjbwn0r` (`user_id`),
  KEY `FKplqe1yfo9uky1eqpohrfxte2v` (`instructor_id`),
  CONSTRAINT `FK909ctn2ik5ccm7l0jqwffga14` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `FKplqe1yfo9uky1eqpohrfxte2v` FOREIGN KEY (`instructor_id`) REFERENCES `instructor_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_table`
--

LOCK TABLES `feedback_table` WRITE;
/*!40000 ALTER TABLE `feedback_table` DISABLE KEYS */;
INSERT INTO `feedback_table` VALUES (1,'The instructor was very knowledgeable and helpful. But could have improved the pace of the course.',1,4,1,252);
/*!40000 ALTER TABLE `feedback_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_table_seq`
--

DROP TABLE IF EXISTS `feedback_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_table_seq`
--

LOCK TABLES `feedback_table_seq` WRITE;
/*!40000 ALTER TABLE `feedback_table_seq` DISABLE KEYS */;
INSERT INTO `feedback_table_seq` VALUES (51);
/*!40000 ALTER TABLE `feedback_table_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_application`
--

DROP TABLE IF EXISTS `instructor_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_application` (
  `id` int NOT NULL,
  `applied_at` date DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `is_pending` bit(1) DEFAULT NULL,
  `is_rejected` bit(1) DEFAULT NULL,
  `linkedin` varchar(255) DEFAULT NULL,
  `qualification` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `resume_url` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3aqbuybh1wx1r12fhb5frwns9` (`user_id`),
  CONSTRAINT `FKrje8tyo08uw2n7hwboncwrvfy` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_application`
--

LOCK TABLES `instructor_application` WRITE;
/*!40000 ALTER TABLE `instructor_application` DISABLE KEYS */;
INSERT INTO `instructor_application` VALUES (1,'2026-02-02','Ashwin',_binary '\0',_binary '\0','https://www.linkedin.com/in/harishsivakumar07/','B.E CSE with distinction, 5 years of experience in software development','I need to teach young minds and get exposture to current trends from them','https://drive.google.com/file/d/1234567890abcdefg/view?usp=sharing',202);
/*!40000 ALTER TABLE `instructor_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_application_seq`
--

DROP TABLE IF EXISTS `instructor_application_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_application_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_application_seq`
--

LOCK TABLES `instructor_application_seq` WRITE;
/*!40000 ALTER TABLE `instructor_application_seq` DISABLE KEYS */;
INSERT INTO `instructor_application_seq` VALUES (51);
/*!40000 ALTER TABLE `instructor_application_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_profile`
--

DROP TABLE IF EXISTS `instructor_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_profile` (
  `id` int NOT NULL,
  `about_sec` varchar(255) DEFAULT NULL,
  `avg_rating` float NOT NULL,
  `completion_rate` float NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `github_url` varchar(255) DEFAULT NULL,
  `head_line` varchar(255) DEFAULT NULL,
  `is_violated` bit(1) DEFAULT NULL,
  `joined_date` date DEFAULT NULL,
  `linkedin_url` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `portfolio_url` varchar(255) DEFAULT NULL,
  `rate_count` int NOT NULL,
  `rate_sum` int NOT NULL,
  `short_bio` varchar(255) DEFAULT NULL,
  `tot_courses` int NOT NULL,
  `tot_exp` varchar(255) DEFAULT NULL,
  `tot_reviews` int NOT NULL,
  `total_cleared` int NOT NULL,
  `total_registered` int NOT NULL,
  `web_url` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKe2h78934ia4cw2x3n80b35vw3` (`user_id`),
  CONSTRAINT `FKii5s640kfw9by0thpv6kmwbu9` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_profile`
--

LOCK TABLES `instructor_profile` WRITE;
/*!40000 ALTER TABLE `instructor_profile` DISABLE KEYS */;
INSERT INTO `instructor_profile` VALUES (1,'I love building scalable backend systems and responsive frontend apps. Always learning new technologies and improving my skills.',4,50,'Ashwin','https://github.com/yourusername',NULL,_binary '','2026-02-02','https://www.linkedin.com/in/yourprofile','harishss.ec24@bitsathy.ac.in','+911234567890','https://yourportfolio.com',0,0,'Passionate developer with 2 years of experience in web applications.',2,'0  Years0  Months4  Days',1,1,2,'https://yourwebsite.com',202);
/*!40000 ALTER TABLE `instructor_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_profile_seq`
--

DROP TABLE IF EXISTS `instructor_profile_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_profile_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_profile_seq`
--

LOCK TABLES `instructor_profile_seq` WRITE;
/*!40000 ALTER TABLE `instructor_profile_seq` DISABLE KEYS */;
INSERT INTO `instructor_profile_seq` VALUES (51);
/*!40000 ALTER TABLE `instructor_profile_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_skills`
--

DROP TABLE IF EXISTS `instructor_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_skills` (
  `inst_id` int NOT NULL,
  `skill_id` int NOT NULL,
  KEY `FKgq0ktpmt89c3xuelna6qus4ke` (`skill_id`),
  KEY `FKior073126ig7dhy02vwxr7oqw` (`inst_id`),
  CONSTRAINT `FKgq0ktpmt89c3xuelna6qus4ke` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKior073126ig7dhy02vwxr7oqw` FOREIGN KEY (`inst_id`) REFERENCES `instructor_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_skills`
--

LOCK TABLES `instructor_skills` WRITE;
/*!40000 ALTER TABLE `instructor_skills` DISABLE KEYS */;
INSERT INTO `instructor_skills` VALUES (1,1),(1,2),(1,3),(1,4),(1,5);
/*!40000 ALTER TABLE `instructor_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_stat_update`
--

DROP TABLE IF EXISTS `instructor_stat_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_stat_update` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `delta_value` int NOT NULL,
  `event_type` enum('COMPLETION','COURSE','ENROLLMENT','FEEDBACK','RATING','UNENROLLMENT') DEFAULT NULL,
  `inst_id` int NOT NULL,
  `proceeded` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_stat_update`
--

LOCK TABLES `instructor_stat_update` WRITE;
/*!40000 ALTER TABLE `instructor_stat_update` DISABLE KEYS */;
INSERT INTO `instructor_stat_update` VALUES (1,'2026-02-03 14:01:46.719768',1,'ENROLLMENT',1,_binary ''),(2,'2026-02-03 14:35:06.344014',1,'ENROLLMENT',1,_binary ''),(52,NULL,0,NULL,0,NULL),(102,NULL,0,NULL,0,NULL),(152,'2026-02-04 22:01:27.560321',1,'COMPLETION',1,_binary ''),(202,'2026-02-05 15:13:02.882540',0,'RATING',1,_binary ''),(203,'2026-02-05 15:13:02.882540',1,'FEEDBACK',1,_binary ''),(252,NULL,0,NULL,0,NULL),(302,NULL,0,NULL,0,NULL),(352,NULL,0,NULL,0,NULL),(402,NULL,0,NULL,0,NULL),(452,NULL,0,NULL,0,NULL),(453,NULL,0,NULL,0,NULL);
/*!40000 ALTER TABLE `instructor_stat_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_stat_update_seq`
--

DROP TABLE IF EXISTS `instructor_stat_update_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_stat_update_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_stat_update_seq`
--

LOCK TABLES `instructor_stat_update_seq` WRITE;
/*!40000 ALTER TABLE `instructor_stat_update_seq` DISABLE KEYS */;
INSERT INTO `instructor_stat_update_seq` VALUES (551);
/*!40000 ALTER TABLE `instructor_stat_update_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_updated_table`
--

DROP TABLE IF EXISTS `instructor_updated_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_updated_table` (
  `id` int NOT NULL,
  `date` date DEFAULT NULL,
  `is_promoted` bit(1) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqfcigknalv6q167suv8tla4cw` (`admin_id`),
  KEY `FK5ibl72a9el2pbrvnkmw0ojw5f` (`user_id`),
  CONSTRAINT `FK5ibl72a9el2pbrvnkmw0ojw5f` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `FKqfcigknalv6q167suv8tla4cw` FOREIGN KEY (`admin_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_updated_table`
--

LOCK TABLES `instructor_updated_table` WRITE;
/*!40000 ALTER TABLE `instructor_updated_table` DISABLE KEYS */;
INSERT INTO `instructor_updated_table` VALUES (1,'2026-02-02',_binary '','2026-02-02 16:19:19.538739',152,202);
/*!40000 ALTER TABLE `instructor_updated_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_updated_table_seq`
--

DROP TABLE IF EXISTS `instructor_updated_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_updated_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_updated_table_seq`
--

LOCK TABLES `instructor_updated_table_seq` WRITE;
/*!40000 ALTER TABLE `instructor_updated_table_seq` DISABLE KEYS */;
INSERT INTO `instructor_updated_table_seq` VALUES (51);
/*!40000 ALTER TABLE `instructor_updated_table_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials_dto`
--

DROP TABLE IF EXISTS `materials_dto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materials_dto` (
  `id` int NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `topic_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32m8byp5gsexnddfad4v8kya6` (`topic_id`),
  CONSTRAINT `FK32m8byp5gsexnddfad4v8kya6` FOREIGN KEY (`topic_id`) REFERENCES `course_contents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials_dto`
--

LOCK TABLES `materials_dto` WRITE;
/*!40000 ALTER TABLE `materials_dto` DISABLE KEYS */;
INSERT INTO `materials_dto` VALUES (1,'video','https://www.youtube.com/watch?v=pNDF1iSoGak',1),(2,'docs','https://docs.google.com',1),(52,'video','https://www.youtube.com/watch?v=4XTsAAHW_Tc',1),(102,'AI content','https://chatgpt.com/c/6982325a-9bd8-83a7-b530-e0ccf195b13e',2),(103,'docs','https://docs.google.com/geiwifoho189',2),(104,'video','https://www.youtube.com/watch?v=4XTsAAHW_Tc',2),(152,'AI content','https://chatgpt.com/c/6982325a-9bd8-83a7-b530-e0ccf195b13e',52),(153,'docs','https://docs.google.com/geiwifoho189',52),(154,'video','https://www.youtube.com/watch?v=4XTsAAHW_Tc',2),(202,'video','https://www.youtube.com/watch?v=4XTsAAHW_Tc',52),(252,'PPT','file:///C:/Users/haris/Downloads/Agm%20Motor%20Iot%20Bom.pdf',102),(253,'PDF','https://docs.google.com/geiwifoho189',102),(254,'Quora','https://www.quora.com/springboot/questions',102);
/*!40000 ALTER TABLE `materials_dto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials_dto_seq`
--

DROP TABLE IF EXISTS `materials_dto_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materials_dto_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials_dto_seq`
--

LOCK TABLES `materials_dto_seq` WRITE;
/*!40000 ALTER TABLE `materials_dto_seq` DISABLE KEYS */;
INSERT INTO `materials_dto_seq` VALUES (351);
/*!40000 ALTER TABLE `materials_dto_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_logs`
--

DROP TABLE IF EXISTS `otp_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_logs` (
  `id` int NOT NULL,
  `generated_time` datetime(6) DEFAULT NULL,
  `is_valid` bit(1) DEFAULT NULL,
  `is_verified` bit(1) DEFAULT NULL,
  `otp` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmkj4d7q4faswog9dmkumix66l` (`user_id`),
  CONSTRAINT `FKmkj4d7q4faswog9dmkumix66l` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_logs`
--

LOCK TABLES `otp_logs` WRITE;
/*!40000 ALTER TABLE `otp_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_logs_seq`
--

DROP TABLE IF EXISTS `otp_logs_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_logs_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_logs_seq`
--

LOCK TABLES `otp_logs_seq` WRITE;
/*!40000 ALTER TABLE `otp_logs_seq` DISABLE KEYS */;
INSERT INTO `otp_logs_seq` VALUES (1);
/*!40000 ALTER TABLE `otp_logs_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL,
  `catagory` varchar(255) DEFAULT NULL,
  `difficuty` varchar(255) DEFAULT NULL,
  `option1` varchar(255) DEFAULT NULL,
  `option2` varchar(255) DEFAULT NULL,
  `option3` varchar(255) DEFAULT NULL,
  `option4` varchar(255) DEFAULT NULL,
  `option5` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `rightans` varchar(255) DEFAULT NULL,
  `inst_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (472,'JAVA','EASY','this','super','extends','implements','inherit','Which keyword is used to inherit a class in Java?','extends',1),(473,'JAVA','EASY','int','float','boolean','String','char','Which of these is not a Java primitive data type?','String',1),(474,'JAVA','MEDIUM','8 bits','16 bits','32 bits','64 bits','Depends on OS','What is the size of int data type in Java?','32 bits',1),(475,'JAVA','MEDIUM','List','ArrayList','Set','Vector','Queue','Which collection does not allow duplicate elements?','Set',1),(476,'JAVA','MEDIUM','NullPointerException','ArithmeticException','NumberFormatException','IOException','RuntimeException','Which exception is thrown when dividing by zero?','ArithmeticException',1),(477,'JAVA','HARD','Method overloading','Method overriding','Encapsulation','Abstraction','Inheritance','Which Java feature supports runtime polymorphism?','Method overriding',1),(478,'JAVA','HARD','Runnable','Serializable','Comparable','Cloneable','Callable','Which interface must be implemented to create a thread?','Runnable',1),(479,'JAVA','MEDIUM','static','private','protected','final','sealed','Which keyword is used to prevent inheritance in Java?','final',1),(480,'JAVA','HARD','To compile Java code','To execute bytecode','To write Java programs','To convert Java to machine code','To manage memory manually','What is the main purpose of the JVM?','To execute bytecode',1),(481,'JAVA','EASY','start()','run()','main()','init()','execute()','Which method is the entry point of a Java program?','main()',1),(512,'JAVA','EASY','class','new','this','object','create','Which keyword is used to create an object in Java?','new',1),(513,'JAVA','EASY','2value','value-1','_value','value@','int','Which of the following is a valid Java identifier?','_value',1),(514,'JAVA','MEDIUM','super','this','self','current','object','Which keyword is used to refer to the current object?','this',1),(515,'JAVA','MEDIUM','ArrayList','HashSet','HashMap','Vector','LinkedList','Which collection is synchronized by default?','Vector',1),(516,'JAVA','MEDIUM','java.io','java.util','java.lang','java.net','java.sql','Which package contains the Scanner class?','java.util',1),(517,'JAVA','HARD','Encapsulation','Abstraction','Method Overriding','Method Overloading','Composition','Which concept allows a subclass to provide a specific implementation of a method?','Method Overriding',1),(518,'JAVA','HARD','Stack','Heap','Method Area','PC Register','Native Stack','Which memory area stores class-level variables?','Method Area',1),(519,'JAVA','MEDIUM','catch','throw','throws','try','All of the above','Which keyword is used to handle exceptions?','All of the above',1),(520,'JAVA','HARD','Factory Pattern','Singleton Pattern','Prototype Pattern','Builder Pattern','Adapter Pattern','Which Java feature ensures that only one instance of a class is created?','Singleton Pattern',1),(521,'JAVA','EASY','=','==','!=','<>','equals','Which operator is used to compare two values?','==',1),(552,'JAVA','HARD','Thread waits normally','IllegalMonitorStateException is thrown','RuntimeException is thrown','Thread sleeps','Compilation error','What happens if a thread calls wait() without owning the object\'s monitor?','IllegalMonitorStateException is thrown',1),(553,'JAVA','HARD','All variables are stored in heap','Local variables are stored in stack','Static variables are stored in stack','Objects are stored in method area','Threads share stack memory','Which statement about Java memory model is correct?','Local variables are stored in stack',1),(554,'JAVA','HARD','For better performance','To support multithreading and security','To reduce memory usage','To simplify garbage collection','To avoid memory leaks','Why is String immutable in Java?','To support multithreading and security',1),(555,'JAVA','HARD','Compilation error','Objects behave correctly in HashMap','Duplicate keys may appear in HashMap','Program crashes','No effect','What will happen if equals() is overridden but hashCode() is not?','Duplicate keys may appear in HashMap',1),(556,'JAVA','HARD','Semaphore','CountDownLatch','CyclicBarrier','ReadWriteLock','ExecutorService','Which concurrency utility allows multiple threads to read but only one to write?','ReadWriteLock',1),(602,'SPRINGBOOT','EASY','@SpringApplication','@EnableSpring','@SpringBootApplication','@BootApplication','@SpringMain','Which annotation is used to mark the main class of a Spring Boot application?','@SpringBootApplication',1),(603,'SPRINGBOOT','EASY','spring.xml','config.xml','application.properties','boot.properties','settings.properties','Which file is used to configure application properties in Spring Boot?','application.properties',1),(604,'SPRINGBOOT','MEDIUM','@Inject','@Autowired','@Resource','@Value','All of the above','Which annotation is used to inject a dependency in Spring?','All of the above',1),(605,'SPRINGBOOT','MEDIUM','Jetty','JBoss','GlassFish','Tomcat','Undertow','Which embedded server is used by default in Spring Boot?','Tomcat',1),(606,'SPRINGBOOT','MEDIUM','@Controller','@Service','@RestController','@Component','@Repository','Which annotation is used to define RESTful web services in Spring Boot?','@RestController',1),(607,'SPRINGBOOT','HARD','XML Configuration','Classpath scanning and conditional beans','Manual bean registration','Servlet configuration','Annotations only','Which mechanism does Spring Boot use for auto-configuration?','Classpath scanning and conditional beans',1),(608,'SPRINGBOOT','HARD','@EnableTasks','@EnableScheduling','@ScheduledTask','@EnableAsync','@CronEnable','Which annotation is used to enable scheduling in Spring Boot?','@EnableScheduling',1),(609,'SPRINGBOOT','HARD','@RequestMapping','@PostMapping','@PutMapping','@GetMapping','@FetchMapping','Which annotation is used to map HTTP GET requests?','@GetMapping',1),(652,'SPRINGBOOT','MEDIUM','By XML parsing','Using @Configuration classes with @Conditional annotations and classpath scanning','Manual bean registration only','Servlet configuration','By reading application.properties only','How does Spring Boot perform auto-configuration at runtime?','Using @Configuration classes with @Conditional annotations and classpath scanning',1),(653,'SPRINGBOOT','MEDIUM','SpringApplicationRunner','CommandLineRunner','SpringApplication','ApplicationContext','ApplicationRunner','Which class bootstraps a Spring Boot application and runs the application context?','SpringApplication',1),(654,'SPRINGBOOT','MEDIUM','Jetty','Tomcat','Undertow','GlassFish','JBoss','Which embedded servlet container is started by default in Spring Boot if no external server is provided?','Tomcat',1),(655,'SPRINGBOOT','MEDIUM','@Autowired','@ConditionalOnClass and other @Conditional annotations','@ComponentScan','@EnableAutoConfiguration','@Bean','Which annotation ensures beans are created only if certain classes or beans are present on the classpath?','@ConditionalOnClass and other @Conditional annotations',1),(656,'SPRINGBOOT','HARD','ApplicationContext refresh → Beans instantiate → SpringApplication run → CommandLineRunner execute','SpringApplication run → ApplicationContext refresh → Beans instantiate → CommandLineRunner execute','CommandLineRunner execute → ApplicationContext refresh → Beans instantiate → SpringApplication run','Beans instantiate → SpringApplication run → ApplicationContext refresh → CommandLineRunner execute','ApplicationContext refresh → SpringApplication run → Beans instantiate → CommandLineRunner execute','What is the order of execution when a Spring Boot application starts?','SpringApplication run → ApplicationContext refresh → Beans instantiate → CommandLineRunner execute',1),(657,'SPRINGBOOT','MEDIUM','META-INF/spring.factories','application.properties','boot.xml','META-INF/beans.xml','resources.yaml','Which classpath resource is used by Spring Boot to discover auto-configuration classes?','META-INF/spring.factories',1),(658,'SPRINGBOOT','MEDIUM','Hardcoded to 8080 always','By reading server.port in application.properties or defaulting to 8080','By JVM arguments only','By scanning classpath','User must set manually','How does Spring Boot decide which port the embedded server should run on?','By reading server.port in application.properties or defaulting to 8080',1),(659,'SPRINGBOOT','MEDIUM','@EnableAutoConfiguration','@ComponentScan','@SpringBootApplication','@Configuration','@Bean','Which annotation triggers component scanning and auto-configuration together?','@SpringBootApplication',1),(660,'SPRINGBOOT','HARD','ApplicationContextInitializer','CommandLineRunner','SpringApplicationRunListener','ApplicationEventPublisher','BeanFactoryPostProcessor','Which interface allows running custom logic immediately after the Spring Boot application context is loaded?','CommandLineRunner',1),(661,'SPRINGBOOT','HARD','Reduces the need for XML configuration and boilerplate code','Automatically writes application code for developers','Makes the app run faster than Spring MVC','Disables component scanning','Only works with Tomcat','What is the main advantage of Spring Boot\'s auto-configuration mechanism?','Reduces the need for XML configuration and boilerplate code',1),(702,'SPRINGBOOT','MEDIUM','ApplicationContextInitializer','SpringApplication','CommandLineRunner','BeanFactory','ApplicationRunner','Which Spring Boot class is responsible for creating the ApplicationContext?','SpringApplication',1),(703,'SPRINGBOOT','MEDIUM','@EnableAutoConfiguration(exclude=...)','@ComponentScan(exclude=...)','@SpringBootApplication(exclude=...)','@Configuration(exclude=...)','@Bean(exclude=...)','Which annotation is used to exclude a specific auto-configuration class in Spring Boot?','@SpringBootApplication(exclude=...)',1),(704,'SPRINGBOOT','MEDIUM','ApplicationRunner','CommandLineRunner','SpringApplicationRunListener','BeanPostProcessor','ApplicationContextInitializer','Which interface is used to execute code after the Spring Boot application has started, with access to application arguments?','ApplicationRunner',1),(705,'SPRINGBOOT','MEDIUM','@ConditionalOnProperty','@ConditionalOnClass','@ConditionalOnBean','@ConditionalOnMissingBean','@Conditional','Which Spring Boot annotation allows registering beans only when a property is set to a specific value?','@ConditionalOnProperty',1),(706,'SPRINGBOOT','HARD','To listen to application events and startup phases during SpringApplication.run()','To register beans in the ApplicationContext','To execute CommandLineRunner beans','To manage embedded servers','To read application.properties only','What is the role of Spring Boot’s SpringApplicationRunListener?','To listen to application events and startup phases during SpringApplication.run()',1),(707,'SPRINGBOOT','MEDIUM','Binder','Environment','PropertySourcesPlaceholderConfigurer','SpringApplication','ConfigurationPropertiesBinding','Which class in Spring Boot is responsible for binding configuration properties to @ConfigurationProperties classes?','Binder',1),(708,'SPRINGBOOT','MEDIUM','By scanning META-INF/spring.factories and applying conditional annotations','By reading only application.properties','By manually registering all beans in main class','By XML bean configuration','By checking JVM arguments','How does Spring Boot detect which beans to instantiate for auto-configuration?','By scanning META-INF/spring.factories and applying conditional annotations',1),(709,'SPRINGBOOT','HARD','@SpringBootApplication is a combination of @Configuration, @EnableAutoConfiguration, and @ComponentScan','@EnableAutoConfiguration is a custom annotation to create beans','@SpringBootApplication disables component scanning','No difference, both are identical','@EnableAutoConfiguration only works for embedded servers','What is the difference between @SpringBootApplication and @EnableAutoConfiguration?','@SpringBootApplication is a combination of @Configuration, @EnableAutoConfiguration, and @ComponentScan',1),(710,'SPRINGBOOT','MEDIUM','@Configuration','@Component','@SpringBootApplication','@EnableAutoConfiguration','@Service','Which annotation is used to define a configuration class in Spring Boot that contains @Bean definitions?','@Configuration',1),(711,'SPRINGBOOT','MEDIUM','Banner API','SpringApplication.setBanner()','application.properties logging.banner','Spring Boot CLI','None of the above','Which Spring Boot feature allows customizing application startup messages and banner?','SpringApplication.setBanner()',1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_seq`
--

DROP TABLE IF EXISTS `questions_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_seq`
--

LOCK TABLES `questions_seq` WRITE;
/*!40000 ALTER TABLE `questions_seq` DISABLE KEYS */;
INSERT INTO `questions_seq` VALUES (801);
/*!40000 ALTER TABLE `questions_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `id` int NOT NULL,
  `isfinal` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topicid` int NOT NULL,
  `course_id` int DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  `sequence_number` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_sequence` (`course_id`,`sequence_number`),
  KEY `FK7dif9eil2px95p7ljo9q2g2t6` (`instructor_id`),
  CONSTRAINT `FK7dif9eil2px95p7ljo9q2g2t6` FOREIGN KEY (`instructor_id`) REFERENCES `instructor_profile` (`id`),
  CONSTRAINT `FKtjk2mruj5csm42vy18hux5d3t` FOREIGN KEY (`course_id`) REFERENCES `course_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (304,_binary '\0','week1Quiz',1,1,1,1),(353,_binary '\0','week2Quiz',2,1,1,2),(402,_binary '','finalQuiz',0,1,1,3),(452,_binary '\0','week1quiz',1,2,1,1),(502,_binary '\0','week2quiz',2,2,1,2),(552,_binary '\0','week2quiz2',2,2,1,3);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_questions`
--

DROP TABLE IF EXISTS `quiz_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_questions` (
  `id` int NOT NULL,
  `marks` int NOT NULL,
  `question_id` int DEFAULT NULL,
  `quiz_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKev41c723fx659v28pjycox15o` (`question_id`),
  KEY `FKcgp9e1c6ww3t383aui4w8feae` (`quiz_id`),
  CONSTRAINT `FKcgp9e1c6ww3t383aui4w8feae` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`),
  CONSTRAINT `FKev41c723fx659v28pjycox15o` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_questions`
--

LOCK TABLES `quiz_questions` WRITE;
/*!40000 ALTER TABLE `quiz_questions` DISABLE KEYS */;
INSERT INTO `quiz_questions` VALUES (272,1,472,304),(273,1,473,304),(274,2,474,304),(275,2,475,304),(276,2,476,304),(277,3,477,304),(278,3,478,304),(279,2,479,304),(280,3,480,304),(281,1,481,304),(312,1,512,353),(313,1,513,353),(314,2,514,353),(315,2,515,353),(316,2,516,353),(317,3,517,353),(318,3,518,353),(319,2,519,353),(320,3,520,353),(321,1,521,353),(352,3,552,402),(353,3,553,402),(354,3,554,402),(355,3,555,402),(356,3,556,402),(357,2,472,402),(358,2,475,402),(359,2,477,402),(360,2,514,402),(361,2,515,402),(402,1,602,452),(403,1,603,452),(404,2,604,452),(405,2,605,452),(406,2,606,452),(407,3,607,452),(408,3,608,452),(409,3,609,452),(410,1,472,452),(411,1,475,452),(412,1,477,452),(452,2,652,502),(453,2,653,502),(454,2,654,502),(455,2,655,502),(456,3,656,502),(457,2,657,502),(458,2,658,502),(459,2,659,502),(460,2,660,502),(461,1,661,502),(462,1,472,502),(463,1,475,502),(464,1,477,502),(502,2,702,552),(503,2,703,552),(504,2,704,552),(505,2,705,552),(506,2,706,552),(507,2,707,552),(508,2,708,552),(509,2,709,552),(510,2,710,552),(511,2,711,552);
/*!40000 ALTER TABLE `quiz_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_questions_seq`
--

DROP TABLE IF EXISTS `quiz_questions_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_questions_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_questions_seq`
--

LOCK TABLES `quiz_questions_seq` WRITE;
/*!40000 ALTER TABLE `quiz_questions_seq` DISABLE KEYS */;
INSERT INTO `quiz_questions_seq` VALUES (601);
/*!40000 ALTER TABLE `quiz_questions_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_seq`
--

DROP TABLE IF EXISTS `quiz_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_seq`
--

LOCK TABLES `quiz_seq` WRITE;
/*!40000 ALTER TABLE `quiz_seq` DISABLE KEYS */;
INSERT INTO `quiz_seq` VALUES (651);
/*!40000 ALTER TABLE `quiz_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL,
  `rolename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_SUPER_ADMIN'),(2,'ROLE_ADMIN_MANAGER'),(3,'ROLE_ADMIN'),(4,'ROLE_INSTRUCTOR'),(5,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_seq`
--

DROP TABLE IF EXISTS `roles_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_seq`
--

LOCK TABLES `roles_seq` WRITE;
/*!40000 ALTER TABLE `roles_seq` DISABLE KEYS */;
INSERT INTO `roles_seq` VALUES (1);
/*!40000 ALTER TABLE `roles_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill_approval`
--

DROP TABLE IF EXISTS `skill_approval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill_approval` (
  `id` int NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `skill_applied` varchar(255) DEFAULT NULL,
  `status` enum('Approved','Pending','Rejected') DEFAULT NULL,
  `handled_admin_id` int DEFAULT NULL,
  `instrutor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKibiovx3qi8u5qy5q7l8xy6d4a` (`handled_admin_id`),
  KEY `FKlxe539iv4cmgs7jlta8b60fas` (`instrutor_id`),
  CONSTRAINT `FKibiovx3qi8u5qy5q7l8xy6d4a` FOREIGN KEY (`handled_admin_id`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `FKlxe539iv4cmgs7jlta8b60fas` FOREIGN KEY (`instrutor_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill_approval`
--

LOCK TABLES `skill_approval` WRITE;
/*!40000 ALTER TABLE `skill_approval` DISABLE KEYS */;
INSERT INTO `skill_approval` VALUES (1,NULL,NULL,'Java','Approved',152,202),(2,NULL,NULL,'Spring Boot','Approved',152,202),(3,NULL,NULL,'Angular','Rejected',152,202),(4,NULL,NULL,'HTML','Approved',152,202),(5,NULL,NULL,'CSS','Rejected',152,202),(6,NULL,NULL,'JavaScript','Approved',152,202),(7,NULL,NULL,'SQL','Approved',152,202);
/*!40000 ALTER TABLE `skill_approval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill_approval_seq`
--

DROP TABLE IF EXISTS `skill_approval_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill_approval_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill_approval_seq`
--

LOCK TABLES `skill_approval_seq` WRITE;
/*!40000 ALTER TABLE `skill_approval_seq` DISABLE KEYS */;
INSERT INTO `skill_approval_seq` VALUES (101);
/*!40000 ALTER TABLE `skill_approval_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` int NOT NULL,
  `skill_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (1,'Java'),(2,'Spring Boot'),(3,'HTML'),(4,'JavaScript'),(5,'SQL');
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills_seq`
--

DROP TABLE IF EXISTS `skills_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills_seq`
--

LOCK TABLES `skills_seq` WRITE;
/*!40000 ALTER TABLE `skills_seq` DISABLE KEYS */;
INSERT INTO `skills_seq` VALUES (101);
/*!40000 ALTER TABLE `skills_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `streak_logs`
--

DROP TABLE IF EXISTS `streak_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `streak_logs` (
  `id` int NOT NULL,
  `date` date DEFAULT NULL,
  `quiz_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkv99i8j56qamyp3wjydo5dja4` (`quiz_id`),
  KEY `FKoybefb1ooxjyjjs48qbpmg96q` (`user_id`),
  CONSTRAINT `FKkv99i8j56qamyp3wjydo5dja4` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`),
  CONSTRAINT `FKoybefb1ooxjyjjs48qbpmg96q` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `streak_logs`
--

LOCK TABLES `streak_logs` WRITE;
/*!40000 ALTER TABLE `streak_logs` DISABLE KEYS */;
INSERT INTO `streak_logs` VALUES (1,'2026-02-04',304,252);
/*!40000 ALTER TABLE `streak_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `streak_logs_seq`
--

DROP TABLE IF EXISTS `streak_logs_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `streak_logs_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `streak_logs_seq`
--

LOCK TABLES `streak_logs_seq` WRITE;
/*!40000 ALTER TABLE `streak_logs_seq` DISABLE KEYS */;
INSERT INTO `streak_logs_seq` VALUES (51);
/*!40000 ALTER TABLE `streak_logs_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `streak_table`
--

DROP TABLE IF EXISTS `streak_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `streak_table` (
  `id` int NOT NULL,
  `streak` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `last_quiz_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKda3ky5a46q5uvdsk1ay2l5quh` (`user_id`),
  CONSTRAINT `FK839w4fs99li60yy134uotvkgr` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `streak_table`
--

LOCK TABLES `streak_table` WRITE;
/*!40000 ALTER TABLE `streak_table` DISABLE KEYS */;
INSERT INTO `streak_table` VALUES (1,0,1,NULL),(2,0,2,NULL),(52,0,52,NULL),(102,0,102,NULL),(152,0,152,NULL),(153,0,153,NULL),(202,0,202,NULL),(252,2,252,'2026-02-07');
/*!40000 ALTER TABLE `streak_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `streak_table_seq`
--

DROP TABLE IF EXISTS `streak_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `streak_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `streak_table_seq`
--

LOCK TABLES `streak_table_seq` WRITE;
/*!40000 ALTER TABLE `streak_table_seq` DISABLE KEYS */;
INSERT INTO `streak_table_seq` VALUES (351);
/*!40000 ALTER TABLE `streak_table_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `super_admin_analytics`
--

DROP TABLE IF EXISTS `super_admin_analytics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `super_admin_analytics` (
  `id` int NOT NULL,
  `last_computed_at` datetime(6) DEFAULT NULL,
  `monthly_new_registrations` int NOT NULL,
  `tot_admin_managers` int NOT NULL,
  `tot_admins` int NOT NULL,
  `tot_courses` int NOT NULL,
  `tot_instructors` bigint NOT NULL,
  `tot_students` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `super_admin_analytics`
--

LOCK TABLES `super_admin_analytics` WRITE;
/*!40000 ALTER TABLE `super_admin_analytics` DISABLE KEYS */;
INSERT INTO `super_admin_analytics` VALUES (1,'2026-02-11 15:20:00.095253',1,1,1,2,1,1);
/*!40000 ALTER TABLE `super_admin_analytics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `super_admin_analytics_seq`
--

DROP TABLE IF EXISTS `super_admin_analytics_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `super_admin_analytics_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `super_admin_analytics_seq`
--

LOCK TABLES `super_admin_analytics_seq` WRITE;
/*!40000 ALTER TABLE `super_admin_analytics_seq` DISABLE KEYS */;
INSERT INTO `super_admin_analytics_seq` VALUES (51);
/*!40000 ALTER TABLE `super_admin_analytics_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` int NOT NULL,
  `avg_certification_rate` float NOT NULL,
  `avg_clearing_rate` float NOT NULL,
  `avg_quizez_cleared` float NOT NULL,
  `college_name` varchar(255) DEFAULT NULL,
  `courses_completed` int NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` enum('Female','Male') DEFAULT NULL,
  `is_email_verified` bit(1) DEFAULT NULL,
  `joined_date` datetime(6) DEFAULT NULL,
  `level` enum('Advanced','AdvancedIntermediate','Beginner','Expert','Intermediate','Master') DEFAULT NULL,
  `linked_in` varchar(255) DEFAULT NULL,
  `no_of_certificates` int NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `quizzes_attended` int NOT NULL,
  `quizzes_cleared` int NOT NULL,
  `streak_maintanance` int NOT NULL,
  `tot_courses_enrolled` int NOT NULL,
  `user_bio` varchar(255) DEFAULT NULL,
  `year_of_study` int NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `avg_course_certification_rate` float NOT NULL,
  `avg_course_completion_rate` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKl6wgfhqrwuy4m1o7bs81ivg6x` (`user_name`),
  CONSTRAINT `FKn091es3htlq4hgbfiuw1doiax` FOREIGN KEY (`user_name`) REFERENCES `user_registration` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (52,0,0,0,NULL,0,NULL,NULL,'harishss.2k07@gmail.com','Harish',NULL,_binary '','2026-02-01 22:49:34.773308',NULL,NULL,0,NULL,NULL,0,0,0,0,NULL,0,'Haris_07',0,0),(102,0,0,0,NULL,0,NULL,NULL,'harishsivakumar935@gmail.com','Sathiya',NULL,_binary '','2026-02-02 14:59:07.589304',NULL,NULL,0,NULL,NULL,0,0,0,0,NULL,0,'Sathiya_83',0,0),(152,0,0,0,NULL,0,NULL,NULL,'harishsathiyasivakumar@gmail.com','Sivakumar',NULL,_binary '','2026-02-02 15:27:03.602228',NULL,NULL,0,NULL,NULL,0,0,0,0,NULL,0,'Siva_k',0,0),(202,0,0,0,NULL,0,NULL,NULL,'harishss.ec24@bitsathy.ac.in','Ashwin',NULL,_binary '','2026-02-02 16:00:30.457382',NULL,NULL,0,NULL,NULL,0,0,0,0,NULL,0,'ashwin_06',0,0),(252,0,50,80,'Bannari Amman Institute Of Technology',1,'2007-03-01','ECE','shivashanmugamk2506@gmail.com','Shiva','Male',_binary '','2026-02-03 11:43:43.076637','Beginner','https://www.linkedin.com/in/harishsivakumar07/',0,'9092418002','TamilNadu, India',5,4,1,2,'Iam an engineering student at xyz college, im an aspiring software developer',2,'shiva_07',0,0);
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile_delta`
--

DROP TABLE IF EXISTS `user_profile_delta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile_delta` (
  `id` int NOT NULL,
  `action` enum('Certificates','Completed','Enrolled','QuizAttended','QuizCleared','Unenrolled') DEFAULT NULL,
  `delta_value` int NOT NULL,
  `is_processed` bit(1) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile_delta`
--

LOCK TABLES `user_profile_delta` WRITE;
/*!40000 ALTER TABLE `user_profile_delta` DISABLE KEYS */;
INSERT INTO `user_profile_delta` VALUES (1,'Enrolled',1,_binary '',252),(2,'Enrolled',1,_binary '',252),(52,'QuizAttended',1,_binary '',252),(53,'QuizCleared',1,_binary '',252),(102,'QuizAttended',1,_binary '',252),(103,'QuizCleared',1,_binary '',252),(152,'QuizAttended',1,_binary '',252),(153,'Completed',1,_binary '',252),(202,'QuizAttended',1,_binary '',252),(203,'QuizCleared',1,_binary '',252),(252,'QuizAttended',1,_binary '',252),(352,'QuizCleared',1,_binary '',252),(402,'QuizCleared',1,_binary '\0',252),(403,'QuizCleared',1,_binary '\0',252);
/*!40000 ALTER TABLE `user_profile_delta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile_delta_seq`
--

DROP TABLE IF EXISTS `user_profile_delta_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile_delta_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile_delta_seq`
--

LOCK TABLES `user_profile_delta_seq` WRITE;
/*!40000 ALTER TABLE `user_profile_delta_seq` DISABLE KEYS */;
INSERT INTO `user_profile_delta_seq` VALUES (501);
/*!40000 ALTER TABLE `user_profile_delta_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile_seq`
--

DROP TABLE IF EXISTS `user_profile_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile_seq`
--

LOCK TABLES `user_profile_seq` WRITE;
/*!40000 ALTER TABLE `user_profile_seq` DISABLE KEYS */;
INSERT INTO `user_profile_seq` VALUES (351);
/*!40000 ALTER TABLE `user_profile_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_registration`
--

DROP TABLE IF EXISTS `user_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_registration` (
  `id` int NOT NULL,
  `account_status` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_email_verified` bit(1) DEFAULT NULL,
  `joined_date` datetime(6) DEFAULT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKp6d8fqfwcfrl4o2x7t92lptbs` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registration`
--

LOCK TABLES `user_registration` WRITE;
/*!40000 ALTER TABLE `user_registration` DISABLE KEYS */;
INSERT INTO `user_registration` VALUES (52,'ACTIVE','harishss.2k07@gmail.com',_binary '','2026-02-01 22:49:30.781573','2026-02-06 15:27:53.709795','Harish','$2a$15$kSNF.coCACKxmgKJTQAJoO.p1gV39.gS/IhWyegA2ZjcFJ7NoNEXa','Haris_07'),(102,'ACTIVE','harishsivakumar935@gmail.com',_binary '','2026-02-02 14:59:03.250247','2026-02-02 15:01:48.629890','Sathiya','$2a$15$R10Eh1SF8txK1MHAgn.z4.eoNS/oBRLH.N9XeehvrRMGaofI2Auw6','Sathiya_83'),(152,'ACTIVE','harishsathiyasivakumar@gmail.com',_binary '','2026-02-02 15:26:59.712391','2026-02-05 12:18:16.640911','Sivakumar','$2a$15$iNN0GitcT21NID5JwwLfCOPAikNlemzPeI1YVwYOqbqpujf5qZw9u','Siva_k'),(202,'ACTIVE','harishss.ec24@bitsathy.ac.in',_binary '','2026-02-02 16:00:26.577634','2026-02-03 13:53:57.087288','Ashwin','$2a$15$Ow8A/t.JWuYWXtJowAOTO.ku/qiAeqUHVL2Hr8Ea3S84Fj8hw.3YG','ashwin_06'),(252,'ACTIVE','shivashanmugamk2506@gmail.com',_binary '','2026-02-03 11:43:35.547166','2026-02-03 13:55:24.176442','Shiva','$2a$15$ddIV5lmr2Wmwn7uIVUJ9UOpxeYLxN5C5GcMt8Gi1aHOAbEH5h978e','shiva_07');
/*!40000 ALTER TABLE `user_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_registration_seq`
--

DROP TABLE IF EXISTS `user_registration_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_registration_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registration_seq`
--

LOCK TABLES `user_registration_seq` WRITE;
/*!40000 ALTER TABLE `user_registration_seq` DISABLE KEYS */;
INSERT INTO `user_registration_seq` VALUES (351);
/*!40000 ALTER TABLE `user_registration_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  KEY `FKmkl4rtae22cisobh4n49inho2` (`user_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKmkl4rtae22cisobh4n49inho2` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (52,1),(102,2),(152,3),(202,5),(202,4),(252,5);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `violations_table`
--

DROP TABLE IF EXISTS `violations_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `violations_table` (
  `id` int NOT NULL,
  `date_of_violation` datetime(6) DEFAULT NULL,
  `final_violation_count` int NOT NULL,
  `initial_violation_count` int NOT NULL,
  `violated` bit(1) NOT NULL,
  `instructor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK15clbmtgc59kiedo0csqpkp5m` (`instructor`),
  CONSTRAINT `FKektvhi747dhj45nuh26ni7u1g` FOREIGN KEY (`instructor`) REFERENCES `instructor_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `violations_table`
--

LOCK TABLES `violations_table` WRITE;
/*!40000 ALTER TABLE `violations_table` DISABLE KEYS */;
INSERT INTO `violations_table` VALUES (1,'2026-02-05 12:31:45.799437',3,3,_binary '',1);
/*!40000 ALTER TABLE `violations_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `violations_table_seq`
--

DROP TABLE IF EXISTS `violations_table_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `violations_table_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `violations_table_seq`
--

LOCK TABLES `violations_table_seq` WRITE;
/*!40000 ALTER TABLE `violations_table_seq` DISABLE KEYS */;
INSERT INTO `violations_table_seq` VALUES (51);
/*!40000 ALTER TABLE `violations_table_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-12 23:22:01
