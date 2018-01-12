-- --------------------------------------------------------

-- 
-- Table structure for table review
-- 

DROP TABLE IF EXISTS review;
CREATE TABLE review (
  review_key int NOT NULL AUTO_INCREMENT,
  sauce_key int NOT NULL,
  user_key int NOT NULL,
  comments varchar(255),
  date_reviewed timestamp NOT NULL,
  rating numeric NOT NULL,
  PRIMARY KEY  (review_key)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Data for table review
-- 

INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (1, 1, 'It''s a great sauce to use on just about anything. Not too hot and a delicious flavor.', '1970-01-01 00:00:01', 5.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (2, 1, 'A must have for sauce lovers. Sweet (but not too sweet) and spicy. Great on pizza, sanwiches, just about anything!', '1975-01-01 00:00:01', 5.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (3, 1, 'Hot but still enjoyable. I love it on my eggs in the morning', '1987-01-01 00:00:01', 4.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (4, 2, 'Very hot. I burned my mouth on this on. Lava is right!', '1999-01-01 00:00:01', 3.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (5, 2, 'Good balance between heat and flavor. It''s mild if you like hot sauces', '1970-01-01 00:00:01', 4.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (6, 2, 'Great heat, good burn. Not for the faint of heart', '1999-01-01 00:00:01', 5.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (7, 2, 'Not as hot as I would hav expected it to be. ', '1979-01-01 00:00:01', 2.0);
INSERT INTO review (sauce_key, user_key, comments, date_reviewed, rating)
    VALUES (8, 2, 'A great standby for adding a little cajon flavor to things.', '1998-01-01 00:00:01', 5.0);
