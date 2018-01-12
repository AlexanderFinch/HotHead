create user 'restUser'@'localhost' identified by 'specialuserpasswordthatcantbeguessed';
grant select on hothead.* to 'restUser'@'localhost';
grant insert on hothead.* to 'restUser'@'localhost';
grant update on hothead.* to 'restUser'@'localhost';