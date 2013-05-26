INSERT INTO benutzer (b_id, nachname, vorname, passwort, email)
VALUES (
	'1', 'Admin', 'Admin', 'admin', 'admin@admin.com'
);

INSERT INTO benutzer (b_id, nachname, vorname, passwort, email)
VALUES (
	'2', 'Test', 'Test', 'test', 'test@test.com'
);

INSERT INTO benutzer (b_id, nachname, vorname, passwort, email)
VALUES (
	'3', 'Wurst', 'Hans', 'hanswurst', 'hans@wurst.com'
);

INSERT INTO news (n_id, datum, titel, inhalt)
VALUES (
	'1', 1369579440000, 'Pizza-Automat', 'Neuer Pizza-Automat wurde aufgestellt.'
);

INSERT INTO news (n_id, datum, titel, inhalt)
VALUES (
	'2', 1369579440000, 'Pasta-Automat', 'Neuer Pasta-Automat wurde aufgestellt.'
);

INSERT INTO news (n_id, datum, titel, inhalt)
VALUES (
	'3', 1369579440000, 'Kaffee-Automat', 'Neuer Kaffee-Automat wurde aufgestellt.'
);

INSERT INTO aktion (a_id, tag, titel, inhalt)
VALUES (
	'1', 'Dienstag', 'Burgertag', 'Jeden Dienstag gibt es leckere Burger!'
);

INSERT INTO aktion (a_id, tag, titel, inhalt)
VALUES (
	'2', 'Mittwoch', 'Pastatag', 'Jeden Mittwoch gibt es leckere Pasta!'
);

INSERT INTO aktion (a_id, tag, titel, inhalt)
VALUES (
	'3', 'Freitag', 'Pizzatag', 'Jeden Freitag gibt es leckere Pizza!'
);

INSERT INTO angebot (an_id, art, titel, zutaten, preis)
VALUES (
	'1', 'Getraenk', 'Coca Cola', 'Braune Bruehe mit Zucker und Coffein', '100'
);

INSERT INTO angebot (an_id, art, titel, zutaten, preis)
VALUES (
	'2', 'Getraenk', 'Fanta', 'Gelbe Bruehe mit Zucker', '100'
);

INSERT INTO angebot (an_id, art, titel, zutaten, preis)
VALUES (
	'3', 'Suesses', 'Snickers', 'Schokoladenriegel mit Nüssen', '50'
);

INSERT INTO angebot (an_id, art, titel, zutaten, preis)
VALUES (
	'4', 'Suesses', 'Bounty', 'Schokoladenriegel mit Kokosflocken', '50'
);

INSERT INTO angebot (an_id, art, titel, zutaten, preis)
VALUES (
	'5', 'Belegte Broetchen', 'Salamibroetchen', 'Salami, Butter, Salat, Käse', '150'
);

INSERT INTO angebot (an_id, art, titel, zutaten, preis)
VALUES (
	'6', 'Belegte Broetchen', 'Lyonerbroetchen', 'Lyoner, Butter, Salat, Tomate', '150'
);

INSERT INTO bewertung (bw_id, datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	'1', 1369579440000, '10', 'Originale Coca Cola ist immernoch die beste', 1, 1
);

INSERT INTO bewertung (bw_id, datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	'2', 1369579440000, '8', 'Die gute alte Coke', 2, 1
);

INSERT INTO bewertung (bw_id, datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	'3', 1369579440000, '6', 'Richtig gut und schoen kalt', 3, 1
);