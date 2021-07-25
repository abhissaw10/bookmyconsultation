CREATE DATABASE IF NOT EXISTS `appointment`;
CREATE DATABASE IF NOT EXISTS `fooddelivery`;

CREATE USER 'appointment'@'%' IDENTIFIED BY 'appointment';
GRANT CREATE, ALTER, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `appointment`.* TO 'appointment'@'%';

CREATE USER 'fooddelivery'@'%' IDENTIFIED BY 'fooddelivery';
GRANT CREATE, ALTER, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `fooddelivery`.* TO 'fooddelivery'@'%';
