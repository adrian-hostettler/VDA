package business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.Encryption;
import util.HttpUtil;

//Quelle: https://github.com/ISchwarz23/FritzBox-API/blob/master/src/main/java/de/ingo/fritzbox/FritzBoxConnector.java

public class FritzBoxConnector {
	private final String url;
	private final String password;
	private String sid;
	public FritzBoxConnector (String url, String password) {
		this.url = url;
		this.password = password;
	}
	public void fritzBoxLogin() {
		/*
		 * Für das Login der FritzBox muss eine Challenge, welche von der Startseite zurückgeschickt wird behandelt werden.
		 *Über eine MD5 Encryption schicken wir die Challenge inklusive Passwort an die FritzBox zurück
		 *Ist diese Challenge mit Passwort korrekt erhalten wir eine gültige SID
		*/
		String response;
		try {
			response = HttpUtil.getURLasString(url + "login_sid.lua");
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
			return;
		}
		final String challenge = response.substring(response.indexOf("<Challenge>")+11, response.indexOf("</Challenge>"));
		//System.out.println(challenge);
		final String stringToHash = challenge + "-" + password;
		String stringToHashUTF16;
		try {
			stringToHashUTF16 = new String(stringToHash.getBytes("UTF-16LE"), "UTF-8");

			final String md5 = Encryption.hashMD5(stringToHashUTF16);
			final String md5_challenge = challenge + "-" + md5;

			String urlWithChallenge = HttpUtil.getURLasString(url + "login_sid.lua?user=&response=" + md5_challenge);
			
			// get challenge challenge
			final String sid = urlWithChallenge.substring(urlWithChallenge.indexOf("<SID>") + 5, urlWithChallenge.indexOf("</SID>"));
			this.sid = sid;
			//System.out.println(sid);
		} catch (final UnsupportedEncodingException ue) {
			// will never appear
			ue.printStackTrace();
			
		} catch (final NoSuchAlgorithmException nae) {
			// will never appear
			nae.printStackTrace();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}
	
	
	
	public String readDSLData() {
		/*
		 * Über den URL: http://192.168.147.200/internet/dsl_status_tab.lua=[*SID] gelange ich zu den DSL-Informationen
		 */
		Document response;
		try {
			response = HttpUtil.getURLasDocument(url + "internet/dsl_stats_tab.lua?sid=" + sid);
			Element dslData = (Element) response.getElementById("Table1");
			
			//Zweidimensionales Array erstellen um Tabelle der DSL-Daten abzubilden
//			String [] [] twoDimArray = new String [15] [4];
//			
//			for (Element table : response.select("Table1")) {
//		        for (Element row : table.select("tr")) {
//		        	int countR;
//		        	for(countR = 0; countR <15; countR++) {
//		        		 Elements tds = row.select("td");
//				            int countTd;
//				            for (countTd=0; countTd <4; countTd++) {
//				            	twoDimArray [countR] [countTd] =
//		        	}
//		        		
//		           
//		            }
//		        }
//		    }
			
//			@TODO Daten aus Tabelle nehmen und Darstellung
//			https://stackoverflow.com/questions/8222118/using-jsoup-to-extract-html-table-contents
//			Tipp: 2.Dimensionales Array erstellen 1.Dimension - Zeilen, 2.Dimension - Spalten zur Weiterverarbeitung
//			Array zurückgeben und DSL Daten schreiben
			System.out.println(dslData);
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
