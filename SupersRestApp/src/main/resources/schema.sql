DROP TABLE IF EXISTS `supers`;
CREATE TABLE `supers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alignment` varchar(255) DEFAULT NULL,
  `combat` int DEFAULT NULL,
  `durability` int DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `intelligence` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `power` int DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `race` varchar(255) DEFAULT NULL,
  `speed` int DEFAULT NULL,
  `strength` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);