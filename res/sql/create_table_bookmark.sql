-- --------------------------------------------------------

-- 
-- Table structure for table bookmark
-- 

DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark (
  bookmark_key int NOT NULL AUTO_INCREMENT,
  sauce_key int NOT NULL,
  user_key int NOT NULL,
  PRIMARY KEY  (bookmark_key)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Data for table bookmark
-- 

INSERT INTO bookmark (sauce_key, user_key) VALUES (1, 1);
