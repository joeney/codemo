CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `authorId` int(11) DEFAULT NULL,
  `pubDate` datetime DEFAULT NULL,
  `pubPressId` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)