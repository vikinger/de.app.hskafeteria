package de.app.hskafeteria.httpclient.domain;

import java.util.ArrayList;

public class Kategorie {

	private String titel;
	private ArrayList<Angebot> angebote = new ArrayList<Angebot>();
	
	public Kategorie (String titel)
	{
		this.setTitel(titel);
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public ArrayList<Angebot> getAngebote() {
		return angebote;
	}

	public void setAngebote(ArrayList<Angebot> angebote) {
		this.angebote = angebote;
	}
}
