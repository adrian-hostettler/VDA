package business;




import org.jsoup.nodes.Document;
/**
 * 
 * @author Adrian Hostettler
 * 
 * @version 1.0
 * 
 * @category business
 *
 */

public class Program {
	
	/**
	 * Main Methode für den Programmablauf
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
				System.out.println("Programm Start!");
				
				System.out.println("1. Verbindung");
				// 1. Verbindung herstellen
					
				// Fritzbox-Connector aufrufen und URL sowie Passwort mitgeben
				FritzBoxConnector fbCon = new FritzBoxConnector("http://192.168.147.200/", "admin", "192.168.147.200");
				// Verbindung zu FritzBox mittels Ping überprüfen
				fbCon.isFritzBoxRechable();
				// Einloggen durch erolgreiche Challenge der Fritzbox
				fbCon.fritzBoxLogin();
				
				System.out.println("2. Verarbeitung");
				// 2. DSL Daten Verarbeitung
				
				// DSL Daten aus HTML lesen und in Document abspeichern
				Document docDSLData = fbCon.readDSLDataAsDocument();
				// DSL Data als Hilfsklasse aufrufen und Document in Array abspeichern
				DSLData dslData = new DSLData();
				String [] [] twoDimArray = dslData.saveDSLDataInTwoDimArray(docDSLData);
				
				System.out.println("3. Ausgabe");
				// 3. Ausgabe der DSL Daten über DSLData Klasse und FileHandler
				// Schreiben ins Textfile
				dslData.writeDSLData(twoDimArray);
				
				System.out.println("Programm Ende!");
		
	}

}
