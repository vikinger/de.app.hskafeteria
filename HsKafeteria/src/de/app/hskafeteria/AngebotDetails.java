package de.app.hskafeteria;

import de.app.hskafeteria.httpclient.domain.Angebot;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AngebotDetails extends Activity{

	String benutzerId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		final Angebot angebot = bundle.getParcelable("angebot");
		setContentView(new AngebotUI(this, angebot));
		
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		benutzerId = prefs.getString("logged_in_user", "");
//		
//		bewertenButton = (Button) findViewById(R.id.btn_bewerten);
//		bewertenButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
	}
	
	class AngebotUI extends LinearLayout {
		private Angebot angebot = null;

		public AngebotUI(Context context, Angebot angebot) {
			super(context);
			this.angebot = angebot;
			inflateLayout(context);
		}

		public AngebotUI(Context context, AttributeSet attrs) {
			super(context, attrs);
			inflateLayout(context);
		}

		@SuppressLint("SimpleDateFormat")
		private void inflateLayout(Context context) {
			LayoutInflater layoutInflater = 
					(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.activity_angebot_details, this);
			((TextView) view.findViewById(R.id.angebotTitel)).setText(angebot.getTitel());
			((TextView) view.findViewById(R.id.angebotZutaten)).setText(angebot.getZutaten());
			((TextView) view.findViewById(R.id.angebotPreis)).setText(Integer.toString(angebot.getPreis()));
		}
	}
	
}
