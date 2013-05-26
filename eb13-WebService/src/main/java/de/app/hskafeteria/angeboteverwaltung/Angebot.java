package de.app.hskafeteria.angeboteverwaltung;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The persistent class for the Angebot database table.
 * 
 */
@XmlRootElement(name="angebot")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"anId", "art", "titel", "zutaten", "preis"})
@Entity
public class Angebot implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="an_id",nullable = false,unique = true, updatable = false)
	private Integer anId;

	@Column(nullable = false)
	private String art;

	@Column(nullable = false)
	private String titel;

	@Column(nullable = false)
	private String zutaten;
	
	@Column(nullable = false)
	private int preis;

	public Angebot() {
	}

	public Integer getAnId() {
		return this.anId;
	}

	public void setAnId(Integer anId) {
		this.anId = anId;
	}

	public String getArt() {
		return this.art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getZutaten() {
		return this.zutaten;
	}

	public void setZutaten(String zutaten) {
		this.zutaten = zutaten;
	}
	
	public Integer getPreis() {
		return this.preis;
	}

	public void setPreis(Integer preis) {
		this.preis = preis;
	}
}