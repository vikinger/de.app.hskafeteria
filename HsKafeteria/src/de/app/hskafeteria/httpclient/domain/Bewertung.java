package de.app.hskafeteria.httpclient.domain;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

@Root(name="bewertung")
public class Bewertung implements Parcelable{
	
	@Element(required=false)
	private int bwId;
	@Element
	private long datum;
	@Element
	private Integer punkte;
	@Element
	private String kommentar;
	@Element
	private Benutzer benutzer;
	@Element
	private Angebot angebot;

	public Bewertung() {
	}
	
	private Bewertung(Parcel in) {
		bwId = in.readInt();
		datum = in.readLong();
		punkte = in.readInt();
		kommentar = in.readString();
    }

	public int getBwId() {
		return this.bwId;
	}

	public void setBwId(int bwId) {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(bwId);
		dest.writeLong(datum);
		dest.writeInt(punkte);
		dest.writeString(kommentar);
	}

	@Override
	public String toString() {
		return this.kommentar;
	}
	
	public static final Parcelable.Creator<Bewertung> CREATOR = new Creator<Bewertung>() {
	    public Bewertung createFromParcel(Parcel in) {
	        return new Bewertung(in);
	    }

	    public Bewertung[] newArray(int size) {
	        return new Bewertung[size];
	    }
	};
	
	public boolean isInputValid() {
		boolean isValid = true;
		if ((punkte <= 0))
			isValid = false;
		if (datum == 0)
			isValid = false;
		if ((benutzer == null))
			isValid = false;
		if (angebot == null)
			isValid = false;
		return isValid;
	}

}