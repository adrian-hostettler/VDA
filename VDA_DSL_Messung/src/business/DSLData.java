package business;


import java.util.Arrays;
import java.util.StringJoiner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * @author Adrian Hostettler
 * 
 * @version 1.0
 * 
 * @category business
 *
 */
public class DSLData {
	
	/**
	 * Wandelt das Array mit Hilfe einer for each Schleife in einen String um
	 * 
	 * @param twoDimArray	Übergabe des zweidimensionalen Array
	 * @return Das zweidimensionale Array als String
	 * 
	 * Quelle der Codestruktur: https://stackoverflow.com/questions/15618823/convert-two-dimensional-array-to-string
	 */
	public String castDSLDataToString(String[][] twoDimArray) {
		
		
		StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
		for (String[] row : twoDimArray) {
		    stringJoiner.add(Arrays.toString(row));
		}
		String result = stringJoiner.toString();
		
		return result;

	}
	

	/**
	 * Nimmt Object und füllt es mit Iteration in das zweidimensionale Array ab
	 * 
	 * @param response
	 * @return twoDimArray
	 */
	public String [][] saveDSLDataInTwoDimArray(Document response) {

		// Zweidimensionales Array erstellen um Tabelle der DSL-Daten abzubilden
		String[][] twoDimArray = new String[15][4];
		int row;
		int column;

		for (row = 0; row < 15; row++) {
			for (column = 0; column < 4; column++) {
				switch (column) {
				case 0:

					Elements dslDataColumn1 = (Elements) response.getElementsByClass("c1");
					twoDimArray[row][column] = dslDataColumn1.get(row).html().toString();
					break;

				case 1:

					Elements dslDataColumn2 = (Elements) response.getElementsByClass("c2");
					twoDimArray[row][column] = dslDataColumn2.get(row).html().toString();
					break;

				case 2:
					if (row == 0) {
						Elements dslDataColumn3 = (Elements) response.getElementsByClass("c3h");
						twoDimArray[row][column] = dslDataColumn3.get(row).html().toString();
					} else {
						Elements dslDataColumn3 = (Elements) response.getElementsByClass("c3");
						twoDimArray[row][column] = dslDataColumn3.get(row - 1).html().toString();
					}

					break;

				case 3:
					if (row == 0) {
						Elements dslDataColumn4 = (Elements) response.getElementsByClass("c4h");
						twoDimArray[row][column] = dslDataColumn4.get(row).html().toString();
					} else {
						Elements dslDataColumn4 = (Elements) response.getElementsByClass("c4");
						twoDimArray[row][column] = dslDataColumn4.get(row - 1).html().toString();
					}

					break;

				default:
					System.out.println("Nichts vom HTML in TwoDimArray geschrieben");
					break;
				}
			}
		}
		return twoDimArray;
		
	}

	/**
	 * Übergibt zweidimensionales Array wandelt es zu einem String und übergibt es dem Filehandler
	 * 
	 * @param twoDimArray
	 */
	public void writeDSLData (String [] [] twoDimArray) {
		
		FileHandler filehandler = new FileHandler();
		filehandler.writeDataInFile(castDSLDataToString(twoDimArray));
		
		/*
		 *  ***** Testing *****
		 *	Pfad und File überprüfen
		 *	System.out.println(path);
		 *	Path pathFile = filehandler.createNewFile(path, "test");
		 *	System.out.println(pathFile);
	     *	filehandler.readDataInFile();
	     *
		 */
		
	}


	
}