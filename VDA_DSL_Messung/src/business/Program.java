package business;

import util.HttpUtil;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			
				FritzBoxConnector fbCon = new FritzBoxConnector("http://192.168.147.200/", "admin");
				fbCon.fritzBoxLogin();
				fbCon.readDSLData();
			
		
	}

}
