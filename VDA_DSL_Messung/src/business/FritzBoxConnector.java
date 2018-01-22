package business;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

import org.jsoup.nodes.Document;

import util.Encryption;
import util.HttpUtil;

/**
 * 
 * @author Adrian Hostettler
 * 
 * @version 1.0
 * 
 * @category business
 *
 * Quelle der Codestruktur: https://github.com/ISchwarz23/FritzBox-API/blob/master/src/main/java/de/ingo/fritzbox/FritzBoxConnector.java
 * 
 */
public class FritzBoxConnector {
	private final String ipAdress;
	private final String url;
	private final String password;
	private String sid;

	// Beim erstellen eines FritzBoxConnector-Objekts werden URL und Passwort mitgegeben, da die IP-Adresse oder das Passwort verändert werden könnte
	public FritzBoxConnector(String url, String password, String ipAdress) {
		this.url = url;
		this.password = password;
		this.ipAdress = ipAdress;
	}
	/**
	 * Methode um einen Ping auf die Fritzbox zu senden. Wenn keine Antwort kommt -> Fehlermeldung
	 * 
	 */
	public void isFritzBoxRechable(){
		 try{
			 InetAddress address = InetAddress.getByName(ipAdress);
			 if(!address.isReachable(5000)) {
				 System.out.println("FritzBox nicht erreichbar! "); 
			 }
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Verbindungstest zur FritzBox fehlgeschlagen! " + e.getMessage());
	        }
		 
	}
	/**
	 * Das Login über die http Seite
	 * Für das Login der FritzBox muss eine Challenge, welche von der Startseite
	 * zurückgeschickt wird behandelt werden. Über eine MD5 Encryption schicken wir
	 * die Challenge inklusive Passwort an die FritzBox zurück Ist diese Challenge
	 * mit Passwort korrekt erhalten wir eine gültige SID
	 * @throws Exception 
	 * 
	 */
	public void fritzBoxLogin() {
		String response;
		response = HttpUtil.getURLasString(url + "login_sid.lua");
		final String challenge = response.substring(response.indexOf("<Challenge>") + 11,
				response.indexOf("</Challenge>"));
		/*
		 *  ***** Testing *****
		 *	Die Challenge aus dem String vom Browser lesen
		 *	System.out.println(challenge);
		 *
		 */
		final String stringToHash = challenge + "-" + password;
		String stringToHashUTF16;
		try {
			stringToHashUTF16 = new String(stringToHash.getBytes("UTF-16LE"), "UTF-8");

			final String md5 = Encryption.hashMD5(stringToHashUTF16);
			final String md5_challenge = challenge + "-" + md5;

			String urlWithChallenge = HttpUtil.getURLasString(url + "login_sid.lua?user=&response=" + md5_challenge);

			// get challenge challenge
			final String sid = urlWithChallenge.substring(urlWithChallenge.indexOf("<SID>") + 5,
					urlWithChallenge.indexOf("</SID>"));
			this.sid = sid;
			// System.out.println(sid);
		} catch (final UnsupportedEncodingException ue) {
			// will never appear
			ue.printStackTrace();

		} catch (final NoSuchAlgorithmException nae) {
			// will never appear
			nae.printStackTrace();
		}
		
	}

	/**
	 * Mit der Jsoup Library und HttpUtil gelande ich an die HTML-Tags der Fritzbox und bekomme sie als Document
	 * 
	 * @return response
	 * 
	 * Quelle der Codestruktur: https://stackoverflow.com/questions/8222118/using-jsoup-to-extract-html-table-contents
	 * Quelle der Codestruktur: https://jsoup.org/cookbook/extracting-data/attributes-text-html
	 * 
	 */
	public Document readDSLDataAsDocument() {
		
		Document response = null;
		response = HttpUtil.getURLasDocument(url + "internet/dsl_stats_tab.lua?sid=" + sid);
		return response;
		/*
		 *  ***** Testing *****
		 *	Ganze Tabelle mit Informationen als in Elementen speichern und anschliessend ausgeben
		 *	Elements dslData = (Elements) response.getElementsByClass("zebra");
		 *	System.out.println(dslData);
		 *
		 */

	}
				
}
