package util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;


//https://stackoverflow.com/questions/4755647/how-to-access-https-sites-in-java

public class MyAuthenticator extends Authenticator {
	// Standard Username und Passwort der Fritz!Box
	String username = "admin";
	String password = "admin";
	
	// Einfügen des Usernamens und Passwort
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password.toCharArray());
	}

}
