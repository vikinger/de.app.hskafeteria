package de.app.hskafeteria.newsverwaltung;

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
 * The persistent class for the News database table.
 * 
 */
@XmlRootElement(name="news")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="n_id")
	private Integer nId;

	@Column(nullable = false)
	private long datum;

	@Column(nullable = false)
	private String titel;

	@Column(nullable = false)
	private String inhalt;

	public News() {
	}

	public Integer getNId() {
		return this.nId;
	}

	public void setNId(Integer nId) {
		this.nId = nId;
	}

	public long getDatum() {
		return this.datum;
	}

	public void setDatum(long datum) {
		this.datum = datum;
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