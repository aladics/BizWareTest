/*
 bizware is the name of the MYSQL_USER set in .env, change it accordingly, bizdb is MYSQL_DATABASE
*/

REVOKE ALL ON *.* FROM 'bizware'@'%'; 
GRANT SELECT, INSERT, DELETE, UPDATE ON bizdb.* TO 'bizware'@'%';
FLUSH PRIVILEGES;

USE bizdb;
CREATE TABLE IF NOT EXISTS visitor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);