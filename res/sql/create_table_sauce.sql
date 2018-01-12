-- --------------------------------------------------------

-- 
-- Table structure for table sauce
-- 

DROP TABLE IF EXISTS sauce;
CREATE TABLE sauce (
  sauce_key int NOT NULL AUTO_INCREMENT,
  name varchar(255),
  description varchar(4000),
  company_key int NOT NULL,
  shu int,
  hotness varchar(10),
  pepper varchar(255) NOT NULL,
  image varchar(255) NOT NULL,
  verified boolean default 0,
  PRIMARY KEY  (sauce_key)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
--
-- Data for table sauce
-- 

 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (1, 'Tapatio Hot Sauce', 'Salsa Picante Hot Sauce', 1, 0, 'medium', 'Red Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (2, 'Sriracha Hot Chili Sauce', 'Sriracha Hot Chili Sauce', 2, 0, 'medium', 'Chili', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (3, 'Hot Licks Serrano Hot Sauce', 'Hot Licks Serrano Extra Hot Sauce', 3, 0, 'medium', 'Serrano', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (4, 'Lava Hot Sauce', 'Raspberry Scorpion Hot Sauce', 4, 0, 'xxxhot', 'Trinidad Scorpion, Habanero Peppers, Thai Dragon Peppers, Bhut Jolokia powder, Scorpion powder, 7-Pot Pepper powder', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (5, 'Gringo Bandito Hot Sauce', 'El Sabor, Mas Sabrosa. Super Hot', 5, 0, 'hot', 'Scorpion Chili Peppers, Jolokia Chili Peppers"', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (6, 'Melinda''s Ghost Pepper Sauce', 'Melinda''s Original Bhut Jolokia Pepper Sauce. All Natural. Gluten Free. XXXtra Hot', 6, 0, 'xxxhot', 'Red Habanero Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (7, 'Melinda''s XXXtra Hot', 'Melinda''s Original Habanero Pepper Sauce. All Natural. Gluten Free.', 6, 0, 'xhot', 'Red Habanero Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (8, 'Tabasco Hot Sauce', 'Tabasco Brand McIlhenny Co. Pepper Sauce. Made in the U.S.A. Avery Island, LA. Est. 1868', 7, 0, 'mild', 'Red Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (9, 'Reaper Sling Blade', 'Reaper Sling Blade Hot Sauce', 8, 0, 'xxxhot', 'Carolina Reaper, Bhut Jolokia', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (10, 'East Asia Teriyaki', 'East Asia Teriyaki Hot Sauce, a Dat''l Do-it Brand Specialty', 9, 0, 'mild', 'Aged Red Cayenne', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (11, 'B.V.I Blazes', 'British Virgin Islands Blazes', 9, 0, 'mild', 'Aged Tabasco, Habanero Mash', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (12, 'Pharaoh''s Revenge', 'Pharaoh''s Revenge Hot Sauce', 9, 0, 'mild', 'Aged Red Cayenne', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (13, 'Reaper of Sorrow', 'Born to Hula All Natural Hot Sauce', 10, 0, 'xxxhot', 'Red Jalapeno Peppers, Carolina Reaper Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (14, 'The Smokeboss', 'In honor of Andy "SMOKEBOSS" Mensing''s Awesome music & career, we proudly offer SMOKEBOSS hot sauce. Packed with Spice & flavor, this sauce incorporates the rich tastes of cayenne pepper, smoked chipotle, and paprika. Smokeboss is so Delicious, you''ll be screaming for an encore!', 11, 0, 'medium', 'Cayenne Peppers, Chipotle Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (15, 'Scotch Bonnet', 'Scotch Bonnet Hot Pepper Sauce.', 12, 0, 'hot', 'Scotch Bonnet Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (16, 'Ring of Fire Hot Sauce', 'Original Ring of Fire Habanero Hot Sauce. "So Hot, It''ll Burn Ya Twice." "ALL NATURAL" Ring of Fire Original Hot Sauce is made from the finest ingredients, including the legendary Habanero Chile. We make it in small batches to capture the garden fresh taste and quality of a genuine homemade sauce. Enriched with savory flavors of the Old Southwest and balanced with the right amount of heat, makes Ring of Fire Hot Sauce the perfect compliment to any dish.', 13, 0, 'hot', 'Habanero Peppers, Serrano Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (17, 'Zeus Juice Xtra Hot', 'Tons of Freshly Pealed Garlic make Zeus Juice worthy of the God of Gods. This is a Spicy Garlic paste with Cayenne Pepper implemented as the driving force behind it.', 11, 0, 'hot', 'Cayenne Peppers, Habanero Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (18, 'Venom', 'A SIX pepper medley makes this sauce a delicious gourmet force to be reckoned with. It''s not a contact burn. You are given a moment to taste your food, taste the flavor of the sauce, then the heat builds... And builds... And... you get the point.', 11, 0, 'medium', 'Cayenne Peppers, Serrano Peppers, Habanero Peppers, Thai Chile Peppers, Butch Trinidad Scorpion Peppers, Smoked  Ghost Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (19, 'The Eiffel Tower', 'Cayenne Hot Sauce. "a taste of Paris"', 9, 0, 'mild', 'Aged Red Cayenne Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (20, '357 Mad Dog Hot Sauce', 'This Sauce Will Blow You Away! 750,000 Scoville. Made with 6 Million Scoville Pepper Extract', 14, 750000, 'xxxhot', 'Chili Extract, Habanero Peppers, 160,000 Scoville Cayenne Peppers', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (21, 'La Victoria Salsa Brava Hot Sauce', 'La Victoria Salsa Brava Hot Sauce', 15, 0, 'hot', 'Jalapeno Peppers, Red California Chiles', '', 1);
 INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
    VALUES (22, 'Gringo Bandito Green Hot Sauce', 'El Sabor, Mas Sabrosa. Green Sauce', 5, 0, 'mild', 'Habanero Peppers, Serrano Peppers, More Peppers', '', 1);
-- INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
--    VALUES (1, 'Tapatio Hot Sauce', 'Salsa Picante Hot Sauce', 'Tapatio Foods, LLC.', 0, 'medium', 'red', '', 1);
-- INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
--    VALUES (1, 'Tapatio Hot Sauce', 'Salsa Picante Hot Sauce', 'Tapatio Foods, LLC.', 0, 'medium', 'red', '', 1);
-- INSERT INTO sauce (sauce_key, name, description, company_key, shu, hotness, pepper, image, verified)
--    VALUES (1, 'Tapatio Hot Sauce', 'Salsa Picante Hot Sauce', 'Tapatio Foods, LLC.', 0, 'medium', 'red', '', 1);
