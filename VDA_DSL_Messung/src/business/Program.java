package business;

import java.nio.file.Path;

import util.HttpUtil;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			
				FritzBoxConnector fbCon = new FritzBoxConnector("http://192.168.147.200/", "admin");
				fbCon.fritzBoxLogin();
				fbCon.readDSLData();
//				
//				Test Filehandler
//				FileHandler filehandler = new FileHandler();
//				filehandler.readDataInFile();
//				filehandler.writeDataInFile("lalalala Test Daten schreiben");
//				Path path = filehandler.createDirectory();
//				System.out.println(path);
//				Path pathFile = filehandler.createNewFile(path, "test");
//				System.out.println(pathFile);
		
	}

}
