package de.app.hskafeteria.bewertungsverwaltung;


import java.util.List;


public interface BewertungDao {
	public void createBewertung(Bewertung Bewertung);
	public void deleteBewertung(String id);
	public List<Bewertung> getAllBewertungen();
	public List<Bewertung> getBewertungenByAngebotTitel(String titel);
	public void shutdown();
}
