package de.app.hskafeteria;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.AngeboteList;
import de.app.hskafeteria.httpclient.domain.Kategorie;

public class newAngebot extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newangebot);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		final Button imageButton = (Button) findViewById(R.id.bt_add_image);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				EditText artikelnameET = (EditText) findViewById(R.id.tx_newangebot_artikelname);
				String artikelname = artikelnameET.getText().toString();
				
				if (artikelname == null || artikelname.isEmpty()) {
					Toast.makeText(newAngebot.this, "Artikelname muss ausfüllt sein",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(takePicture, 0);//zero can be replced with any action code
				
//				Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				startActivityForResult(pickPhoto , 1);//one can be replced with any action code
			}
		});

		final Button insertButton = (Button) findViewById(R.id.bt_newangebot_insert);
		insertButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				angebotEintragen();
			}
		});

		final EditText editText = (EditText) findViewById(R.id.tx_newangebot_kategorie);
		editText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(newAngebot.this);
				dialog.setContentView(R.layout.popup_kategorie);
				dialog.setTitle(R.string.hint_kategorie_newangebot);
				dialog.setCancelable(true);

				final RadioGroup radioGroup = (RadioGroup) dialog
						.findViewById(R.id.radioGroup);
				radioGroup
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								final EditText et = (EditText) findViewById(R.id.tx_newangebot_kategorie);
								RadioButton radioButton = (RadioButton) radioGroup
										.findViewById(checkedId);
								et.setText(radioButton.getText());
								dialog.dismiss();
							}
						});

				dialog.show();
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) {
		case 0:
		    if(resultCode == RESULT_OK){  
//		        Uri selectedImage = imageReturnedIntent.getData();
		        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
		        
//		        String path = Environment.getExternalStorageDirectory().toString();

		        String path = getCacheDir().toString();
		        
		        File outputDir = new File(path);
		        
	            //create dir if not there
		        if (!outputDir.exists()) {
		             outputDir.mkdir();

		        }
		        
		        EditText name = (EditText) findViewById(R.id.tx_newangebot_artikelname);
				String artikelname = name.getText().toString();
				if (artikelname.contains("ü") || artikelname.contains("ö") || artikelname.contains("ä") || artikelname.contains("ß"))
				{
					artikelname = artikelname.replaceAll("ö", "oe");
					
					artikelname = artikelname.replaceAll("ä", "ae");
					
					artikelname = artikelname.replaceAll("ü", "ue");
					
					artikelname = artikelname.replaceAll("ß", "ss");
				}
		        
		        //make another file with the full path AND the image this time, resized is a static string
		        File f = new File(path+File.separator+artikelname+".jpg");

		        //Convert bitmap to byte array
		        Bitmap bitmap = photo;
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        bitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
		        byte[] bitmapdata = bos.toByteArray();

		        //write the bytes in file
		        FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(f);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        try {
					fos.write(bitmapdata);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        new uploadImageAsyncTask().execute(f);
		       
		    }

		break; 
		case 1:
		    if(resultCode == RESULT_OK){  
		        Uri selectedImage = imageReturnedIntent.getData();
//		        imageview.setImageURI(selectedImage);
		    }
		break;
		}
		}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void angebotEintragen() {
		EditText name = (EditText) findViewById(R.id.tx_newangebot_artikelname);
		String artikelname = name.getText().toString();
		if (artikelname.contains("ü") || artikelname.contains("ö") || artikelname.contains("ä"))
		{
			artikelname = artikelname.replaceAll("ö", "oe");
			
			artikelname = artikelname.replaceAll("ä", "ae");
			
			artikelname = artikelname.replaceAll("ü", "ue");
		}

		EditText kat = (EditText) findViewById(R.id.tx_newangebot_kategorie);
		String kategorie = kat.getText().toString();

		EditText pre = (EditText) findViewById(R.id.tx_newangebot_preis);
		String preis = pre.getText().toString();
		//Validierung des Kommas
		if (preis.contains(",")) {
			if ((preis.length()) - (preis.indexOf(",")) > 3) 
			{
				preis = preis.substring(0, preis.indexOf(",")+3);
			}
			if ((preis.length()) - (preis.indexOf(",")) == 3) 
			{
				
			}
			if ((preis.length()) - (preis.indexOf(",")) == 2) 
			{
				preis = preis + "0";
			}
			preis = preis.replace(",", "");
		}
		else
		{
			preis = preis+"00";
		}

		EditText zut = (EditText) findViewById(R.id.tx_newangebot_zutaten);
		String zutaten = zut.getText().toString();

		Angebot neuesAngebot = new Angebot();
		neuesAngebot.setTitel(artikelname);
		neuesAngebot.setArt(kategorie);
		neuesAngebot.setZutaten(zutaten);
		neuesAngebot.setPreis(Integer.parseInt(preis));

		if (!neuesAngebot.isInputValid()) {
			Toast.makeText(this, "Bitte alle Felder ausfüllen",
					Toast.LENGTH_LONG).show();
			return;
		}

		new NewAngebotAsyncTask(this).execute(neuesAngebot);
	}

	private class NewAngebotAsyncTask extends AsyncTask<Angebot, Void, Integer> {

		private ProgressDialog pDlg = null;
		private Context mContext = null;
		private String processMessage = "Angebot wird eingetragen...";
		
        public NewAngebotAsyncTask(Context mContext) {
            this.mContext = mContext;
        }
		
        private void showProgressDialog() {
            
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();
 
        }
        
        @Override
        protected void onPreExecute() {
 
            showProgressDialog();
 
        }
        
		@Override
		protected Integer doInBackground(Angebot... params) {
			NetClient netClient = new NetClient();
			return netClient.createAngebot(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 201) {
				Toast.makeText(newAngebot.this.getApplicationContext(),
						"Das Angebot wurde erfolgreich eingetragen",
						Toast.LENGTH_LONG);
				startActivity(new Intent(newAngebot.this, MainActivity.class));
			} else {
				Toast.makeText(
						newAngebot.this,
						"Das Angebot konnte nicht eingetragen werden. Code = "
								+ result, Toast.LENGTH_LONG).show();
			}
			pDlg.dismiss();
		}
	}
	
	public class ProgressInputStream extends InputStream {

	    /* Key to retrieve progress value from message bundle passed to handler */
	    public static final String PROGRESS_UPDATE = "progress_update";

	    private static final int TEN_KILOBYTES = 1024 * 40;

	    private InputStream inputStream;
	    //private Handler handler;

	    private long progress;
	    private long lastUpdate;

	    private boolean closed;

	    public ProgressInputStream(InputStream inputStream) {
	        this.inputStream = inputStream;

	        this.progress = 0;
	        this.lastUpdate = 0;

	        this.closed = false;
	    }

	    @Override
	    public int read() throws IOException {
	        int count = inputStream.read();
	        return incrementCounterAndUpdateDisplay(count);
	    }

	    @Override
	    public int read(byte[] b, int off, int len) throws IOException {
	        int count = inputStream.read(b, off, len);
	        return incrementCounterAndUpdateDisplay(count);
	    }

	    @Override
	    public void close() throws IOException {
	        super.close();
	        if (closed)
	            throw new IOException("already closed");
	        closed = true;
	    }

	    private int incrementCounterAndUpdateDisplay(int count) {
	        if (count < 0)
	            progress += count;
	        lastUpdate = maybeUpdateDisplay(progress, lastUpdate);
	        return count;
	    }

	    private long maybeUpdateDisplay(long progress, long lastUpdate) {
	        if (progress - lastUpdate < TEN_KILOBYTES) {
	            lastUpdate = progress;
	            sendLong(PROGRESS_UPDATE, progress);
	        }
	        return lastUpdate;
	    }

	    public void sendLong(String key, long value) {
	        Bundle data = new Bundle();
	        data.putLong(key, value);

	        Message message = Message.obtain();
	        message.setData(data);
	        //handler.sendMessage(message);
	    }
	}
	
	private class uploadImageAsyncTask extends AsyncTask<File, Void, Void> {
		
		@Override
		protected Void doInBackground(File... params) {
	            FTPClient ftpClient = new FTPClient();

	            try {
					ftpClient.connect(InetAddress
					        .getByName("hskafeteria.square7.ch"));
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					ftpClient.login("hskafeteria", "hskafeteria");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					System.out.println("status :: " + ftpClient.getStatus());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	            try {
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                        //Your File path set here 
//	                        File file = new File("/sdcard/my pictures/image.png");  
	            BufferedInputStream buffIn = null;
				try {
					buffIn = new BufferedInputStream(
					        new FileInputStream(params[0]));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            ftpClient.enterLocalPassiveMode();
	            ProgressInputStream progressInput = new ProgressInputStream(
	                    buffIn);

	            boolean result = false;
				try {
					result = ftpClient.storeFile(params[0].getName(),
					        progressInput);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            System.out.println("result is  :: " + result);
	            try {
					buffIn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					ftpClient.logout();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            return null;
		}
	}
}
