package de.app.hskafeteria.aktionenverwaltung;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the Aktion database table.
 * 
 */
@XmlRootElement(name="aktion")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Aktion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="a_id")
	private Integer aId;

	@Column(nullable = false)
	private String tag;

	@Column(nullable = false)
	private String titel;

	@Column(nullable = false)
	private String inhalt;

	public Aktion() {
	}

	public Integer getAId() {
		return this.aId;
	}

	public void setAId(Integer aId) {
		this.aId = aId;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getInhalt() {
		return this.inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

}