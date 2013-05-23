package de.app.hskafeteria.benutzerverwaltung;



public interface BenutzerDao {
	public void createBenutzer(Benutzer benutzer);
	public Benutzer getBenutzerById(String id);
	public Benutzer getBenutzerByEmail(String email);
	public Benutzers getAllBenutzers();
	public void updateBenutzer(Benutzer benutzer);
	public void deleteBenutzer(String id);
	public void updatePassword(String email, String newPassword);
	public void shutdown();
}
