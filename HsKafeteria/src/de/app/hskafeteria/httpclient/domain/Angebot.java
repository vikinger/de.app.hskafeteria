package de.app.hskafeteria.httpclient.domain;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

@Root(name="angebot")
public class Angebot implements Parcelable{
	
	@Element(required=false)
	private int anId;
	@Element
	private String art;
	@Element
	private String titel;
	@Element
	private String zutaten;
	@Element
	private int preis;

	public Angebot() {
	}
	
	private Angebot(Parcel in) {
		anId = in.readInt();
        art = in.readString();
        titel = in.readString();
        zutaten = in.readString();
        preis = in.readInt();
    }

	public int getAnId() {
		return this.anId;
	}

	public void setAnId(int anId) {
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
	
	public int getPreis() {
		return this.preis;
	}

	public void setPreis(int preis) {
		this.preis = preis;
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(anId);
		dest.writeString(art);
		dest.writeString(titel);
		dest.writeString(zutaten);
		dest.writeInt(preis);
	}

	@Override
	public String toString() {
		return this.titel;
	}
	
	public static final Parcelable.Creator<Angebot> CREATOR = new Creator<Angebot>() {
	    public Angebot createFromParcel(Parcel in) {
	        return new Angebot(in);
	    }

	    public Angebot[] newArray(int size) {
	        return new Angebot[size];
	    }
	};
	
	public boolean isInputValid() {
		boolean isValid = true;
		if ((titel == null) || (titel.equals("")))
			isValid = false;
		return isValid;
	}

}