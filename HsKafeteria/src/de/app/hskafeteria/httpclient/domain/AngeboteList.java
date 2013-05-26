package de.app.hskafeteria.httpclient.domain;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="angebotelist")
public class AngeboteList {
	@ElementList(inline=true)
	private ArrayList<Angebot> angeboteList;
	
	public AngeboteList() {
		angeboteList = new ArrayList<Angebot>();
	}
	
	public AngeboteList(ArrayList<Angebot> angeboteList) {
		this.angeboteList = angeboteList;
	}
	
	public boolean isEmpty() {
		if (angeboteList.size() == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return angeboteList.size();
	}

	public void addAngebot(Angebot angebot) {
		angeboteList.add(angebot);
	}
	
	public void addAngeboteList(List<Angebot> angeboteList) {
		angeboteList.addAll(angeboteList);
	}

	public ArrayList<Angebot> getAngeboteList() {
		return angeboteList;
	}

	public void setAngeboteList(ArrayList<Angebot> angeboteList) {
		this.angeboteList = angeboteList;
	}
}
