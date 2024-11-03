DROP DATABASE IF EXISTS GestionCoches;
CREATE DATABASE  GestionCoches;
USE GestionCoches;
CREATE TABLE  coche (
    id integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    matricula varchar(10) DEFAULT NULL UNIQUE,
    marca varchar(20) DEFAULT NULL,
    modelo   varchar(20) DEFAULT NULL,
    tipo varchar(20) default null,
);
