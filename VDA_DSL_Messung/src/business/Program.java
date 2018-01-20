package business;



import java.util.StringJoiner;

import org.jsoup.nodes.Document;


public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
				// 1. Verbindung herstellen
				
				// Fritzbox-Connector aufrufen und URL sowie Passwort mitgeben
				FritzBoxConnector fbCon = new FritzBoxConnector("http://192.168.147.200/", "admin");
				// Einloggen durch erolgreiche Challenge der Fritzbox
				fbCon.fritzBoxLogin();
				// DSL Daten aus HTML lesen und in Document abspeichern
				Document docDSLData = fbCon.readDSLDataAsDocument();
				
				// 2. DSL Daten Verarbeitung
				
				// DSL Data als Hilfsklasse aufrufen und Document in Array abspeichern
				DSLData dslData = new DSLData();
				String [] [] twoDimArray = dslData.saveDSLDataInTwoDimArray(docDSLData);
				
				// 3. Ausgabe der DSL Daten über DSL Data Klasse und FileHandler
				
				dslData.writeDSLData(twoDimArray);
		
				
		
	}

}
