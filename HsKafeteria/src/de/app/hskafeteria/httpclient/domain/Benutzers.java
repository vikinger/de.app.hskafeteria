package de.app.hskafeteria.httpclient.domain;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="benutzers")
public class Benutzers {
	@ElementList(inline=true)
	private ArrayList<Benutzer> benutzers;

	public Benutzers() {
		benutzers = new ArrayList<Benutzer>();
	}
	
	public Benutzers(ArrayList<Benutzer> benutzers) {
		this.benutzers = benutzers;
	}

	public boolean isEmpty() {
		if (benutzers.size() == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return benutzers.size();
	}

	public void addBenutzer(Benutzer benutzer) {
		benutzers.add(benutzer);
	}

	public void addBenutzerList(List<Benutzer> userList) {
		benutzers.addAll(userList);
	}

	public ArrayList<Benutzer> getAllUser() {
		return benutzers;
	}
	public void setUsers(ArrayList<Benutzer> benutzers) {
		this.benutzers = benutzers;
	}

	@Override
	public String toString() {
		String str = null;
		if (benutzers.size() == 0)
			str = "Es gibt keinen Benutzer!";
		else {
			str = "Users = [\n";
			for (Benutzer benutzer : benutzers) {
				str += (benutzer.getVorname() + " " + benutzer.getNachname() + 
						" (" + benutzer.getBId() + ")" + ",\n");
			}
			str += "]";
		}
		return str;
	}
}