package de.app.hskafeteria.httpclient.domain;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="bewertungen")
public class Bewertungen {
	@ElementList(inline=true, required=false)
	private ArrayList<Bewertung> bewertungen;
	
	public Bewertungen() {
		bewertungen = new ArrayList<Bewertung>();
	}
	
	public boolean isEmpty() {
		if (bewertungen.size() == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return bewertungen.size();
	}

	public void addBewertung(Bewertung bewertung) {
		bewertungen.add(bewertung);
	}

	public ArrayList<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(ArrayList<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}
}
