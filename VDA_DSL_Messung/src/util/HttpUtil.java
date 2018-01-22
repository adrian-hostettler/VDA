package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * 
 * @author Adrian Hostettler
 * 
 * @version 1.0
 * 
 * @category util
 *
 * Quelle der Codestruktur: https://stackoverflow.com/questions/4755647/how-to-access-https-sites-in-java
 */

public class HttpUtil{
	
/**
 * 
 * Inputstream der Login Seite interpretieren
 * 
 * @param urlString
 * @return inputSteam
 */
	private static InputStream getURL(String urlString){
		try {
		
		URL url = new URL(urlString);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod("GET");
		http.connect();
		InputStream inputStream = http.getInputStream();
		return inputStream;
		}
		
		catch(IOException ioe) {
			System.out.println("IO Exception - Einloggen auf http://192.168.147.200/ hatte nicht geklappt!");
			ioe.printStackTrace();
		}
		catch(Exception e){
			System.out.println("Einloggen auf http://192.168.147.200/ hatte nicht geklappt!");
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Interpretiert Antwort vom Browser als String
	 * 
	 * @param urlString
	 * @return stringBuilder
	 * @throws Exception 
	 * 
	 */
	public static String getURLasString(String urlString) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getURL(urlString)));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
			while ((line = reader.readLine()) != null)
			{
			    stringBuilder.append(line + "\n");
			}
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			System.out.println("Lesen des Input Streams hatte nicht geklappt!");
			ioe.printStackTrace();
		}
        /*
		 *  ***** Testing *****
		 *	Was erwartet mich vom Browser?
		 *	System.out.println(stringBuilder.toString());
		 */
		
		return stringBuilder.toString();
	}
	
	/**
	 * Liest mit Hilfe von Jsoup den Inhalt der Fritz!Box als Document
	 * 
	 * @param urlString
	 * @return
	 * @throws Exception 
	 */
	public static Document getURLasDocument(String urlString){
		 /*
		 *  ***** Testing *****
		 *	Beinhaltet die Session ID etwas?
		 *	String url = urlString.substring(urlWithChallenge.indexOf("<SID>") + 5, urlWithChallenge.indexOf("</SID>"));
		 */	
		InputStream is = getURL(urlString);
		Document jsoupDoc;
		try {
			jsoupDoc = (Document) Jsoup.parse(is, null,"");
			return jsoupDoc;
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			System.out.println("Wandeln vom Input Stream in Document hatte nicht geklappt!");
			ioe.printStackTrace();
		}
		return null;
		
	}
	
	
	
}
