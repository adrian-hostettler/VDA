package business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
/**
 * 
 * @author Adrian Hostettler
 * 
 * @version 1.0
 * 
 * @category business
 * 
 * Quelle der Codestruktur: Modul Java 2 Abend 4 - Java I/O
 *
 */
public class FileHandler {

	private static Path pathReportFile = Paths.get("C:" + File.separator + "vda" + File.separator + "DSL_Data.txt");
	
	public Path createDirectory(){
		
		Path pathReport = Paths.get("C:" + File.separator + "VDA_Report");
		try {
			if (!Files.exists(pathReport)) {
				Files.createDirectory(pathReport);
			}
			
		}
		catch(IOException ioe) {
			System.out.println("Probleme beim Verzeichnis erstellen: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		
		
		return pathReport;
	}
	
	public Path createNewFile(Path dir, String fileName) {
		
		Path pathReportFile = dir.resolve(fileName + ".txt");
		try {
			if (!Files.exists(pathReportFile)) {
				Files.createFile(pathReportFile);
			}
			}
		catch (IOException ioe) {
			System.out.println("Problem beim File erstellen: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		
		return pathReportFile;
	}
	
	public void writeDataInFile(String dslDataAsString) {
		try {
			Path pathReportDir = createDirectory();
			Path pathReportFile = createNewFile(pathReportDir, "Report");
			String pathReportFileAsString = (String) pathReportFile.toString();
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pathReportFileAsString)));
			pw.println(dslDataAsString);
			pw.close();
		}
		catch (IOException ioe) {
			System.out.println("Problem beim Daten in File schreiben: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		
	
	
	}
	
	public void readDataInFile() {
		
		try {
			BufferedReader br = Files.newBufferedReader(pathReportFile, StandardCharsets.UTF_8);
			String line;
			
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		catch (IOException ioe) {
			System.out.println("Problem beim Lesen von Daten" + ioe.getMessage());
			ioe.printStackTrace();
		}
		
		
	}
	
}
