Set foreign_key_checks=0;
DROP TABLE IF EXISTS User;
CREATE TABLE User
(
    userID BIGINT PRIMARY KEY,
    vorname     text,
    nachname    text,
    key_cloack_name text
);

DROP TABLE IF EXISTS Gruppe;
CREATE TABLE Gruppe
(
    gruppeID BIGINT PRIMARY KEY,
    titel text,
    beschreibung text
);

DROP TABLE IF EXISTS Tags;
CREATE TABLE Tags
(
    tagID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_name text
);

DROP TABLE IF EXISTS Gruppenbelegung;
CREATE TABLE Gruppenbelegung
(
    upload_berechtigung boolean,
    gruppeID BIGINT ,
    userID BIGINT,
    foreign key (gruppeID) REFERENCES Gruppe (gruppeID),
    foreign key (userID)  REFERENCES User (userID)
);

DROP TABLE IF EXISTS Datei;
CREATE TABLE Datei
(
    dateiID      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    name         text,
    pfad         text,
    uploaderID   BIGINT,
    upload_datum DATE,
    veroeffentlichungs_datum DATE,
    datei_groesse      BIGINT,
    datei_typ      text,
    gruppeID      BIGINT,
    foreign key (uploaderID) REFERENCES User (userID),
    foreign key (gruppeID) REFERENCES Gruppe (gruppeID)
) ;

DROP TABLE IF EXISTS Tagnutzung;
CREATE TABLE Tagnutzung
(
    dateiID BIGINT,
    tagID BIGINT,
    foreign key (dateiID)  REFERENCES Datei (dateiID),
    foreign key (tagID)  REFERENCES Tags (tagID)
);
