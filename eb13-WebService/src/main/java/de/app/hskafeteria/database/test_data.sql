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
	'Getr�nke', 'Coca-Cola', 'Wasser, Kohlens�ure, Farbstoff E150d, Konservierungsstoffe, Aroma, Coffein', '100'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Getr�nke', 'Fanta', 'Wasser, Kohlens�ure, Farbstoff E104, Konservierungsstoffe, Aroma', '100'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'S��igkeiten', 'Snickers', 'Schokolade, Erdn�sse, Karamell', '50'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'S��igkeiten', 'Bounty', 'Schokolade, Kokos', '50'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Belegte Br�tchen', 'Salamibr�tchen', 'Salami, Butter, Salat, K�se', '150'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Belegte Br�tchen', 'Lyonerbr�tchen', 'Lyoner, Butter, Salat, Tomate', '150'
);

INSERT INTO angebot (art, titel, zutaten, preis)
VALUES (
	'Getr�nke', 'Mezzo Mix', 'Wasser, Kohlens�ure, Farbstoff E150d, Konservierungsstoffe, Aroma', '100'
);

INSERT INTO bewertung (datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	1369579440000, '5', 'Originale Coca Cola ist immernoch die Beste', 1, 1
);

INSERT INTO bewertung (datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	1369579440000, '3', 'Die gute alte Coke', 2, 1
);

INSERT INTO bewertung (datum, punkte, kommentar, BENUTZER_FK, ANGEBOT_FK)
VALUES (
	1369579440000, '4', 'Richtig gut und sch�n kalt', 3, 1
);