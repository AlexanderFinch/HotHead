-- --------------------------------------------------------

-- 
-- Table structure for table user
-- 

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_key int NOT NULL AUTO_INCREMENT,
  screen_name varchar(25) NOT NULL UNIQUE,
  first_name varchar(25) NOT NULL,
  last_name varchar(25) NOT NULL,
  email varchar(60) NOT NULL UNIQUE,
  hash varchar(64),
  code int,
  code_expiry datetime,
  PRIMARY KEY  (user_key)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Data for table user
-- 

INSERT INTO users (user_key, screen_name, first_name, last_name, email)
    VALUES (1, 'A.Dawhg2525', 'Denny', 'Finch', 'cooldude@aol.com');
INSERT INTO users (user_key, screen_name, first_name, last_name, email)
    VALUES (2, 'finchrat', 'Coatrack', 'Finch', 'pardon@me.com');
