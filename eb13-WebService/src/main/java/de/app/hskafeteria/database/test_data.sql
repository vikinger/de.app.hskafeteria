INSERT INTO benutzer (nachname, vorname, passwort, email)
VALUES (
	'Admin', 'Admin', 'admin', 'admin@admin.com'
);

INSERT INTO benutzer (nachname, vorname, passwort, email)
VALUES (
	'Test', 'Test', 'test', 'test@test.com'
);

INSERT INTO benutzer (nachname, vorname, passwort, email)
VALUES (
	'Wurst', 'Hans', 'hanswurst', 'hans@wurst.com'
);

INSERT INTO news (datum, titel, inhalt)
VALUES (
	1369579440000, 'Pizza-Automat', 'Neuer Pizza-Automat wurde aufgestellt.'
);

INSERT INTO news (datum, titel, inhalt)
VALUES (
	1369579440000, 'Pasta-Automat', 'Neuer Pasta-Automat wurde aufgestellt.'
);

INSERT INTO news (datum, titel, inhalt)
VALUES (
	1369579440000, 'Kaffee-Automat', 'Neuer Kaffee-Automat wurde aufgestellt.'
);

INSERT INTO aktion (tag, titel, inhalt)
VALUES (
	'Dienstag', 'Burgertag', 'Jeden Dienstag gibt es leckere Burger!'
);

INSERT INTO aktion (tag, titel, inhalt)
VALUES (
	'Mittwoch', 'Pastatag', 'Jeden Mittwoch gibt es leckere Pasta!'
);

INSERT INTO aktion (tag, titel, inhalt)
VALUES (
	'Freitag', 'Pizzatag', 'Jeden Freitag gibt es leckere Pizza!'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Getraenke', 'Coca Cola', 'Schwarze Bruehe mit Zucker und Coffein', '100'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Getraenke', 'Fanta', 'Gelbe Bruehe mit Zucker', '100'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Suesses', 'Snickers', 'Schokoladenriegel mit Nüssen', '50'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Suesses', 'Bounty', 'Schokoladenriegel mit Kokosflocken', '50'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Belegte Broetchen', 'Salamibroetchen', 'Salami, Butter, Salat, Käse', '150'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Belegte Broetchen', 'Lyonerbroetchen', 'Lyoner, Butter, Salat, Tomate', '150'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Getraenke', 'Mezzo Mix', 'Braune Bruehe mit Zucker und Coffein', '100'
);

INSERT INTO bewertung (datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	1369579440000, '5', 'Originale Coca Cola ist immernoch die beste', 1, 1
);

INSERT INTO bewertung (datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	1369579440000, '3', 'Die gute alte Coke', 2, 1
);

INSERT INTO bewertung (datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	1369579440000, '4', 'Richtig gut und schoen kalt', 3, 1
);