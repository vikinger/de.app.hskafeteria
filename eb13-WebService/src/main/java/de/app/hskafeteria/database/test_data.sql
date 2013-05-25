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
	'1', 1388552400000, 'Pizza-Automat', 'Neuer Pizza-Automat wurde aufgestellt.'
);

INSERT INTO news (n_id, datum, titel, inhalt)
VALUES (
	'2', 1388552400000, 'Pasta-Automat', 'Neuer Pasta-Automat wurde aufgestellt.'
);

INSERT INTO news (n_id, datum, titel, inhalt)
VALUES (
	'3', 1388552400000, 'Kaffee-Automat', 'Neuer Kaffee-Automat wurde aufgestellt.'
);

INSERT INTO aktion (a_id, datum, titel, inhalt)
VALUES (
	'1', 1388552400000, 'Burgertag', 'Jeden Dienstag gibt es leckere Burger!'
);

INSERT INTO aktion (a_id, datum, titel, inhalt)
VALUES (
	'2', 1388552400000, 'Pastatag', 'Jeden Mittwoch gibt es leckere Pasta!'
);

INSERT INTO aktion (a_id, datum, titel, inhalt)
VALUES (
	'3', 1388552400000, 'Pizzatag', 'Jeden Freitag gibt es leckere Pizza!'
);