package de.app.hskafeteria.httpclient.domain;



import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="benutzer")
public class Benutzer {
	@Element(required=false)
	private Integer bId;
	@Element
	private String email;
	@Element(required=false)
	private String nachname;
	@Element(required=false)
	private String passwort;
	@Element(required=false)
	private String vorname;

	public Benutzer() {
	}

	public Integer getBId() {
		return bId;
	}

	public void setBId(Integer bId) {
		this.bId = bId;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNachname() {
		return this.nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getPasswort() {
		return this.passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getVorname() {
		return this.vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	@Override
	public String toString() {
		return "Benutzer [email=" + email + ", nachname=" + nachname
				+ ", passwort=" + passwort + ", vorname=" + vorname + "]";
	}

	public boolean isInputValid() {
		boolean isValid = true;
		
		if ((email == null) || (email.equals("")))
			isValid = false;
		if ((nachname == null) || (nachname.equals("")))
			isValid = false;
		if ((vorname == null) || (vorname.equals("")))
			isValid = false;
		if ((passwort == null) || (passwort.equals("")))
			isValid = false;
		
		return isValid;
	}
}