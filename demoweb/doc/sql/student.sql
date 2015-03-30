CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `version` int(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
)