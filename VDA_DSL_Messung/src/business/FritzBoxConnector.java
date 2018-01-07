package business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.Encryption;
import util.HttpUtil;

//https://github.com/ISchwarz23/FritzBox-API/blob/master/src/main/java/de/ingo/fritzbox/FritzBoxConnector.java
public class FritzBoxConnector {
	private final String url;
	private final String password;
	private String sid;
	public FritzBoxConnector (String url, String password) {
		this.url = url;
		this.password = password;
	}
	public void fritzBoxLogin() {
		
		String response;
		try {
			response = HttpUtil.getURLasString(url + "login_sid.lua");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		} catch (final UnsupportedEncodingException e) {
			// will never appear
			e.printStackTrace();
			
		} catch (final NoSuchAlgorithmException e) {
			// will never appear
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String readDSLData() {
		// URL für DSL Daten
		// http://192.168.147.200/
		Document response;
		try {
			response = HttpUtil.getURLasDocument(url + "internet/dsl_stats_tab.lua?sid=" + sid);
			Element dslData = (Element) response.getElementById("Table1");
//			@TODO Daten aus Tabelle nehemen und Darstellung
//			https://stackoverflow.com/questions/8222118/using-jsoup-to-extract-html-table-contents
//			Tipp: 2.Dimensionales Array erstellen 1.Dimension - Zeilen, 2.Dimension - Spalten zur Weiterverarbeitung
//			Array zurückgeben und DSL Daten schreiben
			System.out.println(dslData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
