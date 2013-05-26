package de.app.hskafeteria.benutzerverwaltung;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The persistent class for the BENUTZER database table.
 * 
 */
@XmlRootElement(name="benutzer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"bId", "vorname", "nachname", "passwort", "email"})
@Entity
public class Benutzer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int BID_LENGTH_MIN = 2;
	public static final int BID_LENGTH_MAX = 30;
	public static final int NACHNAME_LENGTH_MIN = 2;
	public static final int NACHNAME_LENGTH_MAX = 30;
	public static final int VORNAME_LENGTH_MIN = 2;
	public static final int VORNAME_LENGTH_MAX = 30;
	

	@Id
	@Column(name="b_id",nullable = false,unique = true, updatable = false)
	private int bId;
	
	@Column(nullable = false)
	private String nachname;
	
	@Column(nullable = false)
	private String passwort;
	
	@Column(nullable = false)
	private String vorname;
	
	@Column(nullable = false)
	private String email;
	
	public Benutzer() {
	}

	public int getBId() {
		return this.bId;
	}
	public void setBId(int bId) {
		this.bId = bId;
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Benutzer [bId=" + bId + ", nachname=" + nachname
				+ ", passwort=" + passwort + ", vorname=" + vorname + ", email=" + email + "]";
	}

}