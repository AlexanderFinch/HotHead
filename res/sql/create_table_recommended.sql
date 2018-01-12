-- --------------------------------------------------------

-- 
-- Table structure for table recommended
-- 

DROP TABLE IF EXISTS recommended;
CREATE TABLE recommended (
  recommended_key int NOT NULL AUTO_INCREMENT,
  sauce_key int NOT NULL,
  message varchar(255) NOT NULL,
  PRIMARY KEY  (recommended_key)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Data for table recommended
-- 

INSERT INTO recommended (sauce_key, message) VALUES ('4', 'Deliciously fruity, surprisingly spicy!');
INSERT INTO recommended (sauce_key, message) VALUES ('9', 'Try the hottest blade in the west!');
INSERT INTO recommended (sauce_key, message) VALUES ('18', 'This hot sauce is NOT pepper spray, but it WILL kick you in the taste buds quite DELICIOUSLY!');
INSERT INTO recommended (sauce_key, message) VALUES ('22', 'Adventurous flavor. Tingling tantalization. Make your next dining experience a zinger!');
INSERT INTO recommended (sauce_key, message) VALUES ('15', 'The citrus hot sauce that refreshes you as it burns!');
