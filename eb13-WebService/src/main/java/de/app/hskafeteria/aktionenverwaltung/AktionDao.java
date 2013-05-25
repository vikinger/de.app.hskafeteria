package de.app.hskafeteria.aktionenverwaltung;


import java.util.List;



public interface AktionDao {
	public void createAktion(Aktion Aktion);
	public void updateAktion(Aktion Aktion);
	public void deleteAktion(String id);
	public List<Aktion> getAllAktionen();
	public void shutdown();
}
