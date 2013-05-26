package de.app.hskafeteria.angeboteverwaltung;


import java.util.List;



public interface AngebotDao {
	public void createAngebot(Angebot Angebot);
	public void updateAngebot(Angebot Angebot);
	public void deleteAngebot(String id);
	public List<Angebot> getAllAngebote();
	public Angebot getAngebotByTitel(String title);
	public void shutdown();
}
