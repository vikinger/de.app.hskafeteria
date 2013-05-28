DROP TABLE benutzer;
DROP TABLE news;
DROP TABLE aktion;
DROP TABLE angebot;
DROP TABLE bewertung;

DROP SEQUENCE benutzer_id_sequence;
DROP SEQUENCE news_id_sequence;
DROP SEQUENCE aktion_id_sequence;
DROP SEQUENCE angebot_id_sequence;
DROP SEQUENCE bewertung_id_sequence;

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
	tag VARCHAR(12),
	titel VARCHAR(50),
	inhalt VARCHAR(300)
);

CREATE TABLE angebot (
	an_id INTEGER PRIMARY KEY,
	art VARCHAR(30),
	titel VARCHAR(30),
	zutaten VARCHAR(300),
	preis INTEGER
);

CREATE TABLE bewertung (
	bw_id INTEGER PRIMARY KEY,
	datum INTEGER,
	punkte INTEGER,
	kommentar VARCHAR(300),
	BENUTZER_FK INTEGER,
	ANGEBOT_FK INTEGER
);

CREATE SEQUENCE benutzer_id_sequence
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
  
CREATE SEQUENCE news_id_sequence
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
  
CREATE SEQUENCE aktion_id_sequence
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
  
CREATE SEQUENCE angebot_id_sequence
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
  
CREATE SEQUENCE bewertung_id_sequence
  START WITH 1
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

CREATE OR REPLACE TRIGGER trigger_news
  BEFORE INSERT ON news
  FOR EACH ROW
BEGIN
  SELECT news_id_sequence.nextval
    INTO :new.n_id
    FROM dual;
END;

CREATE OR REPLACE TRIGGER trigger_aktion
  BEFORE INSERT ON aktion
  FOR EACH ROW
BEGIN
  SELECT aktion_id_sequence.nextval
    INTO :new.a_id
    FROM dual;
END;

CREATE OR REPLACE TRIGGER trigger_angebot
  BEFORE INSERT ON angebot
  FOR EACH ROW
BEGIN
  SELECT angebot_id_sequence.nextval
    INTO :new.an_id
    FROM dual;
END;

CREATE OR REPLACE TRIGGER trigger_bewertung
  BEFORE INSERT ON bewertung
  FOR EACH ROW
BEGIN
  SELECT bewertung_id_sequence.nextval
    INTO :new.bw_id
    FROM dual;
END;