package de.app.hskafeteria.httpclient.domain;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="aktionenlist")
public class AktionenList {
	@ElementList(inline=true)
	private ArrayList<Aktion> aktionenList;
	
	public AktionenList() {
		aktionenList = new ArrayList<Aktion>();
	}
	
	public AktionenList(ArrayList<Aktion> aktionenList) {
		this.aktionenList = aktionenList;
	}
	
	public boolean isEmpty() {
		if (aktionenList.size() == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return aktionenList.size();
	}

	public void addAktion(Aktion aktion) {
		aktionenList.add(aktion);
	}
	
	public void addAktionenList(List<Aktion> aktionenList) {
		aktionenList.addAll(aktionenList);
	}

	public ArrayList<Aktion> getAktionenList() {
		return aktionenList;
	}

	public void setAktionenList(ArrayList<Aktion> aktionenList) {
		this.aktionenList = aktionenList;
	}
}
