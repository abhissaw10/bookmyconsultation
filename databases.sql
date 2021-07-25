CREATE DATABASE IF NOT EXISTS `appointment`;
CREATE DATABASE IF NOT EXISTS `fooddelivery`;

# create root user and grant rights
CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
GRANT ALL ON *.* TO 'root'@'%';
