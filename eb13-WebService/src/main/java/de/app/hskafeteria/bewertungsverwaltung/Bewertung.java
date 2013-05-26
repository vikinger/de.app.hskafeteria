package de.app.hskafeteria.bewertungsverwaltung;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import de.app.hskafeteria.angeboteverwaltung.Angebot;
import de.app.hskafeteria.benutzerverwaltung.Benutzer;

/**
 * The persistent class for the Bewertung database table.
 * 
 */
@XmlRootElement(name="bewertung")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"bwId", "datum", "punkte", "kommentar", "benutzer", "angebot"})
@Entity
public class Bewertung implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="bw_id")
	private Integer bwId;

	@Column(nullable = false)
	private long datum;

	@Column(nullable = false)
	private Integer punkte;

	@Column(nullable = false)
	private String kommentar;

	//bi-directional many-to-one association to Benutzer
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name="BENUTZER_FK")
	private Benutzer benutzer;

	//bi-directional many-to-one association to Angebot
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name="ANGEBOT_FK")
	private Angebot angebot;

	public Bewertung() {
	}

	public Integer getBwId() {
		return this.bwId;
	}

	public void setBwId(Integer bwId) {
		this.bwId = bwId;
	}

	public long getDatum() {
		return this.datum;
	}

	public void setDatum(long datum) {
		this.datum = datum;
	}

	public Integer getPunkte() {
		return this.punkte;
	}

	public void setPunkte(Integer punkte) {
		this.punkte = punkte;
	}

	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Benutzer getBenutzer() {
		return this.benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}
	
	public Angebot getAngebot() {
		return this.angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}

}