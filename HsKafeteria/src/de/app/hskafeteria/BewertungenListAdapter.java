package de.app.hskafeteria;

import java.util.Date;
import java.util.List;

import de.app.hskafeteria.datetime.DateTimeFormatter;
import de.app.hskafeteria.httpclient.domain.Bewertung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BewertungenListAdapter extends ArrayAdapter<Bewertung> {
  private final Context context;
  private final List<Bewertung> bewertungen;

  public BewertungenListAdapter(Context context, List<Bewertung> values) {
    super(context, R.layout.bewertung_kommentar, values);
    this.context = context;
    this.bewertungen = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    View rowView = inflater.inflate(R.layout.bewertung_kommentar, parent, false);
    
    TextView tv_kommentar = (TextView) rowView.findViewById(R.id.bewertung_kommentar);
    TextView tv_date = (TextView) rowView.findViewById(R.id.bewertung_date);
    TextView tv_author = (TextView) rowView.findViewById(R.id.bewertung_author);
    
    tv_kommentar.setText(bewertungen.get(position).getKommentar());
    tv_author.setText(bewertungen.get(position).getBenutzer().getNachname());

    DateTimeFormatter formatter = new DateTimeFormatter(new Date((Long) bewertungen.get(position).getDatum()));
    tv_date.setText(formatter.getDate());

    return rowView;
  }
} 