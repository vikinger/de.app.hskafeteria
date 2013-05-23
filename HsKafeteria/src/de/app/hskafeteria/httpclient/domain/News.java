package de.app.hskafeteria.httpclient.domain;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;

@Root(name="news")
public class News implements Parcelable{
	
	@Element(required=false)
	private int nId;
	@Element
	private long datum;
	@Element
	private String titel;
	@Element
	private String inhalt;

	public News() {
	}
	
	private News(Parcel in) {
		nId = in.readInt();
        inhalt = in.readString();
        titel = in.readString();
        datum = in.readLong();
    }

	public int getNId() {
		return this.nId;
	}

	public void setNId(int nId) {
		this.nId = nId;
	}

	public long getDatum() {
		return this.datum;
	}

	public void setDatum(long datum) {
		this.datum = datum;
	}

	public String getInhalt() {
		return this.inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(nId);
		dest.writeString(inhalt);
		dest.writeString(titel);
		dest.writeLong(datum);
	}

	@Override
	public String toString() {
		return this.titel;
	}
	
	public static final Parcelable.Creator<News> CREATOR = new Creator<News>() {
	    public News createFromParcel(Parcel in) {
	        return new News(in);
	    }

	    public News[] newArray(int size) {
	        return new News[size];
	    }
	};
	
	public boolean isInputValid() {
		boolean isValid = true;
		if ((titel == null) || (titel.equals("")))
			isValid = false;
		if ((inhalt == null) || (inhalt.equals("")))
			isValid = false;
		return isValid;
	}

}