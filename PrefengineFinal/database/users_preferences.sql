CREATE TABLE `users_preferences` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ipAddress` varchar(45) DEFAULT NULL,
  `departure` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `userPreference` varchar(100) DEFAULT NULL,
  `priceRange` varchar(45) DEFAULT NULL,
  `stopRange` varchar(45) DEFAULT NULL,
  `durationRange` varchar(45) DEFAULT NULL,
  `rankRange` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
