package business;


import java.util.Arrays;
import java.util.StringJoiner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DSLData {

	
	// Methode um Array als String an File Handler weiterzugeben
	// https://stackoverflow.com/questions/15618823/convert-two-dimensional-array-to-string
	public String castDSLDataToString(String[][] twoDimArray) {
		
		
		StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
		for (String[] row : twoDimArray) {
		    stringJoiner.add(Arrays.toString(row));
		}
		String result = stringJoiner.toString();
		
		return result;
//		int row;
//		int column;
//		for (row = 0; row < 15; row++)  {
//			System.out.println();
//			for (column = 0; column < 4; column++){
//				String res = twoDimArray[column][row];
//				System.out.print("[" + res + "]  ");
//			}
//		}
	}
	
	// speichern der DSL Daten als Tabelle in 2 dimensionalem Array
	public String [][] saveDSLDataInTwoDimArray(Document response) {

		// Zweidimensionales Array erstellen um Tabelle der DSL-Daten abzubilden
		String[][] twoDimArray = new String[4][15];
		int row;
		int column;

		for (column = 0; column < 4; column++) {
			for (row = 0; row < 15; row++) {
				switch (column) {
				case 0:

					Elements dslDataColumn1 = (Elements) response.getElementsByClass("c1");
					twoDimArray[column][row] = dslDataColumn1.get(row).html().toString();
					break;

				case 1:

					Elements dslDataColumn2 = (Elements) response.getElementsByClass("c2");
					twoDimArray[column][row] = dslDataColumn2.get(row).html().toString();
					break;

				case 2:
					if (row == 0) {
						Elements dslDataColumn3 = (Elements) response.getElementsByClass("c3h");
						twoDimArray[column][row] = dslDataColumn3.get(row).html().toString();
					} else {
						Elements dslDataColumn3 = (Elements) response.getElementsByClass("c3");
						twoDimArray[column][row] = dslDataColumn3.get(row - 1).html().toString();
					}

					break;

				case 3:
					if (row == 0) {
						Elements dslDataColumn4 = (Elements) response.getElementsByClass("c4h");
						twoDimArray[column][row] = dslDataColumn4.get(row).html().toString();
					} else {
						Elements dslDataColumn4 = (Elements) response.getElementsByClass("c4");
						twoDimArray[column][row] = dslDataColumn4.get(row - 1).html().toString();
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

	public void writeDSLData (String[][] twoDimArray) {
		
		FileHandler filehandler = new FileHandler();
		filehandler.writeDataInFile(castDSLDataToString(twoDimArray));
		
		// Pfad und File überprüfen
		//System.out.println(path);
		//Path pathFile = filehandler.createNewFile(path, "test");
		//System.out.println(pathFile);
		//filehandler.readDataInFile();
	}


	
}