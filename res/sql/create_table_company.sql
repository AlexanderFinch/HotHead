-- --------------------------------------------------------

-- 
-- Table structure for table company
-- 

DROP TABLE IF EXISTS company;
CREATE TABLE company (
  company_key int NOT NULL AUTO_INCREMENT,
  name varchar(255),
  PRIMARY KEY  (company_key)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
--
-- Data for table company
-- 

 INSERT INTO company (company_key, name)
    VALUES (1, 'Tapatio Foods, LLC.');
 INSERT INTO company (company_key, name)
    VALUES (2, 'Sriracha Hot Chili Sauce');
 INSERT INTO company (company_key, name)
    VALUES (3, 'Hot Licks');
 INSERT INTO company (company_key, name)
    VALUES (4, 'Volcanic Peppers, LLC.');
 INSERT INTO company (company_key, name)
    VALUES (5, 'Hungry Punker, Inc.');
 INSERT INTO company (company_key, name)
    VALUES (6, 'Melinda''s, Figueroa Brothers, Inc.');
 INSERT INTO company (company_key, name)
    VALUES (7, 'McIlhenny Company');
 INSERT INTO company (company_key, name)
    VALUES (8, 'Cajon Fiery Foods Co.');
 INSERT INTO company (company_key, name)
    VALUES (9, 'Dat''l Do-it Inc.');
 INSERT INTO company (company_key, name)
    VALUES (10, 'Born to Hula LLC.');
 INSERT INTO company (company_key, name)
    VALUES (11, 'Awesome Hot Sauce, Semper Fry LLC.');
 INSERT INTO company (company_key, name)
    VALUES (12, 'GraceKennedy Limited');
 INSERT INTO company (company_key, name)
    VALUES (13, 'Mike and Diane''s Gourmet Kitchen');
 INSERT INTO company (company_key, name)
    VALUES (14, 'Ashley Food Co., Inc.');
 INSERT INTO company (company_key, name)
    VALUES (15, 'Megamex Foods, LLC.');
