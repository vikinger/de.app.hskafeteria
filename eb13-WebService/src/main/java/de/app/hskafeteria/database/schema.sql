DROP TABLE benutzer;
DROP TABLE news;
DROP TABLE aktion;

CREATE TABLE benutzer (
	b_id INTEGER PRIMARY KEY,
	nachname VARCHAR(30),
	vorname VARCHAR(30),
	passwort VARCHAR(12),
	email VARCHAR(30)
);

CREATE TABLE news (
	n_id INTEGER PRIMARY KEY,
	datum INTEGER,
	titel VARCHAR(50),
	inhalt VARCHAR(300)
);

CREATE TABLE aktion (
	a_id INTEGER PRIMARY KEY,
	datum INTEGER,
	titel VARCHAR(50),
	inhalt VARCHAR(300)
);

CREATE SEQUENCE benutzer_id_sequence
  START WITH 4
  INCREMENT BY 1
  CACHE 100;
  
CREATE OR REPLACE TRIGGER trigger_benutzer
  BEFORE INSERT ON benutzer
  FOR EACH ROW
BEGIN
  SELECT benutzer_id_sequence.nextval
    INTO :new.b_id
    FROM dual;
END;