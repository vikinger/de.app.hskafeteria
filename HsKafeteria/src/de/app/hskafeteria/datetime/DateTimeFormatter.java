package de.app.hskafeteria.datetime;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateTimeFormatter {
	private Date date;
	private String dateStr;
	private String timeStr;
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
	
	public DateTimeFormatter (Date date) {
		this.date = date;
	}
	
	public DateTimeFormatter (String date, String time) {
		this.dateStr = date;
		this.timeStr = time;
	}
	
	public String getDate() {
		return dateFormatter.format(date);
	}
	
	public String getTime() {
		return timeFormatter.format(date);
	}
	
	public Long getDateInLong() {
		final long hoursInMillis = 60L * 60L * 1000L;
		try {
			return (dateFormatter.parse(dateStr).getTime() + timeFormatter.parse(timeStr).getTime()) + hoursInMillis;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
