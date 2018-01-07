package util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

//import javax.xml.ws.http.HTTPException;


public class HttpUtil {
	
	//https://stackoverflow.com/questions/4755647/how-to-access-https-sites-in-java
	
	public static String loginToServer() throws IOException{
		try {
		URL url = new URL("http://192.168.147.200/");
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setAllowUserInteraction(true);
		//Get Methode, da nur Daten zu lesen sind
		http.setRequestMethod("GET");
		http.connect();
		//System.out.println("Erfolgreich eingeloggt");
		return "Eingeloggt";
		}
		/*catch(HTTPException he) {
			do throw(new HTTPException(404));
			return null;
		}
		*/
		catch(IOException ioe) {
			return "Fehler";
		}
	}
	
	
	
	
}
