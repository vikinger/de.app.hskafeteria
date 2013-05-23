DROP TABLE benutzer;
DROP TABLE news;

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