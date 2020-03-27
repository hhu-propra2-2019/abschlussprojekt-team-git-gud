Set foreign_key_checks=0;
DROP TABLE IF EXISTS User;
CREATE TABLE User
(
    userID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    vorname     text NOT NULL,
    nachname    text NOT NULL,
    key_cloak_name VARCHAR(255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS Gruppe;
CREATE TABLE Gruppe
(
    gruppeID varchar(36) PRIMARY KEY NOT NULL,
    titel text NOT NULL,
    beschreibung text NOT NULL
);

DROP TABLE IF EXISTS Tags;
CREATE TABLE Tags
(
    tagID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS Gruppenbelegung;
CREATE TABLE Gruppenbelegung
(
    upload_berechtigung boolean NOT NULL,
    gruppeID varchar(36) NOT NULL,
    userID BIGINT NOT NULL,
    foreign key (gruppeID) REFERENCES Gruppe (gruppeID),
    foreign key (userID)  REFERENCES User (userID)
);

DROP TABLE IF EXISTS Datei;
CREATE TABLE Datei
(
    dateiID      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    name         text NOT NULL,
    uploaderID   BIGINT NOT NULL,
    upload_datum DATE NOT NULL,
    veroeffentlichungs_datum DATE NOT NULL,
    datei_groesse      BIGINT NOT NULL,
    datei_typ      text NOT NULL,
    gruppeID      varchar(36) NOT NULL,
    kategorie   text NOT NULL,
    foreign key (uploaderID) REFERENCES User (userID),
    foreign key (gruppeID) REFERENCES Gruppe (gruppeID)
) ;

DROP TABLE IF EXISTS Tagnutzung;
CREATE TABLE Tagnutzung
(
    dateiID BIGINT NOT NULL,
    tagID BIGINT NOT NULL,
    foreign key (dateiID)  REFERENCES Datei (dateiID),
    foreign key (tagID)  REFERENCES Tags (tagID)
);

DROP TABLE IF EXISTS Status;
CREATE TABLE Status
(
    status BIGINT NOT NULL
);