package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.http.HTTPException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


//import javax.xml.ws.http.HTTPException;


public class HttpUtil{
	
	//https://stackoverflow.com/questions/4755647/how-to-access-https-sites-in-java
	
	private static InputStream getURL(String urlString) {
		try {
		
		// URL bekannt, da eine statische IP-Adresse vergeben wurde
		URL url = new URL(urlString);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		
		
		//Get Methode, da nur Daten zu lesen sind
		http.setRequestMethod("GET");
		http.connect();
		InputStream is = http.getInputStream();
		return is;
		}
		
//		@TODO Recherche Internet, warum HTTP Exception nicht geht
		catch(HTTPException he) {
			System.out.println("HTTPS Exception - Einloggen auf http://192.168.147.200/ hatte nicht geklappt!");
			throw(new HTTPException(404));
		}
		
		catch(IOException ioe) {
			System.out.println("IO Exception - Einloggen auf http://192.168.147.200/ hatte nicht geklappt!");
			ioe.printStackTrace();
		}
		catch(Exception e){
			System.out.println("Einloggen auf http://192.168.147.200/ hatte nicht geklappt!");
		}
		return null;
	}

	public static String getURLasString(String urlString) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getURL(urlString)));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            stringBuilder.append(line + "\n");
        }
		
		
		System.out.println(stringBuilder.toString());
		return stringBuilder.toString();
	}
	public static Document getURLasDocument(String urlString) throws IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(getURL(urlString));
			return document;
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
return null;
		
	}
	
	
	
}
