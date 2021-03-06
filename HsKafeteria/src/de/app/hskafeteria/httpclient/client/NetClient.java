package de.app.hskafeteria.httpclient.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import de.app.hskafeteria.httpclient.domain.Aktion;
import de.app.hskafeteria.httpclient.domain.AktionenList;
import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.AngeboteList;
import de.app.hskafeteria.httpclient.domain.Benutzer;
import de.app.hskafeteria.httpclient.domain.Benutzers;
import de.app.hskafeteria.httpclient.domain.Bewertung;
import de.app.hskafeteria.httpclient.domain.Bewertungen;
import de.app.hskafeteria.httpclient.domain.News;
import de.app.hskafeteria.httpclient.domain.NewsList;


public class NetClient {
	public static String BASE_URL = "http://www.iwi.hs-karlsruhe.de/ebatc/eb13-WebService/rest/";
//	public static String BASE_URL = "http://192.168.178.78/eb13-WebService/rest/";

	public static final String BENUTZER = "Benutzer/";
	public static final String NEWS = "News/";
	public static final String AKTION = "Aktion/";
	public static final String ANGEBOT = "Angebot/";
	public static final String BEWERTUNG = "Bewertung/";
	public static final String NEWPASSWORD = "newpassword/";
	public static final String ANGELEGT = "angelegt/";
	public static final String LOGIN = "Login/";
	public static final String KATEGORIE = "Kategorie/";
	public static enum DomainType {Benutzer, Benutzers, News, NewsList, Aktion, AktionenList, Angebot, AngeboteList, Bewertung, Bewertungen, Kategorie, Kategories};

	public int changePassword(String userEmail, String newPassword) {
		String uri = BASE_URL + BENUTZER + NEWPASSWORD + userEmail + "-" + newPassword;
		int result = post(uri, null);
		return result;
	}
	
	public NewsList getAllNews() {
		String uri = BASE_URL + NEWS;
		NewsList newsList = (NewsList) get(uri, DomainType.NewsList);
		return newsList;
	}

	public int createNews(News n) {
		int responseCode = -1;
		String uri = BASE_URL + NEWS;
		// Serialize
		String xmlStr = serialize(n);
		if (xmlStr != null) {
			responseCode = post(uri, xmlStr);
		}
		return responseCode;
	}

	public int deleteNews(String newsId) {
		String uri = BASE_URL + NEWS + newsId;
		Integer statusCode = delete(uri);
		return statusCode;
	}
	
	public AktionenList getAllAktionen() {
		String uri = BASE_URL + AKTION;
		AktionenList aktionenList = (AktionenList) get(uri, DomainType.AktionenList);
		return aktionenList;
	}

	public int createAktion(Aktion a) {
		int responseCode = -1;
		String uri = BASE_URL + AKTION;
		// Serialize
		String xmlStr = serialize(a);
		if (xmlStr != null) {
			responseCode = post(uri, xmlStr);
		}
		return responseCode;
	}

	public int deleteAktion(String aktionId) {
		String uri = BASE_URL + AKTION + aktionId;
		Integer statusCode = delete(uri);
		return statusCode;
	}
	
	public AngeboteList getAllAngebote() {
		String uri = BASE_URL + ANGEBOT;
		AngeboteList angeboteList = (AngeboteList) get(uri, DomainType.AngeboteList);
//		for (int i=0; i<angeboteList.size();i++){
//			Double preis = angeboteList.getAngeboteList().get(i).getPreis();
//			angeboteList.getAngeboteList().get(i).setPreis(preis/100);
//		}
		return angeboteList;
	}

	public int createAngebot(Angebot a) {
		int responseCode = -1;
		String uri = BASE_URL + ANGEBOT;
		// Serialize
		String xmlStr = serialize(a);
		if (xmlStr != null) {
			responseCode = post(uri, xmlStr);
		}
		return responseCode;
	}

	public int deleteAngebot(String angebotId) {
		String uri = BASE_URL + ANGEBOT + angebotId;
		Integer statusCode = delete(uri);
		return statusCode;
	}

	/**
	 * 
	 * @return 200 if login ok
	 * @return 401 if login failed
	 */
	public int login(String username, String password) {
		String uri = BASE_URL + LOGIN + username + "-" + password;
		int result = getResponseCode(uri);
		return result;
	}

	public Benutzers getAllBenutzers() {
		String uri = BASE_URL + BENUTZER;
		Benutzers benutzers = (Benutzers) get(uri, DomainType.Benutzers);
		return benutzers;
	}

	public Benutzer getBenutzerByEmail(String email) {
		String uri = BASE_URL + BENUTZER + email ;
		Benutzer benutzer = (Benutzer) get(uri, DomainType.Benutzer);
		return benutzer;
	}

	public Integer createNewBenutzer(Benutzer benutzer) {
		Integer responseCode = -1;
		String uri = BASE_URL + BENUTZER;
		String xmlStr = serialize(benutzer);
		if (xmlStr != null) {
			responseCode = post(uri, xmlStr);
		}
		return responseCode;
	}
	
	public Bewertungen getBewertungenByAngebot(String angebotTitel) {
		String uri = BASE_URL + BEWERTUNG + angebotTitel ;
		Bewertungen bewertungen = (Bewertungen) get(uri, DomainType.Bewertungen);
		return bewertungen;
	}
	
	public Integer createBewertung(Bewertung bewertung) {
		Integer responseCode = -1;
		String uri = BASE_URL + BEWERTUNG;
		String xmlStr = serialize(bewertung);
		if (xmlStr != null) {
			responseCode = post(uri, xmlStr);
		}
		return responseCode;
	}

	private Object get(String uri, DomainType type) {
		Object obj = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.getResponseCode();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output = br.readLine();
			obj = deserialize(output, type);
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private int getResponseCode(String uri) {
		int responseCode = -1;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			responseCode = conn.getResponseCode();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseCode;
	}

	/**
	 * 
	 * @param uri
	 * @param input
	 * @return Response Code
	 */
	private int post(String uri, String input) {
		int statusCode = 0;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			OutputStream os = conn.getOutputStream();
			if (input != null)
				os.write(input.getBytes());
			os.flush();
			//			statusCode = conn.getResponseCode();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			statusCode = conn.getResponseCode();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {  
			e.printStackTrace();
		}
		return statusCode;
	}

	private int delete(String uri) {
		int statusCode = 0;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");
			statusCode = conn.getResponseCode();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {  
			e.printStackTrace();
		}
		return statusCode;
	}

	private String serialize(Object obj) {
		Serializer serializer = new Persister();
		String output = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			serializer.write(obj, os);
			output = new String(os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	private Object deserialize(String xmlStr, DomainType type) {
		Serializer serializer = new Persister();
		try {
			switch (type) {
			case Benutzer:
				return serializer.read(Benutzer.class, xmlStr);
			case Benutzers:
				return serializer.read(Benutzers.class, xmlStr);
			case News:
				return serializer.read(News.class, xmlStr);
			case NewsList:
				return serializer.read(NewsList.class, xmlStr);
			case Aktion:
				return serializer.read(Aktion.class, xmlStr);
			case AktionenList:
				return serializer.read(AktionenList.class, xmlStr);
			case Angebot:
				return serializer.read(Angebot.class, xmlStr);
			case AngeboteList:
				return serializer.read(AngeboteList.class, xmlStr);
			case Bewertung:
				return serializer.read(Bewertung.class, xmlStr);
			case Bewertungen:
				return serializer.read(Bewertungen.class, xmlStr);
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem with SimpleXML");
		}
		return null;
	}

}
